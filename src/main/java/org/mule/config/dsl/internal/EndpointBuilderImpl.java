/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import org.mule.MessageExchangePattern;
import org.mule.api.MuleContext;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.config.dsl.EndPointBuilder;
import org.mule.config.dsl.PipelineBuilder;
import org.mule.endpoint.EndpointURIEndpointBuilder;
import org.mule.endpoint.URIBuilder;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotEmpty;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public abstract class EndpointBuilderImpl extends PipelineBuilderImpl implements EndPointBuilder, Builder<ImmutableEndpoint> {

    protected final org.mule.api.endpoint.EndpointBuilder internalEndpointBuilder;
    protected final PipelineBuilder parentScope;

    public EndpointBuilderImpl(final PipelineBuilderImpl parentScope, MuleContext muleContext, String uri) {
        super(muleContext, parentScope);
        checkNotNull(parentScope, "parentScope");
        checkNotNull(muleContext, "muleContext");
        checkNotEmpty(uri, "uri");

        this.internalEndpointBuilder = new EndpointURIEndpointBuilder(new URIBuilder(uri, muleContext));
        this.parentScope = parentScope;
    }

    public abstract ImmutableEndpoint build();

    @Override
    public PipelineBuilder asOneWay() {
        internalEndpointBuilder.setExchangePattern(MessageExchangePattern.ONE_WAY);
        return parentScope;
    }

    @Override
    public PipelineBuilder asRequestResponse() {
        internalEndpointBuilder.setExchangePattern(MessageExchangePattern.REQUEST_RESPONSE);
        return parentScope;
    }

    public static class OutboundEndpointBuilderImpl extends EndpointBuilderImpl implements OutboundEndpointBuilder {

        public OutboundEndpointBuilderImpl(final PipelineBuilderImpl parentScope, MuleContext muleContext, String uri) {
            super(parentScope, muleContext, uri);
        }

        @Override
        public ImmutableEndpoint build() {
            try {
                return internalEndpointBuilder.buildOutboundEndpoint();
            } catch (Exception e) {
                //TODO handle
                throw new RuntimeException();
            }
        }
    }

    public static class InboundEndpointBuilderImpl extends EndpointBuilderImpl implements InboundEndpointBuilder {

        public InboundEndpointBuilderImpl(final PipelineBuilderImpl parentScope, MuleContext muleContext, String uri) {
            super(parentScope, muleContext, uri);
        }

        @Override
        public ImmutableEndpoint build() {
            try {
                return internalEndpointBuilder.buildInboundEndpoint();
            } catch (Exception e) {
                //TODO handle
                throw new RuntimeException(e);
            }
        }
    }

}
