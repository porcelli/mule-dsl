/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.mule.MessageExchangePattern;
import org.mule.api.MuleContext;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.endpoint.EndpointURIEndpointBuilder;
import org.mule.endpoint.URIBuilder;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotEmpty;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public abstract class EndpointBuilder extends PipelineBuilder {

    protected final org.mule.api.endpoint.EndpointBuilder internalEndpointBuilder;
    protected final PipelineBuilder parentScope;

    public EndpointBuilder(PipelineBuilder parentScope, MuleContext muleContext, String uri) {
        checkNotNull(parentScope, "parentScope");
        checkNotNull(muleContext, "muleContext");
        checkNotEmpty(uri, "uri");

        this.internalEndpointBuilder = new EndpointURIEndpointBuilder(new URIBuilder(uri, muleContext));
        this.parentScope = parentScope;

        build();
    }

    protected abstract void build();

    PipelineBuilder asOneWay() {
        internalEndpointBuilder.setExchangePattern(MessageExchangePattern.ONE_WAY);
        build();
        return parentScope;
    }

    PipelineBuilder asRequestResponse() {
        internalEndpointBuilder.setExchangePattern(MessageExchangePattern.REQUEST_RESPONSE);
        return parentScope;
    }


    public static class OutboundEndpointBuilder extends EndpointBuilder {

        private OutboundEndpoint endpoint;

        public OutboundEndpointBuilder(PipelineBuilder parentScope, MuleContext muleContext, String uri) {
            super(parentScope, muleContext, uri);
        }

        @Override
        protected synchronized void build() {
            try {
                endpoint = internalEndpointBuilder.buildOutboundEndpoint();
            } catch (Exception e) {
                //TODO handle
                throw new RuntimeException();
            }
        }
    }

    public static class InboundEndpointBuilder extends EndpointBuilder {

        private InboundEndpoint endpoint;

        public InboundEndpointBuilder(PipelineBuilder parentScope, MuleContext muleContext, String uri) {
            super(parentScope, muleContext, uri);
        }

        @Override
        protected synchronized void build() {
            try {
                ((FlowBuilder) parentScope).setMessageSource(internalEndpointBuilder.buildInboundEndpoint());
            } catch (Exception e) {
                //TODO handle
                throw new RuntimeException();
            }
        }
    }

}
