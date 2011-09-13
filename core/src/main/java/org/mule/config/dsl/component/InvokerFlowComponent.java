/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.component;

import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;
import org.mule.config.dsl.FlowNotFoundException;

import static org.mule.config.dsl.util.Preconditions.checkNotEmpty;
import static org.mule.config.dsl.util.Preconditions.checkNotNull;

/**
 * Simple component that lookups and invokes an already registered flow.
 * <p/>
 * <b>Important Note:</b> this message processor executes a lazy load of the flow to be executed.
 * This lazy load is important due the Mule config lifecycle that, during this class instantiation,
 * has no garantee that given flow is already registered.
 *
 * @author porcelli
 */
public class InvokerFlowComponent implements MessageProcessor {

    private final String flowName;
    private MessageProcessor flow;

    /**
     * @param flowName the name of the flow to be invoked
     */
    public InvokerFlowComponent(final String flowName) {
        this.flowName = checkNotEmpty(flowName, "flowName");
    }

    /**
     * Getter of flow name
     *
     * @return the flow name
     */
    public String getFlowName() {
        return flowName;
    }

    /**
     * Returns the flow.
     * <p/>
     * <b>Note:</b> This method queries the flow on mule context.
     *
     * @param muleContext the mule context
     * @return the the flow
     * @throws FlowNotFoundException if flow not found
     */
    public MessageProcessor getFlow(MuleContext muleContext) throws NullPointerException, FlowNotFoundException {
        checkNotNull(muleContext, "muleContext");
        if (flow == null) {
            flow = (MessageProcessor) muleContext.getRegistry().lookupFlowConstruct(flowName);
            if (flow == null) {
                throw new FlowNotFoundException("Flow not found");
            }
        }
        return flow;
    }

    /**
     * Invokes the given flow
     *
     * @param event the mule event
     * @return the result of invoked flow
     * @throws FlowNotFoundException if flow not found
     * @throws NullPointerException  if {@code event} param is null
     * @throws MuleException         if something went working during flow execution
     */
    @Override
    public MuleEvent process(MuleEvent event) throws MuleException, NullPointerException {
        checkNotNull(event, "event");

        return getFlow(event.getMuleContext()).process(event);
    }
}
