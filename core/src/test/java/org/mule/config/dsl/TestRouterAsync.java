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
import org.mule.api.processor.MessageProcessorChain;
import org.mule.api.source.MessageSource;
import org.mule.component.SimpleCallableJavaComponent;
import org.mule.component.simple.EchoComponent;
import org.mule.config.dsl.hack.PrivateAccessor;
import org.mule.construct.SimpleFlowConstruct;
import org.mule.processor.AsyncDelegateMessageProcessor;

import static org.fest.assertions.Assertions.assertThat;

public class TestRouterAsync {

    @Test
    public void simpleAsync() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .async()
                            .echo()
                            .echo()
                        .endAsync();
            }
        });

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        {
            MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

            assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

            InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

            assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

            assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");

            assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);
        }

        MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();

        assertThat(processor).isNotNull().isInstanceOf(AsyncDelegateMessageProcessor.class);

        {
            AsyncDelegateMessageProcessor asyncRouter = (AsyncDelegateMessageProcessor) processor;

            MessageProcessorChain chain = (MessageProcessorChain) PrivateAccessor.getPrivateFieldValue(asyncRouter, "delegate");

            assertThat(chain.getMessageProcessors()).isNotEmpty().hasSize(2);

            {
                assertThat(chain.getMessageProcessors().get(0)).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

                SimpleCallableJavaComponent echo = (SimpleCallableJavaComponent) chain.getMessageProcessors().get(0);

                assertThat(echo.getObjectType()).isEqualTo(EchoComponent.class);

                assertThat(echo.getObjectFactory().isSingleton()).isEqualTo(true);
            }
            {
                assertThat(chain.getMessageProcessors().get(1)).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

                SimpleCallableJavaComponent echo = (SimpleCallableJavaComponent) chain.getMessageProcessors().get(1);

                assertThat(echo.getObjectType()).isEqualTo(EchoComponent.class);

                assertThat(echo.getObjectFactory().isSingleton()).isEqualTo(true);
            }
        }
    }

    @Test
    public void simpleAsyncNesting() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .async()
                            .echo()
                            .async()
                                .echo()
                            .endAsync()
                            .echo()
                        .endAsync();
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

        assertThat(processor).isNotNull().isInstanceOf(AsyncDelegateMessageProcessor.class);

        {
            AsyncDelegateMessageProcessor asyncRouter = (AsyncDelegateMessageProcessor) processor;

            MessageProcessorChain chain = (MessageProcessorChain) PrivateAccessor.getPrivateFieldValue(asyncRouter, "delegate");

            assertThat(chain.getMessageProcessors()).isNotEmpty().hasSize(3);

            {
                assertThat(chain.getMessageProcessors().get(0)).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

                SimpleCallableJavaComponent echo = (SimpleCallableJavaComponent) chain.getMessageProcessors().get(0);

                assertThat(echo.getObjectType()).isEqualTo(EchoComponent.class);

                assertThat(echo.getObjectFactory().isSingleton()).isEqualTo(true);
            }

            {
                assertThat(chain.getMessageProcessors().get(1)).isNotNull().isInstanceOf(AsyncDelegateMessageProcessor.class);

                AsyncDelegateMessageProcessor innerAsync = (AsyncDelegateMessageProcessor) chain.getMessageProcessors().get(1);

                MessageProcessorChain innerChain = (MessageProcessorChain) PrivateAccessor.getPrivateFieldValue(innerAsync, "delegate");

                assertThat(innerChain.getMessageProcessors()).isNotEmpty().hasSize(1);

                {
                    assertThat(innerChain.getMessageProcessors().get(0)).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

                    SimpleCallableJavaComponent echo = (SimpleCallableJavaComponent) innerChain.getMessageProcessors().get(0);

                    assertThat(echo.getObjectType()).isEqualTo(EchoComponent.class);

                    assertThat(echo.getObjectFactory().isSingleton()).isEqualTo(true);
                }

            }

            {
                assertThat(chain.getMessageProcessors().get(2)).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

                SimpleCallableJavaComponent echo = (SimpleCallableJavaComponent) chain.getMessageProcessors().get(2);

                assertThat(echo.getObjectType()).isEqualTo(EchoComponent.class);

                assertThat(echo.getObjectFactory().isSingleton()).isEqualTo(true);
            }
        }
    }

}
