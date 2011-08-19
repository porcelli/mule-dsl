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
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.source.MessageSource;
import org.mule.construct.SimpleFlowConstruct;
import org.mule.transport.file.FileConnector;
import org.mule.transport.http.HttpConnector;

import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;
import static org.mule.config.dsl.ExchangePattern.ONE_WAY;
import static org.mule.config.dsl.ExchangePattern.REQUEST_RESPONSE;

public class TestSend {

    @Test
    public void simpleBridge() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .send("file:///Users/porcelli/out");
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

        {
            final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

            assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

            assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        {
            final MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();

            assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

            final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

            assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

            assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/out");
        }
    }

    @Test
    public void simpleEchoBridge() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .send("file:///Users/porcelli/out")
                        .send("file:///Users/porcelli/out2");
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();
        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        {
            final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

            assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

            final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

            assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

            assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(2);

        final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();


        {
            final MessageProcessor endpointProcessor = iterator.next();

            assertThat(endpointProcessor).isNotNull().isInstanceOf(OutboundEndpoint.class);

            final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) endpointProcessor;

            assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

            assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/out");
        }

        {
            final MessageProcessor endpointProcessor = iterator.next();

            assertThat(endpointProcessor).isNotNull().isInstanceOf(OutboundEndpoint.class);

            final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) endpointProcessor;

            assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

            assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/out2");
        }
    }

    @Test
    public void simpleExplicitOneWay() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .send("file:///Users/porcelli/out", ONE_WAY);
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        {
            final MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();

            assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

            final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

            assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

            assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/out");
        }
    }

    @Test
    public void outboundUsingExplicitConnector() {
        final FileConnector[] connector = new FileConnector[1];

        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                connector[0] = connector().with(FileConnector.class);
                connector[0].setPollingFrequency(10L);

                flow("MyFlow")
                        .send("file:///Users/porcelli/out", connector[0]);
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        {
            final MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();

            assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

            final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

            assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

            assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/out");

            assertThat(((FileConnector) outboundEndpoint.getConnector()).getPollingFrequency()).isEqualTo(10L);

            assertThat(outboundEndpoint.getConnector()).isNotNull().isEqualTo(connector[0]);
        }
    }

    @Test
    public void outboundUsingExplicitCustomConnectorHttp() {
        final HttpConnector[] connector = new HttpConnector[1];
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                connector[0] = connector().with(HttpConnector.class);
                connector[0].setKeepAlive(true);

                flow("MyFlow")
                        .send("http://localhost:8080", REQUEST_RESPONSE, connector[0]);
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        {
            final MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();

            assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

            final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

            assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.REQUEST_RESPONSE);

            assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("http");

            assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("http://localhost:8080");

            assertThat(((HttpConnector) outboundEndpoint.getConnector()).isKeepAlive()).isEqualTo(true);

            assertThat(outboundEndpoint.getConnector()).isNotNull().isEqualTo(connector[0]);
        }
    }

    @Test
    public void outboundUsingExplicitCustomConnectorHttpOneWay() {
        final HttpConnector[] connector = new HttpConnector[1];
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                connector[0] = connector().with(HttpConnector.class);
                connector[0].setKeepAlive(true);

                flow("MyFlow")
                        .send("http://localhost:8080", ONE_WAY, connector[0]);
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        {
            final MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();

            assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

            final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

            assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("http");

            assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("http://localhost:8080");

            assertThat(((HttpConnector) outboundEndpoint.getConnector()).isKeepAlive()).isEqualTo(true);

            assertThat(outboundEndpoint.getConnector()).isNotNull().isEqualTo(connector[0]);
        }
    }

    @Test
    public void outboundUsingExplicitCustomConnectorStringHttpOneWay() {
        final HttpConnector[] connector = new HttpConnector[1];
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                connector[0] = connector("MyHTTPConn").with(HttpConnector.class);
                connector[0].setKeepAlive(true);

                flow("MyFlow")
                        .send("http://localhost:8080", ONE_WAY, "MyHTTPConn");
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        {
            final MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();

            assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

            final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

            assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("http");

            assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("http://localhost:8080");

            assertThat(((HttpConnector) outboundEndpoint.getConnector()).isKeepAlive()).isEqualTo(true);

            assertThat(outboundEndpoint.getConnector()).isNotNull().isEqualTo(connector[0]);
        }
    }

    @Test(expected = RuntimeException.class)
    public void emptyURI() {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .send("");
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void nullURI() {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .send(null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void invalidConnector() {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                FileConnector connector = connector().with(FileConnector.class);
                connector.setRecursive(true);

                flow("MyFlow")
                        .send("http://localhost:8080", connector);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void invalidConnectorUsingString() {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                FileConnector connector = connector("MyConn").with(FileConnector.class);
                connector.setRecursive(true);

                flow("MyFlow")
                        .send("http://localhost:8080", "MyConn");
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void requestResponseNotSupported() throws InitialisationException, org.mule.api.config.ConfigurationException {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .send("file:///Users/porcelli/test", REQUEST_RESPONSE);
            }
        });
    }
}
