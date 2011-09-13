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
 * Enum that defines the possible exchange patterns.
 *
 * @author porcelli
 */
public enum ExchangePattern {
    /**
     * Receives a message and puts it on a SEDA queue. The callee thread returns and
     * the message is processed by the SEDA thread pool. Nothing gets returned from the
     * result of the call.
     *
     * @see <a href="http://www.mulesoft.org/documentation/display/MULE3USER/Service+Messaging+Styles#ServiceMessagingStyles-Oneway">More about One-Way</a>
     */
    ONE_WAY,
    /**
     * Receives a message and the component returns a message. If the component call returns null, then
     * a {@code MuleMessage} with a {@code NullPayload} is returned. If the call method is {@code void}
     * the request message is returned.
     *
     * @see <a href="http://www.mulesoft.org/documentation/display/MULE3USER/Service+Messaging+Styles#ServiceMessagingStyles-RequestResponse">More about Request-Response</a>
     */
    REQUEST_RESPONSE;
}
