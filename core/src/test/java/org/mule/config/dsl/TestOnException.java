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
import org.mule.api.MuleException;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.processor.MessageProcessorChain;
import org.mule.api.source.MessageSource;
import org.mule.api.transformer.Transformer;
import org.mule.component.SimpleCallableJavaComponent;
import org.mule.config.dsl.component.InvokerMessageProcessorAdaptor;
import org.mule.config.dsl.component.SimpleLogComponent;
import org.mule.config.dsl.component.SubFlowMessagingExceptionHandler;
import org.mule.construct.Flow;
import org.mule.routing.ChoiceRouter;
import org.mule.routing.MessageProcessorFilterPair;
import org.mule.routing.filters.RegExFilter;
import org.mule.routing.outbound.MulticastingRouter;
import org.mule.transformer.types.SimpleDataType;

import java.util.Iterator;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mule.config.dsl.expression.CoreExpr.regex;
import static org.mule.config.dsl.internal.util.PrivateAccessorHack.getPrivateFieldValue;

public class TestOnException {

    @Test
    public void simpleCase() throws MuleException, InterruptedException {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/testIn")
                        .onException().process("MyErrorHandler");

                flow("MyErrorHandler")
                        .send("vm://err-out");
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(2);
        Iterator<FlowConstruct> flowIterator = muleContext.getRegistry().lookupFlowConstructs().iterator();

        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            {
                final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

                assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

                final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

                assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

                assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/testIn");
            }

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isEmpty();

            assertThat(flowConstruct.getExceptionListener()).isNotNull().isInstanceOf(SubFlowMessagingExceptionHandler.class);
        }

        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyErrorHandler");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            {
                final MessageProcessor processor = ((Flow) flowConstruct).getMessageProcessors().iterator().next();

                assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

                final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

                assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("VM");

                assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("vm://err-out");
            }
        }
    }

    @Test
    public void regularConfig() throws MuleException, InterruptedException {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/testIn")
                        .invoke(new MyClass()).methodName("execute")
                        .onException().process("MyErrorHandler");

                flow("MyErrorHandler")
                        .send("vm://err-out");
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(2);
        Iterator<FlowConstruct> flowIterator = muleContext.getRegistry().lookupFlowConstructs().iterator();

        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            {
                final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

                assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

                final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

                assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

                assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/testIn");
            }

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

            {
                final MessageProcessor invokeProcessor = iterator.next();

                assertThat(invokeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorAdaptor.class);

                final InvokerMessageProcessorAdaptor invoke = (InvokerMessageProcessorAdaptor) invokeProcessor;

                assertThat(invoke.getMethodName()).isEqualTo("execute");

                assertThat(invoke.getObject()).isNotNull().isInstanceOf(MyClass.class);

                assertThat(invoke.getArguments()).isEmpty();
            }
            {
                assertThat(flowConstruct.getExceptionListener()).isNotNull().isInstanceOf(SubFlowMessagingExceptionHandler.class);
            }
        }

        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyErrorHandler");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            {
                final MessageProcessor processor = ((Flow) flowConstruct).getMessageProcessors().iterator().next();

                assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

                final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

                assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("VM");

                assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("vm://err-out");
            }
        }
    }

    @Test
    public void resgularConfig2() throws MuleException, InterruptedException {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/testIn")
                        .invoke(new MyClass()).methodName("execute").withoutArgs()
                        .onException().process("MyErrorHandler");

                flow("MyErrorHandler")
                        .send("vm://err-out");
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(2);
        Iterator<FlowConstruct> flowIterator = muleContext.getRegistry().lookupFlowConstructs().iterator();

        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            {
                final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

                assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

                final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

                assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

                assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/testIn");
            }

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

            {
                final MessageProcessor invokeProcessor = iterator.next();

                assertThat(invokeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorAdaptor.class);

                final InvokerMessageProcessorAdaptor invoke = (InvokerMessageProcessorAdaptor) invokeProcessor;

                assertThat(invoke.getMethodName()).isEqualTo("execute");

                assertThat(invoke.getObject()).isNotNull().isInstanceOf(MyClass.class);

                assertThat(invoke.getArguments()).isEmpty();
            }
            {
                assertThat(flowConstruct.getExceptionListener()).isNotNull().isInstanceOf(SubFlowMessagingExceptionHandler.class);
            }
        }
        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyErrorHandler");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            {
                final MessageProcessor processor = ((Flow) flowConstruct).getMessageProcessors().iterator().next();

                assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

                final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

                assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("VM");

                assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("vm://err-out");
            }
        }
    }

    @Test
    public void regularAfterChoiceRouterOnExceptionUsage() throws MuleException, InterruptedException {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/testIn")
                        .transformTo(String.class)
                        .log()
                        .choice()
                        .when(regex("foo*"))
                        .invoke(new MyClass()).methodName("execute").withoutArgs()
                        .endChoice()
                        .onException().process("MyErrorHandler");

                flow("MyErrorHandler")
                        .send("vm://err-out");
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(2);
        Iterator<FlowConstruct> flowIterator = muleContext.getRegistry().lookupFlowConstructs().iterator();

        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            {
                final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

                assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

                final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

                assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

                assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/testIn");
            }

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(3);

            final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

            {
                final MessageProcessor transformerProcessor = iterator.next();
                assertThat(transformerProcessor).isNotNull().isInstanceOf(Transformer.class);

                final Transformer transformer = (Transformer) transformerProcessor;

                assertThat(transformer.getReturnDataType()).isEqualTo(new SimpleDataType<String>(String.class));
            }

            {
                final MessageProcessor logProcessor = iterator.next();

                assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

                final SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

                assertThat(log.getObjectType()).isEqualTo(SimpleLogComponent.class);

                assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);
            }
            {
                final MessageProcessor processor = iterator.next();

                assertThat(processor).isNotNull().isInstanceOf(ChoiceRouter.class);

                final MessageProcessor otherwiseProcessor = (MessageProcessor) getPrivateFieldValue(processor, "defaultProcessor");

                assertThat(otherwiseProcessor).isNull();

                final List<MessageProcessorFilterPair> whenList = (List<MessageProcessorFilterPair>) getPrivateFieldValue(processor, "conditionalMessageProcessors");

                assertThat(whenList).isNotNull().isNotEmpty().hasSize(1);

                {
                    assertThat(whenList.get(0).getFilter()).isNotNull().isInstanceOf(RegExFilter.class);

                    assertThat(whenList.get(0).getMessageProcessor()).isNotNull().isInstanceOf(MessageProcessorChain.class);
                }
                final MessageProcessorChain whenChain = (MessageProcessorChain) whenList.get(0).getMessageProcessor();

                {
                    final MessageProcessor invokeProcessor = whenChain.getMessageProcessors().get(0);

                    assertThat(invokeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorAdaptor.class);

                    final InvokerMessageProcessorAdaptor invoke = (InvokerMessageProcessorAdaptor) invokeProcessor;

                    assertThat(invoke.getMethodName()).isEqualTo("execute");

                    assertThat(invoke.getObject()).isNotNull().isInstanceOf(MyClass.class);

                    assertThat(invoke.getArguments()).isEmpty();
                }
                {
                    assertThat(flowConstruct.getExceptionListener()).isNotNull().isInstanceOf(SubFlowMessagingExceptionHandler.class);
                }
            }
        }

        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyErrorHandler");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            {
                final MessageProcessor processor = ((Flow) flowConstruct).getMessageProcessors().iterator().next();

                assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

                final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

                assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("VM");

                assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("vm://err-out");
            }
        }
    }

    @Test
    public void regularAfterBroadcastRouterOnExceptionUsage() throws MuleException, InterruptedException {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/testIn")
                        .transformTo(String.class)
                        .log()
                        .broadcast()
                        .invoke(new MyClass()).methodName("execute").withoutArgs()
                        .endBroadcast()
                        .onException().process("MyErrorHandler");

                flow("MyErrorHandler")
                        .send("vm://err-out");
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(2);
        Iterator<FlowConstruct> flowIterator = muleContext.getRegistry().lookupFlowConstructs().iterator();

        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            {
                final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

                assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

                final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

                assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

                assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/testIn");
            }

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(3);

            final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

            {
                final MessageProcessor transformerProcessor = iterator.next();
                assertThat(transformerProcessor).isNotNull().isInstanceOf(Transformer.class);

                final Transformer transformer = (Transformer) transformerProcessor;

                assertThat(transformer.getReturnDataType()).isEqualTo(new SimpleDataType<String>(String.class));
            }

            {
                final MessageProcessor logProcessor = iterator.next();

                assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

                final SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

                assertThat(log.getObjectType()).isEqualTo(SimpleLogComponent.class);

                assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);
            }
            {
                final MessageProcessor processor = iterator.next();

                assertThat(processor).isNotNull().isInstanceOf(MulticastingRouter.class);

                assertThat(((MulticastingRouter) processor).getRoutes()).isNotEmpty().hasSize(1);

                {
                    final MessageProcessor invokeProcessor = ((MulticastingRouter) processor).getRoutes().get(0);

                    assertThat(invokeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorAdaptor.class);

                    final InvokerMessageProcessorAdaptor invoke = (InvokerMessageProcessorAdaptor) invokeProcessor;

                    assertThat(invoke.getMethodName()).isEqualTo("execute");

                    assertThat(invoke.getObject()).isNotNull().isInstanceOf(MyClass.class);

                    assertThat(invoke.getArguments()).isEmpty();
                }
                {
                    assertThat(flowConstruct.getExceptionListener()).isNotNull().isInstanceOf(SubFlowMessagingExceptionHandler.class);
                }
            }
        }
        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyErrorHandler");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            {
                final MessageProcessor processor = ((Flow) flowConstruct).getMessageProcessors().iterator().next();

                assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

                final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

                assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("VM");

                assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("vm://err-out");
            }
        }
    }

    @Test
    public void nestedScopeOnBroadcastRouterExceptionUsage() throws MuleException, InterruptedException {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/testIn")
                        .transformTo(String.class)
                        .log()
                        .broadcast()
                        .invoke(new MyClass()).methodName("execute").withoutArgs()
                        .onException().process("MyErrorHandler");

                flow("MyErrorHandler")
                        .send("vm://err-out");
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(2);
        Iterator<FlowConstruct> flowIterator = muleContext.getRegistry().lookupFlowConstructs().iterator();

        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            {
                final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

                assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

                final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

                assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

                assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/testIn");
            }

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(3);

            final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

            {
                final MessageProcessor transformerProcessor = iterator.next();
                assertThat(transformerProcessor).isNotNull().isInstanceOf(Transformer.class);

                final Transformer transformer = (Transformer) transformerProcessor;

                assertThat(transformer.getReturnDataType()).isEqualTo(new SimpleDataType<String>(String.class));
            }

            {
                final MessageProcessor logProcessor = iterator.next();

                assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

                final SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

                assertThat(log.getObjectType()).isEqualTo(SimpleLogComponent.class);

                assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);
            }
            {
                final MessageProcessor processor = iterator.next();

                assertThat(processor).isNotNull().isInstanceOf(MulticastingRouter.class);

                assertThat(((MulticastingRouter) processor).getRoutes()).isNotEmpty().hasSize(1);

                {
                    final MessageProcessor invokeProcessor = ((MulticastingRouter) processor).getRoutes().get(0);

                    assertThat(invokeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorAdaptor.class);

                    final InvokerMessageProcessorAdaptor invoke = (InvokerMessageProcessorAdaptor) invokeProcessor;

                    assertThat(invoke.getMethodName()).isEqualTo("execute");

                    assertThat(invoke.getObject()).isNotNull().isInstanceOf(MyClass.class);

                    assertThat(invoke.getArguments()).isEmpty();
                }
                {
                    assertThat(flowConstruct.getExceptionListener()).isNotNull().isInstanceOf(SubFlowMessagingExceptionHandler.class);
                }
            }
        }
        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyErrorHandler");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            {
                final MessageProcessor processor = ((Flow) flowConstruct).getMessageProcessors().iterator().next();

                assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

                final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

                assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("VM");

                assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("vm://err-out");
            }
        }
    }

    @Test
    public void nestedScopeOnChoiceRouterExceptionUsage() throws MuleException, InterruptedException {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/testIn")
                        .transformTo(String.class)
                        .log()
                        .choice()
                        .when(regex("foo*"))
                        .invoke(new MyClass()).methodName("execute").withoutArgs()
                        .onException().process("MyErrorHandler");

                flow("MyErrorHandler")
                        .send("vm://err-out");
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(2);
        Iterator<FlowConstruct> flowIterator = muleContext.getRegistry().lookupFlowConstructs().iterator();

        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            {
                final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

                assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

                final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

                assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

                assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/testIn");
            }

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(3);

            final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

            {
                final MessageProcessor transformerProcessor = iterator.next();
                assertThat(transformerProcessor).isNotNull().isInstanceOf(Transformer.class);

                final Transformer transformer = (Transformer) transformerProcessor;

                assertThat(transformer.getReturnDataType()).isEqualTo(new SimpleDataType<String>(String.class));
            }

            {
                final MessageProcessor logProcessor = iterator.next();

                assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

                final SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

                assertThat(log.getObjectType()).isEqualTo(SimpleLogComponent.class);

                assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);
            }
            {
                final MessageProcessor processor = iterator.next();

                assertThat(processor).isNotNull().isInstanceOf(ChoiceRouter.class);

                final MessageProcessor otherwiseProcessor = (MessageProcessor) getPrivateFieldValue(processor, "defaultProcessor");

                assertThat(otherwiseProcessor).isNull();

                final List<MessageProcessorFilterPair> whenList = (List<MessageProcessorFilterPair>) getPrivateFieldValue(processor, "conditionalMessageProcessors");

                assertThat(whenList).isNotNull().isNotEmpty().hasSize(1);

                {
                    assertThat(whenList.get(0).getFilter()).isNotNull().isInstanceOf(RegExFilter.class);

                    assertThat(whenList.get(0).getMessageProcessor()).isNotNull().isInstanceOf(MessageProcessorChain.class);
                }
                final MessageProcessorChain whenChain = (MessageProcessorChain) whenList.get(0).getMessageProcessor();

                {
                    final MessageProcessor invokeProcessor = whenChain.getMessageProcessors().get(0);

                    assertThat(invokeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorAdaptor.class);

                    final InvokerMessageProcessorAdaptor invoke = (InvokerMessageProcessorAdaptor) invokeProcessor;

                    assertThat(invoke.getMethodName()).isEqualTo("execute");

                    assertThat(invoke.getObject()).isNotNull().isInstanceOf(MyClass.class);

                    assertThat(invoke.getArguments()).isEmpty();
                }
                {
                    assertThat(flowConstruct.getExceptionListener()).isNotNull().isInstanceOf(SubFlowMessagingExceptionHandler.class);
                }
            }
        }
        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyErrorHandler");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            {
                final MessageProcessor processor = ((Flow) flowConstruct).getMessageProcessors().iterator().next();

                assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

                final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

                assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("VM");

                assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("vm://err-out");
            }
        }
    }

    @Test
    public void simpleCaseRef() throws MuleException, InterruptedException {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                FlowDefinition errFlow = flow("MyErrorHandler").send("vm://err-out");

                flow("MyFlow")
                        .from("file:///Users/porcelli/testIn")
                        .onException().process(errFlow);
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(2);
        Iterator<FlowConstruct> flowIterator = muleContext.getRegistry().lookupFlowConstructs().iterator();

        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            {
                final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

                assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

                final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

                assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

                assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/testIn");
            }

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isEmpty();

            assertThat(flowConstruct.getExceptionListener()).isNotNull().isInstanceOf(SubFlowMessagingExceptionHandler.class);
        }
        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyErrorHandler");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            {
                final MessageProcessor processor = ((Flow) flowConstruct).getMessageProcessors().iterator().next();

                assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

                final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

                assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("VM");

                assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("vm://err-out");
            }
        }
    }

    @Test
    public void regularConfigRef() throws MuleException, InterruptedException {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                FlowDefinition errFlow = flow("MyErrorHandler").send("vm://err-out");

                flow("MyFlow")
                        .from("file:///Users/porcelli/testIn")
                        .invoke(new MyClass()).methodName("execute")
                        .onException().process(errFlow);
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(2);
        Iterator<FlowConstruct> flowIterator = muleContext.getRegistry().lookupFlowConstructs().iterator();

        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            {
                final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

                assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

                final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

                assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

                assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/testIn");
            }

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

            {
                final MessageProcessor invokeProcessor = iterator.next();

                assertThat(invokeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorAdaptor.class);

                final InvokerMessageProcessorAdaptor invoke = (InvokerMessageProcessorAdaptor) invokeProcessor;

                assertThat(invoke.getMethodName()).isEqualTo("execute");

                assertThat(invoke.getObject()).isNotNull().isInstanceOf(MyClass.class);

                assertThat(invoke.getArguments()).isEmpty();
            }
            {
                assertThat(flowConstruct.getExceptionListener()).isNotNull().isInstanceOf(SubFlowMessagingExceptionHandler.class);
            }
        }
        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyErrorHandler");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            {
                final MessageProcessor processor = ((Flow) flowConstruct).getMessageProcessors().iterator().next();

                assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

                final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

                assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("VM");

                assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("vm://err-out");
            }
        }
    }

    @Test
    public void resgularConfig2Ref() throws MuleException, InterruptedException {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                FlowDefinition errFlow = flow("MyErrorHandler").send("vm://err-out");

                flow("MyFlow")
                        .from("file:///Users/porcelli/testIn")
                        .invoke(new MyClass()).methodName("execute").withoutArgs()
                        .onException().process(errFlow);
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(2);
        Iterator<FlowConstruct> flowIterator = muleContext.getRegistry().lookupFlowConstructs().iterator();

        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            {
                final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

                assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

                final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

                assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

                assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/testIn");
            }

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

            {
                final MessageProcessor invokeProcessor = iterator.next();

                assertThat(invokeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorAdaptor.class);

                final InvokerMessageProcessorAdaptor invoke = (InvokerMessageProcessorAdaptor) invokeProcessor;

                assertThat(invoke.getMethodName()).isEqualTo("execute");

                assertThat(invoke.getObject()).isNotNull().isInstanceOf(MyClass.class);

                assertThat(invoke.getArguments()).isEmpty();
            }
            {
                assertThat(flowConstruct.getExceptionListener()).isNotNull().isInstanceOf(SubFlowMessagingExceptionHandler.class);
            }
        }
        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyErrorHandler");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            {
                final MessageProcessor processor = ((Flow) flowConstruct).getMessageProcessors().iterator().next();

                assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

                final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

                assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("VM");

                assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("vm://err-out");
            }
        }
    }

    @Test
    public void regularAfterChoiceRouterOnExceptionUsageRef() throws MuleException, InterruptedException {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                FlowDefinition errFlow = flow("MyErrorHandler").send("vm://err-out");

                flow("MyFlow")
                        .from("file:///Users/porcelli/testIn")
                        .transformTo(String.class)
                        .log()
                        .choice()
                        .when(regex("foo*"))
                        .invoke(new MyClass()).methodName("execute").withoutArgs()
                        .endChoice()
                        .onException().process(errFlow);
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(2);
        Iterator<FlowConstruct> flowIterator = muleContext.getRegistry().lookupFlowConstructs().iterator();

        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            {
                final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

                assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

                final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

                assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

                assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/testIn");
            }

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(3);

            final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

            {
                final MessageProcessor transformerProcessor = iterator.next();
                assertThat(transformerProcessor).isNotNull().isInstanceOf(Transformer.class);

                final Transformer transformer = (Transformer) transformerProcessor;

                assertThat(transformer.getReturnDataType()).isEqualTo(new SimpleDataType<String>(String.class));
            }

            {
                final MessageProcessor logProcessor = iterator.next();

                assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

                final SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

                assertThat(log.getObjectType()).isEqualTo(SimpleLogComponent.class);

                assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);
            }
            {
                final MessageProcessor processor = iterator.next();

                assertThat(processor).isNotNull().isInstanceOf(ChoiceRouter.class);

                final MessageProcessor otherwiseProcessor = (MessageProcessor) getPrivateFieldValue(processor, "defaultProcessor");

                assertThat(otherwiseProcessor).isNull();

                final List<MessageProcessorFilterPair> whenList = (List<MessageProcessorFilterPair>) getPrivateFieldValue(processor, "conditionalMessageProcessors");

                assertThat(whenList).isNotNull().isNotEmpty().hasSize(1);

                {
                    assertThat(whenList.get(0).getFilter()).isNotNull().isInstanceOf(RegExFilter.class);

                    assertThat(whenList.get(0).getMessageProcessor()).isNotNull().isInstanceOf(MessageProcessorChain.class);
                }
                final MessageProcessorChain whenChain = (MessageProcessorChain) whenList.get(0).getMessageProcessor();

                {
                    final MessageProcessor invokeProcessor = whenChain.getMessageProcessors().get(0);

                    assertThat(invokeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorAdaptor.class);

                    final InvokerMessageProcessorAdaptor invoke = (InvokerMessageProcessorAdaptor) invokeProcessor;

                    assertThat(invoke.getMethodName()).isEqualTo("execute");

                    assertThat(invoke.getObject()).isNotNull().isInstanceOf(MyClass.class);

                    assertThat(invoke.getArguments()).isEmpty();
                }
                {
                    assertThat(flowConstruct.getExceptionListener()).isNotNull().isInstanceOf(SubFlowMessagingExceptionHandler.class);
                }
            }
        }
        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyErrorHandler");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            {
                final MessageProcessor processor = ((Flow) flowConstruct).getMessageProcessors().iterator().next();

                assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

                final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

                assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("VM");

                assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("vm://err-out");
            }
        }
    }

    @Test
    public void regularAfterBroadcastRouterOnExceptionUsageRef() throws MuleException, InterruptedException {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                FlowDefinition errFlow = flow("MyErrorHandler").send("vm://err-out");

                flow("MyFlow")
                        .from("file:///Users/porcelli/testIn")
                        .transformTo(String.class)
                        .log()
                        .broadcast()
                        .invoke(new MyClass()).methodName("execute").withoutArgs()
                        .endBroadcast()
                        .onException().process(errFlow);
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(2);
        Iterator<FlowConstruct> flowIterator = muleContext.getRegistry().lookupFlowConstructs().iterator();

        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            {
                final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

                assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

                final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

                assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

                assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/testIn");
            }

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(3);

            final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

            {
                final MessageProcessor transformerProcessor = iterator.next();
                assertThat(transformerProcessor).isNotNull().isInstanceOf(Transformer.class);

                final Transformer transformer = (Transformer) transformerProcessor;

                assertThat(transformer.getReturnDataType()).isEqualTo(new SimpleDataType<String>(String.class));
            }

            {
                final MessageProcessor logProcessor = iterator.next();

                assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

                final SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

                assertThat(log.getObjectType()).isEqualTo(SimpleLogComponent.class);

                assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);
            }
            {
                final MessageProcessor processor = iterator.next();

                assertThat(processor).isNotNull().isInstanceOf(MulticastingRouter.class);

                assertThat(((MulticastingRouter) processor).getRoutes()).isNotEmpty().hasSize(1);

                {
                    final MessageProcessor invokeProcessor = ((MulticastingRouter) processor).getRoutes().get(0);

                    assertThat(invokeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorAdaptor.class);

                    final InvokerMessageProcessorAdaptor invoke = (InvokerMessageProcessorAdaptor) invokeProcessor;

                    assertThat(invoke.getMethodName()).isEqualTo("execute");

                    assertThat(invoke.getObject()).isNotNull().isInstanceOf(MyClass.class);

                    assertThat(invoke.getArguments()).isEmpty();
                }
                {
                    assertThat(flowConstruct.getExceptionListener()).isNotNull().isInstanceOf(SubFlowMessagingExceptionHandler.class);
                }
            }
        }
        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyErrorHandler");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            {
                final MessageProcessor processor = ((Flow) flowConstruct).getMessageProcessors().iterator().next();

                assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

                final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

                assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("VM");

                assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("vm://err-out");
            }
        }
    }

    @Test
    public void nestedScopeOnBroadcastRouterExceptionUsageRef() throws MuleException, InterruptedException {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                FlowDefinition errFlow = flow("MyErrorHandler").send("vm://err-out");

                flow("MyFlow")
                        .from("file:///Users/porcelli/testIn")
                        .transformTo(String.class)
                        .log()
                        .broadcast()
                        .invoke(new MyClass()).methodName("execute").withoutArgs()
                        .onException().process(errFlow);
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(2);
        Iterator<FlowConstruct> flowIterator = muleContext.getRegistry().lookupFlowConstructs().iterator();

        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            {
                final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

                assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

                final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

                assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

                assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/testIn");
            }

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(3);

            final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

            {
                final MessageProcessor transformerProcessor = iterator.next();
                assertThat(transformerProcessor).isNotNull().isInstanceOf(Transformer.class);

                final Transformer transformer = (Transformer) transformerProcessor;

                assertThat(transformer.getReturnDataType()).isEqualTo(new SimpleDataType<String>(String.class));
            }

            {
                final MessageProcessor logProcessor = iterator.next();

                assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

                final SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

                assertThat(log.getObjectType()).isEqualTo(SimpleLogComponent.class);

                assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);
            }
            {
                final MessageProcessor processor = iterator.next();

                assertThat(processor).isNotNull().isInstanceOf(MulticastingRouter.class);

                assertThat(((MulticastingRouter) processor).getRoutes()).isNotEmpty().hasSize(1);

                {
                    final MessageProcessor invokeProcessor = ((MulticastingRouter) processor).getRoutes().get(0);

                    assertThat(invokeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorAdaptor.class);

                    final InvokerMessageProcessorAdaptor invoke = (InvokerMessageProcessorAdaptor) invokeProcessor;

                    assertThat(invoke.getMethodName()).isEqualTo("execute");

                    assertThat(invoke.getObject()).isNotNull().isInstanceOf(MyClass.class);

                    assertThat(invoke.getArguments()).isEmpty();
                }
                {
                    assertThat(flowConstruct.getExceptionListener()).isNotNull().isInstanceOf(SubFlowMessagingExceptionHandler.class);
                }
            }
        }
        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyErrorHandler");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            {
                final MessageProcessor processor = ((Flow) flowConstruct).getMessageProcessors().iterator().next();

                assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

                final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

                assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("VM");

                assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("vm://err-out");
            }
        }
    }

    @Test
    public void nestedScopeOnChoiceRouterExceptionUsageRef() throws MuleException, InterruptedException {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                FlowDefinition errFlow = flow("MyErrorHandler").send("vm://err-out");

                flow("MyFlow")
                        .from("file:///Users/porcelli/testIn")
                        .transformTo(String.class)
                        .log()
                        .choice()
                        .when(regex("foo*"))
                        .invoke(new MyClass()).methodName("execute").withoutArgs()
                        .onException().process(errFlow);
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(2);
        Iterator<FlowConstruct> flowIterator = muleContext.getRegistry().lookupFlowConstructs().iterator();

        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            {
                final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

                assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

                final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

                assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

                assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/testIn");
            }

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(3);

            final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

            {
                final MessageProcessor transformerProcessor = iterator.next();
                assertThat(transformerProcessor).isNotNull().isInstanceOf(Transformer.class);

                final Transformer transformer = (Transformer) transformerProcessor;

                assertThat(transformer.getReturnDataType()).isEqualTo(new SimpleDataType<String>(String.class));
            }

            {
                final MessageProcessor logProcessor = iterator.next();

                assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

                final SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

                assertThat(log.getObjectType()).isEqualTo(SimpleLogComponent.class);

                assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);
            }
            {
                final MessageProcessor processor = iterator.next();

                assertThat(processor).isNotNull().isInstanceOf(ChoiceRouter.class);

                final MessageProcessor otherwiseProcessor = (MessageProcessor) getPrivateFieldValue(processor, "defaultProcessor");

                assertThat(otherwiseProcessor).isNull();

                final List<MessageProcessorFilterPair> whenList = (List<MessageProcessorFilterPair>) getPrivateFieldValue(processor, "conditionalMessageProcessors");

                assertThat(whenList).isNotNull().isNotEmpty().hasSize(1);

                {
                    assertThat(whenList.get(0).getFilter()).isNotNull().isInstanceOf(RegExFilter.class);

                    assertThat(whenList.get(0).getMessageProcessor()).isNotNull().isInstanceOf(MessageProcessorChain.class);
                }
                final MessageProcessorChain whenChain = (MessageProcessorChain) whenList.get(0).getMessageProcessor();

                {
                    final MessageProcessor invokeProcessor = whenChain.getMessageProcessors().get(0);

                    assertThat(invokeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorAdaptor.class);

                    final InvokerMessageProcessorAdaptor invoke = (InvokerMessageProcessorAdaptor) invokeProcessor;

                    assertThat(invoke.getMethodName()).isEqualTo("execute");

                    assertThat(invoke.getObject()).isNotNull().isInstanceOf(MyClass.class);

                    assertThat(invoke.getArguments()).isEmpty();
                }
                {
                    assertThat(flowConstruct.getExceptionListener()).isNotNull().isInstanceOf(SubFlowMessagingExceptionHandler.class);
                }
            }
        }
        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyErrorHandler");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            {
                final MessageProcessor processor = ((Flow) flowConstruct).getMessageProcessors().iterator().next();

                assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

                final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

                assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("VM");

                assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("vm://err-out");
            }
        }
    }

    @Test(expected = RuntimeException.class)
    public void simpleErrEmptyString() throws MuleException, InterruptedException {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/testIn")
                        .onException().process("");
            }
        }).advanced().muleContext();
    }

    @Test(expected = RuntimeException.class)
    public void simpleErrNullString() throws MuleException, InterruptedException {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/testIn")
                        .onException().process((String) null);
            }
        }).advanced().muleContext();
    }

    public static class MyClass {
        public void execute() {
            throw new RuntimeException("MY TEST EXCEPTION INSIDE MYCLASS.CLASS");
        }
    }
}
