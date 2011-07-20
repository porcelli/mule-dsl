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
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.source.MessageSource;
import org.mule.component.DefaultJavaComponent;
import org.mule.component.SimpleCallableJavaComponent;
import org.mule.component.simple.EchoComponent;
import org.mule.construct.SimpleFlowConstruct;
import org.mule.routing.RoundRobin;

import static org.fest.assertions.Assertions.assertThat;

public class TestRouterRoundRobin {

    @Test
    public void simpleRoundRobin() {
        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .roundRobin()
                            .echo()
                            .echo()
                        .endRoundRobin();
            }
        });

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

        final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();

        assertThat(processor).isNotNull().isInstanceOf(RoundRobin.class);

        final RoundRobin roundRobinRouter = (RoundRobin) processor;

        assertThat(roundRobinRouter.getRoutes()).isNotEmpty().hasSize(2);

        assertThat(roundRobinRouter.getRoutes().get(0)).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent echo1 = (SimpleCallableJavaComponent) roundRobinRouter.getRoutes().get(0);

        assertThat(echo1.getObjectType()).isEqualTo(EchoComponent.class);

        assertThat(echo1.getObjectFactory().isSingleton()).isEqualTo(true);

        assertThat(roundRobinRouter.getRoutes().get(1)).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent echo2 = (SimpleCallableJavaComponent) roundRobinRouter.getRoutes().get(1);

        assertThat(echo2.getObjectType()).isEqualTo(EchoComponent.class);

        assertThat(echo2.getObjectFactory().isSingleton()).isEqualTo(true);
    }

    @Test
    public void simpleRoundRobinNesting() {
        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .roundRobin()
                            .echo()
                            .roundRobin()
                                .echo()
                            .endRoundRobin()
                            .echo()
                        .endRoundRobin();
            }
        });

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

        final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();

        assertThat(processor).isNotNull().isInstanceOf(RoundRobin.class);

        final RoundRobin roundRobinRouter = (RoundRobin) processor;

        assertThat(roundRobinRouter.getRoutes()).isNotEmpty().hasSize(3);

        assertThat(roundRobinRouter.getRoutes().get(0)).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent echo1 = (SimpleCallableJavaComponent) roundRobinRouter.getRoutes().get(0);

        assertThat(echo1.getObjectType()).isEqualTo(EchoComponent.class);

        assertThat(echo1.getObjectFactory().isSingleton()).isEqualTo(true);


        assertThat(roundRobinRouter.getRoutes().get(1)).isNotNull().isInstanceOf(RoundRobin.class);

        final RoundRobin innerAll = (RoundRobin) roundRobinRouter.getRoutes().get(1);

        assertThat(innerAll.getRoutes()).isNotEmpty().hasSize(1);

        assertThat(innerAll.getRoutes().get(0)).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);


        assertThat(roundRobinRouter.getRoutes().get(2)).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent echo2 = (SimpleCallableJavaComponent) roundRobinRouter.getRoutes().get(2);

        assertThat(echo2.getObjectType()).isEqualTo(EchoComponent.class);

        assertThat(echo2.getObjectFactory().isSingleton()).isEqualTo(true);
    }

    @Test
    public void simpleRoundRobinWithSend() {
        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .roundRobin()
                            .send("file:///Users/porcelli/out", ExchangePattern.ONE_WAY)
                        .endRoundRobin();
            }
        });

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

        final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();

        assertThat(processor).isNotNull().isInstanceOf(RoundRobin.class);

        final RoundRobin roundRobinRouter = (RoundRobin) processor;

        assertThat(roundRobinRouter.getRoutes()).isNotEmpty().hasSize(1);

        assertThat(roundRobinRouter.getRoutes().get(0)).isNotNull().isInstanceOf(ImmutableEndpoint.class);
    }

    @Test
    public void simpleRoundRobinWithInvoke() {
        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .roundRobin()
                            .invoke(Simple.class, Scope.PROTOTYPE)
                            .invoke(Simple.class, Scope.PROTOTYPE).withDefaultArg()
                        .endRoundRobin();

                bind(Simple.class).to(Simple2.class);
            }
        });

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

        final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();

        assertThat(processor).isNotNull().isInstanceOf(RoundRobin.class);

        final RoundRobin roundRobinRouter = (RoundRobin) processor;

        assertThat(roundRobinRouter.getRoutes()).isNotEmpty().hasSize(2);

        assertThat(roundRobinRouter.getRoutes().get(0)).isNotNull().isInstanceOf(DefaultJavaComponent.class);
        assertThat(roundRobinRouter.getRoutes().get(1)).isNotNull().isInstanceOf(DefaultJavaComponent.class);
    }


    public static interface Simple {
        void execute(String string);
    }

    public static class Simple2 implements Simple {
        @Override
		public void execute(final String string) {
            System.out.println("SIMPLE 2! : " + string);
        }
    }

}
