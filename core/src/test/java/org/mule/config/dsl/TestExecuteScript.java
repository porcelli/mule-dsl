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
import org.mule.construct.SimpleFlowConstruct;
import org.mule.module.scripting.component.ScriptComponent;

import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;

public class TestExecuteScript {

    @Test
    public void simpleExecuteScript() throws MuleException, InterruptedException {
        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .executeScript(ScriptLanguage.GROOVY, "println \"$payload Received\"");
            }
        });

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);
        Iterator<FlowConstruct> flowIterator = muleContext.getRegistry().lookupFlowConstructs().iterator();
        final FlowConstruct flowConstruct = flowIterator.next();

        {
            assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
            assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

            final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

            assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

            final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

            assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

            assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");

        }
        {
            assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            final MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();

            assertThat(processor).isNotNull().isInstanceOf(ScriptComponent.class);

            final ScriptComponent scriptComponent = (ScriptComponent) processor;

            assertThat(scriptComponent.getScript().getScriptEngineName()).isEqualTo(ScriptLanguage.GROOVY.toString());

            assertThat(scriptComponent.getScript().getScriptText()).isNotNull().isEqualTo("println \"$payload Received\"");
        }
    }

    @Test
    public void simpleExecuteScriptLang() throws MuleException, InterruptedException {
        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .executeScript("groovy", "println \"$payload Received\"");
            }
        });

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);
        Iterator<FlowConstruct> flowIterator = muleContext.getRegistry().lookupFlowConstructs().iterator();
        final FlowConstruct flowConstruct = flowIterator.next();

        {
            assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
            assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

            final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

            assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

            final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

            assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

            assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");

        }
        {
            assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            final MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();

            assertThat(processor).isNotNull().isInstanceOf(ScriptComponent.class);

            final ScriptComponent scriptComponent = (ScriptComponent) processor;

            assertThat(scriptComponent.getScript().getScriptEngineName()).isEqualTo(ScriptLanguage.GROOVY.toString());

            assertThat(scriptComponent.getScript().getScriptText()).isNotNull().isEqualTo("println \"$payload Received\"");
        }
    }

    @Test
    public void simpleExecuteScriptLangClasspath() throws MuleException, InterruptedException {
        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .executeScript("groovy", classpath("test.groovy"));
            }
        });

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);
        Iterator<FlowConstruct> flowIterator = muleContext.getRegistry().lookupFlowConstructs().iterator();
        final FlowConstruct flowConstruct = flowIterator.next();

        {
            assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
            assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

            final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

            assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

            final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

            assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

            assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");

        }
        {
            assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            final MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();

            assertThat(processor).isNotNull().isInstanceOf(ScriptComponent.class);

            final ScriptComponent scriptComponent = (ScriptComponent) processor;

            assertThat(scriptComponent.getScript().getScriptEngineName()).isEqualTo(ScriptLanguage.GROOVY.toString());

            assertThat(scriptComponent.getScript().getScriptText()).isNotNull().isEqualTo("println \"$payload Received\"");
        }
    }

    @Test
    public void simpleExecuteScriptLangFile() throws MuleException, InterruptedException {
        final MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .executeScript("groovy", file("./src/test/resources/test.groovy"));
            }
        });

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);
        Iterator<FlowConstruct> flowIterator = muleContext.getRegistry().lookupFlowConstructs().iterator();
        final FlowConstruct flowConstruct = flowIterator.next();

        {
            assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
            assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

            final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

            assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

            final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

            assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

            assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");

        }
        {
            assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            final MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();

            assertThat(processor).isNotNull().isInstanceOf(ScriptComponent.class);

            final ScriptComponent scriptComponent = (ScriptComponent) processor;

            assertThat(scriptComponent.getScript().getScriptEngineName()).isEqualTo(ScriptLanguage.GROOVY.toString());

            assertThat(scriptComponent.getScript().getScriptText()).isNotNull().isEqualTo("println \"$payload Received\"");
        }
    }

    @Test(expected = RuntimeException.class)
    public void simpleNull() throws MuleException, InterruptedException {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .executeScript((String) null, (String) null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void simpleEmpty() throws MuleException, InterruptedException {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .executeScript("", "");
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void simpleNull2() throws MuleException, InterruptedException {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .executeScript((String) null, (AbstractModule.FileRefBuilder) null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void simpleEmpty2() throws MuleException, InterruptedException {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .executeScript("", (AbstractModule.FileRefBuilder) null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void simpleNull3() throws MuleException, InterruptedException {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .executeScript((String) null, (ClasspathBuilder) null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void simpleEmpty3() throws MuleException, InterruptedException {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .executeScript("", (ClasspathBuilder) null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void simpleNull4() throws MuleException, InterruptedException {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .executeScript((ScriptLanguage) null, (String) null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void simpleEmpty4() throws MuleException, InterruptedException {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .executeScript((ScriptLanguage) null, "");
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void simpleNull5() throws MuleException, InterruptedException {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .executeScript((ScriptLanguage) null, (AbstractModule.FileRefBuilder) null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void simpleNull6() throws MuleException, InterruptedException {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .executeScript((ScriptLanguage) null, (ClasspathBuilder) null);
            }
        });
    }


}