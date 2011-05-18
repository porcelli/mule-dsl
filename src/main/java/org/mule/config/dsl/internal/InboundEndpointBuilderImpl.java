/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import com.google.inject.Injector;
import org.mule.MessageExchangePattern;
import org.mule.api.MuleContext;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.config.dsl.InboundEndpointBuilder;
import org.mule.config.dsl.PipelineBuilder;
import org.mule.endpoint.EndpointURIEndpointBuilder;
import org.mule.endpoint.URIBuilder;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotEmpty;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class InboundEndpointBuilderImpl<P extends PipelineBuilder<P>> extends PipelineBuilderImpl<P> implements InboundEndpointBuilder<P>, Builder<ImmutableEndpoint> {

    protected final org.mule.api.endpoint.EndpointBuilder internalEndpointBuilder;

    public InboundEndpointBuilderImpl(final PipelineBuilderImpl<P> parentScope, MuleContext muleContext, String uri) {
        super(muleContext, parentScope);
        checkNotNull(parentScope, "parentScope");
        checkNotNull(muleContext, "muleContext");
        checkNotEmpty(uri, "uri");

        this.internalEndpointBuilder = new EndpointURIEndpointBuilder(new URIBuilder(uri, muleContext));
    }

    @Override
    public ImmutableEndpoint build(Injector injector) {
        try {
            return internalEndpointBuilder.buildInboundEndpoint();
        } catch (Exception e) {
            //TODO handle
            throw new RuntimeException(e);
        }
    }

    public P asOneWay() {
        internalEndpointBuilder.setExchangePattern(MessageExchangePattern.ONE_WAY);
        return getThis();
    }

    public P asRequestResponse() {
        internalEndpointBuilder.setExchangePattern(MessageExchangePattern.REQUEST_RESPONSE);
        return getThis();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected P getThis() {
        return (P) this;
    }

}
