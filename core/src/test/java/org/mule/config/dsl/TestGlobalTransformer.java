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
import org.mule.config.dsl.internal.TransformerDefinitionImpl;
import org.mule.construct.SimpleFlowConstruct;
import org.mule.transformer.AbstractTransformer;

import static org.fest.assertions.Assertions.assertThat;

public class TestGlobalTransformer {

    @Test
    public void globalReferenceTypeUsingVariable() throws MuleException, InterruptedException {
        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {

                final TransformerDefinition myTransformer = transformer("test").with(MyTransformer.class);

                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .transformWith(myTransformer);
            }
        });

        assertThat(muleContext.getRegistry().lookupTransformer("test")).isNotNull().isInstanceOf(MyTransformer.class);

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
    public void globalReferenceTypeUsingStringRef() throws MuleException, InterruptedException {
        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {

                transformer("test").with(MyTransformer.class);

                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .transformWith("test");
            }
        });

        assertThat(muleContext.getRegistry().lookupTransformer("test")).isNotNull().isInstanceOf(MyTransformer.class);

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
    public void globalReferenceInstanceUsingVariable() throws MuleException, InterruptedException {
        final MyTransformer transformer = new MyTransformer();

        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {

                final TransformerDefinition myTransformer = transformer("test").with(transformer);

                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .transformWith(myTransformer);
            }
        });

        assertThat(muleContext.getRegistry().lookupTransformer("test")).isNotNull().isInstanceOf(MyTransformer.class);

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
    public void globalReferenceInstanceUsingStringRef() throws MuleException, InterruptedException {
        final MyTransformer transformer = new MyTransformer();

        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {

                transformer("test").with(transformer);

                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .transformWith("test");
            }
        });

        assertThat(muleContext.getRegistry().lookupTransformer("test")).isNotNull().isInstanceOf(MyTransformer.class);

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
    public void globalReferenceInstanceUsingVariableByInjector() throws MuleException, InterruptedException {
        final MyTransformer transformer = new MyTransformer();

        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {

                final TransformerDefinition myTransformer = transformer("test").with(MyTransformer.class);

                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .transformWith(myTransformer);

                bind(MyTransformer.class).toInstance(transformer);
            }
        });

        assertThat(muleContext.getRegistry().lookupTransformer("test")).isNotNull().isInstanceOf(MyTransformer.class);

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
    public void globalReferenceInstanceUsingStringRefByInjector() throws MuleException, InterruptedException {
        final MyTransformer transformer = new MyTransformer();

        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {

                transformer("test").with(MyTransformer.class);

                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .transformWith("test");

                bind(MyTransformer.class).toInstance(transformer);
            }
        });

        assertThat(muleContext.getRegistry().lookupTransformer("test")).isNotNull().isInstanceOf(MyTransformer.class);

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

    @Test(expected = RuntimeException.class)
    public void invalidGlobalDefinitionConstructor() throws MuleException, InterruptedException {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {

                transformer("test").with(MyComplexTransformer.class);

                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .filterWith("test");
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void invalidGlobalDefinitionType() throws MuleException, InterruptedException {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                transformer().with((Class<Transformer>) null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void invalidGlobalDefinitionInstance() throws MuleException, InterruptedException {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                transformer().with((Transformer) null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void invalidGlobalReferenceUsingStringRef() throws MuleException, InterruptedException {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .transformWith("test");
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void invalidGlobalReferenceUsingUnboundVariable() throws MuleException, InterruptedException {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                final TransformerDefinition myTransformer = new TransformerDefinitionImpl<MyTransformer>("test", MyTransformer.class);

                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .transformWith(myTransformer);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void invalidGlobalReferenceUsingNullVariable() throws MuleException, InterruptedException {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                final TransformerDefinition myTransformer = null;

                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .transformWith(myTransformer);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void invalidGlobalReferenceUsingNullStringRef() throws MuleException, InterruptedException {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .transformWith((String) null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void invalidDuplicateGlobalDefinition() throws MuleException, InterruptedException {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {

                transformer("test").with(MyTransformer.class);

                transformer("test").with(MyTransformer.class);

                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .filterWith("test");
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
