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
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.processor.MessageProcessorChain;
import org.mule.api.source.MessageSource;
import org.mule.component.SimpleCallableJavaComponent;
import org.mule.component.simple.EchoComponent;
import org.mule.config.dsl.component.SimpleLogComponent;
import org.mule.construct.Flow;
import org.mule.routing.ChoiceRouter;
import org.mule.routing.MessageProcessorFilterPair;
import org.mule.routing.filters.WildcardFilter;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mule.config.dsl.expression.CoreExpr.wildcard;
import static org.mule.config.dsl.internal.util.PrivateAccessorHack.getPrivateFieldValue;

public class TestRouterChoice {

    @Test
    public void simpleChoice() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .choice()
                            .when(wildcard("foo*"))
                                .log()
                            .otherwise()
                                .echo()
                        .endChoice();
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(Flow.class);

        final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

        final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final MessageProcessor processor = ((Flow) flowConstruct).getMessageProcessors().iterator().next();

        assertThat(processor).isNotNull().isInstanceOf(ChoiceRouter.class);

        final MessageProcessor otherwiseProcessor = (MessageProcessor) getPrivateFieldValue(processor, "defaultProcessor");

        assertThat(otherwiseProcessor).isNotNull().isInstanceOf(MessageProcessorChain.class);

        final MessageProcessorChain echoChain = (MessageProcessorChain) otherwiseProcessor;

        assertThat(echoChain.getMessageProcessors()).isNotEmpty().hasSize(1);

        assertThat(echoChain.getMessageProcessors().get(0)).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent echo = (SimpleCallableJavaComponent) echoChain.getMessageProcessors().get(0);

        assertThat(echo.getObjectType()).isEqualTo(EchoComponent.class);

        assertThat(echo.getObjectFactory().isSingleton()).isEqualTo(true);

        final List<MessageProcessorFilterPair> whenList = (List<MessageProcessorFilterPair>) getPrivateFieldValue(processor, "conditionalMessageProcessors");

        assertThat(whenList).isNotNull().isNotEmpty().hasSize(1);

        assertThat(whenList.get(0).getFilter()).isNotNull().isInstanceOf(WildcardFilter.class);

        assertThat(whenList.get(0).getMessageProcessor()).isNotNull().isInstanceOf(MessageProcessorChain.class);

        final MessageProcessorChain echoChain2 = (MessageProcessorChain) whenList.get(0).getMessageProcessor();

        assertThat(echoChain2.getMessageProcessors().get(0)).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent echo2 = (SimpleCallableJavaComponent) echoChain2.getMessageProcessors().get(0);

        assertThat(echo2.getObjectType()).isEqualTo(SimpleLogComponent.class);

        assertThat(echo2.getObjectFactory().isSingleton()).isEqualTo(true);
    }

        @Test
    public void simpleChoiceWithInnerChoiceOnWhen() {
            final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .choice()
                            .when(wildcard("foo*"))
                                .choice()
                                    .when(wildcard("foobar*"))
                                        .echo()
                                    .otherwise()
                                        .log()
                                .endChoice()
                            .otherwise()
                                .choice()
                                    .when(wildcard("bar*"))
                                        .log()
                                    .otherwise()
                                        .echo()
                                .endChoice()
                        .endChoice();
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(Flow.class);

        final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

        final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final MessageProcessor processor = ((Flow) flowConstruct).getMessageProcessors().iterator().next();

        assertThat(processor).isNotNull().isInstanceOf(ChoiceRouter.class);

        final MessageProcessor otherwiseProcessor = (MessageProcessor) getPrivateFieldValue(processor, "defaultProcessor");

        assertThat(otherwiseProcessor).isNotNull().isInstanceOf(MessageProcessorChain.class);

        final MessageProcessorChain echoChain = (MessageProcessorChain) otherwiseProcessor;

        assertThat(echoChain.getMessageProcessors()).isNotEmpty().hasSize(1);

        {//test on inner choice inside otherwise
            assertThat(echoChain.getMessageProcessors().get(0)).isNotNull().isInstanceOf(ChoiceRouter.class);

            final ChoiceRouter innerChoiceOnOtherwise = (ChoiceRouter) echoChain.getMessageProcessors().get(0);

            final MessageProcessor otherwiseOnInnerChoiceOtherwiseProcessor = (MessageProcessor) getPrivateFieldValue(innerChoiceOnOtherwise, "defaultProcessor");

            assertThat(otherwiseOnInnerChoiceOtherwiseProcessor).isNotNull().isInstanceOf(MessageProcessorChain.class);

            final MessageProcessorChain echoChainOnInnerChoiceOtherwise = (MessageProcessorChain) otherwiseOnInnerChoiceOtherwiseProcessor;

            assertThat(echoChainOnInnerChoiceOtherwise.getMessageProcessors()).isNotEmpty().hasSize(1);

            assertThat(echoChainOnInnerChoiceOtherwise.getMessageProcessors().get(0)).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

            final SimpleCallableJavaComponent echoOnInnerChoiceOtherwise = (SimpleCallableJavaComponent) echoChainOnInnerChoiceOtherwise.getMessageProcessors().get(0);

            assertThat(echoOnInnerChoiceOtherwise.getObjectType()).isEqualTo(EchoComponent.class);

            assertThat(echoOnInnerChoiceOtherwise.getObjectFactory().isSingleton()).isEqualTo(true);

            final List<MessageProcessorFilterPair> whenListOnInnerChoiceOtherwise = (List<MessageProcessorFilterPair>) getPrivateFieldValue(innerChoiceOnOtherwise, "conditionalMessageProcessors");

            assertThat(whenListOnInnerChoiceOtherwise).isNotNull().isNotEmpty().hasSize(1);

            assertThat(whenListOnInnerChoiceOtherwise.get(0).getFilter()).isNotNull().isInstanceOf(WildcardFilter.class);

            assertThat(whenListOnInnerChoiceOtherwise.get(0).getMessageProcessor()).isNotNull().isInstanceOf(MessageProcessorChain.class);

            final MessageProcessorChain logChain = (MessageProcessorChain) whenListOnInnerChoiceOtherwise.get(0).getMessageProcessor();

            assertThat(logChain.getMessageProcessors().get(0)).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

            final SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logChain.getMessageProcessors().get(0);

            assertThat(log.getObjectType()).isEqualTo(SimpleLogComponent.class);

            assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);
        }


        final List<MessageProcessorFilterPair> whenList = (List<MessageProcessorFilterPair>) getPrivateFieldValue(processor, "conditionalMessageProcessors");

        assertThat(whenList).isNotNull().isNotEmpty().hasSize(1);

        assertThat(whenList.get(0).getFilter()).isNotNull().isInstanceOf(WildcardFilter.class);

        assertThat(whenList.get(0).getMessageProcessor()).isNotNull().isInstanceOf(MessageProcessorChain.class);

        final MessageProcessorChain chain2 = (MessageProcessorChain) whenList.get(0).getMessageProcessor();

        {//test on inner choice inside when
            assertThat(chain2.getMessageProcessors().get(0)).isNotNull().isInstanceOf(ChoiceRouter.class);

            final ChoiceRouter innerChoiceOnWhen = (ChoiceRouter) chain2.getMessageProcessors().get(0);

            final MessageProcessor otherwiseOnInnerChoiceWhenProcessor = (MessageProcessor) getPrivateFieldValue(innerChoiceOnWhen, "defaultProcessor");

            assertThat(otherwiseOnInnerChoiceWhenProcessor).isNotNull().isInstanceOf(MessageProcessorChain.class);

            final MessageProcessorChain echoChainOnInnerChoiceOtherwise = (MessageProcessorChain) otherwiseOnInnerChoiceWhenProcessor;

            assertThat(echoChainOnInnerChoiceOtherwise.getMessageProcessors()).isNotEmpty().hasSize(1);

            assertThat(echoChainOnInnerChoiceOtherwise.getMessageProcessors().get(0)).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

            final SimpleCallableJavaComponent echoOnInnerChoiceOtherwise = (SimpleCallableJavaComponent) echoChainOnInnerChoiceOtherwise.getMessageProcessors().get(0);

            assertThat(echoOnInnerChoiceOtherwise.getObjectType()).isEqualTo(SimpleLogComponent.class);

            assertThat(echoOnInnerChoiceOtherwise.getObjectFactory().isSingleton()).isEqualTo(true);

            final List<MessageProcessorFilterPair> whenListOnInnerChoiceOtherwise = (List<MessageProcessorFilterPair>) getPrivateFieldValue(innerChoiceOnWhen, "conditionalMessageProcessors");

            assertThat(whenListOnInnerChoiceOtherwise).isNotNull().isNotEmpty().hasSize(1);

            assertThat(whenListOnInnerChoiceOtherwise.get(0).getFilter()).isNotNull().isInstanceOf(WildcardFilter.class);

            assertThat(whenListOnInnerChoiceOtherwise.get(0).getMessageProcessor()).isNotNull().isInstanceOf(MessageProcessorChain.class);

            final MessageProcessorChain logChain = (MessageProcessorChain) whenListOnInnerChoiceOtherwise.get(0).getMessageProcessor();

            assertThat(logChain.getMessageProcessors().get(0)).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

            final SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logChain.getMessageProcessors().get(0);

            assertThat(log.getObjectType()).isEqualTo(EchoComponent.class);

            assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);
        }

    }
    @Test
    public void simpleChoiceWithoutOtherwise() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .choice()
                            .when(wildcard("foo*"))
                                .log()
                        .endChoice();
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(Flow.class);

        final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

        final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final MessageProcessor processor = ((Flow) flowConstruct).getMessageProcessors().iterator().next();

        assertThat(processor).isNotNull().isInstanceOf(ChoiceRouter.class);

        final MessageProcessor otherwiseProcessor = (MessageProcessor) getPrivateFieldValue(processor, "defaultProcessor");

        assertThat(otherwiseProcessor).isNull();

        final List<MessageProcessorFilterPair> whenList = (List<MessageProcessorFilterPair>) getPrivateFieldValue(processor, "conditionalMessageProcessors");

        assertThat(whenList).isNotNull().isNotEmpty().hasSize(1);

        assertThat(whenList.get(0).getFilter()).isNotNull().isInstanceOf(WildcardFilter.class);

        assertThat(whenList.get(0).getMessageProcessor()).isNotNull().isInstanceOf(MessageProcessorChain.class);

        final MessageProcessorChain echoChain2 = (MessageProcessorChain) whenList.get(0).getMessageProcessor();

        assertThat(echoChain2.getMessageProcessors().get(0)).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent echo2 = (SimpleCallableJavaComponent) echoChain2.getMessageProcessors().get(0);

        assertThat(echo2.getObjectType()).isEqualTo(SimpleLogComponent.class);

        assertThat(echo2.getObjectFactory().isSingleton()).isEqualTo(true);
    }

    @Test
    public void simpleChoiceWithSend() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .choice()
                            .when(wildcard("foo*"))
                                .send("file:///Users/porcelli/out")
                                .send("file:///Users/porcelli/out1")
                            .when(wildcard("bar*"))
                                .send("file:///Users/porcelli/out2")
                                .send("file:///Users/porcelli/out3")
                            .otherwise()
                                .send("file:///Users/porcelli/out4")
                                .send("file:///Users/porcelli/out5")
                        .endChoice();
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(Flow.class);

        final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

        final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final MessageProcessor processor = ((Flow) flowConstruct).getMessageProcessors().iterator().next();

        assertThat(processor).isNotNull().isInstanceOf(ChoiceRouter.class);

        final MessageProcessor otherwiseProcessor = (MessageProcessor) getPrivateFieldValue(processor, "defaultProcessor");

        assertThat(otherwiseProcessor).isNotNull().isInstanceOf(MessageProcessorChain.class);

        final MessageProcessorChain echoChain = (MessageProcessorChain) otherwiseProcessor;

        assertThat(echoChain.getMessageProcessors()).isNotEmpty().hasSize(2);

        assertThat(echoChain.getMessageProcessors().get(0)).isNotNull().isInstanceOf(OutboundEndpoint.class);
        final OutboundEndpoint outbound4 = (OutboundEndpoint) echoChain.getMessageProcessors().get(0);
        assertThat(outbound4.getAddress()).isEqualTo("file:///Users/porcelli/out4");

        assertThat(echoChain.getMessageProcessors().get(1)).isNotNull().isInstanceOf(OutboundEndpoint.class);
        final OutboundEndpoint outbound5 = (OutboundEndpoint) echoChain.getMessageProcessors().get(1);
        assertThat(outbound5.getAddress()).isEqualTo("file:///Users/porcelli/out5");

        final List<MessageProcessorFilterPair> whenList = (List<MessageProcessorFilterPair>) getPrivateFieldValue(processor, "conditionalMessageProcessors");

        assertThat(whenList).isNotNull().isNotEmpty().hasSize(2);

        assertThat(whenList.get(0).getFilter()).isNotNull().isInstanceOf(WildcardFilter.class);

        assertThat(((WildcardFilter)whenList.get(0).getFilter()).getPattern()).isNotNull().isEqualTo("foo*");

        assertThat(whenList.get(0).getMessageProcessor()).isNotNull().isInstanceOf(MessageProcessorChain.class);

        final MessageProcessorChain whenChain2 = (MessageProcessorChain) whenList.get(0).getMessageProcessor();

        assertThat(whenChain2.getMessageProcessors().get(0)).isNotNull().isInstanceOf(OutboundEndpoint.class);
        final OutboundEndpoint outbound = (OutboundEndpoint) whenChain2.getMessageProcessors().get(0);
        assertThat(outbound.getAddress()).isEqualTo("file:///Users/porcelli/out");

        assertThat(whenChain2.getMessageProcessors().get(1)).isNotNull().isInstanceOf(OutboundEndpoint.class);
        final OutboundEndpoint outbound1 = (OutboundEndpoint) whenChain2.getMessageProcessors().get(1);
        assertThat(outbound1.getAddress()).isEqualTo("file:///Users/porcelli/out1");

        assertThat(whenList.get(1).getFilter()).isNotNull().isInstanceOf(WildcardFilter.class);

        assertThat(((WildcardFilter)whenList.get(1).getFilter()).getPattern()).isNotNull().isEqualTo("bar*");

        assertThat(whenList.get(1).getMessageProcessor()).isNotNull().isInstanceOf(MessageProcessorChain.class);

        final MessageProcessorChain whenChain3 = (MessageProcessorChain) whenList.get(1).getMessageProcessor();

        assertThat(whenChain3.getMessageProcessors().get(0)).isNotNull().isInstanceOf(OutboundEndpoint.class);
        final OutboundEndpoint outbound2 = (OutboundEndpoint) whenChain3.getMessageProcessors().get(0);
        assertThat(outbound2.getAddress()).isEqualTo("file:///Users/porcelli/out2");

        assertThat(whenChain3.getMessageProcessors().get(1)).isNotNull().isInstanceOf(OutboundEndpoint.class);
        final OutboundEndpoint outbound3 = (OutboundEndpoint) whenChain3.getMessageProcessors().get(1);
        assertThat(outbound3.getAddress()).isEqualTo("file:///Users/porcelli/out3");
    }

    @Test
    public void simpleChoiceWithSendWithoutOtherwise() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .choice()
                            .when(wildcard("foo*"))
                                .send("file:///Users/porcelli/out")
                                .send("file:///Users/porcelli/out1")
                            .when(wildcard("bar*"))
                                .send("file:///Users/porcelli/out2")
                                .send("file:///Users/porcelli/out3")
                        .endChoice();
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(Flow.class);

        final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

        final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final MessageProcessor processor = ((Flow) flowConstruct).getMessageProcessors().iterator().next();

        assertThat(processor).isNotNull().isInstanceOf(ChoiceRouter.class);

        final List<MessageProcessorFilterPair> whenList = (List<MessageProcessorFilterPair>) getPrivateFieldValue(processor, "conditionalMessageProcessors");

        assertThat(whenList).isNotNull().isNotEmpty().hasSize(2);

        assertThat(whenList.get(0).getFilter()).isNotNull().isInstanceOf(WildcardFilter.class);

        assertThat(((WildcardFilter)whenList.get(0).getFilter()).getPattern()).isNotNull().isEqualTo("foo*");

        assertThat(whenList.get(0).getMessageProcessor()).isNotNull().isInstanceOf(MessageProcessorChain.class);

        final MessageProcessorChain whenChain2 = (MessageProcessorChain) whenList.get(0).getMessageProcessor();

        assertThat(whenChain2.getMessageProcessors().get(0)).isNotNull().isInstanceOf(OutboundEndpoint.class);
        final OutboundEndpoint outbound = (OutboundEndpoint) whenChain2.getMessageProcessors().get(0);
        assertThat(outbound.getAddress()).isEqualTo("file:///Users/porcelli/out");

        assertThat(whenChain2.getMessageProcessors().get(1)).isNotNull().isInstanceOf(OutboundEndpoint.class);
        final OutboundEndpoint outbound1 = (OutboundEndpoint) whenChain2.getMessageProcessors().get(1);
        assertThat(outbound1.getAddress()).isEqualTo("file:///Users/porcelli/out1");

        assertThat(whenList.get(1).getFilter()).isNotNull().isInstanceOf(WildcardFilter.class);

        assertThat(((WildcardFilter)whenList.get(1).getFilter()).getPattern()).isNotNull().isEqualTo("bar*");

        assertThat(whenList.get(1).getMessageProcessor()).isNotNull().isInstanceOf(MessageProcessorChain.class);

        final MessageProcessorChain whenChain3 = (MessageProcessorChain) whenList.get(1).getMessageProcessor();

        assertThat(whenChain3.getMessageProcessors().get(0)).isNotNull().isInstanceOf(OutboundEndpoint.class);
        final OutboundEndpoint outbound2 = (OutboundEndpoint) whenChain3.getMessageProcessors().get(0);
        assertThat(outbound2.getAddress()).isEqualTo("file:///Users/porcelli/out2");

        assertThat(whenChain3.getMessageProcessors().get(1)).isNotNull().isInstanceOf(OutboundEndpoint.class);
        final OutboundEndpoint outbound3 = (OutboundEndpoint) whenChain3.getMessageProcessors().get(1);
        assertThat(outbound3.getAddress()).isEqualTo("file:///Users/porcelli/out3");
    }
}
