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
import org.mule.component.SimpleCallableJavaComponent;
import org.mule.component.simple.EchoComponent;
import org.mule.construct.SimpleFlowConstruct;

import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;

public class TestEcho {

    @Test
    public void simpleEcho() throws Exception {
        final MuleContext muleContext = new Mule(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .echo();
            }
        }).advanced().muleContext();

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

        final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor logProcessor = iterator.next();

        assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent echo = (SimpleCallableJavaComponent) logProcessor;

        assertThat(echo.getObjectType()).isEqualTo(EchoComponent.class);

        assertThat(echo.getObjectFactory().isSingleton()).isEqualTo(true);

        final EchoComponent echo1 = (EchoComponent) echo.getObjectFactory().getInstance(null);
        final EchoComponent echo2 = (EchoComponent) echo.getObjectFactory().getInstance(null);

        assertThat(echo1 == echo2).isEqualTo(true);
    }

    @Test
    public void simpleEchoChain() throws Exception {
        final MuleContext muleContext = new Mule(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .echo()
                        .echo();
            }
        }).advanced().muleContext();

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

        final SimpleCallableJavaComponent echo = (SimpleCallableJavaComponent) echoProcessor;

        assertThat(echo.getObjectType()).isEqualTo(EchoComponent.class);

        assertThat(echo.getObjectFactory().isSingleton()).isEqualTo(true);

        final EchoComponent echo_1 = (EchoComponent) echo.getObjectFactory().getInstance(null);
        final EchoComponent echo_2 = (EchoComponent) echo.getObjectFactory().getInstance(null);

        assertThat(echo_1 == echo_2).isEqualTo(true);

        final MessageProcessor echoProcessor2 = iterator.next();

        assertThat(echoProcessor2).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent log2 = (SimpleCallableJavaComponent) echoProcessor2;

        assertThat(log2.getObjectType()).isEqualTo(EchoComponent.class);

        assertThat(log2.getObjectFactory().isSingleton()).isEqualTo(true);

        final EchoComponent echo2_1 = (EchoComponent) log2.getObjectFactory().getInstance(null);
        final EchoComponent echo2_2 = (EchoComponent) log2.getObjectFactory().getInstance(null);

        assertThat(echo2_1 == echo2_2).isEqualTo(true);

        assertThat(echo_1 != echo2_1).isEqualTo(true);
        assertThat(echo_2 != echo2_2).isEqualTo(true);
    }

}