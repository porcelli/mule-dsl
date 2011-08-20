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

import static org.mule.config.dsl.internal.util.Preconditions.checkNotEmpty;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

/**
 * Simple message processor that process an already registered flow.
 * <p/>
 * <b>Note:</b> This wrapper is necessary because during the {@link org.mule.config.dsl.internal.PollBuilderImpl#build(org.mule.api.MuleContext, org.mule.config.dsl.PropertyPlaceholder)}
 * some flows maybe not yet registered. This components avoids thru a lazy load of given flow.
 *
 * @author porcelli
 * @see org.mule.config.dsl.internal.PollBuilderImpl#build(org.mule.api.MuleContext, org.mule.config.dsl.PropertyPlaceholder)
 * @see org.mule.config.dsl.FlowBuilder#poll(org.mule.config.dsl.FlowBuilder)
 * @see org.mule.config.dsl.FlowBuilder#poll(String)
 * @see org.mule.api.construct.FlowConstruct
 */
public class FlowMessageSourceWrapper implements MessageProcessor {

    private final MuleContext muleContext;
    private final String flowName;
    private MessageProcessor flow = null;

    /**
     * @param muleContext the mule context
     * @param flowName    the flow name to be processed
     * @throws NullPointerException     if {@code muleContext} params is null
     * @throws IllegalArgumentException if {@code flowName} params is null or empty
     */
    public FlowMessageSourceWrapper(MuleContext muleContext, String flowName) throws NullPointerException, IllegalArgumentException {
        this.muleContext = checkNotNull(muleContext, "muleContext");
        this.flowName = checkNotEmpty(flowName, "flowName");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MuleEvent process(MuleEvent event) throws MuleException {
        return getFlow().process(event);
    }

    /**
     * Returns the flow name.
     * <p/>
     * <b>Note:</b> This method does not query the flow on mule context.
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
     * @return the the flow
     * @throws FlowNotFoundException if flow not found
     */
    public MessageProcessor getFlow() throws FlowNotFoundException {
        if (flow == null) {
            flow = (MessageProcessor) muleContext.getRegistry().lookupFlowConstruct(flowName);
            if (flow == null) {
                throw new FlowNotFoundException("Flow not found");
            }
        }
        return flow;
    }

}
