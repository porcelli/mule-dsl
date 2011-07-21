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
 * Interface that defines all outbound ednpoit's related operations.
 *
 * @author porcelli
 */
public interface PipelineOutboundEndpointOperations<P extends PipelineBuilder<P>> {

    /* outbound */

    /**
     * Sends current {@link org.mule.api.MuleMessage} to given outbound endpoint.
     *
     * @param uri the outbound endpoint uri
     * @return the parameterized builder
     * @throws IllegalArgumentException if {@code uri} param is null or empty
     * @see org.mule.api.endpoint.OutboundEndpoint
     */
    P send(String uri) throws IllegalArgumentException;

    /**
     * Sends current {@link org.mule.api.MuleMessage} to given outbound endpoint
     * using given exchange pattern.
     *
     * @param uri     the outbound endpoint uri
     * @param pattern the exchange pattern, null is allowed
     * @return the parameterized builder
     * @throws IllegalArgumentException if {@code uri} param is null or empty
     * @see org.mule.api.endpoint.OutboundEndpoint
     */
    P send(String uri, ExchangePattern pattern) throws IllegalArgumentException;

}
