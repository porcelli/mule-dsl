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
import org.mule.api.processor.MessageProcessor;
import org.mule.api.source.MessageSource;
import org.mule.construct.SimpleFlowConstruct;
import org.mule.transformer.simple.MessagePropertiesTransformer;

import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;
import static org.mule.config.dsl.expression.CoreExpr.payload;
import static org.mule.config.dsl.expression.CoreExpr.string;

public class TestMessageProperties {

    @Test
    public void simpleManipulation() throws Exception {
        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .messageProperties()
                        .put("key", "value")
                        .put("key2", "value2")
                        .remove("key2Remove")
                        .rename("xxx", "yyy");
            }
        });

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

        {
            assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

            final MessageProcessor messagePropertiesTransformerProcessor = iterator.next();

            assertThat(messagePropertiesTransformerProcessor).isNotNull().isInstanceOf(MessagePropertiesTransformer.class);

            final MessagePropertiesTransformer propertiesTransformer = (MessagePropertiesTransformer) messagePropertiesTransformerProcessor;

            assertThat(propertiesTransformer.isOverwrite()).isTrue();

            assertThat(propertiesTransformer.getDeleteProperties()).isNotEmpty().hasSize(1).contains("key2Remove");

            assertThat(propertiesTransformer.getAddProperties()).isNotEmpty().hasSize(2).includes(entry("key", "value"), entry("key2", "value2"));

            assertThat(propertiesTransformer.getRenameProperties()).isNotEmpty().hasSize(1).includes(entry("xxx", "yyy"));
        }
    }

    @Test
    public void placeholderManipulation() throws Exception {
        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                propertyResolver(classpath("test-resource.properties"));

                flow("MyFlow")
                        .from("file://${in.folder.path}")
                        .messageProperties()
                        .put("key", "${my.app.name}")
                        .put("key2", "value2")
                        .remove("key2Remove")
                        .rename("xxx", "${out.folder.path}");
            }
        });

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

        {
            assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

            final MessageProcessor messagePropertiesTransformerProcessor = iterator.next();

            assertThat(messagePropertiesTransformerProcessor).isNotNull().isInstanceOf(MessagePropertiesTransformer.class);

            final MessagePropertiesTransformer propertiesTransformer = (MessagePropertiesTransformer) messagePropertiesTransformerProcessor;

            assertThat(propertiesTransformer.isOverwrite()).isTrue();

            assertThat(propertiesTransformer.getDeleteProperties()).isNotEmpty().hasSize(1).contains("key2Remove");

            assertThat(propertiesTransformer.getAddProperties()).isNotEmpty().hasSize(2).includes(entry("key", "My App cool name"), entry("key2", "value2"));

            assertThat(propertiesTransformer.getRenameProperties()).isNotEmpty().hasSize(1).includes(entry("xxx", "/Users/porcelli/out"));
        }
    }

    @Test
    public void expressionManipulation() throws Exception {
        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .messageProperties()
                        .put("key", string("value"))
                        .put("key2", payload())
                        .remove("key2Remove")
                        .rename("xxx", string("yyy"));
            }
        });

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

        {
            assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

            final MessageProcessor messagePropertiesTransformerProcessor = iterator.next();

            assertThat(messagePropertiesTransformerProcessor).isNotNull().isInstanceOf(MessagePropertiesTransformer.class);

            final MessagePropertiesTransformer propertiesTransformer = (MessagePropertiesTransformer) messagePropertiesTransformerProcessor;

            assertThat(propertiesTransformer.isOverwrite()).isTrue();

            assertThat(propertiesTransformer.getDeleteProperties()).isNotEmpty().hasSize(1).contains("key2Remove");

            assertThat(propertiesTransformer.getAddProperties()).isNotEmpty().hasSize(2).includes(entry("key", "#[string:value]"), entry("key2", "#[payload]"));

            assertThat(propertiesTransformer.getRenameProperties()).isNotEmpty().hasSize(1).includes(entry("xxx", "#[string:yyy]"));
        }
    }

    @Test
    public void expressionAndPlaceholderManipulation() throws Exception {
        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                propertyResolver(classpath("test-resource.properties"));

                flow("MyFlow")
                        .from("file://${in.folder.path}")
                        .messageProperties()
                        .put("key", string("${my.app.name}"))
                        .put("key2", payload())
                        .remove("key2Remove")
                        .rename("xxx", string("${out.folder.path}"));
            }
        });

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

        {
            assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

            final MessageProcessor messagePropertiesTransformerProcessor = iterator.next();

            assertThat(messagePropertiesTransformerProcessor).isNotNull().isInstanceOf(MessagePropertiesTransformer.class);

            final MessagePropertiesTransformer propertiesTransformer = (MessagePropertiesTransformer) messagePropertiesTransformerProcessor;

            assertThat(propertiesTransformer.isOverwrite()).isTrue();

            assertThat(propertiesTransformer.getDeleteProperties()).isNotEmpty().hasSize(1).contains("key2Remove");

            assertThat(propertiesTransformer.getAddProperties()).isNotEmpty().hasSize(2).includes(entry("key", "#[string:My App cool name]"), entry("key2", "#[payload]"));

            assertThat(propertiesTransformer.getRenameProperties()).isNotEmpty().hasSize(1).includes(entry("xxx", "#[string:/Users/porcelli/out]"));
        }
    }

    @Test(expected = RuntimeException.class)
    public void testNullOnPut1() throws Exception {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .messageProperties()
                        .put(null, null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void testNullOnPut2() throws Exception {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .messageProperties()
                        .put(null, (Object) null);
            }
        });
    }


    @Test(expected = RuntimeException.class)
    public void testNullOnPut3() throws Exception {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .messageProperties()
                        .put("key", null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void testNullOnPut4() throws Exception {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .messageProperties()
                        .put("key", (Object) null);
            }
        });
    }


    @Test(expected = RuntimeException.class)
    public void testNullOnPut5() throws Exception {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .messageProperties()
                        .put(null, "value");
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void testEmptyOnPut1() throws Exception {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .messageProperties()
                        .put("", "");
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void testEmptyOnPut2() throws Exception {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .messageProperties()
                        .put("", "value");
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void testNullOnRename1() throws Exception {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .messageProperties()
                        .rename(null, (String) null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void testNullOnRename2() throws Exception {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .messageProperties()
                        .rename(null, (ExpressionEvaluatorDefinition) null);
            }
        });
    }


    @Test(expected = RuntimeException.class)
    public void testNullOnRename3() throws Exception {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .messageProperties()
                        .rename("key", (String) null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void testNullOnRename4() throws Exception {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .messageProperties()
                        .rename("key", (ExpressionEvaluatorDefinition) null);
            }
        });
    }


    @Test(expected = RuntimeException.class)
    public void testNullOnRename5() throws Exception {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .messageProperties()
                        .rename(null, "value");
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void testEmptyOnRename1() throws Exception {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .messageProperties()
                        .rename("", "");
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void testEmptyOnRename2() throws Exception {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .messageProperties()
                        .rename("", "value");
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void testEmptyOnRename3() throws Exception {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .messageProperties()
                        .rename("key", "");
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void testNullOnRemove() throws Exception {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .messageProperties()
                        .remove(null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void testEmptyOnRemove() throws Exception {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .messageProperties()
                        .remove("");
            }
        });
    }

}