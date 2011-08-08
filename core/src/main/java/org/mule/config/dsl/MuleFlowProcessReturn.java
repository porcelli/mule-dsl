/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;

/**
 * Utility class that wraps the flow return ({@link MuleEvent})
 * and expose it thru some sugar methods.
 *
 * @author porcelli
 */
public interface MuleFlowProcessReturn {

    /**
     * Returns the payload based on expected type.
     *
     * @param type the expected type
     * @return the payload typed
     * @throws ClassCastException if can't cast payload to expected type
     */
    <T> T getPayloadAs(Class<T> type) throws ClassCastException;

    /**
     * Returns the payload
     *
     * @return the payload
     */
    Object getPayload();

    /**
     * Returns the message payload for this event
     *
     * @return the message payload for this event
     */
    MuleMessage getMessage();

    /**
     * Returns the wrapped event
     *
     * @return the mule event
     */
    MuleEvent getMuleEvent();

}
