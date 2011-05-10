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
import org.mule.api.MuleException;
import org.mule.api.component.JavaComponent;
import org.mule.api.config.ConfigurationException;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.lifecycle.Callable;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.source.MessageSource;
import org.mule.construct.SimpleFlowConstruct;

import static org.fest.assertions.Assertions.assertThat;

public class TestSimpleServiceFlow {

//    @Test
//    public void testMuleRegistry() throws MuleException, InitialisationException, ConfigurationException, InterruptedException {
//        MuleContextFactory muleContextFactory = new DefaultMuleContextFactory();
//
//        MuleContext muleContext = muleContextFactory.createMuleContext();
//        EndpointBuilder endpointBuilder = new EndpointURIEndpointBuilder(new URIBuilder("file:///Users/porcelli/test", muleContext));
//        InboundEndpoint endpoint = endpointBuilder.buildInboundEndpoint();
//
//        SimpleFlowConstruct flow = new SimpleFlowConstruct("MyFlow", muleContext);
//        flow.setMessageSource(endpoint);
//
//        List<MessageProcessor> list = new ArrayList<MessageProcessor>();
//
//        list.add(new SimpleCallableJavaComponent(SimpleCallable.class));
//
//        flow.setMessageProcessors(list);
//
//        muleContext.getRegistry().registerFlowConstruct(flow);
//
//
//        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);
//
//        FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();
//
//        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
//        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);
//
//        MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();
//
//        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);
//
//        InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;
//
//        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);
//
//        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");
//
//        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");
//
//        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);
//
//        MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();
//
//        assertThat(processor).isNotNull().isInstanceOf(JavaComponent.class);
//
//        muleContext.start();
//    }


    @Test
    public void simpleService() throws MuleException, ConfigurationException, InterruptedException {
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

        MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();

        assertThat(processor).isNotNull().isInstanceOf(JavaComponent.class);
    }

    public static class SimpleCallable implements Callable {

        @Override
        public Object onCall(MuleEventContext muleEventContext) throws Exception {
            System.out.println("AQUI!");
            return null;
        }
    }

}
