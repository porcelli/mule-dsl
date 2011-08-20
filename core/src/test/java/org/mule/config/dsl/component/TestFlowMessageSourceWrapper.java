/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.component;

import org.junit.Test;
import org.mule.DefaultMuleEvent;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.exception.MessagingExceptionHandler;
import org.mule.api.lifecycle.LifecycleState;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.processor.MessageProcessorChain;
import org.mule.api.routing.MessageInfoMapping;
import org.mule.config.dsl.AbstractModule;
import org.mule.config.dsl.Mule;
import org.mule.management.stats.FlowConstructStatistics;
import org.mule.model.seda.SedaService;
import org.mule.session.DefaultMuleSession;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

public class TestFlowMessageSourceWrapper {

    @Test
    public void testConfig() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .send("file:///Users/porcelli/out");
            }
        }).advanced().muleContext();
        FlowMessageSourceWrapper flowWrapper = new FlowMessageSourceWrapper(muleContext, "MyFlow");

        assertThat(flowWrapper.getFlowName()).isEqualTo("MyFlow");
        assertThat(flowWrapper.getFlow()).isNotNull();
    }

    @Test
    public void testExec() throws MuleException {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
            }
        }).advanced().muleContext();
        muleContext.getRegistry().registerFlowConstruct(new MyFlorForTest());
        FlowMessageSourceWrapper flowWrapper = new FlowMessageSourceWrapper(muleContext, "MyTEST");

        assertThat(flowWrapper.getFlowName()).isEqualTo("MyTEST");
        assertThat(flowWrapper.getFlow()).isNotNull();

        MuleEvent event = getEvent(muleContext, "A");
        flowWrapper.process(event);

        assertThat(flowWrapper.getFlow()).isNotNull().isInstanceOf(MyFlorForTest.class);

        assertThat(((MyFlorForTest) flowWrapper.getFlow()).getEvent()).isNotNull().isEqualTo(event);
    }

    @Test(expected = RuntimeException.class)
    public void testNullMuleContext() throws MuleException {
        new FlowMessageSourceWrapper(null, "MyTEST");
    }

    @Test(expected = RuntimeException.class)
    public void testEmptyFlow() throws MuleException {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
            }
        }).advanced().muleContext();

        new FlowMessageSourceWrapper(muleContext, "");
    }

    @Test
    public void testFlowNotFound() throws MuleException {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .send("file:///Users/porcelli/out");
            }
        }).advanced().muleContext();

        FlowMessageSourceWrapper flowWrapper = new FlowMessageSourceWrapper(muleContext, "MyFlow2");

        assertThat(flowWrapper.getFlowName()).isNotNull().isEqualTo("MyFlow2");

        try {
            flowWrapper.getFlow();
            fail();
        } catch (Exception ex) {
        }
    }

    @Test
    public void testFlowNotFoundEmpty() throws MuleException {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
            }
        }).advanced().muleContext();

        FlowMessageSourceWrapper flowWrapper = new FlowMessageSourceWrapper(muleContext, "MyFlow2");

        assertThat(flowWrapper.getFlowName()).isNotNull().isEqualTo("MyFlow2");

        try {
            flowWrapper.getFlow();
            fail();
        } catch (Exception ex) {
        }
    }

    @Test(expected = RuntimeException.class)
    public void testNullFlow() throws MuleException {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
            }
        }).advanced().muleContext();

        new FlowMessageSourceWrapper(muleContext, null);
    }

    private MuleEvent getEvent(final MuleContext muleContext, final Object messageContent) {
        return new DefaultMuleEvent(new DefaultMuleMessage(messageContent, muleContext), null, new DefaultMuleSession(new SedaService(muleContext), muleContext));
    }

    private final class MyFlorForTest implements FlowConstruct, MessageProcessor {

        private MuleEvent event;

        @Override
        public String getName() {
            return "MyTEST";
        }

        @Override
        public MessagingExceptionHandler getExceptionListener() {
            return null;
        }

        @Override
        public FlowConstructStatistics getStatistics() {
            return null;
        }

        @Override
        public MessageInfoMapping getMessageInfoMapping() {
            return null;
        }

        @Override
        public MessageProcessorChain getMessageProcessorChain() {
            return null;
        }

        @Override
        public LifecycleState getLifecycleState() {
            return null;
        }

        @Override
        public MuleEvent process(MuleEvent event) throws MuleException {
            this.event = event;
            return null;
        }

        public MuleEvent getEvent() {
            return event;
        }
    }
}