/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import com.google.inject.Injector;
import org.mule.api.MuleContext;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.source.MessageSource;
import org.mule.config.dsl.internal.EndpointBuilderImpl;
import org.mule.config.dsl.internal.PipelineBuilderImpl;
import org.mule.construct.SimpleFlowConstruct;

public class FlowBuilder extends PipelineBuilderImpl {

    private final SimpleFlowConstruct flow;
    private EndpointBuilderImpl.InboundEndpointBuilderImpl inboundEndpointBuilder;

    public FlowBuilder(String name, MuleContext muleContext) {
        super(muleContext, null);
        this.flow = new SimpleFlowConstruct(name, muleContext);
    }

    EndPointBuilder.InboundEndpointBuilder from(String uri) {
        this.inboundEndpointBuilder = new EndpointBuilderImpl.InboundEndpointBuilderImpl(this, this.flow.getMuleContext(), uri);
        return inboundEndpointBuilder;
    }

    FlowConstruct build(Injector injector) {
        if (inboundEndpointBuilder != null) {
            flow.setMessageSource((MessageSource) inboundEndpointBuilder.build(injector));
        }
        if (!isProcessorListEmpty()) {
            flow.setMessageProcessors(buildProcessorList(injector));
        }
        return flow;
    }
}
