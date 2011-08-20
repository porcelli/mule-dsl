/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.mule.api.processor.MessageProcessor;
import org.mule.api.transport.Connector;

/**
 * Interface that extends {@link PipelineBuilder} and adds inbound endpoints.
 *
 * @author porcelli
 */
public interface FlowBuilder extends PipelineBuilder<FlowPipeline> {

    /**
     * Creates an URI based inbound endpoint.
     *
     * @param uri the inbound endpoint uri
     * @return the pipeline builder
     * @throws IllegalArgumentException if {@code uri} is null or empty
     * @see org.mule.api.endpoint.InboundEndpoint
     */
    PipelineBuilder<FlowPipeline> from(String uri) throws IllegalArgumentException;

    /**
     * Creates an URI based inbound endpoint using given connector.
     *
     * @param uri       the inbound endpoint uri
     * @param connector the connector to be used
     * @return the pipeline builder
     * @throws IllegalArgumentException if {@code uri} is null or empty
     * @throws NullPointerException     if {@code connector} is null
     */
    <C extends Connector> PipelineBuilder<FlowPipeline> from(String uri, C connector) throws IllegalArgumentException, NullPointerException;

    /**
     * Creates an URI based inbound endpoint using given global connector reference.
     *
     * @param uri           the inbound endpoint uri
     * @param connectorName the global connector name
     * @return the pipeline builder
     * @throws IllegalArgumentException if {@code uri} or {@code connectorName} are null or empty
     */
    PipelineBuilder<FlowPipeline> from(String uri, String connectorName) throws IllegalArgumentException;

    /**
     * Creates an URI based inbound endpoint using given exchange pattern.
     *
     * @param uri     the inbound endpoint uri
     * @param pattern the exchange pattern, null is allowed
     * @return the pipeline builder
     * @throws IllegalArgumentException if {@code uri} is null or empty
     * @see org.mule.api.endpoint.InboundEndpoint
     */
    PipelineBuilder<FlowPipeline> from(String uri, ExchangePattern pattern) throws IllegalArgumentException;

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
    <C extends Connector> PipelineBuilder<FlowPipeline> from(String uri, ExchangePattern pattern, C connector) throws IllegalArgumentException, NullPointerException;

    /**
     * Creates an URI based inbound endpoint using given exchange pattern and global connector reference.
     *
     * @param uri           the inbound endpoint uri
     * @param pattern       the exchange pattern, null is allowed
     * @param connectorName the global connector name
     * @return the pipeline builder
     * @throws IllegalArgumentException if {@code uri} or {@code connectorName} are null or empty
     */
    PipelineBuilder<FlowPipeline> from(String uri, ExchangePattern pattern, String connectorName) throws IllegalArgumentException;

    /**
     * Creates an inbound endpoint that polls given type.
     *
     * @param clazz the type to be polled, Mule will instantiate an object at runtime
     * @return the poll builder
     * @throws NullPointerException if {@code clazz} param is null
     */
    <MP extends MessageProcessor> PollBuilder poll(Class<MP> clazz) throws NullPointerException;

    /**
     * Creates an inbound endpoint that polls given object.
     *
     * @param obj the message processor instance to be polled
     * @return the poll builder
     * @throws NullPointerException if {@code obj} param is null
     */
    <MP extends MessageProcessor> PollBuilder poll(MP obj) throws NullPointerException;

    /**
     * Creates an inbound endpoint that polls given flow.
     *
     * @param flow the flow to be polled
     * @return the poll builder
     * @throws NullPointerException if {@code flow} param is null
     */
    PollBuilder poll(FlowDefinition flow) throws NullPointerException;

    /**
     * Creates an inbound endpoint that polls given flow.
     *
     * @param flowName the flow name to be polled
     * @return the poll builder
     * @throws NullPointerException if {@code flowName} param is null or empty
     */
    PollBuilder poll(String flowName) throws IllegalArgumentException;

    /**
     * Interface that extends {@link PipelineBuilder} and adds a poll related config method.
     *
     * @author porcelli
     */
    public static interface PollBuilder extends PipelineBuilder<FlowPipeline> {

        /**
         * Sets the polling frequency based on given params.
         *
         * @param duration the duration
         * @param period   the time period unit
         * @return the pipeline builder
         * @throws IllegalArgumentException if {@code duration} param is less than or equal to zero
         * @throws NullPointerException     if {@code period} param is null
         */
        PipelineBuilder<FlowPipeline> every(long duration, TimePeriod period) throws IllegalArgumentException, NullPointerException;
    }
}