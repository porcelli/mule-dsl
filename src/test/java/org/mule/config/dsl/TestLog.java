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
import org.mule.api.MuleContext;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleException;
import org.mule.api.config.ConfigurationException;
import org.mule.api.lifecycle.Callable;

public class TestLog {

    @Test
    public void simpleBridge() throws MuleException, ConfigurationException, InterruptedException {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .transformTo(String.class)
                        .execute(SimpleCallable.class);
            }
        });

        muleContext.start();

        Thread.sleep(10000);

//        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);
//
//        FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();
//
//        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
//        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);
//
//        MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();
//
//        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);
//
//        InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;
//
//        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);
//
//        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");
//
//        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");
//
//        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);
//
//        MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();
//
//        assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);
//
//        OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;
//
//        assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);
//
//        assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");
//
//        assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/out");
    }



    public static class SimpleCallable implements Callable {

        @Override
        public Object onCall(MuleEventContext muleEventContext) throws Exception {
            System.out.println(muleEventContext.getMessage().getPayload().getClass().getName());
            System.out.println((String) muleEventContext.getMessage().getPayload());
            return muleEventContext.getMessage().getPayload();
        }
    }

}
