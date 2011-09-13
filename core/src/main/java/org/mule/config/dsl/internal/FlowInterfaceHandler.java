/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import org.mule.DefaultMuleEvent;
import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.transport.MuleMessageFactory;
import org.mule.api.transport.PropertyScope;
import org.mule.config.dsl.ConfigurationException;
import org.mule.config.dsl.FlowNotFoundException;
import org.mule.config.dsl.FlowProcessException;
import org.mule.config.dsl.MuleFlowProcessReturn;
import org.mule.config.dsl.internal.util.MessageFactoryUtil;
import org.mule.session.DefaultMuleSession;

import java.util.HashMap;
import java.util.Map;

import static org.mule.config.dsl.util.Preconditions.checkNotNull;

/**
 * Groups operations for working with flows.
 *
 * @author porcelli
 */
public class FlowInterfaceHandler {

    private static MuleFlowProcessReturnImpl nullFlowReturn = new MuleFlowProcessReturnImpl(null);

    private final FlowConstruct flow;
    private final MuleContext muleContext;

    public FlowInterfaceHandler(final FlowConstruct flow, final MuleContext muleContext) {
        this.flow = checkNotNull(flow, "flow");
        this.muleContext = checkNotNull(muleContext, "muleContext");
    }

    /**
     * Process a flow based on given parameters.
     *
     * @return an instance of {@link org.mule.api.MuleEvent} wrapped by
     * @throws IllegalArgumentException if {@code flowName} is empty or null
     * @throws org.mule.config.dsl.FlowNotFoundException
     *                                  if {@code flowName} is not registered or found on mule context
     * @throws org.mule.config.dsl.ConfigurationException
     *                                  if can't configure properly and cereate a {@link org.mule.api.MuleMessage}
     * @throws org.mule.config.dsl.FlowProcessException
     *                                  if some problem occurs on flow execution
     */
    public synchronized MuleFlowProcessReturn process()
            throws IllegalArgumentException, FlowNotFoundException, ConfigurationException, FlowProcessException {
        return process(null, null);
    }

    /**
     * Process a flow based on given parameters.
     *
     * @param input input data to be used as payload
     * @return an instance of {@link org.mule.api.MuleEvent} wrapped by
     * @throws IllegalArgumentException if {@code flowName} is empty or null
     * @throws org.mule.config.dsl.FlowNotFoundException
     *                                  if {@code flowName} is not registered or found on mule context
     * @throws org.mule.config.dsl.ConfigurationException
     *                                  if can't configure properly and cereate a {@link org.mule.api.MuleMessage}
     * @throws org.mule.config.dsl.FlowProcessException
     *                                  if some problem occurs on flow execution
     */
    public synchronized MuleFlowProcessReturn process(final Object input)
            throws IllegalArgumentException, FlowNotFoundException, ConfigurationException, FlowProcessException {
        return process(input, null);
    }

    /**
     * Process a flow based on given parameters.
     *
     * @param input      input data to be used as payload
     * @param properties properties to be used on message payload {@link org.mule.config.dsl.MuleFlowProcessReturn}
     * @return an instance of {@link org.mule.api.MuleEvent} wrapped by
     * @throws IllegalArgumentException if {@code flowName} is empty or null
     * @throws org.mule.config.dsl.FlowNotFoundException
     *                                  if {@code flowName} is not registered or found on mule context
     * @throws org.mule.config.dsl.ConfigurationException
     *                                  if can't configure properly and create a {@link org.mule.api.MuleMessage}
     * @throws org.mule.config.dsl.FlowProcessException
     *                                  if some problem occurs during flow processing
     */
    public synchronized MuleFlowProcessReturn process(final Object input, final Map<String, Object> properties)
            throws IllegalArgumentException, FlowNotFoundException, ConfigurationException, FlowProcessException {
        if (flow.getMessageProcessorChain().getMessageProcessors().size() == 0) {
            return FlowInterfaceHandler.nullFlowReturn;
        }
        final MuleMessageFactory messageFactory = MessageFactoryUtil.getMessageFactory(input, muleContext);

        if (messageFactory == null) {
            throw new ConfigurationException("Can't create message.");
        }

        final MuleMessage message;
        try {
            message = messageFactory.create(input, null);
        } catch (Exception e) {
            throw new ConfigurationException("Can't create message.", e);
        }

        if (properties != null) {
            message.addProperties(new HashMap<String, Object>(properties), PropertyScope.OUTBOUND);
        }

        try {
            DefaultMuleEvent muleEvent = new DefaultMuleEvent(message, null, new DefaultMuleSession(flow, muleContext));
            muleEvent.setTimeout(0); // necessary (why?) when inbound endpoint is null
            return new MuleFlowProcessReturnImpl(flow.getMessageProcessorChain().process(muleEvent));
        } catch (MuleException e) {
            throw new FlowProcessException("Error during flow process.", e);
        }
    }
}
