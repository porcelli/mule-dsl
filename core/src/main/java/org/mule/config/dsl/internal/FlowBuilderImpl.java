/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import org.mule.api.MuleContext;
import org.mule.api.exception.MessagingExceptionHandler;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.transport.Connector;
import org.mule.config.dsl.*;
import org.mule.construct.SimpleFlowConstruct;

import java.util.List;

import static org.mule.config.dsl.internal.util.Preconditions.*;

/**
 * Internal implementation of {@link FlowBuilder} and {@link PollBuilder} interfaces that, based on its internal state,
 * builds a {@link SimpleFlowConstruct}.
 *
 * @author porcelli
 * @see org.mule.config.dsl.AbstractModule#flow()
 * @see org.mule.config.dsl.AbstractModule#flow(String)
 */
public class FlowBuilderImpl extends PipelineBuilderImpl<FlowPipeline> implements FlowNameAware, FlowPipeline, FlowBuilder, FlowBuilder.PollBuilder, Builder<SimpleFlowConstruct> {

    private InboundEndpointBuilderImpl inboundEndpointBuilder = null;
    private PollBuilderImpl pollBuilder = null;

    /**
     * @param name the flow name
     * @throws IllegalArgumentException if {@code name} param is empty or null
     */
    public FlowBuilderImpl(final String name) throws IllegalArgumentException {
        super(null, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PipelineBuilder<FlowPipeline> from(final String uri) throws IllegalArgumentException {
        return from(uri, null, (String) null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PipelineBuilder<FlowPipeline> from(String uri, String connectorName) throws IllegalArgumentException {
        checkNotEmpty(uri, "uri");
        checkNotEmpty(connectorName, "connectorName");
        return from(uri, null, connectorName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <C extends Connector> PipelineBuilder<FlowPipeline> from(String uri, C connector) throws IllegalArgumentException, NullPointerException {
        checkNotNull(connector, "connector");
        return from(uri, null, connector);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public PipelineBuilder<FlowPipeline> from(final String uri, final ExchangePattern pattern) throws IllegalArgumentException {
        return from(uri, pattern, (String) null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PipelineBuilder<FlowPipeline> from(String uri, ExchangePattern pattern, String connectorName) throws IllegalArgumentException {
        checkNotEmpty(uri, "uri");
        this.inboundEndpointBuilder = new InboundEndpointBuilderImpl(uri, pattern, connectorName);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <C extends Connector> PipelineBuilder<FlowPipeline> from(String uri, ExchangePattern pattern, C connector) throws IllegalArgumentException, NullPointerException {
        checkNotEmpty(uri, "uri");
        this.inboundEndpointBuilder = new InboundEndpointBuilderImpl(uri, pattern, connector);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <MP extends MessageProcessor> PollBuilder poll(Class<MP> clazz) throws NullPointerException {
        checkNotNull(clazz, "clazz");
        this.pollBuilder = new PollBuilderImpl(clazz);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <MP extends MessageProcessor> PollBuilder poll(MP obj) throws NullPointerException {
        checkNotNull(obj, "clazz");
        this.pollBuilder = new PollBuilderImpl(obj);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PollBuilder poll(FlowDefinition flow) throws NullPointerException {
        checkNotNull(flow, "flow");
        this.pollBuilder = new PollBuilderImpl(((FlowBuilderImpl) flow).getFlowName());
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PollBuilder poll(String flowName) throws IllegalArgumentException {
        checkNotEmpty(flowName, "flowName");
        this.pollBuilder = new PollBuilderImpl(flowName);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PipelineBuilder<FlowPipeline> every(long duration, TimePeriod period) throws IllegalArgumentException, NullPointerException {
        checkState(duration > 0, "Duration should be higher than zero.");
        checkNotNull(period, "period");

        this.pollBuilder.setFrequency(duration, period);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SimpleFlowConstruct build(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        final SimpleFlowConstruct flow = new SimpleFlowConstruct(getFlowName(), muleContext);
        if (inboundEndpointBuilder != null) {
            flow.setMessageSource(inboundEndpointBuilder.build(muleContext, placeholder));
        } else if (pollBuilder != null) {
            flow.setMessageSource(pollBuilder.build(muleContext, placeholder));
        }

        if (!isBuilderListEmpty()) {
            flow.setMessageProcessors(buildMessageProcessorList(muleContext, placeholder));
        }

        if (!isExceptionBuilderListEmpty()) {
            List<MessagingExceptionHandler> exceptionList = buildExceptionHandlerList(muleContext, placeholder);
            flow.setExceptionListener(exceptionList.get(0));
        }

        return flow;
    }
}
