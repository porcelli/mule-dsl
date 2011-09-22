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
import org.mule.api.MuleEventContext;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.lifecycle.Callable;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.source.MessageSource;
import org.mule.component.DefaultJavaComponent;
import org.mule.component.SimpleCallableJavaComponent;
import org.mule.construct.Flow;

import javax.inject.Singleton;
import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;
import static org.mule.config.dsl.Scope.PROTOTYPE;
import static org.mule.config.dsl.Scope.SINGLETON;

public class TestInvokeSimple {

    @Test
    public void simpleObjectInvoke() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(new Simple2());
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

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor invokeProcessor = iterator.next();

        assertThat(invokeProcessor).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        final DefaultJavaComponent invoke = (DefaultJavaComponent) invokeProcessor;

        assertThat(invoke.getObjectType()).isEqualTo(Simple2.class);

        assertThat(invoke.getObjectFactory().isSingleton()).isEqualTo(true);
    }


    @Test
    public void simpleChainObjectInvoke() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(new Simple2())
                        .invoke(new Simple3());
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

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(2);

        final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor invokeProcessor1 = iterator.next();

        assertThat(invokeProcessor1).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        final DefaultJavaComponent invoke1 = (DefaultJavaComponent) invokeProcessor1;

        assertThat(invoke1.getObjectType()).isEqualTo(Simple2.class);

        assertThat(invoke1.getObjectFactory().isSingleton()).isEqualTo(true);

        final MessageProcessor invokeProcessor2 = iterator.next();

        assertThat(invokeProcessor2).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        final DefaultJavaComponent invoke2 = (DefaultJavaComponent) invokeProcessor2;

        assertThat(invoke2.getObjectType()).isEqualTo(Simple3.class);

        assertThat(invoke2.getObjectFactory().isSingleton()).isEqualTo(true);
    }

    @Test
    public void simpleCallableObjectInvoke() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(new SimpleCallable());
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

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor invokeProcessor = iterator.next();

        assertThat(invokeProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent invoke = (SimpleCallableJavaComponent) invokeProcessor;

        assertThat(invoke.getObjectType()).isEqualTo(SimpleCallable.class);

        assertThat(invoke.getObjectFactory().isSingleton()).isEqualTo(true);
    }


    @Test
    public void simpleChainCallableObjectInvoke() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(new SimpleCallable())
                        .invoke(new SimpleCallable());
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

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(2);

        final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor invokeProcessor1 = iterator.next();

        assertThat(invokeProcessor1).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent invoke1 = (SimpleCallableJavaComponent) invokeProcessor1;

        assertThat(invoke1.getObjectType()).isEqualTo(SimpleCallable.class);

        assertThat(invoke1.getObjectFactory().isSingleton()).isEqualTo(true);

        final MessageProcessor invokeProcessor2 = iterator.next();

        assertThat(invokeProcessor2).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent invoke2 = (SimpleCallableJavaComponent) invokeProcessor2;

        assertThat(invoke2.getObjectType()).isEqualTo(SimpleCallable.class);

        assertThat(invoke2.getObjectFactory().isSingleton()).isEqualTo(true);
    }


    @Test
    public void simpleCallableClassInvoke() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(SimpleCallable.class);
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

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor invokeProcessor = iterator.next();

        assertThat(invokeProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent invoke = (SimpleCallableJavaComponent) invokeProcessor;

        assertThat(invoke.getObjectType()).isEqualTo(SimpleCallable.class);

        assertThat(invoke.getObjectFactory().isSingleton()).isEqualTo(false);
    }

    @Test
    public void simpleCallableClassInjectedInvoke() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(ComplexCallable.class);

                bind(ComplexCallable.class).toInstance(new ComplexCallable(null));
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

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor invokeProcessor = iterator.next();

        assertThat(invokeProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent invoke = (SimpleCallableJavaComponent) invokeProcessor;

        assertThat(invoke.getObjectType()).isEqualTo(ComplexCallable.class);

        assertThat(invoke.getObjectFactory().isSingleton()).isEqualTo(true);
    }

    @Test
    public void simpleChainCallableClassInvoke() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(SimpleCallable.class)
                        .invoke(SimpleCallable.class);
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

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(2);

        final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor invokeProcessor1 = iterator.next();

        assertThat(invokeProcessor1).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent invoke1 = (SimpleCallableJavaComponent) invokeProcessor1;

        assertThat(invoke1.getObjectType()).isEqualTo(SimpleCallable.class);

        assertThat(invoke1.getObjectFactory().isSingleton()).isEqualTo(false);

        final MessageProcessor invokeProcessor2 = iterator.next();

        assertThat(invokeProcessor2).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent invoke2 = (SimpleCallableJavaComponent) invokeProcessor2;

        assertThat(invoke2.getObjectType()).isEqualTo(SimpleCallable.class);

        assertThat(invoke2.getObjectFactory().isSingleton()).isEqualTo(false);
    }

    @Test
    public void simpleClassInvoke() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(Simple2.class);
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

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor invokeProcessor = iterator.next();

        assertThat(invokeProcessor).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        final DefaultJavaComponent invoke = (DefaultJavaComponent) invokeProcessor;

        assertThat(invoke.getObjectType()).isEqualTo(Simple2.class);

        assertThat(invoke.getObjectFactory().isSingleton()).isEqualTo(false);
    }


    @Test
    public void simpleChainClassInvoke() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(Simple2.class)
                        .invoke(Simple3.class);
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

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(2);

        final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor invokeProcessor1 = iterator.next();

        assertThat(invokeProcessor1).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        final DefaultJavaComponent invoke1 = (DefaultJavaComponent) invokeProcessor1;

        assertThat(invoke1.getObjectType()).isEqualTo(Simple2.class);

        assertThat(invoke1.getObjectFactory().isSingleton()).isEqualTo(false);

        final MessageProcessor invokeProcessor2 = iterator.next();

        assertThat(invokeProcessor2).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        final DefaultJavaComponent invoke2 = (DefaultJavaComponent) invokeProcessor2;

        assertThat(invoke2.getObjectType()).isEqualTo(Simple3.class);

        assertThat(invoke2.getObjectFactory().isSingleton()).isEqualTo(false);
    }

    @Test
    public void simpleClassPrototypeInvoke() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(Complex2.class, PROTOTYPE);
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

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor invokeProcessor = iterator.next();

        assertThat(invokeProcessor).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        final DefaultJavaComponent invoke = (DefaultJavaComponent) invokeProcessor;

        assertThat(invoke.getObjectType()).isEqualTo(Complex2.class);

        assertThat(invoke.getObjectFactory().isSingleton()).isEqualTo(false);
    }


    @Test
    public void simpleChainClassPrototypeInvoke() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(Complex2.class, PROTOTYPE)
                        .invoke(Complex2.class, PROTOTYPE);
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

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(2);

        final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor invokeProcessor1 = iterator.next();

        assertThat(invokeProcessor1).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        final DefaultJavaComponent invoke1 = (DefaultJavaComponent) invokeProcessor1;

        assertThat(invoke1.getObjectType()).isEqualTo(Complex2.class);

        assertThat(invoke1.getObjectFactory().isSingleton()).isEqualTo(false);

        final MessageProcessor invokeProcessor2 = iterator.next();

        assertThat(invokeProcessor2).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        final DefaultJavaComponent invoke2 = (DefaultJavaComponent) invokeProcessor2;

        assertThat(invoke2.getObjectType()).isEqualTo(Complex2.class);

        assertThat(invoke2.getObjectFactory().isSingleton()).isEqualTo(false);
    }

    @Test
    public void simpleClassSingletonInvoke() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(Complex2.class, SINGLETON);
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

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor invokeProcessor = iterator.next();

        assertThat(invokeProcessor).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        final DefaultJavaComponent invoke = (DefaultJavaComponent) invokeProcessor;

        assertThat(invoke.getObjectType()).isEqualTo(Complex2.class);

        assertThat(invoke.getObjectFactory().isSingleton()).isEqualTo(true);
    }


    @Test
    public void simpleChainClassSingletonInvoke() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(Complex2.class, SINGLETON)
                        .invoke(Complex2.class, SINGLETON);
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

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(2);

        final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor invokeProcessor1 = iterator.next();

        assertThat(invokeProcessor1).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        final DefaultJavaComponent invoke1 = (DefaultJavaComponent) invokeProcessor1;

        assertThat(invoke1.getObjectType()).isEqualTo(Complex2.class);

        assertThat(invoke1.getObjectFactory().isSingleton()).isEqualTo(true);

        final MessageProcessor invokeProcessor2 = iterator.next();

        assertThat(invokeProcessor2).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        final DefaultJavaComponent invoke2 = (DefaultJavaComponent) invokeProcessor2;

        assertThat(invoke2.getObjectType()).isEqualTo(Complex2.class);

        assertThat(invoke2.getObjectFactory().isSingleton()).isEqualTo(true);
    }


    @Test
    public void simpleClassGuiceProvidedInvoke() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(Simple.class);

                bind(Simple.class).to(Simple2.class);
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

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor invokeProcessor = iterator.next();

        assertThat(invokeProcessor).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        final DefaultJavaComponent invoke = (DefaultJavaComponent) invokeProcessor;

        assertThat(invoke.getObjectType()).isEqualTo(Simple.class);

        assertThat(invoke.getObjectFactory().isSingleton()).isEqualTo(false);
    }


    @Test
    public void simpleChainGuiceClassInvoke() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(Simple.class)
                        .invoke(Simple.class);

                bind(Simple.class).to(Simple2.class);
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

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(2);

        final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor invokeProcessor1 = iterator.next();

        assertThat(invokeProcessor1).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        final DefaultJavaComponent invoke1 = (DefaultJavaComponent) invokeProcessor1;

        assertThat(invoke1.getObjectType()).isEqualTo(Simple.class);

        assertThat(invoke1.getObjectFactory().isSingleton()).isEqualTo(false);

        final MessageProcessor invokeProcessor2 = iterator.next();

        assertThat(invokeProcessor2).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        final DefaultJavaComponent invoke2 = (DefaultJavaComponent) invokeProcessor2;

        assertThat(invoke2.getObjectType()).isEqualTo(Simple.class);

        assertThat(invoke2.getObjectFactory().isSingleton()).isEqualTo(false);
    }


    @Test
    public void simpleClassGuiceSingletonProvidedInvoke() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(Simple.class);

                bind(Simple.class).to(Simple2.class).in(Singleton.class);
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

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor invokeProcessor = iterator.next();

        assertThat(invokeProcessor).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        final DefaultJavaComponent invoke = (DefaultJavaComponent) invokeProcessor;

        assertThat(invoke.getObjectType()).isEqualTo(Simple.class);

        assertThat(invoke.getObjectFactory().isSingleton()).isEqualTo(true);
    }


    @Test
    public void simpleChainGuiceSingletonClassInvoke() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(Simple.class)
                        .invoke(Simple.class);

                bind(Simple.class).to(Simple2.class).in(Singleton.class);
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

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(2);

        final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor invokeProcessor1 = iterator.next();

        assertThat(invokeProcessor1).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        final DefaultJavaComponent invoke1 = (DefaultJavaComponent) invokeProcessor1;

        assertThat(invoke1.getObjectType()).isEqualTo(Simple.class);

        assertThat(invoke1.getObjectFactory().isSingleton()).isEqualTo(true);

        final MessageProcessor invokeProcessor2 = iterator.next();

        assertThat(invokeProcessor2).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        final DefaultJavaComponent invoke2 = (DefaultJavaComponent) invokeProcessor2;

        assertThat(invoke2.getObjectType()).isEqualTo(Simple.class);

        assertThat(invoke2.getObjectFactory().isSingleton()).isEqualTo(true);
    }

    @Test
    public void complexChainInvoke() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(Simple.class)
                        .invoke(SimpleCallable.class)
                        .invoke(Complex2.class, SINGLETON)
                        .invoke(new Simple3())
                        .invoke(Complex2.class, PROTOTYPE)
                        .invoke(new SimpleCallable());

                bind(Simple.class).to(Simple2.class).in(Singleton.class);
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

        assertThat(((Flow) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(6);

        final Iterator<MessageProcessor> iterator = ((Flow) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor invokeProcessor1 = iterator.next();

        assertThat(invokeProcessor1).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        final DefaultJavaComponent invoke1 = (DefaultJavaComponent) invokeProcessor1;

        assertThat(invoke1.getObjectType()).isEqualTo(Simple.class);

        assertThat(invoke1.getObjectFactory().isSingleton()).isEqualTo(true);

        final MessageProcessor invokeProcessor2 = iterator.next();

        assertThat(invokeProcessor2).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent invoke2 = (SimpleCallableJavaComponent) invokeProcessor2;

        assertThat(invoke2.getObjectType()).isEqualTo(SimpleCallable.class);

        assertThat(invoke2.getObjectFactory().isSingleton()).isEqualTo(false);

        final MessageProcessor invokeProcessor3 = iterator.next();

        assertThat(invokeProcessor3).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        final DefaultJavaComponent invoke3 = (DefaultJavaComponent) invokeProcessor3;

        assertThat(invoke3.getObjectType()).isEqualTo(Complex2.class);

        assertThat(invoke3.getObjectFactory().isSingleton()).isEqualTo(true);

        final MessageProcessor invokeProcessor4 = iterator.next();

        assertThat(invokeProcessor4).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        final DefaultJavaComponent invoke4 = (DefaultJavaComponent) invokeProcessor4;

        assertThat(invoke4.getObjectType()).isEqualTo(Simple3.class);

        assertThat(invoke4.getObjectFactory().isSingleton()).isEqualTo(true);

        final MessageProcessor invokeProcessor5 = iterator.next();

        assertThat(invokeProcessor5).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        final DefaultJavaComponent invoke5 = (DefaultJavaComponent) invokeProcessor5;

        assertThat(invoke5.getObjectType()).isEqualTo(Complex2.class);

        assertThat(invoke5.getObjectFactory().isSingleton()).isEqualTo(false);

        final MessageProcessor invokeProcessor6 = iterator.next();

        assertThat(invokeProcessor6).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent invoke6 = (SimpleCallableJavaComponent) invokeProcessor6;

        assertThat(invoke6.getObjectType()).isEqualTo(SimpleCallable.class);

        assertThat(invoke6.getObjectFactory().isSingleton()).isEqualTo(true);
    }

    public static class SimpleCallable implements Callable {
        @Override
        public Object onCall(final MuleEventContext muleEventContext) throws Exception {
            System.out.println("HERE!");
            return muleEventContext.getMessage().getPayload();
        }
    }

    public static class ComplexCallable implements Callable {

        public ComplexCallable(final String none) {

        }

        @Override
        public Object onCall(final MuleEventContext muleEventContext) throws Exception {
            System.out.println("HERE!");
            return muleEventContext.getMessage().getPayload();
        }
    }


    public static interface Simple {
        void invoke(String string);
    }

    public static class Simple2 implements Simple {
        @Override
		public void invoke(final String string) {
            System.out.println("SIMPLE 2! : " + string);
        }
    }

    public static class Complex2 implements Simple {

        public Complex2(final String value, final String value2) {

        }

        @Override
		public void invoke(final String string) {
            System.out.println("SIMPLE 2! : " + string);
        }
    }

    public static class Simple3 implements Simple {
        @Override
		public void invoke(final String string) {
            System.out.println("SIMPLE 3! : " + string);
        }
    }

}
