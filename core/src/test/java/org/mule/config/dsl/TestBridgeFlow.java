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
import org.mule.api.MuleEventContext;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.lifecycle.Callable;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.source.MessageSource;
import org.mule.component.SimpleCallableJavaComponent;
import org.mule.construct.SimpleFlowConstruct;

import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;

public class TestBridgeFlow {

    @Test
    public void simpleBridge() {
        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .send("file:///Users/porcelli/out");
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

        assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

        final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

        assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/out");
    }

    @Test
    public void simpleEchoBridge() {
        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .echo()
                        .send("file:///Users/porcelli/out");
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

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(2);

        final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor echoProcessor = iterator.next();

        assertThat(echoProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor endpointProcessor = iterator.next();

        assertThat(endpointProcessor).isNotNull().isInstanceOf(OutboundEndpoint.class);

        final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) endpointProcessor;

        assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/out");

    }

    @Test
    public void simpleServiceBridgeObjectInstance() {
        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                final SimpleCallable myCallable = new SimpleCallable();
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(myCallable)
                        .echo()
                        .send("file:///Users/porcelli/out", ExchangePattern.ONE_WAY)
                        .send("file:///Users/porcelli/out2", ExchangePattern.ONE_WAY);
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

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(4);

        final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor myCallableProcessor = iterator.next();

        assertThat(myCallableProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor echoProcessor = iterator.next();

        assertThat(echoProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor endpointProcessor = iterator.next();

        assertThat(endpointProcessor).isNotNull().isInstanceOf(OutboundEndpoint.class);

        final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) endpointProcessor;

        assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/out");

        final OutboundEndpoint outboundEndpoint2 = (OutboundEndpoint) endpointProcessor;

        assertThat(outboundEndpoint2.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(outboundEndpoint2.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(outboundEndpoint2.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/out");
    }

    public static class SimpleCallable implements Callable {

        @Override
        public Object onCall(final MuleEventContext muleEventContext) throws Exception {
            System.out.println("AQUI!");
            return muleEventContext.getMessage().getPayload();
        }
    }

}
