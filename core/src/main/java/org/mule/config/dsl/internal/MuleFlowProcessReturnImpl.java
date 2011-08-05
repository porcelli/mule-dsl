/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.config.dsl.MuleFlowProcessReturn;

/**
 * Internal implementation of {@link MuleFlowProcessReturn} interface.
 *
 * @author porcelli
 * @see MuleFlowProcessReturn
 */
public class MuleFlowProcessReturnImpl implements MuleFlowProcessReturn {

    final MuleEvent event;

    /**
     * @param event mule event instance to be wrapped, can be null
     */
    public MuleFlowProcessReturnImpl(final MuleEvent event) {
        this.event = event;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T getPayload(Class<T> type) throws ClassCastException {
        if (event == null) {
            return null;
        }
        try {
            return event.getMessage().getPayload(type);
        } catch (TransformerException e) {
            throw new ClassCastException(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getPayload() {
        if (event == null) {
            return null;
        }
        return event.getMessage().getPayload();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MuleMessage getMessage() {
        if (event == null) {
            return null;
        }
        return event.getMessage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MuleEvent getMuleEvent() {
        return event;
    }
}
