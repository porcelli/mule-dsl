/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import static org.fest.assertions.Assertions.assertThat;
import static org.mule.config.dsl.expression.CoreExpr.string;

import java.util.Iterator;

import org.junit.Test;
import org.mule.MessageExchangePattern;
import org.mule.api.MuleContext;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.source.MessageSource;
import org.mule.component.SimpleCallableJavaComponent;
import org.mule.config.dsl.component.ExpressionLogComponent;
import org.mule.config.dsl.component.ExtendedLogComponent;
import org.mule.config.dsl.component.SimpleLogComponent;
import org.mule.construct.SimpleFlowConstruct;

public class TestLog {

    @Test
    public void simpleLog() throws Exception {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .log();
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

        MessageProcessor logProcessor = iterator.next();

        assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

        assertThat(log.getObjectType()).isEqualTo(SimpleLogComponent.class);

        assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);

        SimpleLogComponent log1 = (SimpleLogComponent) log.getObjectFactory().getInstance(null);
        SimpleLogComponent log2 = (SimpleLogComponent) log.getObjectFactory().getInstance(null);

        assertThat(log1 == log2).isEqualTo(true);

        assertThat(log1.getLevel()).isEqualTo(LogLevel.INFO);
    }

    @Test
    public void simpleLogChain() throws Exception {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .log()
                        .log();
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

        MessageProcessor logProcessor = iterator.next();

        assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

        assertThat(log.getObjectType()).isEqualTo(SimpleLogComponent.class);

        assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);

        SimpleLogComponent log_1 = (SimpleLogComponent) log.getObjectFactory().getInstance(null);
        SimpleLogComponent log_2 = (SimpleLogComponent) log.getObjectFactory().getInstance(null);

        assertThat(log_1 == log_2).isEqualTo(true);

        assertThat(log_1.getLevel()).isEqualTo(LogLevel.INFO);

        MessageProcessor logProcessor2 = iterator.next();

        assertThat(logProcessor2).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent log2 = (SimpleCallableJavaComponent) logProcessor2;

        assertThat(log2.getObjectType()).isEqualTo(SimpleLogComponent.class);

        assertThat(log2.getObjectFactory().isSingleton()).isEqualTo(true);

        SimpleLogComponent log2_1 = (SimpleLogComponent) log2.getObjectFactory().getInstance(null);
        SimpleLogComponent log2_2 = (SimpleLogComponent) log2.getObjectFactory().getInstance(null);

        assertThat(log2_1 == log2_2).isEqualTo(true);

        assertThat(log2_1.getLevel()).isEqualTo(LogLevel.INFO);

        assertThat(log_1 != log2_1).isEqualTo(true);
        assertThat(log_2 != log2_2).isEqualTo(true);
    }


    @Test
    public void simpleLogJustLevel() throws Exception {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .log(LogLevel.ERROR);
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

        MessageProcessor logProcessor = iterator.next();

        assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

        assertThat(log.getObjectType()).isEqualTo(SimpleLogComponent.class);

        assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);

        SimpleLogComponent log_1 = (SimpleLogComponent) log.getObjectFactory().getInstance(null);
        SimpleLogComponent log_2 = (SimpleLogComponent) log.getObjectFactory().getInstance(null);

        assertThat(log_1 == log_2).isEqualTo(true);

        assertThat(log_1.getLevel()).isEqualTo(LogLevel.ERROR);
    }

    @Test
    public void simpleLogJustLevelChain() throws Exception {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .log(LogLevel.ERROR)
                        .log(LogLevel.WARN);
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

        MessageProcessor logProcessor = iterator.next();

        assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

        assertThat(log.getObjectType()).isEqualTo(SimpleLogComponent.class);

        assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);

        SimpleLogComponent log_1 = (SimpleLogComponent) log.getObjectFactory().getInstance(null);
        SimpleLogComponent log_2 = (SimpleLogComponent) log.getObjectFactory().getInstance(null);

        assertThat(log_1 == log_2).isEqualTo(true);

        assertThat(log_1.getLevel()).isEqualTo(LogLevel.ERROR);

        MessageProcessor logProcessor2 = iterator.next();

        assertThat(logProcessor2).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent log2 = (SimpleCallableJavaComponent) logProcessor2;

        assertThat(log2.getObjectType()).isEqualTo(SimpleLogComponent.class);

        assertThat(log2.getObjectFactory().isSingleton()).isEqualTo(true);

        SimpleLogComponent log2_1 = (SimpleLogComponent) log2.getObjectFactory().getInstance(null);
        SimpleLogComponent log2_2 = (SimpleLogComponent) log2.getObjectFactory().getInstance(null);

        assertThat(log2_1 == log2_2).isEqualTo(true);

        assertThat(log2_1.getLevel()).isEqualTo(LogLevel.WARN);

        assertThat(log_1 != log2_1).isEqualTo(true);
        assertThat(log_2 != log2_2).isEqualTo(true);

    }

    @Test
    public void simpleLogJustMessage() throws Exception {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .log("message here!");
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

        MessageProcessor logProcessor = iterator.next();

        assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

        assertThat(log.getObjectType()).isEqualTo(ExtendedLogComponent.class);

        assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);

        ExtendedLogComponent log1 = (ExtendedLogComponent) log.getObjectFactory().getInstance(null);
        ExtendedLogComponent log2 = (ExtendedLogComponent) log.getObjectFactory().getInstance(null);

        assertThat(log1 == log2).isEqualTo(true);

        assertThat(log1.getLevel()).isEqualTo(LogLevel.INFO);
        assertThat(log1.getMessage()).isEqualTo("message here!");
    }

    @Test
    public void simpleLogJustMessageChain() throws Exception {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .log("message here!")
                        .log("message here 2!");
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

        MessageProcessor logProcessor = iterator.next();

        assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

        assertThat(log.getObjectType()).isEqualTo(ExtendedLogComponent.class);

        assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);

        ExtendedLogComponent log_1 = (ExtendedLogComponent) log.getObjectFactory().getInstance(null);
        ExtendedLogComponent log_2 = (ExtendedLogComponent) log.getObjectFactory().getInstance(null);

        assertThat(log_1 == log_2).isEqualTo(true);

        assertThat(log_1.getLevel()).isEqualTo(LogLevel.INFO);
        assertThat(log_1.getMessage()).isEqualTo("message here!");

        MessageProcessor log2Processor = iterator.next();

        assertThat(log2Processor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent log2 = (SimpleCallableJavaComponent) log2Processor;

        assertThat(log2.getObjectType()).isEqualTo(ExtendedLogComponent.class);

        assertThat(log2.getObjectFactory().isSingleton()).isEqualTo(true);

        ExtendedLogComponent log2_1 = (ExtendedLogComponent) log2.getObjectFactory().getInstance(null);
        ExtendedLogComponent log2_2 = (ExtendedLogComponent) log2.getObjectFactory().getInstance(null);

        assertThat(log2_1 == log2_2).isEqualTo(true);

        assertThat(log2_1.getLevel()).isEqualTo(LogLevel.INFO);
        assertThat(log2_1.getMessage()).isEqualTo("message here 2!");

        assertThat(log_1 != log2_1).isEqualTo(true);
        assertThat(log_2 != log2_2).isEqualTo(true);
    }

    @Test
    public void simpleLogMessageAndLevel() throws Exception {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .log("message here!", LogLevel.WARN);
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

        MessageProcessor logProcessor = iterator.next();

        assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

        assertThat(log.getObjectType()).isEqualTo(ExtendedLogComponent.class);

        assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);

        ExtendedLogComponent log1 = (ExtendedLogComponent) log.getObjectFactory().getInstance(null);
        ExtendedLogComponent log2 = (ExtendedLogComponent) log.getObjectFactory().getInstance(null);

        assertThat(log1 == log2).isEqualTo(true);

        assertThat(log1.getLevel()).isEqualTo(LogLevel.WARN);
        assertThat(log1.getMessage()).isEqualTo("message here!");
    }


    @Test
    public void simpleLogMessageAndLevelChain() throws Exception {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .log("message here!", LogLevel.WARN)
                        .log("message here 2!", LogLevel.FATAL);
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

        MessageProcessor logProcessor = iterator.next();

        assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

        assertThat(log.getObjectType()).isEqualTo(ExtendedLogComponent.class);

        assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);

        ExtendedLogComponent log_1 = (ExtendedLogComponent) log.getObjectFactory().getInstance(null);
        ExtendedLogComponent log_2 = (ExtendedLogComponent) log.getObjectFactory().getInstance(null);

        assertThat(log_1 == log_2).isEqualTo(true);

        assertThat(log_1.getLevel()).isEqualTo(LogLevel.WARN);
        assertThat(log_1.getMessage()).isEqualTo("message here!");

        MessageProcessor log2Processor = iterator.next();

        assertThat(log2Processor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent log2 = (SimpleCallableJavaComponent) log2Processor;

        assertThat(log2.getObjectType()).isEqualTo(ExtendedLogComponent.class);

        assertThat(log2.getObjectFactory().isSingleton()).isEqualTo(true);

        ExtendedLogComponent log2_1 = (ExtendedLogComponent) log2.getObjectFactory().getInstance(null);
        ExtendedLogComponent log2_2 = (ExtendedLogComponent) log2.getObjectFactory().getInstance(null);

        assertThat(log2_1 == log2_2).isEqualTo(true);

        assertThat(log2_1.getLevel()).isEqualTo(LogLevel.FATAL);
        assertThat(log2_1.getMessage()).isEqualTo("message here 2!");

        assertThat(log_1 != log2_1).isEqualTo(true);
        assertThat(log_2 != log2_2).isEqualTo(true);
    }


    @Test
    public void expressionLogJustMessage() throws Exception {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .log(string("payload content: #[mule:message.payload()]"));
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

        MessageProcessor logProcessor = iterator.next();

        assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

        assertThat(log.getObjectType()).isEqualTo(ExpressionLogComponent.class);

        assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);

        ExpressionLogComponent log1 = (ExpressionLogComponent) log.getObjectFactory().getInstance(null);
        ExpressionLogComponent log2 = (ExpressionLogComponent) log.getObjectFactory().getInstance(null);

        assertThat(log1 == log2).isEqualTo(true);

        assertThat(log1.getLevel()).isEqualTo(LogLevel.INFO);
        assertThat(log1.getExpression()).isEqualTo("payload content: #[mule:message.payload()]");
        assertThat(log1.getEvaluator()).isEqualTo("string");
        assertThat(log1.getCustomEvaluator()).isNull();
    }

    @Test
    public void expressionLogJustMessageChain() throws Exception {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .log(string("payload content: #[mule:message.payload()]"))
                        .log(string("payload2 content: #[mule:message.payload()]"));
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

        MessageProcessor logProcessor = iterator.next();

        assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

        assertThat(log.getObjectType()).isEqualTo(ExpressionLogComponent.class);

        assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);

        ExpressionLogComponent log_1 = (ExpressionLogComponent) log.getObjectFactory().getInstance(null);
        ExpressionLogComponent log_2 = (ExpressionLogComponent) log.getObjectFactory().getInstance(null);

        assertThat(log_1 == log_2).isEqualTo(true);

        assertThat(log_1.getLevel()).isEqualTo(LogLevel.INFO);
        assertThat(log_1.getExpression()).isEqualTo("payload content: #[mule:message.payload()]");
        assertThat(log_1.getEvaluator()).isEqualTo("string");
        assertThat(log_1.getCustomEvaluator()).isNull();

        MessageProcessor log2Processor = iterator.next();

        assertThat(log2Processor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent log2 = (SimpleCallableJavaComponent) log2Processor;

        assertThat(log2.getObjectType()).isEqualTo(ExpressionLogComponent.class);

        assertThat(log2.getObjectFactory().isSingleton()).isEqualTo(true);

        ExpressionLogComponent log2_1 = (ExpressionLogComponent) log2.getObjectFactory().getInstance(null);
        ExpressionLogComponent log2_2 = (ExpressionLogComponent) log2.getObjectFactory().getInstance(null);

        assertThat(log2_1 == log2_2).isEqualTo(true);

        assertThat(log2_1.getLevel()).isEqualTo(LogLevel.INFO);
        assertThat(log2_1.getExpression()).isEqualTo("payload2 content: #[mule:message.payload()]");
        assertThat(log2_1.getEvaluator()).isEqualTo("string");
        assertThat(log2_1.getCustomEvaluator()).isNull();

        assertThat(log_1 != log2_1).isEqualTo(true);
        assertThat(log_2 != log2_2).isEqualTo(true);
    }

    @Test
    public void expressionLogMessageAndLevel() throws Exception {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .log(string("payload content: #[mule:message.payload()]"), LogLevel.WARN);
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

        MessageProcessor logProcessor = iterator.next();

        assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

        assertThat(log.getObjectType()).isEqualTo(ExpressionLogComponent.class);

        assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);

        ExpressionLogComponent log1 = (ExpressionLogComponent) log.getObjectFactory().getInstance(null);
        ExpressionLogComponent log2 = (ExpressionLogComponent) log.getObjectFactory().getInstance(null);

        assertThat(log1 == log2).isEqualTo(true);

        assertThat(log1.getLevel()).isEqualTo(LogLevel.WARN);
        assertThat(log1.getExpression()).isEqualTo("payload content: #[mule:message.payload()]");
        assertThat(log1.getEvaluator()).isEqualTo("string");
        assertThat(log1.getCustomEvaluator()).isNull();
    }


    @Test
    public void expressionLogMessageAndLevelChain() throws Exception {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .log(string("payload content: #[mule:message.payload()]"), LogLevel.WARN)
                        .log(string("payload2 content: #[mule:message.payload()]"), LogLevel.FATAL);
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

        MessageProcessor logProcessor = iterator.next();

        assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

        assertThat(log.getObjectType()).isEqualTo(ExpressionLogComponent.class);

        assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);

        ExpressionLogComponent log_1 = (ExpressionLogComponent) log.getObjectFactory().getInstance(null);
        ExpressionLogComponent log_2 = (ExpressionLogComponent) log.getObjectFactory().getInstance(null);

        assertThat(log_1 == log_2).isEqualTo(true);

        assertThat(log_1.getLevel()).isEqualTo(LogLevel.WARN);
        assertThat(log_1.getExpression()).isEqualTo("payload content: #[mule:message.payload()]");
        assertThat(log_1.getEvaluator()).isEqualTo("string");
        assertThat(log_1.getCustomEvaluator()).isNull();

        MessageProcessor log2Processor = iterator.next();

        assertThat(log2Processor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        SimpleCallableJavaComponent log2 = (SimpleCallableJavaComponent) log2Processor;

        assertThat(log2.getObjectType()).isEqualTo(ExpressionLogComponent.class);

        assertThat(log2.getObjectFactory().isSingleton()).isEqualTo(true);

        ExpressionLogComponent log2_1 = (ExpressionLogComponent) log2.getObjectFactory().getInstance(null);
        ExpressionLogComponent log2_2 = (ExpressionLogComponent) log2.getObjectFactory().getInstance(null);

        assertThat(log2_1 == log2_2).isEqualTo(true);

        assertThat(log2_1.getLevel()).isEqualTo(LogLevel.FATAL);
        assertThat(log_1.getExpression()).isEqualTo("payload content: #[mule:message.payload()]");
        assertThat(log_1.getEvaluator()).isEqualTo("string");
        assertThat(log_1.getCustomEvaluator()).isNull();

        assertThat(log_1 != log2_1).isEqualTo(true);
        assertThat(log_2 != log2_2).isEqualTo(true);
    }
}