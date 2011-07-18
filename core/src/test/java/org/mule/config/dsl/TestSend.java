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
import org.mule.api.source.MessageSource;
import org.mule.construct.SimpleFlowConstruct;

import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;

public class TestSend {

    @Test
    public void simpleBridge() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .send("file:///Users/porcelli/out");
            }
        });

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

        InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();

        assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

        OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

        assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/out");
    }

    @Test
    public void simpleEchoBridge() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .send("file:///Users/porcelli/out")
                        .send("file:///Users/porcelli/out2");
            }
        });

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

        InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(2);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        MessageProcessor endpointProcessor1 = iterator.next();

        assertThat(endpointProcessor1).isNotNull().isInstanceOf(OutboundEndpoint.class);

        OutboundEndpoint outboundEndpoint1 = (OutboundEndpoint) endpointProcessor1;

        assertThat(outboundEndpoint1.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(outboundEndpoint1.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(outboundEndpoint1.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/out");

        MessageProcessor endpointProcessor2 = iterator.next();

        assertThat(endpointProcessor2).isNotNull().isInstanceOf(OutboundEndpoint.class);

        OutboundEndpoint outboundEndpoint2 = (OutboundEndpoint) endpointProcessor2;

        assertThat(outboundEndpoint2.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(outboundEndpoint2.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(outboundEndpoint2.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/out2");

    }

}