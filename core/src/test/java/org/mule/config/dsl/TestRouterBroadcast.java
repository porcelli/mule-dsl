/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import static org.fest.assertions.Assertions.assertThat;

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
import org.mule.routing.outbound.MulticastingRouter;

public class TestRouterBroadcast {

    @Test
    public void simpleBroadcast() {
        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .broadcast()
                            .echo()
                            .echo()
                        .endBroadcast();
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

        assertThat(processor).isNotNull().isInstanceOf(MulticastingRouter.class);

        final MulticastingRouter multicastingRouter = (MulticastingRouter) processor;

        assertThat(multicastingRouter.getRoutes()).isNotEmpty().hasSize(2);

        assertThat(multicastingRouter.getRoutes().get(0)).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent echo1 = (SimpleCallableJavaComponent) multicastingRouter.getRoutes().get(0);

        assertThat(echo1.getObjectType()).isEqualTo(EchoComponent.class);

        assertThat(echo1.getObjectFactory().isSingleton()).isEqualTo(true);

        assertThat(multicastingRouter.getRoutes().get(1)).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent echo2 = (SimpleCallableJavaComponent) multicastingRouter.getRoutes().get(1);

        assertThat(echo2.getObjectType()).isEqualTo(EchoComponent.class);

        assertThat(echo2.getObjectFactory().isSingleton()).isEqualTo(true);
    }

    @Test
    public void simpleBroadcastNesting() {
        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .broadcast()
                            .echo()
                            .broadcast()
                                .echo()
                            .endBroadcast()
                            .echo()
                        .endBroadcast();
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

        assertThat(processor).isNotNull().isInstanceOf(MulticastingRouter.class);

        final MulticastingRouter multicastingRouter = (MulticastingRouter) processor;

        assertThat(multicastingRouter.getRoutes()).isNotEmpty().hasSize(3);

        assertThat(multicastingRouter.getRoutes().get(0)).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent echo1 = (SimpleCallableJavaComponent) multicastingRouter.getRoutes().get(0);

        assertThat(echo1.getObjectType()).isEqualTo(EchoComponent.class);

        assertThat(echo1.getObjectFactory().isSingleton()).isEqualTo(true);


        assertThat(multicastingRouter.getRoutes().get(1)).isNotNull().isInstanceOf(MulticastingRouter.class);

        final MulticastingRouter innerAll = (MulticastingRouter) multicastingRouter.getRoutes().get(1);

        assertThat(innerAll.getRoutes()).isNotEmpty().hasSize(1);

        assertThat(innerAll.getRoutes().get(0)).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);


        assertThat(multicastingRouter.getRoutes().get(2)).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent echo2 = (SimpleCallableJavaComponent) multicastingRouter.getRoutes().get(2);

        assertThat(echo2.getObjectType()).isEqualTo(EchoComponent.class);

        assertThat(echo2.getObjectFactory().isSingleton()).isEqualTo(true);
    }

    @Test
    public void simpleBroadcastWithSend() {
        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .broadcast()
                            .send("file:///Users/porcelli/out", ExchangePattern.ONE_WAY)
                        .endBroadcast();
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

        assertThat(processor).isNotNull().isInstanceOf(MulticastingRouter.class);

        final MulticastingRouter multicastingRouter = (MulticastingRouter) processor;

        assertThat(multicastingRouter.getRoutes()).isNotEmpty().hasSize(1);

        assertThat(multicastingRouter.getRoutes().get(0)).isNotNull().isInstanceOf(ImmutableEndpoint.class);
    }

    @Test
    public void simpleBroadcastWithExecute() {
        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .broadcast()
                            .execute(Simple.class, Scope.PROTOTYPE)
                            .execute(Simple.class, Scope.PROTOTYPE).withDefaultArg()
                        .endBroadcast();

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

        assertThat(processor).isNotNull().isInstanceOf(MulticastingRouter.class);

        final MulticastingRouter multicastingRouter = (MulticastingRouter) processor;

        assertThat(multicastingRouter.getRoutes()).isNotEmpty().hasSize(2);

        assertThat(multicastingRouter.getRoutes().get(0)).isNotNull().isInstanceOf(DefaultJavaComponent.class);
        assertThat(multicastingRouter.getRoutes().get(1)).isNotNull().isInstanceOf(DefaultJavaComponent.class);
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
