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
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.source.MessageSource;
import org.mule.config.dsl.component.FlowMessageSourceWrapper;
import org.mule.construct.SimpleFlowConstruct;
import org.mule.transport.AbstractConnector;
import org.mule.transport.polling.MessageProcessorPollingMessageReceiver;

import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.mule.config.dsl.TimePeriod.MINUTES;
import static org.mule.config.dsl.TimePeriod.SECONDS;

public class TestPoll {


    @Test
    public void simplePollFlowUsingString() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow").poll("SourceFlow").every(10, SECONDS);

                flow("SourceFlow").send("http://localhost:8080");
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(2);

        Iterator<FlowConstruct> iterator = muleContext.getRegistry().lookupFlowConstructs().iterator();

        {
            final FlowConstruct flowConstruct = iterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("SourceFlow");
            assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

            assertThat(((SimpleFlowConstruct) flowConstruct).getMessageSource()).isNull();

            assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            {
                final MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();

                assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

                final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

                assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.REQUEST_RESPONSE);

                assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("http");

                assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("http://localhost:8080");

            }
        }

        {
            final FlowConstruct flowConstruct = iterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
            assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

            final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

            {
                assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

                final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

                assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("polling");

                assertThat(inboundEndpoint.getProperty(AbstractConnector.PROPERTY_POLLING_FREQUENCY))
                        .isNotNull().isInstanceOf(Long.class);

                assertThat((Long) inboundEndpoint.getProperty(AbstractConnector.PROPERTY_POLLING_FREQUENCY)).isEqualTo(SECONDS.toMillis(10));

                assertThat(inboundEndpoint.getProperty(MessageProcessorPollingMessageReceiver.SOURCE_MESSAGE_PROCESSOR_PROPERTY_NAME))
                        .isNotNull().isInstanceOf(FlowMessageSourceWrapper.class);

                FlowMessageSourceWrapper flowWrapper =
                        (FlowMessageSourceWrapper) inboundEndpoint.getProperty(MessageProcessorPollingMessageReceiver.SOURCE_MESSAGE_PROCESSOR_PROPERTY_NAME);

                assertThat(flowWrapper.getFlowName()).isNotEmpty().isEqualTo("SourceFlow");

                assertThat(flowWrapper.getFlow()).isNotNull();

                assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isEmpty();
            }
        }
    }

    @Test
    public void simplePollFlowUsingBuilder() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                FlowDefinition fd = flow("SourceFlow").send("http://localhost:8080");

                flow("MyFlow").poll(fd).every(10, SECONDS);

            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(2);

        Iterator<FlowConstruct> iterator = muleContext.getRegistry().lookupFlowConstructs().iterator();

        {
            final FlowConstruct flowConstruct = iterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("SourceFlow");
            assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

            assertThat(((SimpleFlowConstruct) flowConstruct).getMessageSource()).isNull();

            assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            {
                final MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();

                assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

                final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

                assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.REQUEST_RESPONSE);

                assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("http");

                assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("http://localhost:8080");

            }
        }

        {
            final FlowConstruct flowConstruct = iterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
            assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

            final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

            {
                assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

                final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

                assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("polling");

                assertThat(inboundEndpoint.getProperty(AbstractConnector.PROPERTY_POLLING_FREQUENCY))
                        .isNotNull().isInstanceOf(Long.class);

                assertThat((Long) inboundEndpoint.getProperty(AbstractConnector.PROPERTY_POLLING_FREQUENCY)).isEqualTo(SECONDS.toMillis(10));

                assertThat(inboundEndpoint.getProperty(MessageProcessorPollingMessageReceiver.SOURCE_MESSAGE_PROCESSOR_PROPERTY_NAME))
                        .isNotNull().isInstanceOf(FlowMessageSourceWrapper.class);

                FlowMessageSourceWrapper flowWrapper =
                        (FlowMessageSourceWrapper) inboundEndpoint.getProperty(MessageProcessorPollingMessageReceiver.SOURCE_MESSAGE_PROCESSOR_PROPERTY_NAME);

                assertThat(flowWrapper.getFlowName()).isNotEmpty().isEqualTo("SourceFlow");

                assertThat(flowWrapper.getFlow()).isNotNull();

                assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isEmpty();
            }
        }
    }

    @Test
    public void simplePollMPUsingObj() {
        final MyCustomMP mp = new MyCustomMP();
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow").poll(mp).every(10, MINUTES);

            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        Iterator<FlowConstruct> iterator = muleContext.getRegistry().lookupFlowConstructs().iterator();

        {
            final FlowConstruct flowConstruct = iterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
            assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

            final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

            {
                assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

                final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

                assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("polling");

                assertThat(inboundEndpoint.getProperty(AbstractConnector.PROPERTY_POLLING_FREQUENCY))
                        .isNotNull().isInstanceOf(Long.class);

                assertThat((Long) inboundEndpoint.getProperty(AbstractConnector.PROPERTY_POLLING_FREQUENCY)).isEqualTo(MINUTES.toMillis(10));

                assertThat(inboundEndpoint.getProperty(MessageProcessorPollingMessageReceiver.SOURCE_MESSAGE_PROCESSOR_PROPERTY_NAME))
                        .isNotNull().isInstanceOf(MyCustomMP.class).isEqualTo(mp);

                assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isEmpty();
            }
        }
    }

    @Test
    public void simplePollMPUsingType() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow").poll(MyCustomMP.class).every(10, MINUTES);

            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        Iterator<FlowConstruct> iterator = muleContext.getRegistry().lookupFlowConstructs().iterator();

        {
            final FlowConstruct flowConstruct = iterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
            assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

            final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

            {
                assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

                final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

                assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("polling");

                assertThat(inboundEndpoint.getProperty(AbstractConnector.PROPERTY_POLLING_FREQUENCY))
                        .isNotNull().isInstanceOf(Long.class);

                assertThat((Long) inboundEndpoint.getProperty(AbstractConnector.PROPERTY_POLLING_FREQUENCY)).isEqualTo(MINUTES.toMillis(10));

                assertThat(inboundEndpoint.getProperty(MessageProcessorPollingMessageReceiver.SOURCE_MESSAGE_PROCESSOR_PROPERTY_NAME))
                        .isNotNull().isInstanceOf(MyCustomMP.class);

                assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isEmpty();
            }
        }
    }

    @Test
    public void simplePollMPUsingComplexType() {
        final MyComplexMP mp = new MyComplexMP(1, 1);
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow").poll(MyComplexMP.class).every(10, MINUTES);

                bind(MyComplexMP.class).toInstance(mp);
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        Iterator<FlowConstruct> iterator = muleContext.getRegistry().lookupFlowConstructs().iterator();

        {
            final FlowConstruct flowConstruct = iterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
            assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

            final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

            {
                assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

                final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

                assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

                assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("polling");

                assertThat(inboundEndpoint.getProperty(AbstractConnector.PROPERTY_POLLING_FREQUENCY))
                        .isNotNull().isInstanceOf(Long.class);

                assertThat((Long) inboundEndpoint.getProperty(AbstractConnector.PROPERTY_POLLING_FREQUENCY)).isEqualTo(MINUTES.toMillis(10));

                assertThat(inboundEndpoint.getProperty(MessageProcessorPollingMessageReceiver.SOURCE_MESSAGE_PROCESSOR_PROPERTY_NAME))
                        .isNotNull().isInstanceOf(MyComplexMP.class).isEqualTo(mp);

                assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isEmpty();
            }
        }
    }

    @Test
    public void nonExistingFlow() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .poll("NOT").every(10, SECONDS);
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        Iterator<FlowConstruct> iterator = muleContext.getRegistry().lookupFlowConstructs().iterator();

        final FlowConstruct flowConstruct = iterator.next();

        final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

        {
            assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

            final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

            assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("polling");

            assertThat(inboundEndpoint.getProperty(AbstractConnector.PROPERTY_POLLING_FREQUENCY))
                    .isNotNull().isInstanceOf(Long.class);

            assertThat((Long) inboundEndpoint.getProperty(AbstractConnector.PROPERTY_POLLING_FREQUENCY)).isEqualTo(SECONDS.toMillis(10));

            assertThat(inboundEndpoint.getProperty(MessageProcessorPollingMessageReceiver.SOURCE_MESSAGE_PROCESSOR_PROPERTY_NAME))
                    .isNotNull().isInstanceOf(FlowMessageSourceWrapper.class);

            assertThat(inboundEndpoint.getProperty(MessageProcessorPollingMessageReceiver.SOURCE_MESSAGE_PROCESSOR_PROPERTY_NAME))
                    .isNotNull().isInstanceOf(FlowMessageSourceWrapper.class);

            FlowMessageSourceWrapper flowWrapper =
                    (FlowMessageSourceWrapper) inboundEndpoint.getProperty(MessageProcessorPollingMessageReceiver.SOURCE_MESSAGE_PROCESSOR_PROPERTY_NAME);

            assertThat(flowWrapper.getFlowName()).isNotEmpty().isEqualTo("NOT");

            try {
                assertThat(flowWrapper.getFlow()).isNull();
                fail();
            } catch (FlowNotFoundException e) {
            }
        }
    }


    @Test(expected = RuntimeException.class)
    public void nullObj() {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .poll((MessageProcessor) null).every(10, SECONDS);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void nullClass() {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .poll((Class<MessageProcessor>) null).every(10, SECONDS);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void nullFlowBuilder() {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .poll((FlowDefinition) null).every(10, SECONDS);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void nullString() {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .poll((String) null).every(10, SECONDS);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void emptyString() {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .poll("").every(10, SECONDS);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void frequency0Duration() {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .poll(MyCustomMP.class).every(0, SECONDS);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void frequencyNullPeriod() {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .poll(MyCustomMP.class).every(1, null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void frequencyNullPeriodAnd0Duration() {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .poll(MyCustomMP.class).every(0, null);
            }
        });
    }

    public static class MyCustomMP implements MessageProcessor {

        @Override
        public MuleEvent process(final MuleEvent event) throws MuleException {
            return null;
        }
    }

    public static class MyComplexMP implements MessageProcessor {

        MyComplexMP(long value, long value2) {

        }

        @Override
        public MuleEvent process(final MuleEvent event) throws MuleException {
            return null;
        }
    }


}
