/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.mule.api.MuleContext;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.processor.MessageProcessor;
import org.mule.construct.SimpleFlowConstruct;

import java.util.ArrayList;
import java.util.List;

public class FlowBuilder extends PipelineBuilder {

    private final SimpleFlowConstruct flow;
    private final List<MessageProcessor> processorList;

    public FlowBuilder(String name, MuleContext muleContext) {
        this.flow = new SimpleFlowConstruct(name, muleContext);
        processorList = new ArrayList<MessageProcessor>();
    }

    EndpointBuilder.InboundEndpointBuilder from(String uri) {
        return new EndpointBuilder.InboundEndpointBuilder(this, this.flow.getMuleContext(), uri);
    }

    FlowConstruct build() {
        if (processorList != null && processorList.size() > 0) {
            flow.setMessageProcessors(processorList);
        }
        return flow;
    }

    void setMessageSource(InboundEndpoint endpoint) {
        flow.setMessageSource(endpoint);
    }

}
