/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

/**
 * Interface that extends {@link PipelineBuilder} and adds inbound endpoints.
 *
 * @author porcelli
 */
public interface FlowBuilder extends PipelineBuilder<FlowBuilder> {

    /**
     * Creates an inbound endpoint based on given uri parameter.
     *
     * @param uri the inbound endpoint uri
     * @return the pipeline builder
     * @throws IllegalArgumentException if {@code uri} is null or empty
     * @see org.mule.api.endpoint.InboundEndpoint
     */
    PipelineBuilder<FlowBuilder> from(String uri) throws IllegalArgumentException;

//    <C extends Connector> PipelineBuilder<FlowBuilder> from(String uri, C connector) throws IllegalArgumentException, NullPointerException;
//
//    PipelineBuilder<FlowBuilder> from(String uri, String connectorName) throws IllegalArgumentException;

    /**
     * Creates an inbound endpoint based on uri parameter using given exchange pattern.
     *
     * @param uri     the inbound endpoint uri
     * @param pattern the exchange pattern, null is allowed
     * @return the pipeline builder
     * @throws IllegalArgumentException if {@code uri} is null or empty
     * @see org.mule.api.endpoint.InboundEndpoint
     */
    PipelineBuilder<FlowBuilder> from(String uri, ExchangePattern pattern) throws IllegalArgumentException;

//    <C extends Connector> PipelineBuilder<FlowBuilder> from(String uri, ExchangePattern pattern, C connector) throws IllegalArgumentException, NullPointerException;
//
//    PipelineBuilder<FlowBuilder> from(String uri, ExchangePattern pattern, String connectorName) throws IllegalArgumentException;

}