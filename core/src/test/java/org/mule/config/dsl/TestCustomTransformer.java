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
import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.source.MessageSource;
import org.mule.api.transformer.Transformer;
import org.mule.api.transformer.TransformerException;
import org.mule.construct.SimpleFlowConstruct;
import org.mule.transformer.AbstractTransformer;

import static org.fest.assertions.Assertions.assertThat;

public class TestCustomTransformer {

    @Test
    public void customTransformerByType() throws MuleException, InterruptedException {
        final MuleContext muleContext = new Mule(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .transformWith(MyTransformer.class);
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
            assertThat(processor).isNotNull().isInstanceOf(MyTransformer.class);
        }
    }

    @Test
    public void customTransformerByInstance() throws MuleException, InterruptedException {
        final MuleContext muleContext = new Mule(new AbstractModule() {
            @Override
            public void configure() {

                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .transformWith(new MyTransformer());
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
            assertThat(processor).isNotNull().isInstanceOf(MyTransformer.class);
        }
    }

    @Test
    public void customTransformerByInjector() throws MuleException, InterruptedException {
        final MyTransformer transformer = new MyTransformer();

        final MuleContext muleContext = new Mule(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .transformWith(MyTransformer.class);

                bind(MyTransformer.class).toInstance(transformer);
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
            assertThat(processor).isNotNull().isInstanceOf(MyTransformer.class).isEqualTo(transformer);
        }
    }

    @Test
    public void customTransformerByReflection() throws MuleException, InterruptedException {
        final MuleContext muleContext = new Mule(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .transformWith(MyTransformer.class);
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
            assertThat(processor).isNotNull().isInstanceOf(MyTransformer.class);
        }
    }

    @Test(expected = RuntimeException.class)
    public void invalidConstructor() throws MuleException, InterruptedException {
        new Mule(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .transformWith(MyComplexTransformer.class);
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
                        .transformWith((Class<Transformer>) null);
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
                        .transformWith((Transformer) null);
            }
        });
    }

    public static class MyTransformer extends AbstractTransformer {
        @Override
        protected Object doTransform(final Object o, final String s) throws TransformerException {
            return "MY TRANSFORMED TEXT";
        }
    }

    public static class MyComplexTransformer extends AbstractTransformer {

        public MyComplexTransformer(final String none) {
        }

        @Override
        protected Object doTransform(final Object o, final String s) throws TransformerException {
            return "MY TRANSFORMED TEXT";
        }
    }

}
