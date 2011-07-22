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
import org.mule.api.construct.FlowConstruct;
import org.mule.api.construct.FlowConstructInvalidException;
import org.mule.api.processor.MessageProcessor;
import org.mule.config.i18n.MessageFactory;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotEmpty;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

/**
 * Simple component that lookups and invokes an already registered flow.
 *
 * @author porcelli
 * @see FlowConstruct
 * @see org.mule.api.registry.MuleRegistry#lookupFlowConstruct(String)
 */
public class InvokerFlowComponent implements MessageProcessor {

    private final String flowName;

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
     * Invokes the given flow
     *
     * @param event the mule event
     * @return the result of invoked flow
     * @throws FlowConstructInvalidException if flow not found
     * @throws NullPointerException          if {@code event} param is null
     * @throws MuleException                 if something went working during flow execution
     */
    @Override
    public MuleEvent process(MuleEvent event) throws MuleException, FlowConstructInvalidException, NullPointerException {
        checkNotNull(event, "event");

        FlowConstruct flow = event.getMuleContext().getRegistry().lookupFlowConstruct(flowName);
        if (flow == null) {
            throw new FlowConstructInvalidException(MessageFactory.createStaticMessage("Flow not found."));
        }

        return ((MessageProcessor) flow).process(event);
    }
}
