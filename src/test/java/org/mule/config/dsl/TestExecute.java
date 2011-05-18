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
import org.mule.construct.SimpleFlowConstruct;

import javax.inject.Singleton;
import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;

public class TestExecute {

    @Test
    public void simpleObjectExecute() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(new Simple2());
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

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        MessageProcessor executeProcessor = iterator.next();

        assertThat(executeProcessor).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        DefaultJavaComponent execute = (DefaultJavaComponent) executeProcessor;

        assertThat(execute.getObjectType()).isEqualTo(Simple2.class);

        assertThat(execute.getObjectFactory().isSingleton()).isEqualTo(true);
    }


    @Test
    public void simpleChainObjectExecute() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(new Simple2())
                        .execute(new Simple3());
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

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(2);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        MessageProcessor executeProcessor1 = iterator.next();

        assertThat(executeProcessor1).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        DefaultJavaComponent execute1 = (DefaultJavaComponent) executeProcessor1;

        assertThat(execute1.getObjectType()).isEqualTo(Simple2.class);

        assertThat(execute1.getObjectFactory().isSingleton()).isEqualTo(true);

        MessageProcessor executeProcessor2 = iterator.next();

        assertThat(executeProcessor2).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        DefaultJavaComponent execute2 = (DefaultJavaComponent) executeProcessor2;

        assertThat(execute2.getObjectType()).isEqualTo(Simple3.class);

        assertThat(execute2.getObjectFactory().isSingleton()).isEqualTo(true);
    }

    @Test
    public void simpleCallableObjectExecute() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(new SimpleCallable());
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

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        MessageProcessor executeProcessor = iterator.next();

        assertThat(executeProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent execute = (SimpleCallableJavaComponent) executeProcessor;

        assertThat(execute.getObjectType()).isEqualTo(SimpleCallable.class);

        assertThat(execute.getObjectFactory().isSingleton()).isEqualTo(true);
    }


    @Test
    public void simpleChainCallableObjectExecute() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(new SimpleCallable())
                        .execute(new SimpleCallable());
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

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(2);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        MessageProcessor executeProcessor1 = iterator.next();

        assertThat(executeProcessor1).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent execute1 = (SimpleCallableJavaComponent) executeProcessor1;

        assertThat(execute1.getObjectType()).isEqualTo(SimpleCallable.class);

        assertThat(execute1.getObjectFactory().isSingleton()).isEqualTo(true);

        MessageProcessor executeProcessor2 = iterator.next();

        assertThat(executeProcessor2).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent execute2 = (SimpleCallableJavaComponent) executeProcessor2;

        assertThat(execute2.getObjectType()).isEqualTo(SimpleCallable.class);

        assertThat(execute2.getObjectFactory().isSingleton()).isEqualTo(true);
    }


    @Test
    public void simpleCallableClassExecute() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(SimpleCallable.class);
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

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        MessageProcessor executeProcessor = iterator.next();

        assertThat(executeProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent execute = (SimpleCallableJavaComponent) executeProcessor;

        assertThat(execute.getObjectType()).isEqualTo(SimpleCallable.class);

        assertThat(execute.getObjectFactory().isSingleton()).isEqualTo(true);
    }


    @Test
    public void simpleChainCallableClassExecute() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(SimpleCallable.class)
                        .execute(SimpleCallable.class);
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

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(2);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        MessageProcessor executeProcessor1 = iterator.next();

        assertThat(executeProcessor1).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent execute1 = (SimpleCallableJavaComponent) executeProcessor1;

        assertThat(execute1.getObjectType()).isEqualTo(SimpleCallable.class);

        assertThat(execute1.getObjectFactory().isSingleton()).isEqualTo(true);

        MessageProcessor executeProcessor2 = iterator.next();

        assertThat(executeProcessor2).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent execute2 = (SimpleCallableJavaComponent) executeProcessor2;

        assertThat(execute2.getObjectType()).isEqualTo(SimpleCallable.class);

        assertThat(execute2.getObjectFactory().isSingleton()).isEqualTo(true);
    }

    @Test
    public void simpleClassExecute() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Simple2.class);
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

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        MessageProcessor executeProcessor = iterator.next();

        assertThat(executeProcessor).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        DefaultJavaComponent execute = (DefaultJavaComponent) executeProcessor;

        assertThat(execute.getObjectType()).isEqualTo(Simple2.class);

        assertThat(execute.getObjectFactory().isSingleton()).isEqualTo(false);
    }


    @Test
    public void simpleChainClassExecute() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Simple2.class)
                        .execute(Simple3.class);
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

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(2);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        MessageProcessor executeProcessor1 = iterator.next();

        assertThat(executeProcessor1).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        DefaultJavaComponent execute1 = (DefaultJavaComponent) executeProcessor1;

        assertThat(execute1.getObjectType()).isEqualTo(Simple2.class);

        assertThat(execute1.getObjectFactory().isSingleton()).isEqualTo(false);

        MessageProcessor executeProcessor2 = iterator.next();

        assertThat(executeProcessor2).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        DefaultJavaComponent execute2 = (DefaultJavaComponent) executeProcessor2;

        assertThat(execute2.getObjectType()).isEqualTo(Simple3.class);

        assertThat(execute2.getObjectFactory().isSingleton()).isEqualTo(false);
    }

    @Test
    public void simpleClassPrototypeExecute() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Complex2.class).asPrototype();
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

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        MessageProcessor executeProcessor = iterator.next();

        assertThat(executeProcessor).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        DefaultJavaComponent execute = (DefaultJavaComponent) executeProcessor;

        assertThat(execute.getObjectType()).isEqualTo(Complex2.class);

        assertThat(execute.getObjectFactory().isSingleton()).isEqualTo(false);
    }


    @Test
    public void simpleChainClassPrototypeExecute() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Complex2.class).asPrototype()
                        .execute(Complex2.class).asPrototype();
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

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(2);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        MessageProcessor executeProcessor1 = iterator.next();

        assertThat(executeProcessor1).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        DefaultJavaComponent execute1 = (DefaultJavaComponent) executeProcessor1;

        assertThat(execute1.getObjectType()).isEqualTo(Complex2.class);

        assertThat(execute1.getObjectFactory().isSingleton()).isEqualTo(false);

        MessageProcessor executeProcessor2 = iterator.next();

        assertThat(executeProcessor2).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        DefaultJavaComponent execute2 = (DefaultJavaComponent) executeProcessor2;

        assertThat(execute2.getObjectType()).isEqualTo(Complex2.class);

        assertThat(execute2.getObjectFactory().isSingleton()).isEqualTo(false);
    }

    @Test
    public void simpleClassSingletonExecute() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Complex2.class).asSingleton();
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

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        MessageProcessor executeProcessor = iterator.next();

        assertThat(executeProcessor).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        DefaultJavaComponent execute = (DefaultJavaComponent) executeProcessor;

        assertThat(execute.getObjectType()).isEqualTo(Complex2.class);

        assertThat(execute.getObjectFactory().isSingleton()).isEqualTo(true);
    }


    @Test
    public void simpleChainClassSingletonExecute() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Complex2.class).asSingleton()
                        .execute(Complex2.class).asSingleton();
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

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(2);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        MessageProcessor executeProcessor1 = iterator.next();

        assertThat(executeProcessor1).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        DefaultJavaComponent execute1 = (DefaultJavaComponent) executeProcessor1;

        assertThat(execute1.getObjectType()).isEqualTo(Complex2.class);

        assertThat(execute1.getObjectFactory().isSingleton()).isEqualTo(true);

        MessageProcessor executeProcessor2 = iterator.next();

        assertThat(executeProcessor2).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        DefaultJavaComponent execute2 = (DefaultJavaComponent) executeProcessor2;

        assertThat(execute2.getObjectType()).isEqualTo(Complex2.class);

        assertThat(execute2.getObjectFactory().isSingleton()).isEqualTo(true);
    }


    @Test
    public void simpleClassGuiceProvidedExecute() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Simple.class);

                bind(Simple.class).to(Simple2.class);
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

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        MessageProcessor executeProcessor = iterator.next();

        assertThat(executeProcessor).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        DefaultJavaComponent execute = (DefaultJavaComponent) executeProcessor;

        assertThat(execute.getObjectType()).isEqualTo(Simple.class);

        assertThat(execute.getObjectFactory().isSingleton()).isEqualTo(false);
    }


    @Test
    public void simpleChainGuiceClassExecute() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Simple.class)
                        .execute(Simple.class);

                bind(Simple.class).to(Simple2.class);
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

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(2);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        MessageProcessor executeProcessor1 = iterator.next();

        assertThat(executeProcessor1).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        DefaultJavaComponent execute1 = (DefaultJavaComponent) executeProcessor1;

        assertThat(execute1.getObjectType()).isEqualTo(Simple.class);

        assertThat(execute1.getObjectFactory().isSingleton()).isEqualTo(false);

        MessageProcessor executeProcessor2 = iterator.next();

        assertThat(executeProcessor2).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        DefaultJavaComponent execute2 = (DefaultJavaComponent) executeProcessor2;

        assertThat(execute2.getObjectType()).isEqualTo(Simple.class);

        assertThat(execute2.getObjectFactory().isSingleton()).isEqualTo(false);
    }


    @Test
    public void simpleClassGuiceSingletonProvidedExecute() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Simple.class);

                bind(Simple.class).to(Simple2.class).in(Singleton.class);
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

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        MessageProcessor executeProcessor = iterator.next();

        assertThat(executeProcessor).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        DefaultJavaComponent execute = (DefaultJavaComponent) executeProcessor;

        assertThat(execute.getObjectType()).isEqualTo(Simple.class);

        assertThat(execute.getObjectFactory().isSingleton()).isEqualTo(true);
    }


    @Test
    public void simpleChainGuiceSingletonClassExecute() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Simple.class)
                        .execute(Simple.class);

                bind(Simple.class).to(Simple2.class).in(Singleton.class);
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

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(2);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        MessageProcessor executeProcessor1 = iterator.next();

        assertThat(executeProcessor1).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        DefaultJavaComponent execute1 = (DefaultJavaComponent) executeProcessor1;

        assertThat(execute1.getObjectType()).isEqualTo(Simple.class);

        assertThat(execute1.getObjectFactory().isSingleton()).isEqualTo(true);

        MessageProcessor executeProcessor2 = iterator.next();

        assertThat(executeProcessor2).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        DefaultJavaComponent execute2 = (DefaultJavaComponent) executeProcessor2;

        assertThat(execute2.getObjectType()).isEqualTo(Simple.class);

        assertThat(execute2.getObjectFactory().isSingleton()).isEqualTo(true);
    }

    @Test
    public void complexChainExecute() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Simple.class)
                        .execute(SimpleCallable.class)
                        .execute(Complex2.class).asSingleton()
                        .execute(new Simple3())
                        .execute(Complex2.class).asPrototype()
                        .execute(new SimpleCallable());

                bind(Simple.class).to(Simple2.class).in(Singleton.class);
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

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(6);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        MessageProcessor executeProcessor1 = iterator.next();

        assertThat(executeProcessor1).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        DefaultJavaComponent execute1 = (DefaultJavaComponent) executeProcessor1;

        assertThat(execute1.getObjectType()).isEqualTo(Simple.class);

        assertThat(execute1.getObjectFactory().isSingleton()).isEqualTo(true);

        MessageProcessor executeProcessor2 = iterator.next();

        assertThat(executeProcessor2).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent execute2 = (SimpleCallableJavaComponent) executeProcessor2;

        assertThat(execute2.getObjectType()).isEqualTo(SimpleCallable.class);

        assertThat(execute2.getObjectFactory().isSingleton()).isEqualTo(true);

        MessageProcessor executeProcessor3 = iterator.next();

        assertThat(executeProcessor3).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        DefaultJavaComponent execute3 = (DefaultJavaComponent) executeProcessor3;

        assertThat(execute3.getObjectType()).isEqualTo(Complex2.class);

        assertThat(execute3.getObjectFactory().isSingleton()).isEqualTo(true);

        MessageProcessor executeProcessor4 = iterator.next();

        assertThat(executeProcessor4).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        DefaultJavaComponent execute4 = (DefaultJavaComponent) executeProcessor4;

        assertThat(execute4.getObjectType()).isEqualTo(Simple3.class);

        assertThat(execute4.getObjectFactory().isSingleton()).isEqualTo(true);

        MessageProcessor executeProcessor5 = iterator.next();

        assertThat(executeProcessor5).isNotNull().isInstanceOf(DefaultJavaComponent.class);

        DefaultJavaComponent execute5 = (DefaultJavaComponent) executeProcessor5;

        assertThat(execute5.getObjectType()).isEqualTo(Complex2.class);

        assertThat(execute5.getObjectFactory().isSingleton()).isEqualTo(false);

        MessageProcessor executeProcessor6 = iterator.next();

        assertThat(executeProcessor6).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent execute6 = (SimpleCallableJavaComponent) executeProcessor6;

        assertThat(execute6.getObjectType()).isEqualTo(SimpleCallable.class);

        assertThat(execute6.getObjectFactory().isSingleton()).isEqualTo(true);
    }

    public static class SimpleCallable implements Callable {
        @Override
        public Object onCall(MuleEventContext muleEventContext) throws Exception {
            System.out.println("HERE!");
            return muleEventContext.getMessage().getPayload();
        }
    }

    public static interface Simple {
        void execute(String string);
    }

    public static class Simple2 implements Simple {
        public void execute(String string) {
            System.out.println("SIMPLE 2! : " + string);
        }
    }

    public static class Complex2 implements Simple {

        public Complex2(String value, String value2) {

        }

        public void execute(String string) {
            System.out.println("SIMPLE 2! : " + string);
        }
    }

    public static class Simple3 implements Simple {
        public void execute(String string) {
            System.out.println("SIMPLE 3! : " + string);
        }
    }

}
