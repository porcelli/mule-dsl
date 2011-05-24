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
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .echo();
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

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        MessageProcessor logProcessor = iterator.next();

        assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent echo = (SimpleCallableJavaComponent) logProcessor;

        assertThat(echo.getObjectType()).isEqualTo(EchoComponent.class);

        assertThat(echo.getObjectFactory().isSingleton()).isEqualTo(true);

        EchoComponent echo1 = (EchoComponent) echo.getObjectFactory().getInstance(null);
        EchoComponent echo2 = (EchoComponent) echo.getObjectFactory().getInstance(null);

        assertThat(echo1 == echo2).isEqualTo(true);
    }

    @Test
    public void simpleEchoChain() throws Exception {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .echo()
                        .echo();
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

        MessageProcessor echoProcessor = iterator.next();

        assertThat(echoProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent echo = (SimpleCallableJavaComponent) echoProcessor;

        assertThat(echo.getObjectType()).isEqualTo(EchoComponent.class);

        assertThat(echo.getObjectFactory().isSingleton()).isEqualTo(true);

        EchoComponent echo_1 = (EchoComponent) echo.getObjectFactory().getInstance(null);
        EchoComponent echo_2 = (EchoComponent) echo.getObjectFactory().getInstance(null);

        assertThat(echo_1 == echo_2).isEqualTo(true);

        MessageProcessor echoProcessor2 = iterator.next();

        assertThat(echoProcessor2).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent log2 = (SimpleCallableJavaComponent) echoProcessor2;

        assertThat(log2.getObjectType()).isEqualTo(EchoComponent.class);

        assertThat(log2.getObjectFactory().isSingleton()).isEqualTo(true);

        EchoComponent echo2_1 = (EchoComponent) log2.getObjectFactory().getInstance(null);
        EchoComponent echo2_2 = (EchoComponent) log2.getObjectFactory().getInstance(null);

        assertThat(echo2_1 == echo2_2).isEqualTo(true);

        assertThat(echo_1 != echo2_1).isEqualTo(true);
        assertThat(echo_2 != echo2_2).isEqualTo(true);
    }

}