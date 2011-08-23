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
import org.mule.api.MuleException;

import static org.fest.assertions.Assertions.assertThat;
import static org.mule.config.dsl.expression.CoreExpr.regex;

public class TestOnException {

    @Test
    public void simpleConfig() throws MuleException, InterruptedException {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/testIn")
                        .transformTo(String.class)
                        .log()
                        .choice()
                            .when(regex("foo*"))
                                .invoke(new MyClass()).methodName("execute").withoutArgs()
                                .onException().process("MyErrorHandler");

                flow("MyErrorHandler")
                        .log(LogLevel.ERROR)
                        .log("ERROR HANDLING AQUI!!!", LogLevel.ERROR)
                        .send("vm://err-out");
            }
        }).advanced().muleContext();
//        muleContext.start();
//
//        Thread.sleep(10000);

//        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(2);
//
//        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();
//
//        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
//        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);
//
//        {
//            final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();
//
//            assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);
//
//            final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;
//
//            assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);
//
//            assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");
//
//            assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");
//
//            assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);
//        }
//
//        {
//            final MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();
//            assertThat(processor).isNotNull().isInstanceOf(MessageFilter.class);
//
//            final MessageFilter messageFilter = (MessageFilter) processor;
//
//            assertThat(messageFilter.getFilter()).isInstanceOf(MyFilter.class);
//        }
    }

    public static class MyClass {
        public void execute() {
            throw new RuntimeException("MY TEST EXCEPTION INSIDE MYCLASS.CLASS");
        }
    }

//    @Test(expected = RuntimeException.class)
//    public void invalidInstanceNull() throws MuleException, InterruptedException {
//        Mule.newInstance(new AbstractModule() {
//            @Override
//            public void configure() {
//                flow("MyFlow")
//                        .from("file:///Users/porcelli/test")
//                        .filterWith((Filter) null);
//            }
//        });
//    }
}
