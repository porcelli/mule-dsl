/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.mule.api.transport.Connector;

/**
 * Interface that defines all outbound ednpoit's related operations.
 *
 * @author porcelli
 */
public interface PipelineOutboundEndpointOperations<P extends PipelineBuilder<P>> {

    /**
     * Sends current {@link org.mule.api.MuleMessage} to an URI based outbound endpoint.
     *
     * @param uri the outbound endpoint uri
     * @return the parameterized builder
     * @throws IllegalArgumentException if {@code uri} param is null or empty
     * @see org.mule.api.endpoint.OutboundEndpoint
     */
    P send(String uri) throws IllegalArgumentException;

    /**
     * Sends current {@link org.mule.api.MuleMessage} to an URI based outbound endpoint
     * using given connector.
     *
     * @param uri       the outbound endpoint uri
     * @param connector the connector to be used
     * @return the parameterized builder
     * @throws IllegalArgumentException if {@code uri} param is null or empt
     * @throws NullPointerException     if {@code connector} param is null
     */
    <C extends Connector> P send(String uri, C connector) throws IllegalArgumentException, NullPointerException;

    /**
     * Sends current {@link org.mule.api.MuleMessage} to an URI based outbound endpoint
     * using given global connector reference.
     *
     * @param uri           the outbound endpoint uri
     * @param connectorName the global connector name
     * @return the parameterized builder
     * @throws IllegalArgumentException if {@code uri} or {@code connectorName} are null or empty
     */
    P send(String uri, String connectorName) throws IllegalArgumentException;

    /**
     * Sends current {@link org.mule.api.MuleMessage} to an URI based outbound endpoint
     * using given exchange pattern.
     *
     * @param uri     the outbound endpoint uri
     * @param pattern the exchange pattern, null is allowed
     * @return the parameterized builder
     * @throws IllegalArgumentException if {@code uri} param is null or empty
     * @throws NullPointerException     if {@code pattern} param is null
     * @see org.mule.api.endpoint.OutboundEndpoint
     */
    P send(String uri, ExchangePattern pattern) throws IllegalArgumentException, NullPointerException;

    /**
     * Sends current {@link org.mule.api.MuleMessage} to an URI based outbound endpoint
     * using given exchange pattern and connector.
     *
     * @param uri       the outbound endpoint uri
     * @param pattern   the exchange pattern
     * @param connector the connector to be used
     * @return the parameterized builder
     * @throws IllegalArgumentException if {@code uri} param is null or empty
     * @throws NullPointerException     if {@code pattern} or {@code connector} are null
     */
    <C extends Connector> P send(String uri, ExchangePattern pattern, C connector) throws IllegalArgumentException, NullPointerException;

    /**
     * Sends current {@link org.mule.api.MuleMessage} to an URI based outbound endpoint
     * using given exchange pattern and global connector reference.
     *
     * @param uri           the outbound endpoint uri
     * @param pattern       the exchange pattern
     * @param connectorName the global connector name
     * @return the parameterized builder
     * @throws IllegalArgumentException if {@code uri} or {@code connectorName} are null or empty
     * @throws NullPointerException     if {@code pattern} param is null
     */
    P send(String uri, ExchangePattern pattern, String connectorName) throws IllegalArgumentException, NullPointerException;
}
