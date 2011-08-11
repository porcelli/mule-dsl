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
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.config.dsl.ConfigurationException;
import org.mule.config.dsl.ExchangePattern;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.endpoint.EndpointURIEndpointBuilder;
import org.mule.endpoint.URIBuilder;

import static org.mule.config.dsl.internal.util.ExchangePatternUtil.convert;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotEmpty;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

/**
 * Internal class that builds, based on string based URI's, an {@link OutboundEndpointBuilderImpl}.
 *
 * @author porcelli
 * @see org.mule.config.dsl.PipelineBuilder#send(String)
 * @see org.mule.config.dsl.PipelineBuilder#send(String, org.mule.config.dsl.ExchangePattern)
 */
public class OutboundEndpointBuilderImpl implements Builder<OutboundEndpoint> {

    private final String uri;
    private ExchangePattern exchangePattern = null;

    /**
     * @param uri             the outbound endpoint uri
     * @param exchangePattern the exchange pattern, null is allowed
     * @throws IllegalArgumentException if {@code uri} param is empty or null
     */
    public OutboundEndpointBuilderImpl(final String uri, final ExchangePattern exchangePattern) {
        checkNotEmpty(uri, "uri");

        this.uri = checkNotEmpty(uri, "uri");
        this.exchangePattern = exchangePattern;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OutboundEndpoint build(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        final org.mule.api.endpoint.EndpointBuilder internalEndpointBuilder = new EndpointURIEndpointBuilder(new URIBuilder(placeholder.replace(uri), muleContext));
        if (exchangePattern != null) {
            internalEndpointBuilder.setExchangePattern(convert(exchangePattern));
        }

        try {
            return internalEndpointBuilder.buildOutboundEndpoint();
        } catch (final Exception e) {
            throw new ConfigurationException("Failed to configure an OutboundEndpoint.", e);
        }
    }
}
