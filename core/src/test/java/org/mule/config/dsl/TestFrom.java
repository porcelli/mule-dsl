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
import org.mule.api.config.ConfigurationException;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.source.MessageSource;
import org.mule.construct.Flow;
import org.mule.transport.file.FileConnector;
import org.mule.transport.http.HttpConnector;

import static org.fest.assertions.Assertions.assertThat;
import static org.mule.config.dsl.ExchangePattern.ONE_WAY;
import static org.mule.config.dsl.ExchangePattern.REQUEST_RESPONSE;

public class TestFrom {


    @Test
    public void simpleInbound() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow").from("file:///Users/porcelli/test");
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(Flow.class);

        final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

        final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isEmpty();
    }

    @Test
    public void simpleOneWayInbound() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test", ONE_WAY);
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(Flow.class);

        final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

        final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isEmpty();
    }

    @Test
    public void inboundUsingExplicitCustomConnector() {
        final FileConnector[] connector = new FileConnector[1];
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                connector[0] = connector().with(FileConnector.class);
                connector[0].setPollingFrequency(10L);

                flow("MyFlow")
                        .from("file:///Users/porcelli/test", connector[0]);
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(Flow.class);

        final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

        final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");

        assertThat(inboundEndpoint.getConnector()).isNotNull().isEqualTo(connector[0]);

        assertThat(((FileConnector) inboundEndpoint.getConnector()).getPollingFrequency()).isEqualTo(10L);

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isEmpty();
    }

    @Test
    public void inboundUsingExplicitCustomConnectorHttp() {
        final HttpConnector[] connector = new HttpConnector[1];
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                connector[0] = connector().with(HttpConnector.class);
                connector[0].setKeepAlive(true);

                flow("MyFlow")
                        .from("http://localhost:8080", REQUEST_RESPONSE, connector[0]);
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(Flow.class);

        final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

        final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.REQUEST_RESPONSE);

        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("http");

        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("http://localhost:8080");

        assertThat(inboundEndpoint.getConnector()).isNotNull().isEqualTo(connector[0]);

        assertThat(((HttpConnector) inboundEndpoint.getConnector()).isKeepAlive()).isEqualTo(true);

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isEmpty();
    }

    @Test
    public void inboundUsingExplicitCustomConnectorHttpOneWay() {
        final HttpConnector[] connector = new HttpConnector[1];
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                connector[0] = connector().with(HttpConnector.class);
                connector[0].setKeepAlive(true);

                flow("MyFlow")
                        .from("http://localhost:8080", ONE_WAY, connector[0]);
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(Flow.class);

        final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

        final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("http");

        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("http://localhost:8080");

        assertThat(inboundEndpoint.getConnector()).isNotNull().isEqualTo(connector[0]);

        assertThat(((HttpConnector) inboundEndpoint.getConnector()).isKeepAlive()).isEqualTo(true);

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isEmpty();
    }

    @Test
    public void inboundUsingExplicitCustomConnectorStringHttpOneWay() {
        final HttpConnector[] connector = new HttpConnector[1];
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                connector[0] = connector("MyHTTPConn").with(HttpConnector.class);
                connector[0].setKeepAlive(true);

                flow("MyFlow")
                        .from("http://localhost:8080", ONE_WAY, "MyHTTPConn");
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(Flow.class);

        final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

        final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("http");

        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("http://localhost:8080");

        assertThat(inboundEndpoint.getConnector()).isNotNull().isEqualTo(connector[0]);

        assertThat(((HttpConnector) inboundEndpoint.getConnector()).isKeepAlive()).isEqualTo(true);

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isEmpty();
    }

    @Test
    public void inboundUsingExplicitCustomConnectorRefByString() {
        final FileConnector[] connector = new FileConnector[1];
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                connector[0] = connector("MyFileConn").with(FileConnector.class);
                connector[0].setPollingFrequency(10L);

                flow("MyFlow")
                        .from("file:///Users/porcelli/test", "MyFileConn");
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(Flow.class);

        final MessageSource messageSource = ((Flow) flowConstruct).getMessageSource();

        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

        final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");

        assertThat(inboundEndpoint.getConnector()).isNotNull().isEqualTo(connector[0]);

        assertThat(((FileConnector) inboundEndpoint.getConnector()).getPollingFrequency()).isEqualTo(10L);

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isEmpty();
    }


    @Test(expected = RuntimeException.class)
    public void emptyURI() {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("");
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void nullURI() {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from(null);
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
                        .from("http://localhost:8080", connector);
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
                        .from("http://localhost:8080", "MyConn");
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void requestResponseNotSupported() throws InitialisationException, ConfigurationException {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test", REQUEST_RESPONSE);
            }
        });
    }
}
