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
import org.mule.routing.MessageFilter;
import org.mule.routing.filters.PayloadTypeFilter;

import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;

public class TestTypeBasedFilter {

    @Test
    public void testSimpleFilterBy() throws MuleException, InterruptedException {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .filterBy(String.class);
            }
        }).advanced().muleContext();

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

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            final MessageProcessor filterProcessor = iterator.next();

            assertThat(filterProcessor).isNotNull().isInstanceOf(MessageFilter.class);

            final MessageFilter filter = (MessageFilter) filterProcessor;

            assertThat(filter.getFilter()).isInstanceOf(PayloadTypeFilter.class);

            final PayloadTypeFilter typeFilter = (PayloadTypeFilter) filter.getFilter();

            assertThat(typeFilter.getExpectedType()).isEqualTo(String.class);
        }
    }

    @Test
    public void testFilterByWithPromitiveType() throws MuleException, InterruptedException {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .filterBy(int.class);
            }
        }).advanced().muleContext();

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

        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

        final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();

        {
            final MessageProcessor filterProcessor = iterator.next();

            assertThat(filterProcessor).isNotNull().isInstanceOf(MessageFilter.class);

            final MessageFilter filter = (MessageFilter) filterProcessor;

            assertThat(filter.getFilter()).isInstanceOf(PayloadTypeFilter.class);

            final PayloadTypeFilter typeFilter = (PayloadTypeFilter) filter.getFilter();

            assertThat(typeFilter.getExpectedType()).isEqualTo(int.class);
        }
    }

    @Test(expected = RuntimeException.class)
    public void testFilterByNull() throws MuleException, InterruptedException {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .filterBy(null);
            }
        });

    }
}