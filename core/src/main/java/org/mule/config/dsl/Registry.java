/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.construct.FlowConstruct;
import org.mule.config.dsl.internal.FlowBuilderImpl;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class Registry {

    private final Map<String, FlowBuilderImpl> flows;
    private final MuleContext muleContext;
    private final Injector injector;
    private final Properties properties;

    public Registry(MuleContext muleContext) {
        this.muleContext = checkNotNull(muleContext, "muleContext");
        this.flows = new HashMap<String, FlowBuilderImpl>();
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

    public void build(Injector injector) {
        PropertyPlaceholder placeholder = new PropertyPlaceholder(properties);
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
