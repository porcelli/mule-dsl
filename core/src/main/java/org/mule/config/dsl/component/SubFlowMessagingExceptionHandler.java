/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.component;

import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.exception.MessagingExceptionHandler;
import org.mule.api.exception.RollbackSourceCallback;
import org.mule.config.dsl.FlowProcessException;

import static org.mule.config.dsl.util.Preconditions.checkNotEmpty;

/**
 * Will execute a given flow in case of exception.
 *
 * @author porcelli
 */
public class SubFlowMessagingExceptionHandler implements MessagingExceptionHandler {

    private final InvokerFlowComponent flowInvoker;

    /**
     * @param flowName the flow to be executed
     */
    public SubFlowMessagingExceptionHandler(String flowName) {
        checkNotEmpty(flowName, "flowName");
        this.flowInvoker = new InvokerFlowComponent(flowName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MuleEvent handleException(Exception exception, MuleEvent event) throws FlowProcessException {
        return handleException(exception, event, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MuleEvent handleException(Exception exception, MuleEvent event, RollbackSourceCallback rollbackMethod) {
        try {
            return flowInvoker.process(event);
        } catch (MuleException e) {
            throw new FlowProcessException("Error during flow process", e);
        }
    }
}
