/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.mule.api.transport.Connector;

/**
 * Interface that extends {@link PipelineBuilder} and adds inbound endpoints.
 *
 * @author porcelli
 */
public interface FlowBuilder extends PipelineBuilder<FlowBuilder> {

    /**
     * Creates an URI based inbound endpoint.
     *
     * @param uri the inbound endpoint uri
     * @return the pipeline builder
     * @throws IllegalArgumentException if {@code uri} is null or empty
     * @see org.mule.api.endpoint.InboundEndpoint
     */
    PipelineBuilder<FlowBuilder> from(String uri) throws IllegalArgumentException;

    /**
     * Creates an URI based inbound endpoint using given connector.
     *
     * @param uri       the inbound endpoint uri
     * @param connector the connector to be used
     * @return the pipeline builder
     * @throws IllegalArgumentException if {@code uri} is null or empty
     * @throws NullPointerException     if {@code connector} is null
     */
    <C extends Connector> PipelineBuilder<FlowBuilder> from(String uri, C connector) throws IllegalArgumentException, NullPointerException;

    /**
     * Creates an URI based inbound endpoint using given global connector reference.
     *
     * @param uri           the inbound endpoint uri
     * @param connectorName the global connector name
     * @return the pipeline builder
     * @throws IllegalArgumentException if {@code uri} or {@code connectorName} are null or empty
     */
    PipelineBuilder<FlowBuilder> from(String uri, String connectorName) throws IllegalArgumentException;

    /**
     * Creates an URI based inbound endpoint using given exchange pattern.
     *
     * @param uri     the inbound endpoint uri
     * @param pattern the exchange pattern, null is allowed
     * @return the pipeline builder
     * @throws IllegalArgumentException if {@code uri} is null or empty
     * @see org.mule.api.endpoint.InboundEndpoint
     */
    PipelineBuilder<FlowBuilder> from(String uri, ExchangePattern pattern) throws IllegalArgumentException;

    /**
     * Creates an URI based inbound endpoint using given exchange pattern and connector.
     *
     * @param uri       the inbound endpoint uri
     * @param pattern   the exchange pattern, null is allowed
     * @param connector the connector to be used
     * @return the pipeline builder
     * @throws IllegalArgumentException if {@code uri} is null or empty
     * @throws NullPointerException     if {@code connector} is null
     */
    <C extends Connector> PipelineBuilder<FlowBuilder> from(String uri, ExchangePattern pattern, C connector) throws IllegalArgumentException, NullPointerException;

    /**
     * Creates an URI based inbound endpoint using given exchange pattern and global connector reference.
     *
     * @param uri           the inbound endpoint uri
     * @param pattern       the exchange pattern, null is allowed
     * @param connectorName the global connector name
     * @return the pipeline builder
     * @throws IllegalArgumentException if {@code uri} or {@code connectorName} are null or empty
     */
    PipelineBuilder<FlowBuilder> from(String uri, ExchangePattern pattern, String connectorName) throws IllegalArgumentException;

}