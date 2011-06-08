/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.transformer.Transformer;
import org.mule.config.dsl.FilterBuilder;
import org.mule.config.dsl.FlowBuilder;
import org.mule.config.dsl.TransformerBuilder;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.routing.MessageFilter;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class Registry {

    private final MuleContext muleContext;
    private final Injector injector;
    private final Properties properties;
    private final Map<String, FlowBuilderImpl> flows;
    private final Map<String, TransformerBuilderImpl> globalTransformers;
    private final Map<String, FilterBuilderImpl> globalFilters;

    public Registry(MuleContext muleContext) {
        this.muleContext = checkNotNull(muleContext, "muleContext");
        this.flows = new HashMap<String, FlowBuilderImpl>();
        this.globalTransformers = new HashMap<String, TransformerBuilderImpl>();
        this.globalFilters = new HashMap<String, FilterBuilderImpl>();
        this.injector = Guice.createInjector();
        this.properties = new Properties();
    }

    public FlowBuilder flow(String flowName) {
        if (flows.containsKey(flowName)) {
            throw new IllegalArgumentException();
        }

        FlowBuilderImpl fb = new FlowBuilderImpl(flowName);
        flows.put(flowName, fb);

        return fb;
    }

    public TransformerBuilder transformer(String transformerName) {
        if (globalTransformers.containsKey(transformerName)) {
            throw new IllegalArgumentException();
        }

        TransformerBuilderImpl tb = new TransformerBuilderImpl(transformerName);
        globalTransformers.put(transformerName, tb);

        return tb;
    }

    public FilterBuilder filter(String filterName) {
        if (globalFilters.containsKey(filterName)) {
            throw new IllegalArgumentException();
        }

        FilterBuilderImpl fb = new FilterBuilderImpl(filterName);
        globalFilters.put(filterName, fb);

        return fb;
    }

    public void registerPropertyResolver(InputStream inputStream) {
        checkNotNull(inputStream, "inputStream");
        Properties fileProperties = new Properties();
        try {
            fileProperties.load(inputStream);
            properties.putAll(fileProperties);
        } catch (IOException e) {
            //TODO handle here
            throw new RuntimeException(e);
        }
    }

    public void registerPropertyResolver(Map<String, String> properties) {
        checkNotNull(properties, "properties");
        this.properties.putAll(properties);
    }

    public void registerPropertyResolver(Properties properties) {
        checkNotNull(properties, "properties");
        this.properties.putAll(properties);
    }


    public void build(Injector injector) {
        PropertyPlaceholder placeholder = new PropertyPlaceholder(properties);

        for (TransformerBuilderImpl transformerBuilder : globalTransformers.values()) {
            Transformer transformer = transformerBuilder.build(muleContext, injector, placeholder);
            if (transformer != null) {
                try {
                    transformer.setName(transformerBuilder.getName());
                    muleContext.getRegistry().registerTransformer(transformer);
                } catch (MuleException e) {
                    //TODO handle
                    throw new RuntimeException(e);
                }
            }
        }

        for (FilterBuilderImpl filterBuilder : globalFilters.values()) {
            MessageFilter filter = filterBuilder.build(muleContext, injector, placeholder);
            if (filter != null) {
                try {
                    muleContext.getRegistry().registerObject(filterBuilder.getName(), filter);
                } catch (MuleException e) {
                    //TODO handle
                    throw new RuntimeException(e);
                }
            }
        }

        for (FlowBuilderImpl activeFlowBuilder : flows.values()) {
            FlowConstruct flow = activeFlowBuilder.build(muleContext, injector, placeholder);
            if (flow != null) {
                try {
                    muleContext.getRegistry().registerFlowConstruct(flow);
                } catch (MuleException e) {
                    //TODO handle
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
