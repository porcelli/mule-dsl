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
import org.mule.routing.MessageFilter;
import org.mule.routing.filters.ExpressionFilter;

import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;
import static org.mule.config.dsl.expression.CoreExpr.generic;
import static org.mule.config.dsl.expression.CoreExpr.string;

public class TestExpressionFilter {

    @Test
    public void testSimpleFilterExpression() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .filter(generic("#[wildcard:foo*]"));
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

        Iterator iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        MessageProcessor filterProcessor = (MessageProcessor) iterator.next();

        assertThat(filterProcessor).isNotNull().isInstanceOf(MessageFilter.class);

        MessageFilter filter = (MessageFilter) filterProcessor;

        assertThat(filter.getFilter()).isInstanceOf(ExpressionFilter.class);

        ExpressionFilter exprFilter = (ExpressionFilter) filter.getFilter();

        assertThat(exprFilter.getExpression()).isEqualTo("foo*");
        assertThat(exprFilter.getEvaluator()).isEqualTo("wildcard");
        assertThat(exprFilter.getCustomEvaluator()).isNull();
    }

    @Test
    public void testChainFilterExpression() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .filter(generic("#[wildcard:foo*]"))
                        .filter(string("'some text here'"));
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

        Iterator iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        MessageProcessor filterProcessor = (MessageProcessor) iterator.next();

        assertThat(filterProcessor).isNotNull().isInstanceOf(MessageFilter.class);

        MessageFilter filter = (MessageFilter) filterProcessor;

        assertThat(filter.getFilter()).isInstanceOf(ExpressionFilter.class);

        ExpressionFilter exprFilter = (ExpressionFilter) filter.getFilter();

        assertThat(exprFilter.getExpression()).isEqualTo("foo*");
        assertThat(exprFilter.getEvaluator()).isEqualTo("wildcard");
        assertThat(exprFilter.getCustomEvaluator()).isNull();

        MessageProcessor filterProcessor2 = (MessageProcessor) iterator.next();

        assertThat(filterProcessor2).isNotNull().isInstanceOf(MessageFilter.class);

        MessageFilter filter2 = (MessageFilter) filterProcessor2;

        assertThat(filter2.getFilter()).isInstanceOf(ExpressionFilter.class);

        ExpressionFilter exprFilter2 = (ExpressionFilter) filter2.getFilter();

        assertThat(exprFilter2.getExpression()).isEqualTo("'some text here'");
        assertThat(exprFilter2.getEvaluator()).isEqualTo("string");
        assertThat(exprFilter2.getCustomEvaluator()).isNull();
    }
}