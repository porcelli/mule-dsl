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
import org.mule.config.dsl.internal.InboundEndpointBuilderImpl;
import org.mule.config.dsl.internal.PipelineBuilderImpl;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.construct.SimpleFlowConstruct;

public class FlowBuilder extends PipelineBuilderImpl<FlowBuilder> {

    private final SimpleFlowConstruct flow;
    private InboundEndpointBuilderImpl<FlowBuilder> inboundEndpointBuilder;

    public FlowBuilder(String name, MuleContext muleContext) {
        super(null);
        this.flow = new SimpleFlowConstruct(name, muleContext);
    }

    public InboundEndpointBuilder<FlowBuilder> from(String uri) {
        this.inboundEndpointBuilder = new InboundEndpointBuilderImpl<FlowBuilder>(this, uri);
        return inboundEndpointBuilder;
    }

    FlowConstruct build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        if (inboundEndpointBuilder != null) {
            flow.setMessageSource((MessageSource) inboundEndpointBuilder.build(muleContext, injector, placeholder));
        }
        if (!isProcessorListEmpty()) {
            flow.setMessageProcessors(buildProcessorList(muleContext, injector, placeholder));
        }
        return flow;
    }
}
