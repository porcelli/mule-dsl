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

public class TestProcessMPDefinition {

    @Test
    public void simpleProcess() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("Receiver")
                        .from("file:///Users/porcelli/test")
                        .process(new MySimpleMPMessageProcessorDefinition());

            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);
        Iterator<FlowConstruct> flowIterator = muleContext.getRegistry().lookupFlowConstructs().iterator();

        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("Receiver");
            assertThat(flowConstruct).isInstanceOf(Flow.class);

            final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

            assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

            final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

            assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

            assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");

            assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            final MessageProcessor processor = ((Flow) flowConstruct).getMessageProcessors().iterator().next();

            assertThat(processor).isNotNull().isInstanceOf(MyMessageProcessor.class);
        }
    }

    @Test(expected = RuntimeException.class)
    public void simpleProcessNull() {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("Receiver")
                        .from("file:///Users/porcelli/test")
                        .process((MessageProcessorDefinition) null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void simpleExecuteFlowNull() {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("Receiver")
                        .from("file:///Users/porcelli/test")
                        .process(new MyNullMessageProcessorDefinition());
            }
        });
    }

    public static class MyNullMessageProcessorDefinition implements MessageProcessorDefinition {
    }

    public static class MySimpleMPMessageProcessorDefinition implements MessageProcessorDefinition, org.mule.config.dsl.internal.DSLBuilder<MyMessageProcessor> {

        @Override
        public MyMessageProcessor build(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
            return new MyMessageProcessor();
        }
    }

    public static class MyMessageProcessor implements MessageProcessor {

        @Override
        public MuleEvent process(MuleEvent event) throws MuleException {
            return null;
        }
    }

}