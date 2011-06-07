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
import org.mule.config.dsl.internal.FilterDefinitionImpl;
import org.mule.construct.SimpleFlowConstruct;
import org.mule.routing.MessageFilter;

import static org.fest.assertions.Assertions.assertThat;

public class TestGlobalFilter {

    @Test
    public void globalReferenceTypeUsingVariable() throws MuleException, InterruptedException {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {

                FilterDefinition<MyFilter> myFilter = filter("test").with(MyFilter.class);

                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .filterWith(myFilter);
            }
        });

        {
            assertThat(muleContext.getRegistry().lookupObject("test")).isNotNull().isInstanceOf(MessageFilter.class);

            MessageFilter messageFilter = muleContext.getRegistry().lookupObject("test");

            assertThat(messageFilter.getFilter()).isInstanceOf(MyFilter.class);
        }

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

        {
            MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();
            assertThat(processor).isNotNull().isInstanceOf(MessageFilter.class);

            MessageFilter messageFilter = (MessageFilter) processor;

            assertThat(messageFilter.getFilter()).isInstanceOf(MyFilter.class);
        }
    }

    @Test
    public void globalReferenceTypeUsingStringRef() throws MuleException, InterruptedException {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {

                filter("test").with(MyFilter.class);

                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .filterWith("test");
            }
        });

        {
            assertThat(muleContext.getRegistry().lookupObject("test")).isNotNull().isInstanceOf(MessageFilter.class);

            MessageFilter messageFilter = muleContext.getRegistry().lookupObject("test");

            assertThat(messageFilter.getFilter()).isInstanceOf(MyFilter.class);
        }

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

        {
            MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();
            assertThat(processor).isNotNull().isInstanceOf(MessageFilter.class);

            MessageFilter messageFilter = (MessageFilter) processor;

            assertThat(messageFilter.getFilter()).isInstanceOf(MyFilter.class);
        }
    }

    @Test
    public void globalReferenceInstanceUsingVariable() throws MuleException, InterruptedException {
        final MyFilter filter = new MyFilter();

        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {

                FilterDefinition<MyFilter> myFilter = filter("test").with(filter);

                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .filterWith(myFilter);
            }
        });

        {
            assertThat(muleContext.getRegistry().lookupObject("test")).isNotNull().isInstanceOf(MessageFilter.class);

            MessageFilter messageFilter = muleContext.getRegistry().lookupObject("test");

            assertThat(messageFilter.getFilter()).isInstanceOf(MyFilter.class);
        }

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

        {
            MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();
            assertThat(processor).isNotNull().isInstanceOf(MessageFilter.class);

            MessageFilter messageFilter = (MessageFilter) processor;

            assertThat(messageFilter.getFilter()).isInstanceOf(MyFilter.class).isEqualTo(filter);
        }
    }

    @Test
    public void globalReferenceInstanceUsingStringRef() throws MuleException, InterruptedException {
        final MyFilter filter = new MyFilter();

        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {

                filter("test").with(filter);

                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .filterWith("test");
            }
        });

        {
            assertThat(muleContext.getRegistry().lookupObject("test")).isNotNull().isInstanceOf(MessageFilter.class);

            MessageFilter messageFilter = muleContext.getRegistry().lookupObject("test");

            assertThat(messageFilter.getFilter()).isInstanceOf(MyFilter.class);
        }

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

        {
            MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();
            assertThat(processor).isNotNull().isInstanceOf(MessageFilter.class);

            MessageFilter messageFilter = (MessageFilter) processor;

            assertThat(messageFilter.getFilter()).isInstanceOf(MyFilter.class).isEqualTo(filter);
        }
    }

    @Test
    public void globalReferenceInstanceUsingVariableByInjector() throws MuleException, InterruptedException {
        final MyFilter filter = new MyFilter();

        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {

                FilterDefinition<MyFilter> myFilter = filter("test").with(MyFilter.class);

                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .filterWith(myFilter);

                bind(MyFilter.class).toInstance(filter);
            }
        });

        {
            assertThat(muleContext.getRegistry().lookupObject("test")).isNotNull().isInstanceOf(MessageFilter.class);

            MessageFilter messageFilter = muleContext.getRegistry().lookupObject("test");

            assertThat(messageFilter.getFilter()).isInstanceOf(MyFilter.class);
        }

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

        {
            MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();
            assertThat(processor).isNotNull().isInstanceOf(MessageFilter.class);

            MessageFilter messageFilter = (MessageFilter) processor;

            assertThat(messageFilter.getFilter()).isInstanceOf(MyFilter.class).isEqualTo(filter);
        }
    }

    @Test
    public void globalReferenceInstanceUsingStringRefByInjector() throws MuleException, InterruptedException {
        final MyFilter filter = new MyFilter();

        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {

                filter("test").with(MyFilter.class);

                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .filterWith("test");

                bind(MyFilter.class).toInstance(filter);
            }
        });

        {
            assertThat(muleContext.getRegistry().lookupObject("test")).isNotNull().isInstanceOf(MessageFilter.class);

            MessageFilter messageFilter = muleContext.getRegistry().lookupObject("test");

            assertThat(messageFilter.getFilter()).isInstanceOf(MyFilter.class);
        }

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

        {
            MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();
            assertThat(processor).isNotNull().isInstanceOf(MessageFilter.class);

            MessageFilter messageFilter = (MessageFilter) processor;

            assertThat(messageFilter.getFilter()).isInstanceOf(MyFilter.class);
        }
    }

    @Test(expected = RuntimeException.class)
    public void invalidGlobalDefinitionConstructor() throws MuleException, InterruptedException {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {

                filter("test").with(MyComplexFilter.class);

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
                filter().with((Class<Filter>) null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void invalidGlobalDefinitionInstance() throws MuleException, InterruptedException {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                filter().with((Filter) null);
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
                        .filterWith("test");
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void invalidGlobalReferenceUsingUnboundVariable() throws MuleException, InterruptedException {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                FilterDefinition<MyFilter> myFilter = new FilterDefinitionImpl<MyFilter>("test", MyFilter.class);

                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .filterWith(myFilter);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void invalidGlobalReferenceUsingNullVariable() throws MuleException, InterruptedException {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                FilterDefinition<MyFilter> myFilter = null;

                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .filterWith(myFilter);
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
                        .filterWith((String) null);
            }
        });
    }

    public static class MyFilter implements Filter {

        @Override
        public boolean accept(MuleMessage muleMessage) {
            return false;
        }
    }

    public static class MyComplexFilter implements Filter {

        public MyComplexFilter(String none) {

        }

        @Override
        public boolean accept(MuleMessage muleMessage) {
            return false;
        }
    }


}
