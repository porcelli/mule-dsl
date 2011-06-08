/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import com.google.inject.name.Named;
import com.google.inject.name.Names;
import org.junit.Test;
import org.mule.MessageExchangePattern;
import org.mule.api.MuleContext;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.source.MessageSource;
import org.mule.config.dsl.component.InvokerMessageProcessorGuiceAdaptor;
import org.mule.construct.SimpleFlowConstruct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;
import static org.mule.config.dsl.expression.CoreExpr.payload;

public class TestExecuteNamedMethod {

    @Test
    public void simpleObjectNamedAnnotation() {
        final Simple mySimple = new Simple();
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(mySimple).methodAnnotatedWith(Names.named("ToExecute"));
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
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            MessageProcessor executeProcessor = iterator.next();

            assertThat(executeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorGuiceAdaptor.class);

            InvokerMessageProcessorGuiceAdaptor execute = (InvokerMessageProcessorGuiceAdaptor) executeProcessor;

            assertThat(execute.getMethodName()).isEqualTo("execute");

            assertThat(execute.getObject()).isEqualTo(mySimple).isInstanceOf(Simple.class);

            assertThat(execute.getArguments()).isEmpty();
        }
    }

    @Test
    public void simpleTypeNamedAnnotation() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Simple.class).methodAnnotatedWith(Names.named("ToExecute"));
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
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            MessageProcessor executeProcessor = iterator.next();

            assertThat(executeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorGuiceAdaptor.class);

            InvokerMessageProcessorGuiceAdaptor execute = (InvokerMessageProcessorGuiceAdaptor) executeProcessor;

            assertThat(execute.getMethodName()).isEqualTo("execute");

            assertThat(execute.getObjectType()).isEqualTo(Simple.class);

            assertThat(execute.getObject()).isInstanceOf(Simple.class);

            assertThat(execute.getArguments()).isEmpty();
        }
    }

    @Test
    public void simpleInjectedNamedAnnotation() {
        final Simple2 mySimple2 = new Simple2(null);

        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Simple2.class).methodAnnotatedWith(Names.named("ToExecute"));

                bind(Simple2.class).toInstance(mySimple2);
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
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            MessageProcessor executeProcessor = iterator.next();

            assertThat(executeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorGuiceAdaptor.class);

            InvokerMessageProcessorGuiceAdaptor execute = (InvokerMessageProcessorGuiceAdaptor) executeProcessor;

            assertThat(execute.getMethodName()).isEqualTo("execute");

            assertThat(execute.getObjectType()).isEqualTo(Simple2.class);

            assertThat(execute.getObject()).isInstanceOf(Simple2.class);

            assertThat(execute.getArguments()).isEmpty();
        }
    }


    @Test
    public void simpleObjectNamedAnnotationWithDefaultArgs() {
        final Simple mySimple = new Simple();
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(mySimple).methodAnnotatedWith(Names.named("ToExecute")).withoutArgs();
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
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            MessageProcessor executeProcessor = iterator.next();

            assertThat(executeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorGuiceAdaptor.class);

            InvokerMessageProcessorGuiceAdaptor execute = (InvokerMessageProcessorGuiceAdaptor) executeProcessor;

            assertThat(execute.getMethodName()).isEqualTo("execute");

            assertThat(execute.getObject()).isEqualTo(mySimple).isInstanceOf(Simple.class);

            assertThat(execute.getArguments()).isEmpty();
        }
    }

    @Test
    public void simpleTypeNamedAnnotationWithDefaultArgs() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Simple.class).methodAnnotatedWith(Names.named("ToExecute")).withoutArgs();
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
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            MessageProcessor executeProcessor = iterator.next();

            assertThat(executeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorGuiceAdaptor.class);

            InvokerMessageProcessorGuiceAdaptor execute = (InvokerMessageProcessorGuiceAdaptor) executeProcessor;

            assertThat(execute.getMethodName()).isEqualTo("execute");

            assertThat(execute.getObjectType()).isEqualTo(Simple.class);

            assertThat(execute.getObject()).isInstanceOf(Simple.class);

            assertThat(execute.getArguments()).isEmpty();
        }
    }

    @Test
    public void simpleInjectedNamedAnnotationWithDefaultArgs() {
        final Simple2 mySimple2 = new Simple2(null);

        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Simple2.class).methodAnnotatedWith(Names.named("ToExecute")).withoutArgs();

                bind(Simple2.class).toInstance(mySimple2);
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
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            MessageProcessor executeProcessor = iterator.next();

            assertThat(executeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorGuiceAdaptor.class);

            InvokerMessageProcessorGuiceAdaptor execute = (InvokerMessageProcessorGuiceAdaptor) executeProcessor;

            assertThat(execute.getMethodName()).isEqualTo("execute");

            assertThat(execute.getObjectType()).isEqualTo(Simple2.class);

            assertThat(execute.getObject()).isInstanceOf(Simple2.class);

            assertThat(execute.getArguments()).isEmpty();
        }
    }

    @Test
    public void simpleObjectNamedAnnotationWithArgs() {
        final Simple mySimple = new Simple();
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(mySimple).methodAnnotatedWith(Names.named("ToExecute2")).args(payload());
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
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            MessageProcessor executeProcessor = iterator.next();

            assertThat(executeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorGuiceAdaptor.class);

            InvokerMessageProcessorGuiceAdaptor execute = (InvokerMessageProcessorGuiceAdaptor) executeProcessor;

            assertThat(execute.getMethodName()).isEqualTo("execute2");

            assertThat(execute.getObject()).isEqualTo(mySimple).isInstanceOf(Simple.class);

            assertThat(execute.getArguments()).hasSize(1);

            assertThat(execute.getArguments().get(0)).isEqualTo("#[payload]");
        }
    }

    @Test
    public void simpleTypeNamedAnnotationWithArgs() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Simple.class).methodAnnotatedWith(Names.named("ToExecute2")).args(payload());
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
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            MessageProcessor executeProcessor = iterator.next();

            assertThat(executeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorGuiceAdaptor.class);

            InvokerMessageProcessorGuiceAdaptor execute = (InvokerMessageProcessorGuiceAdaptor) executeProcessor;

            assertThat(execute.getMethodName()).isEqualTo("execute2");

            assertThat(execute.getObjectType()).isEqualTo(Simple.class);

            assertThat(execute.getObject()).isInstanceOf(Simple.class);

            assertThat(execute.getArguments()).hasSize(1);

            assertThat(execute.getArguments().get(0)).isEqualTo("#[payload]");
        }
    }

    @Test
    public void simpleInjectedNamedAnnotationWithArgs() {
        final Simple2 mySimple2 = new Simple2(null);

        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Simple2.class).methodAnnotatedWith(Names.named("ToExecute2")).args(payload());

                bind(Simple2.class).toInstance(mySimple2);
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
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            MessageProcessor executeProcessor = iterator.next();

            assertThat(executeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorGuiceAdaptor.class);

            InvokerMessageProcessorGuiceAdaptor execute = (InvokerMessageProcessorGuiceAdaptor) executeProcessor;

            assertThat(execute.getMethodName()).isEqualTo("execute2");

            assertThat(execute.getObjectType()).isEqualTo(Simple2.class);

            assertThat(execute.getObject()).isInstanceOf(Simple2.class);

            assertThat(execute.getArguments()).hasSize(1);

            assertThat(execute.getArguments().get(0)).isEqualTo("#[payload]");
        }
    }

    @Test
    public void simpleObjectTypedAnnotation() {
        final Complex myComplex = new Complex();
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(myComplex).methodAnnotatedWith(ToExecute.class);
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
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            MessageProcessor executeProcessor = iterator.next();

            assertThat(executeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorGuiceAdaptor.class);

            InvokerMessageProcessorGuiceAdaptor execute = (InvokerMessageProcessorGuiceAdaptor) executeProcessor;

            assertThat(execute.getMethodName()).isEqualTo("execute");

            assertThat(execute.getObject()).isEqualTo(myComplex).isInstanceOf(Complex.class);

            assertThat(execute.getArguments()).isEmpty();
        }
    }

    @Test
    public void simpleTypeTypedAnnotation() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Complex.class).methodAnnotatedWith(ToExecute.class);
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
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            MessageProcessor executeProcessor = iterator.next();

            assertThat(executeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorGuiceAdaptor.class);

            InvokerMessageProcessorGuiceAdaptor execute = (InvokerMessageProcessorGuiceAdaptor) executeProcessor;

            assertThat(execute.getMethodName()).isEqualTo("execute");

            assertThat(execute.getObjectType()).isEqualTo(Complex.class);

            assertThat(execute.getObject()).isInstanceOf(Complex.class);

            assertThat(execute.getArguments()).isEmpty();
        }
    }

    @Test
    public void simpleInjectedTypedAnnotation() {
        final Complex2 myComplex2 = new Complex2(null);

        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Complex2.class).methodAnnotatedWith(ToExecute.class);

                bind(Complex2.class).toInstance(myComplex2);
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
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            MessageProcessor executeProcessor = iterator.next();

            assertThat(executeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorGuiceAdaptor.class);

            InvokerMessageProcessorGuiceAdaptor execute = (InvokerMessageProcessorGuiceAdaptor) executeProcessor;

            assertThat(execute.getMethodName()).isEqualTo("execute");

            assertThat(execute.getObjectType()).isEqualTo(Complex2.class);

            assertThat(execute.getObject()).isInstanceOf(Complex2.class);

            assertThat(execute.getArguments()).isEmpty();
        }
    }


    @Test
    public void simpleObjectTypedAnnotationWithDefaultArgs() {
        final Complex myComplex = new Complex();
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(myComplex).methodAnnotatedWith(ToExecute.class).withoutArgs();
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
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            MessageProcessor executeProcessor = iterator.next();

            assertThat(executeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorGuiceAdaptor.class);

            InvokerMessageProcessorGuiceAdaptor execute = (InvokerMessageProcessorGuiceAdaptor) executeProcessor;

            assertThat(execute.getMethodName()).isEqualTo("execute");

            assertThat(execute.getObject()).isEqualTo(myComplex).isInstanceOf(Complex.class);

            assertThat(execute.getArguments()).isEmpty();
        }
    }

    @Test
    public void simpleTypeTypedAnnotationWithDefaultArgs() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Complex.class).methodAnnotatedWith(ToExecute.class).withoutArgs();
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
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            MessageProcessor executeProcessor = iterator.next();

            assertThat(executeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorGuiceAdaptor.class);

            InvokerMessageProcessorGuiceAdaptor execute = (InvokerMessageProcessorGuiceAdaptor) executeProcessor;

            assertThat(execute.getMethodName()).isEqualTo("execute");

            assertThat(execute.getObjectType()).isEqualTo(Complex.class);

            assertThat(execute.getObject()).isInstanceOf(Complex.class);

            assertThat(execute.getArguments()).isEmpty();
        }
    }

    @Test
    public void simpleInjectedTypedAnnotationWithDefaultArgs() {
        final Complex2 myComplex2 = new Complex2(null);

        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Complex2.class).methodAnnotatedWith(ToExecute.class).withoutArgs();

                bind(Complex2.class).toInstance(myComplex2);
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
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            MessageProcessor executeProcessor = iterator.next();

            assertThat(executeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorGuiceAdaptor.class);

            InvokerMessageProcessorGuiceAdaptor execute = (InvokerMessageProcessorGuiceAdaptor) executeProcessor;

            assertThat(execute.getMethodName()).isEqualTo("execute");

            assertThat(execute.getObjectType()).isEqualTo(Complex2.class);

            assertThat(execute.getObject()).isInstanceOf(Complex2.class);

            assertThat(execute.getArguments()).isEmpty();
        }
    }

    @Test
    public void simpleObjectTypedAnnotationWithArgs() {
        final Complex myComplex = new Complex();
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(myComplex).methodAnnotatedWith(ToExecute2.class).args(payload());
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
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            MessageProcessor executeProcessor = iterator.next();

            assertThat(executeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorGuiceAdaptor.class);

            InvokerMessageProcessorGuiceAdaptor execute = (InvokerMessageProcessorGuiceAdaptor) executeProcessor;

            assertThat(execute.getMethodName()).isEqualTo("execute2");

            assertThat(execute.getObject()).isEqualTo(myComplex).isInstanceOf(Complex.class);

            assertThat(execute.getArguments()).hasSize(1);

            assertThat(execute.getArguments().get(0)).isEqualTo("#[payload]");
        }
    }

    @Test
    public void simpleTypeTypedAnnotationWithArgs() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Complex.class).methodAnnotatedWith(ToExecute2.class).args(payload());
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
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            MessageProcessor executeProcessor = iterator.next();

            assertThat(executeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorGuiceAdaptor.class);

            InvokerMessageProcessorGuiceAdaptor execute = (InvokerMessageProcessorGuiceAdaptor) executeProcessor;

            assertThat(execute.getMethodName()).isEqualTo("execute2");

            assertThat(execute.getObjectType()).isEqualTo(Complex.class);

            assertThat(execute.getObject()).isInstanceOf(Complex.class);

            assertThat(execute.getArguments()).hasSize(1);

            assertThat(execute.getArguments().get(0)).isEqualTo("#[payload]");
        }
    }

    @Test
    public void simpleInjectedTypedAnnotationWithArgs() {
        final Complex2 myComplex2 = new Complex2(null);

        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Complex2.class).methodAnnotatedWith(ToExecute2.class).args(payload());

                bind(Complex2.class).toInstance(myComplex2);
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
        }

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            MessageProcessor executeProcessor = iterator.next();

            assertThat(executeProcessor).isNotNull().isInstanceOf(InvokerMessageProcessorGuiceAdaptor.class);

            InvokerMessageProcessorGuiceAdaptor execute = (InvokerMessageProcessorGuiceAdaptor) executeProcessor;

            assertThat(execute.getMethodName()).isEqualTo("execute2");

            assertThat(execute.getObjectType()).isEqualTo(Complex2.class);

            assertThat(execute.getObject()).isInstanceOf(Complex2.class);

            assertThat(execute.getArguments()).hasSize(1);

            assertThat(execute.getArguments().get(0)).isEqualTo("#[payload]");
        }
    }

    public static class Simple {
        @Named("ToExecute")
        public void execute() {
            System.out.println("SIMPLE! : ");
        }

        @Named("ToExecute2")
        public void execute2(String value) {
            System.out.println("SIMPLE 2! : " + value);
        }
    }

    public static class Simple2 extends Simple {
        public Simple2(String value) {
        }
    }

    public static class Complex {
        @ToExecute
        public void execute() {
            System.out.println("SIMPLE! : ");
        }

        @ToExecute2
        public void execute2(String value) {
            System.out.println("SIMPLE 2! : " + value);
        }
    }

    public static class Complex2 extends Complex {
        public Complex2(String value) {
        }
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ToExecute2 {
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ToExecute {
    }

}
