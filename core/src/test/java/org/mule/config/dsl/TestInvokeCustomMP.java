/*
 * ---------------------------------------------------------------------------
 *  Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;

public class TestInvokeCustomMP {

    @Test(expected = RuntimeException.class)
    public void simpleAnonymousMP() {
        final MessageProcessor mp = new MessageProcessor() {
            @Override
            public MuleEvent process(final MuleEvent event) throws MuleException {
                return null;
            }
        };

        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(mp);
            }
        });

//        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);
//
//        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();
//
//        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
//        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);
//
//        final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();
//
//        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);
//
//        final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;
//
//        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);
//
//        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");
//
//        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");
//
//        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);
//
//        final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();
//
//        final MessageProcessor invokeProcessor = iterator.next();
//
//        assertThat(invokeProcessor).isEqualTo(mp);
    }

    @Test(expected = RuntimeException.class)
    public void simpleCustomMPClazz() {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(MyCustomMP.class);
            }
        });

//        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);
//
//        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();
//
//        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
//        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);
//
//        final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();
//
//        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);
//
//        final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;
//
//        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);
//
//        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");
//
//        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");
//
//        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);
//
//        final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();
//
//        final MessageProcessor invokeProcessor = iterator.next();
//
//        assertThat(invokeProcessor).isNotNull().isInstanceOf(MyCustomMP.class);
    }

    @Test(expected = RuntimeException.class)
    public void simpleCustomMPClazz2() {
        final MyCustomMP myCustomMP = new MyCustomMP();
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(myCustomMP);
            }
        });

//        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);
//
//        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();
//
//        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
//        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);
//
//        final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();
//
//        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);
//
//        final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;
//
//        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);
//
//        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");
//
//        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");
//
//        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);
//
//        final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();
//
//        final MessageProcessor invokeProcessor = iterator.next();
//
//        assertThat(invokeProcessor).isNotNull().isInstanceOf(MyCustomMP.class);
//
//        assertThat(invokeProcessor).isEqualTo(myCustomMP);
    }

    public static class MyCustomMP implements MessageProcessor {

        @Override
        public MuleEvent process(final MuleEvent event) throws MuleException {
            return null;
        }
    }
}
