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
import org.mule.MessageExchangePattern;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.exception.MessagingExceptionHandler;
import org.mule.api.lifecycle.LifecycleState;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.MessageInfoMapping;
import org.mule.config.dsl.AbstractModule;
import org.mule.config.dsl.Mule;
import org.mule.management.stats.FlowConstructStatistics;
import org.mule.model.seda.SedaService;
import org.mule.session.DefaultMuleSession;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

public class TestInvokerFlowComponent {

    @Test
    public void testConfig() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .send("file:///Users/porcelli/out");
            }
        }).advanced().muleContext();
        InvokerFlowComponent flowWrapper = new InvokerFlowComponent("MyFlow");

        assertThat(flowWrapper.getFlowName()).isEqualTo("MyFlow");
        assertThat(flowWrapper.getFlow(muleContext)).isNotNull();
    }

    @Test
    public void testExec() throws MuleException {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
            }
        }).advanced().muleContext();
        muleContext.getRegistry().registerFlowConstruct(new MyFlorForTest());
        InvokerFlowComponent flowWrapper = new InvokerFlowComponent("MyTEST");

        assertThat(flowWrapper.getFlowName()).isEqualTo("MyTEST");
        assertThat(flowWrapper.getFlow(muleContext)).isNotNull();

        MuleEvent event = getEvent(muleContext, "A");
        flowWrapper.process(event);

        assertThat(flowWrapper.getFlow(muleContext)).isNotNull().isInstanceOf(MyFlorForTest.class);

        assertThat(((MyFlorForTest) flowWrapper.getFlow(muleContext)).getEvent()).isNotNull().isEqualTo(event);
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

        InvokerFlowComponent flowWrapper = new InvokerFlowComponent("MyFlow2");

        assertThat(flowWrapper.getFlowName()).isNotNull().isEqualTo("MyFlow2");

        try {
            flowWrapper.getFlow(muleContext);
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

        InvokerFlowComponent flowWrapper = new InvokerFlowComponent("MyFlow2");

        assertThat(flowWrapper.getFlowName()).isNotNull().isEqualTo("MyFlow2");

        try {
            flowWrapper.getFlow(muleContext);
            fail();
        } catch (Exception ex) {
        }
    }

    @Test(expected = RuntimeException.class)
    public void testNullFlow() throws MuleException {
        new InvokerFlowComponent(null);
    }

    @Test(expected = RuntimeException.class)
    public void testEmptyFlow() throws MuleException {
        new InvokerFlowComponent("");
    }

    private MuleEvent getEvent(final MuleContext muleContext, final Object messageContent) {
        return new DefaultMuleEvent(new DefaultMuleMessage(messageContent, muleContext), MessageExchangePattern.ONE_WAY, new DefaultMuleSession(new SedaService(muleContext), muleContext));
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
        public MuleContext getMuleContext() {
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

    @Test(expected = NullPointerException.class)
    public void testNullEvent() throws Exception {
        final InvokerFlowComponent component = new InvokerFlowComponent("xxx");
        component.process(null);
    }
}