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
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.api.source.MessageSource;
import org.mule.construct.SimpleFlowConstruct;
import org.mule.routing.MessageFilter;

import static org.fest.assertions.Assertions.assertThat;

public class TestCustomFilter {

    @Test
    public void customFilterByType() throws MuleException, InterruptedException {
        final MuleContext muleContext = new Mule(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .filterWith(MyFilter.class);
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

            assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);
        }

        {
            final MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();
            assertThat(processor).isNotNull().isInstanceOf(MessageFilter.class);

            final MessageFilter messageFilter = (MessageFilter) processor;

            assertThat(messageFilter.getFilter()).isInstanceOf(MyFilter.class);
        }
    }

    @Test
    public void customFilterByInstance() throws MuleException, InterruptedException {
        final MuleContext muleContext = new Mule(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .filterWith(new MyFilter());
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

            assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);
        }

        {
            final MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();
            assertThat(processor).isNotNull().isInstanceOf(MessageFilter.class);

            final MessageFilter messageFilter = (MessageFilter) processor;

            assertThat(messageFilter.getFilter()).isInstanceOf(MyFilter.class);
        }
    }

    @Test
    public void customFilterByInjector() throws MuleException, InterruptedException {
        final MyFilter filter = new MyFilter();

        final MuleContext muleContext = new Mule(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .filterWith(MyFilter.class);

                bind(MyFilter.class).toInstance(filter);
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

            assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);
        }

        {
            final MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();
            assertThat(processor).isNotNull().isInstanceOf(MessageFilter.class);

            final MessageFilter messageFilter = (MessageFilter) processor;

            assertThat(messageFilter.getFilter()).isInstanceOf(MyFilter.class).isEqualTo(filter);
        }
    }

    @Test
    public void customFilterByReflection() throws MuleException, InterruptedException {
        final MuleContext muleContext = new Mule(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .filterWith(MyFilter.class);
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

            assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);
        }

        {
            final MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();
            assertThat(processor).isNotNull().isInstanceOf(MessageFilter.class);

            final MessageFilter messageFilter = (MessageFilter) processor;

            assertThat(messageFilter.getFilter()).isInstanceOf(MyFilter.class);
        }
    }

    @Test(expected = RuntimeException.class)
    public void invalidConstructor() throws MuleException, InterruptedException {
        new Mule(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .filterWith(MyComplexFilter.class);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void invalidTypeNull() throws MuleException, InterruptedException {
        new Mule(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .filterWith((Class<Filter>) null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void invalidInstanceNull() throws MuleException, InterruptedException {
        new Mule(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .filterWith((Filter) null);
            }
        });
    }

    public static class MyFilter implements Filter {

        @Override
        public boolean accept(final MuleMessage muleMessage) {
            return false;
        }
    }

    public static class MyComplexFilter implements Filter {

        public MyComplexFilter(final String none){

        }

        @Override
        public boolean accept(final MuleMessage muleMessage) {
            return false;
        }
    }

}
