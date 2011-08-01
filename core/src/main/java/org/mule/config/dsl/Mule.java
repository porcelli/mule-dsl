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
import org.mule.config.dsl.internal.DefaultCatalogImpl;
import org.mule.config.dsl.internal.MuleContextBuilder;
import org.mule.construct.AbstractFlowConstruct;
import org.mule.session.DefaultMuleSession;
import org.mule.transport.DefaultMuleMessageFactory;

import java.util.ArrayList;
import java.util.List;

import static org.mule.config.dsl.internal.util.Preconditions.*;

public final class Mule {

    private Mule() {
    }

    private static MuleContext muleContext = null;

    public static MuleContext newMuleContext(final Module... modules) throws NullPointerException, IllegalArgumentException {

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

    public static synchronized void startMuleContext(final Module... modules) throws FailedToStartException {
        if (Mule.muleContext == null) {
            Mule.muleContext = newMuleContext(modules);
        }
        if (Mule.muleContext.isStarted()) {
            return;
        }
        try {
            muleContext.start();
        } catch (final MuleException e) {
            throw new FailedToStartException("Can't start mule context.", e);
        }
    }

    public static synchronized void stopMuleContext() throws FailedToStopException {
        if (muleContext != null && muleContext.isStarted()) {
            try {
                muleContext.stop();
            } catch (final MuleException e) {
                throw new FailedToStopException("Can't stop mule context.", e);
            }
        }
    }

    public static synchronized void process(String flowName, Object input) throws IllegalArgumentException, NullPointerException {
        checkNotEmpty(flowName, "flowName");
        checkNotNull(input, "input");
        if (muleContext != null && muleContext.isStarted()) {
            final FlowConstruct flow = muleContext.getRegistry().lookupFlowConstruct(flowName);
            if (flow == null) {
                throw new RuntimeException("Flow not found");
            }
            if (flow.getMessageProcessorChain().getMessageProcessors().size() == 0) {
                return;
            }
            InboundEndpoint source = null;
            MuleMessageFactory messageFactory = null;
            if (flow instanceof AbstractFlowConstruct && ((AbstractFlowConstruct) flow).getMessageSource() != null && ((AbstractFlowConstruct) flow).getMessageSource() instanceof InboundEndpoint) {
                source = (InboundEndpoint) ((AbstractFlowConstruct) flow).getMessageSource();
                try {
                    messageFactory = source.getConnector().createMuleMessageFactory();
                } catch (CreateException e) {
                }
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
                        throw new RuntimeException("Can't create message.", e);
                    }
                }
                if (message == null) {
                    throw new RuntimeException("Can't create message.", e);
                }
            }

            try {
                DefaultMuleEvent muleEvent = new DefaultMuleEvent(message, source, new DefaultMuleSession(flow, muleContext));
                if (source == null) {
                    muleEvent.setTimeout(1);
                }
                flow.getMessageProcessorChain().process(muleEvent);
            } catch (MuleException e) {
                throw new RuntimeException("Error during flow process.", e);
            }
        }
    }

}
