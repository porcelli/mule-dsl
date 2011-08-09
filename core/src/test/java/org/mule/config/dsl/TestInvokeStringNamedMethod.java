/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.junit.Test;
import org.mule.MessageExchangePattern;
import org.mule.api.MuleContext;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.source.MessageSource;
import org.mule.config.dsl.component.InvokerMessageProcessorAdaptor;
import org.mule.construct.SimpleFlowConstruct;

import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;
import static org.mule.config.dsl.expression.CoreExpr.payload;

public class TestInvokeStringNamedMethod {

    @Test
    public void simpleObjectNamed() {
        final Simple mySimple = new Simple();
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(mySimple).methodName("invoke");
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        {
            final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

            assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

            final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

            assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

            assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            final MessageProcessor invokeProcessor = iterator.next();

            assertThat(invokeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorAdaptor.class);

            final InvokerMessageProcessorAdaptor invoke = (InvokerMessageProcessorAdaptor) invokeProcessor;

            assertThat(invoke.getMethodName()).isEqualTo("invoke");

            assertThat(invoke.getObject()).isEqualTo(mySimple).isInstanceOf(Simple.class);

            assertThat(invoke.getArguments()).isEmpty();
        }
    }

    @Test
    public void simpleTypeNamed() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(Simple.class).methodName("invoke");
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        {
            final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

            assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

            final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

            assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

            assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            final MessageProcessor invokeProcessor = iterator.next();

            assertThat(invokeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorAdaptor.class);

            final InvokerMessageProcessorAdaptor invoke = (InvokerMessageProcessorAdaptor) invokeProcessor;

            assertThat(invoke.getMethodName()).isEqualTo("invoke");

            assertThat(invoke.getObjectType()).isEqualTo(Simple.class);

            assertThat(invoke.getObject()).isInstanceOf(Simple.class);

            assertThat(invoke.getArguments()).isEmpty();
        }
    }

    @Test
    public void simpleInjectedNamed() {
        final Simple2 mySimple2 = new Simple2(null);

        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(Simple2.class).methodName("invoke");

                bind(Simple2.class).toInstance(mySimple2);
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        {
            final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

            assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

            final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

            assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

            assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            final MessageProcessor invokeProcessor = iterator.next();

            assertThat(invokeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorAdaptor.class);

            final InvokerMessageProcessorAdaptor invoke = (InvokerMessageProcessorAdaptor) invokeProcessor;

            assertThat(invoke.getMethodName()).isEqualTo("invoke");

            assertThat(invoke.getObjectType()).isEqualTo(Simple2.class);

            assertThat(invoke.getObject()).isInstanceOf(Simple2.class);

            assertThat(invoke.getArguments()).isEmpty();
        }
    }


    @Test
    public void simpleObjectNamedAnnotationWithDefaultArgs() {
        final Simple mySimple = new Simple();
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(mySimple).methodName("invoke").withoutArgs();
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        {
            final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

            assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

            final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

            assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

            assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            final MessageProcessor invokeProcessor = iterator.next();

            assertThat(invokeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorAdaptor.class);

            final InvokerMessageProcessorAdaptor invoke = (InvokerMessageProcessorAdaptor) invokeProcessor;

            assertThat(invoke.getMethodName()).isEqualTo("invoke");

            assertThat(invoke.getObject()).isEqualTo(mySimple).isInstanceOf(Simple.class);

            assertThat(invoke.getArguments()).isEmpty();
        }
    }

    @Test
    public void simpleTypeNamedAnnotationWithDefaultArgs() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(Simple.class).methodName("invoke").withoutArgs();
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        {
            final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

            assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

            final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

            assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

            assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            final MessageProcessor invokeProcessor = iterator.next();

            assertThat(invokeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorAdaptor.class);

            final InvokerMessageProcessorAdaptor invoke = (InvokerMessageProcessorAdaptor) invokeProcessor;

            assertThat(invoke.getMethodName()).isEqualTo("invoke");

            assertThat(invoke.getObjectType()).isEqualTo(Simple.class);

            assertThat(invoke.getObject()).isInstanceOf(Simple.class);

            assertThat(invoke.getArguments()).isEmpty();
        }
    }

    @Test
    public void simpleInjectedNamedAnnotationWithDefaultArgs() {
        final Simple2 mySimple2 = new Simple2(null);

        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(Simple2.class).methodName("invoke").withoutArgs();

                bind(Simple2.class).toInstance(mySimple2);
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        {
            final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

            assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

            final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

            assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

            assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            final MessageProcessor invokeProcessor = iterator.next();

            assertThat(invokeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorAdaptor.class);

            final InvokerMessageProcessorAdaptor invoke = (InvokerMessageProcessorAdaptor) invokeProcessor;

            assertThat(invoke.getMethodName()).isEqualTo("invoke");

            assertThat(invoke.getObjectType()).isEqualTo(Simple2.class);

            assertThat(invoke.getObject()).isInstanceOf(Simple2.class);

            assertThat(invoke.getArguments()).isEmpty();
        }
    }

    @Test
    public void simpleObjectNamedAnnotationWithArgs() {
        final Simple mySimple = new Simple();
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(mySimple).methodName("invoke2").args(payload());
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        {
            final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

            assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

            final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

            assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

            assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            final MessageProcessor invokeProcessor = iterator.next();

            assertThat(invokeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorAdaptor.class);

            final InvokerMessageProcessorAdaptor invoke = (InvokerMessageProcessorAdaptor) invokeProcessor;

            assertThat(invoke.getMethodName()).isEqualTo("invoke2");

            assertThat(invoke.getObject()).isEqualTo(mySimple).isInstanceOf(Simple.class);

            assertThat(invoke.getArguments()).hasSize(1);

            assertThat(invoke.getArguments().get(0)).isEqualTo("#[payload]");
        }
    }

    @Test
    public void simpleTypeNamedAnnotationWithArgs() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(Simple.class).methodName("invoke2").args(payload());
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        {
            final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

            assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

            final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

            assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

            assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            final MessageProcessor invokeProcessor = iterator.next();

            assertThat(invokeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorAdaptor.class);

            final InvokerMessageProcessorAdaptor invoke = (InvokerMessageProcessorAdaptor) invokeProcessor;

            assertThat(invoke.getMethodName()).isEqualTo("invoke2");

            assertThat(invoke.getObjectType()).isEqualTo(Simple.class);

            assertThat(invoke.getObject()).isInstanceOf(Simple.class);

            assertThat(invoke.getArguments()).hasSize(1);

            assertThat(invoke.getArguments().get(0)).isEqualTo("#[payload]");
        }
    }

    @Test
    public void simpleInjectedNamedAnnotationWithArgs() {
        final Simple2 mySimple2 = new Simple2(null);

        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(Simple2.class).methodName("invoke2").args(payload());

                bind(Simple2.class).toInstance(mySimple2);
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        {
            final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

            assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

            final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

            assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

            assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            final MessageProcessor invokeProcessor = iterator.next();

            assertThat(invokeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorAdaptor.class);

            final InvokerMessageProcessorAdaptor invoke = (InvokerMessageProcessorAdaptor) invokeProcessor;

            assertThat(invoke.getMethodName()).isEqualTo("invoke2");

            assertThat(invoke.getObjectType()).isEqualTo(Simple2.class);

            assertThat(invoke.getObject()).isInstanceOf(Simple2.class);

            assertThat(invoke.getArguments()).hasSize(1);

            assertThat(invoke.getArguments().get(0)).isEqualTo("#[payload]");
        }
    }

    public static class Simple {
        public void invoke() {
            System.out.println("SIMPLE! : ");
        }

        public void invoke2(final String value) {
            System.out.println("SIMPLE 2! : " + value);
        }
    }

    public static class Simple2 extends Simple {
        public Simple2(final String value) {
        }
    }

    public static class Complex {
        public void invoke() {
            System.out.println("SIMPLE! : ");
        }

        public void invoke2(final String value) {
            System.out.println("SIMPLE 2! : " + value);
        }
    }

    public static class Complex2 extends Complex {
        public Complex2(final String value) {
        }
    }

}
