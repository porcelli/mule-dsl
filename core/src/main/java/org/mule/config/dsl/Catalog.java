/*
 * ---------------------------------------------------------------------------
 *  Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 */

package org.mule.config.dsl;

import java.util.Properties;

/**
 * Collects configuration information (primarily <i>flows</i>) which will be
 * used to create a {@link org.mule.api.MuleContext}. Mule DSL provides this object to your
 * application's {@link Module} implementors so they may each contribute
 * their own flows and other registrations.
 *
 * @author porcelli
 */
public interface Catalog {

    /**
     * Add {@code properties} to the central property placeholder resolver.
     *
     * @param properties property set to be added to property placeholder resolver.
     * @throws NullPointerException if {@code properties} param is null
     */
    void addToPropertyPlaceholder(final Properties properties) throws NullPointerException;

    /**
     * Getter of property placeholder resolver.
     *
     * @return the property placeholder resolver
     */
    PropertyPlaceholder getPropertyPlaceholder();

    /**
     * Register the given compoenent on DSL catalog. Registered components are available
     * on MuleContext after its configuraton.
     *
     * @param name name that identifies uniquely the component
     * @throws IllegalArgumentException if {@code name} param is null, empty or already exists in catalog
     * @throws NullPointerException     if {@code obj} param is null
     */
    <O> void newRegistry(String name, O obj) throws IllegalArgumentException, NullPointerException;

    /**
     * Creates a global flow entry into catalog using {@code flowName} as
     * an unique identifier, returning an instance of a {@link FlowBuilder} that
     * should be configured.
     *
     * @param flowName flow name that identifies uniquely the flow
     * @return the flow builder to be configured
     * @throws IllegalArgumentException if {@code flowName} param is null, empty or already exists in catalog
     * @see FlowBuilder
     * @see org.mule.api.construct.FlowConstruct
     */
    FlowBuilder newFlow(final String flowName) throws IllegalArgumentException;

    /**
     * Creates a global transformer entry into catalog using {@code transformerName} as
     * an unique identifier, returning an instance of a {@link TransformerBuilder} that
     * should be configured.
     *
     * @param transformerName transformer name that identifies uniquely the global transformer
     * @return the tranformer builder to be configured
     * @throws IllegalArgumentException if {@code transformerName} param is null, empty or already exists in catalog
     * @see TransformerBuilder
     * @see org.mule.api.transformer.Transformer
     */
    TransformerBuilder newTransformer(final String transformerName) throws IllegalArgumentException;

    /**
     * Creates a global filter entry into catalog using {@code filterName} as
     * an unique identifier, returning an instance of a {@link FilterBuilder} that
     * should be configured.
     *
     * @param filterName filter name that identifies uniquely the global filter
     * @return the filter builder to be configured
     * @throws IllegalArgumentException if {@code filterName} param is null, empty or already exists in catalog
     * @see FilterBuilder
     * @see org.mule.routing.MessageFilter
     */
    FilterBuilder newFilter(final String filterName) throws IllegalArgumentException;

}
