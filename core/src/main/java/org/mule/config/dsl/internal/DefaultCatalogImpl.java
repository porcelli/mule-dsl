/*
 * ---------------------------------------------------------------------------
 *  Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import org.mule.config.dsl.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static java.util.Collections.unmodifiableMap;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotEmpty;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

/**
 * Default implementation of {@link Catalog} interface.
 *
 * @author porcelli
 */
public class DefaultCatalogImpl implements Catalog {

    private final Properties properties;
    private final Map<String, FlowBuilderImpl> flows;
    private final Map<String, TransformerBuilderImpl> globalTransformers;
    private final Map<String, FilterBuilderImpl> globalFilters;
    private final PropertyPlaceholder placeholder;

    public DefaultCatalogImpl() {
        this.flows = new HashMap<String, FlowBuilderImpl>();
        this.globalTransformers = new HashMap<String, TransformerBuilderImpl>();
        this.globalFilters = new HashMap<String, FilterBuilderImpl>();
        this.properties = new Properties();
        this.placeholder = new PropertyPlaceholderImpl(properties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FlowBuilder newFlow(final String flowName) throws IllegalArgumentException {
        checkNotEmpty(flowName, "flowName");
        if (flows.containsKey(flowName)) {
            throw new IllegalArgumentException("Flow name already registered.");
        }

        final FlowBuilderImpl fb = new FlowBuilderImpl(flowName);
        flows.put(flowName, fb);

        return fb;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransformerBuilder newTransformer(final String transformerName) throws IllegalArgumentException {
        checkNotEmpty(transformerName, "transformerName");

        if (globalTransformers.containsKey(transformerName)) {
            throw new IllegalArgumentException("Global transformer name already registered.");
        }

        final TransformerBuilderImpl tb = new TransformerBuilderImpl(transformerName);
        globalTransformers.put(transformerName, tb);

        return tb;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FilterBuilder newFilter(final String filterName) throws IllegalArgumentException {
        checkNotEmpty(filterName, "filterName");

        if (globalFilters.containsKey(filterName)) {
            throw new IllegalArgumentException("Global filter name already registered.");
        }

        final FilterBuilderImpl fb = new FilterBuilderImpl(filterName);
        globalFilters.put(filterName, fb);

        return fb;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addToPropertyPlaceholder(final Properties properties) throws NullPointerException {
        checkNotNull(properties, "properties");
        this.properties.putAll(properties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PropertyPlaceholder getPropertyPlaceholder() {
        return placeholder;
    }

    /**
     * Returns an unmodifiable map of registered flow builders
     *
     * @return the unmodifiable map of registered flow builders
     * @see java.util.Collections#unmodifiableMap(java.util.Map)
     * @see FlowBuilderImpl
     */
    Map<String, FlowBuilderImpl> getFlows() {
        return unmodifiableMap(flows);
    }

    /**
     * Returns an unmodifiable map of registered global transformer builders
     *
     * @return the unmodifiable map of registered global transformer builders
     * @see java.util.Collections#unmodifiableMap(java.util.Map)
     * @see TransformerBuilderImpl
     */
    Map<String, TransformerBuilderImpl> getGlobalTransformers() {
        return unmodifiableMap(globalTransformers);
    }

    /**
     * Returns an unmodifiable map of registered global filter builders
     *
     * @return the unmodifiable map of registered global filter builders
     * @see java.util.Collections#unmodifiableMap(java.util.Map)
     * @see FilterBuilderImpl
     */
    Map<String, FilterBuilderImpl> getGlobalFilters() {
        return unmodifiableMap(globalFilters);
    }

}
