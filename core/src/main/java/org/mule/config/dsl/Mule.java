/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.mule.DefaultMuleEvent;
import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.api.transport.MuleMessageFactory;
import org.mule.api.transport.PropertyScope;
import org.mule.config.dsl.internal.DefaultCatalogImpl;
import org.mule.config.dsl.internal.MuleContextBuilder;
import org.mule.config.dsl.internal.MuleFlowProcessReturnImpl;
import org.mule.construct.AbstractFlowConstruct;
import org.mule.session.DefaultMuleSession;
import org.mule.transport.DefaultMuleMessageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mule.config.dsl.internal.util.Preconditions.checkContentsNotNull;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotEmpty;

/**
 * {@code Mule} is the entry point to Mule DSL. Creates {@link MuleContext} from
 * {@link Module}s and also allows user's directly invoke defined flows.
 *
 * @author porcelli
 */
public final class Mule {

    private Mule() {
    }

    private static MuleContext globalMuleContext = null;
    private static MuleFlowProcessReturnImpl nullFlowReturn = new MuleFlowProcessReturnImpl(null);

    /**
     * Creates a {@link MuleContext} for the given set of modules.
     *
     * @param modules array of non-null {@link Module}s
     * @return the mule context properly configured, but not started
     * @throws NullPointerException     if any of given {@code modules} is null
     * @throws IllegalArgumentException if {@code modules} is empty
     */
    public static synchronized MuleContext newMuleContext(final Module... modules) throws NullPointerException, IllegalArgumentException {

        checkContentsNotNull(modules, "modules");

        if (modules.length < 1) {
            throw new IllegalArgumentException("At least one module should be provided.");
        }

        final DefaultCatalogImpl myCatalog = new DefaultCatalogImpl();
        final List<com.google.inject.Module> guiceModules = new ArrayList<com.google.inject.Module>();

        for (final Module module : modules) {
            module.configure(myCatalog);
            if (module instanceof com.google.inject.Module) {
                guiceModules.add((com.google.inject.Module) module);
            }
        }

        final MuleContextBuilder muleContextBuilder;
        if (guiceModules.size() > 0) {
            final Injector injector = Guice.createInjector(guiceModules);
            muleContextBuilder = new MuleContextBuilder(myCatalog, injector);
        } else {
            muleContextBuilder = new MuleContextBuilder(myCatalog, null);
        }

        return muleContextBuilder.build();
    }

    /**
     *
     * Creates and starts {@link MuleContext} for the given set of modules.
     * <p/>
     * Using this method user's don't need interact directly with {@link MuleContext}, it's up to
     * {@link Mule} class handle it.
     *
     * @param modules array of non-null {@link Module}s
     * @throws NullPointerException     if any of given {@code modules} is null
     * @throws IllegalArgumentException if {@code modules} is empty
     * @throws FailedToStartException   if can't start mule context
     */
    public static synchronized void startMuleContext(final Module... modules) throws FailedToStartException {
        if (Mule.globalMuleContext != null && Mule.globalMuleContext.isStarted()) {
            return;
        }

        try {
            Mule.globalMuleContext = newMuleContext(modules);
            globalMuleContext.start();
        } catch (final MuleException e) {
            throw new FailedToStartException("Can't start mule context.", e);
        }
    }

    /**
     * Stops mule context.
     *
     * @throws FailedToStopException if can't stop mule context
     */
    public static synchronized void stopMuleContext() throws FailedToStopException {
        stopMuleContext(globalMuleContext);
    }

    /**
     * Checks if mule is started.
     *
     * @return true if started, otherwise false
     */
    public static synchronized boolean isStarted() {
        if (globalMuleContext == null) {
            return false;
        }
        return globalMuleContext.isStarted();
    }


    /**
     * Stops the given mule context.
     *
     * @param muleContext the mule context
     * @throws FailedToStopException if can't stop mule context
     */
    public static synchronized void stopMuleContext(final MuleContext muleContext) throws FailedToStopException {
        if (muleContext != null && muleContext.isStarted()) {
            try {
                muleContext.stop();
            } catch (final MuleException e) {
                throw new FailedToStopException("Can't stop mule context.", e);
            }
        }
    }

    /**
     * Process a flow based on given parameters.
     *
     * @param flowName the name of the flow to be executed
     * @return an instance of {@link org.mule.api.MuleEvent} wrapped by
     * @throws IllegalArgumentException if {@code flowName} is empty or null
     * @throws FlowNotFoundException    if {@code flowName} is not registered or found on mule context
     * @throws ConfigurationException   if can't configure properly and cereate a {@link MuleMessage}
     * @throws FlowProcessException     if some problem occurs on flow execution
     */
    public static synchronized MuleFlowProcessReturn process(final String flowName)
            throws IllegalArgumentException, FlowNotFoundException, ConfigurationException, FlowProcessException {
        return process(globalMuleContext, flowName);
    }

    /**
     * Process a flow based on given parameters.
     *
     * @param flowName the name of the flow to be executed
     * @param input    input data to be used as payload
     * @return an instance of {@link org.mule.api.MuleEvent} wrapped by
     * @throws IllegalArgumentException if {@code flowName} is empty or null
     * @throws FlowNotFoundException    if {@code flowName} is not registered or found on mule context
     * @throws ConfigurationException   if can't configure properly and cereate a {@link MuleMessage}
     * @throws FlowProcessException     if some problem occurs on flow execution
     */
    public static synchronized MuleFlowProcessReturn process(final String flowName, final Object input)
            throws IllegalArgumentException, FlowNotFoundException, ConfigurationException, FlowProcessException {
        return process(globalMuleContext, flowName, input);
    }

    /**
     * Process a flow based on given parameters.
     *
     * @param flowName   the name of the flow to be executed
     * @param input      input data to be used as payload
     * @param properties properties to be uset on message payload {@link MuleFlowProcessReturn}
     * @return an instance of {@link org.mule.api.MuleEvent} wrapped by
     * @throws IllegalArgumentException if {@code flowName} is empty or null
     * @throws FlowNotFoundException    if {@code flowName} is not registered or found on mule context
     * @throws ConfigurationException   if can't configure properly and cereate a {@link MuleMessage}
     * @throws FlowProcessException     if some problem occurs on flow execution
     */
    public static synchronized MuleFlowProcessReturn process(final String flowName, final Object input, Map<String, Object> properties)
            throws IllegalArgumentException, FlowNotFoundException, ConfigurationException, FlowProcessException {
        return process(globalMuleContext, flowName, input, properties);
    }

    /**
     * Process a flow based on given parameters.
     *
     * @param muleContext the mule context
     * @param flowName    the name of the flow to be executed
     * @return an instance of {@link org.mule.api.MuleEvent} wrapped by
     * @throws IllegalArgumentException if {@code flowName} is empty or null
     * @throws FlowNotFoundException    if {@code flowName} is not registered or found on mule context
     * @throws ConfigurationException   if can't configure properly and cereate a {@link MuleMessage}
     * @throws FlowProcessException     if some problem occurs on flow execution
     */
    public static synchronized MuleFlowProcessReturn process(final MuleContext muleContext, final String flowName)
            throws IllegalArgumentException, FlowNotFoundException, ConfigurationException, FlowProcessException {
        return process(muleContext, flowName, null, null);
    }

    /**
     * Process a flow based on given parameters.
     *
     * @param muleContext the mule context
     * @param flowName    the name of the flow to be executed
     * @param input       input data to be used as payload
     * @return an instance of {@link org.mule.api.MuleEvent} wrapped by
     * @throws IllegalArgumentException if {@code flowName} is empty or null
     * @throws FlowNotFoundException    if {@code flowName} is not registered or found on mule context
     * @throws ConfigurationException   if can't configure properly and cereate a {@link MuleMessage}
     * @throws FlowProcessException     if some problem occurs on flow execution
     */
    public static synchronized MuleFlowProcessReturn process(final MuleContext muleContext, final String flowName, final Object input)
            throws IllegalArgumentException, FlowNotFoundException, ConfigurationException, FlowProcessException {
        return process(muleContext, flowName, input, null);
    }

    /**
     * Process a flow based on given parameters.
     *
     * @param muleContext the mule context
     * @param flowName    the name of the flow to be executed
     * @param input       input data to be used as payload
     * @param properties  properties to be uset on message payload {@link MuleFlowProcessReturn}
     * @return an instance of {@link org.mule.api.MuleEvent} wrapped by
     * @throws IllegalArgumentException if {@code flowName} is empty or null
     * @throws FlowNotFoundException    if {@code flowName} is not registered or found on mule context
     * @throws ConfigurationException   if can't configure properly and cereate a {@link MuleMessage}
     * @throws FlowProcessException     if some problem occurs on flow execution
     */
    public static synchronized MuleFlowProcessReturn process(final MuleContext muleContext, final String flowName, final Object input, Map<String, Object> properties)
            throws IllegalArgumentException, FlowNotFoundException, ConfigurationException, FlowProcessException {
        checkNotEmpty(flowName, "flowName");

        if (muleContext != null && muleContext.isStarted()) {
            final FlowConstruct flow = muleContext.getRegistry().lookupFlowConstruct(flowName);
            if (flow == null) {
                throw new FlowNotFoundException("Flow not found");
            }
            if (flow.getMessageProcessorChain().getMessageProcessors().size() == 0) {
                return nullFlowReturn;
            }
            InboundEndpoint source = null;
            MuleMessageFactory messageFactory = null;
            if (flow instanceof AbstractFlowConstruct && ((AbstractFlowConstruct) flow).getMessageSource() != null && ((AbstractFlowConstruct) flow).getMessageSource() instanceof InboundEndpoint) {
                source = (InboundEndpoint) ((AbstractFlowConstruct) flow).getMessageSource();
                try {
                    messageFactory = source.getConnector().createMuleMessageFactory();
                } catch (CreateException e) {
                }
            } else {
                flow.getStatistics().setEnabled(false);
            }

            MuleMessage message = null;
            boolean isDefaultMessageFactory = false;

            if (messageFactory == null) {
                isDefaultMessageFactory = true;
                messageFactory = new DefaultMuleMessageFactory(muleContext);
            }

            try {
                message = messageFactory.create(input, null);
            } catch (Exception e) {
                if (!isDefaultMessageFactory) {
                    try {
                        message = new DefaultMuleMessageFactory(muleContext).create(input, null);
                    } catch (Exception e1) {
                        throw new ConfigurationException("Can't create message.", e);
                    }
                }
                if (message == null) {
                    throw new ConfigurationException("Can't create message.", e);
                }
            }

            if (properties != null) {
                message.addProperties(new HashMap<String, Object>(properties), PropertyScope.OUTBOUND);
            }

            try {
                DefaultMuleEvent muleEvent = new DefaultMuleEvent(message, source, new DefaultMuleSession(flow, muleContext));
                if (source == null) {
                    muleEvent.setTimeout(0);
                }
                return new MuleFlowProcessReturnImpl(flow.getMessageProcessorChain().process(muleEvent));
            } catch (MuleException e) {
                throw new FlowProcessException("Error during flow process.", e);
            }
        }
        return nullFlowReturn;
    }
}