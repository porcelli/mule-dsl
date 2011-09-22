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
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.source.MessageSource;
import org.mule.construct.Flow;

import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;

public class TestProcessCustomMP {

    @Test
    public void simpleAnonymousMP() {
        final MessageProcessor mp = new MessageProcessor() {
            @Override
            public MuleEvent process(final MuleEvent event) throws MuleException {
                return null;
            }
        };

        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .process(mp);
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

        final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor invokeProcessor = iterator.next();

        assertThat(invokeProcessor).isEqualTo(mp);
    }

    @Test
    public void simpleCustomMPClazz() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .process(MyCustomMP.class);
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

        final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor invokeProcessor = iterator.next();

        assertThat(invokeProcessor).isNotNull().isInstanceOf(MyCustomMP.class);
    }

    @Test
    public void simpleCustomMPClazz2() {
        final MyCustomMP myCustomMP = new MyCustomMP();
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .process(myCustomMP);
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

        final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor invokeProcessor = iterator.next();

        assertThat(invokeProcessor).isNotNull().isInstanceOf(MyCustomMP.class);

        assertThat(invokeProcessor).isEqualTo(myCustomMP);
    }

    @Test(expected = RuntimeException.class)
    public void simpleExecuteMPClassNull() {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("Receiver")
                        .from("file:///Users/porcelli/test")
                        .process((Class<MessageProcessor>) null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void simpleExecuteMPNull() {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("Receiver")
                        .from("file:///Users/porcelli/test")
                        .process((MessageProcessor) null);
            }
        });
    }

    public static class MyCustomMP implements MessageProcessor {

        @Override
        public MuleEvent process(final MuleEvent event) throws MuleException {
            return null;
        }
    }
}
