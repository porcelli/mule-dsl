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
import org.mule.expression.transformers.ExpressionArgument;
import org.mule.expression.transformers.ExpressionTransformer;

import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;
import static org.mule.config.dsl.expression.CoreExpr.string;

public class TestExpressionTransform {

    @Test
    public void testSimpleExpressionTransform() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .transform(string("'JUST #[mule:message.payload()] COOL!'"));
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

        final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor transformerProcessor = iterator.next();

        assertThat(transformerProcessor).isNotNull().isInstanceOf(ExpressionTransformer.class);

        final ExpressionTransformer transformer = (ExpressionTransformer) transformerProcessor;

        assertThat(transformer.getArguments()).isNotEmpty().hasSize(1);

        assertThat(transformer.getArguments().get(0)).isNotNull().isInstanceOf(ExpressionArgument.class);

        final ExpressionArgument argument = transformer.getArguments().get(0);

        assertThat(argument.getExpression()).isEqualTo("'JUST #[mule:message.payload()] COOL!'");
        assertThat(argument.getEvaluator()).isEqualTo("string");
        assertThat(argument.getCustomEvaluator()).isNull();
    }


    @Test
    public void testChainExpressionTransform() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .transform(string("'JUST #[mule:message.payload()] COOL!'"))
                        .transform(string("'JUST2 #[mule:message.payload()] COOL!'"));
            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);

        final FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();

        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

        final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

        final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(2);

        final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        final MessageProcessor transformerProcessor = iterator.next();

        assertThat(transformerProcessor).isNotNull().isInstanceOf(ExpressionTransformer.class);

        final ExpressionTransformer transformer = (ExpressionTransformer) transformerProcessor;

        assertThat(transformer.getArguments()).isNotEmpty().hasSize(1);

        assertThat(transformer.getArguments().get(0)).isNotNull().isInstanceOf(ExpressionArgument.class);

        final ExpressionArgument argument = transformer.getArguments().get(0);

        assertThat(argument.getExpression()).isEqualTo("'JUST #[mule:message.payload()] COOL!'");
        assertThat(argument.getEvaluator()).isEqualTo("string");
        assertThat(argument.getCustomEvaluator()).isNull();


        final MessageProcessor transformerProcessor2 = iterator.next();

        assertThat(transformerProcessor2).isNotNull().isInstanceOf(ExpressionTransformer.class);

        final ExpressionTransformer transformer2 = (ExpressionTransformer) transformerProcessor2;

        assertThat(transformer2.getArguments()).isNotEmpty().hasSize(1);

        assertThat(transformer2.getArguments().get(0)).isNotNull().isInstanceOf(ExpressionArgument.class);

        final ExpressionArgument argument2 = transformer2.getArguments().get(0);

        assertThat(argument2.getExpression()).isEqualTo("'JUST2 #[mule:message.payload()] COOL!'");
        assertThat(argument2.getEvaluator()).isEqualTo("string");
        assertThat(argument2.getCustomEvaluator()).isNull();

    }
}