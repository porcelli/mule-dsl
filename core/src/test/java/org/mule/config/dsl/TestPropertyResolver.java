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
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.source.MessageSource;
import org.mule.component.SimpleCallableJavaComponent;
import org.mule.config.dsl.component.ExtendedLogComponent;
import org.mule.construct.SimpleFlowConstruct;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import static org.fest.assertions.Assertions.assertThat;

public class TestPropertyResolver {

    @Test(expected = RuntimeException.class)
    public void classpathResolverNotFound() throws Exception {
        new Mule(new AbstractModule() {
            @Override
            public void configure() {

                propertyResolver(classpath("test-resource-not-found.properties"));

                flow("MyFlow")
                        .from("file://${in.folder.path}")
                        .log("message here: ${my.app.name}!")
                        .send("file://${out.folder.path}");
            }
        });
    }

    @Test
    public void classpathSimpleInboundLogAndOutbound() throws Exception {
        final MuleContext muleContext = new Mule(new AbstractModule() {
            @Override
            public void configure() {

                propertyResolver(classpath("test-resource.properties"));

                flow("MyFlow")
                        .from("file://${in.folder.path}")
                        .log("message here: ${my.app.name}!")
                        .send("file://${out.folder.path}");
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

        final MessageProcessor logProcessor = iterator.next();

        assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

        assertThat(log.getObjectType()).isEqualTo(ExtendedLogComponent.class);

        assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);

        final ExtendedLogComponent log1 = (ExtendedLogComponent) log.getObjectFactory().getInstance(null);
        final ExtendedLogComponent log2 = (ExtendedLogComponent) log.getObjectFactory().getInstance(null);

        assertThat(log1 == log2).isEqualTo(true);

        assertThat(log1.getLevel()).isEqualTo(LogLevel.INFO);
        assertThat(log1.getMessage()).isEqualTo("message here: My App cool name!");

        final MessageProcessor processor = iterator.next();

        assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

        final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

        assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/out");
    }

    @Test(expected = RuntimeException.class)
    public void fileResolverNotFound() throws Exception {
        new Mule(new AbstractModule() {
            @Override
            public void configure() {

                propertyResolver(file("test-resource-not-found.properties"));

                flow("MyFlow")
                        .from("file://${in.folder.path}")
                        .log("message here: ${my.app.name}!")
                        .send("file://${out.folder.path}");
            }
        });
    }

    @Test
    public void fileSimpleInboundLogAndOutbound() throws Exception {
        final MuleContext muleContext = new Mule(new AbstractModule() {
            @Override
            public void configure() {

                propertyResolver(file("./src/test/resources/test-resource.properties"));

                flow("MyFlow")
                        .from("file://${in.folder.path}")
                        .log("message here: ${my.app.name}!")
                        .send("file://${out.folder.path}");
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

        final MessageProcessor logProcessor = iterator.next();

        assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

        assertThat(log.getObjectType()).isEqualTo(ExtendedLogComponent.class);

        assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);

        final ExtendedLogComponent log1 = (ExtendedLogComponent) log.getObjectFactory().getInstance(null);
        final ExtendedLogComponent log2 = (ExtendedLogComponent) log.getObjectFactory().getInstance(null);

        assertThat(log1 == log2).isEqualTo(true);

        assertThat(log1.getLevel()).isEqualTo(LogLevel.INFO);
        assertThat(log1.getMessage()).isEqualTo("message here: My App cool name!");

        final MessageProcessor processor = iterator.next();

        assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

        final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

        assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/out");
    }


    @Test(expected = RuntimeException.class)
    public void inputStreamResolverNotFound() throws Exception {
        new Mule(new AbstractModule() {
            @Override
            public void configure() {

                propertyResolver(getClass().getResourceAsStream("test-resource.properties"));

                flow("MyFlow")
                        .from("file://${in.folder.path}")
                        .log("message here: ${my.app.name}!")
                        .send("file://${out.folder.path}");
            }
        });
    }

    @Test
    public void inputStreamSimpleInboundLogAndOutbound() throws Exception {
        final MuleContext muleContext = new Mule(new AbstractModule() {
            @Override
            public void configure() {

                propertyResolver(getClass().getResourceAsStream("../../../../test-resource.properties"));

                flow("MyFlow")
                        .from("file://${in.folder.path}")
                        .log("message here: ${my.app.name}!")
                        .send("file://${out.folder.path}");
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

        final MessageProcessor logProcessor = iterator.next();

        assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

        assertThat(log.getObjectType()).isEqualTo(ExtendedLogComponent.class);

        assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);

        final ExtendedLogComponent log1 = (ExtendedLogComponent) log.getObjectFactory().getInstance(null);
        final ExtendedLogComponent log2 = (ExtendedLogComponent) log.getObjectFactory().getInstance(null);

        assertThat(log1 == log2).isEqualTo(true);

        assertThat(log1.getLevel()).isEqualTo(LogLevel.INFO);
        assertThat(log1.getMessage()).isEqualTo("message here: My App cool name!");

        final MessageProcessor processor = iterator.next();

        assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

        final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

        assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/out");
    }

    @Test
    public void inputMapInboundLogAndOutbound() throws Exception {
        final MuleContext muleContext = new Mule(new AbstractModule() {
            @Override
            public void configure() {

                final Map<String, String> properties = new HashMap<String, String>();
                properties.put("my.app.name", "My App cool name");
                properties.put("in.folder.path", "/Users/porcelli/test");
                properties.put("out.folder.path", "/Users/porcelli/out");

                propertyResolver(properties);

                flow("MyFlow")
                        .from("file://${in.folder.path}")
                        .log("message here: ${my.app.name}!")
                        .send("file://${out.folder.path}");
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

        final MessageProcessor logProcessor = iterator.next();

        assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

        assertThat(log.getObjectType()).isEqualTo(ExtendedLogComponent.class);

        assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);

        final ExtendedLogComponent log1 = (ExtendedLogComponent) log.getObjectFactory().getInstance(null);
        final ExtendedLogComponent log2 = (ExtendedLogComponent) log.getObjectFactory().getInstance(null);

        assertThat(log1 == log2).isEqualTo(true);

        assertThat(log1.getLevel()).isEqualTo(LogLevel.INFO);
        assertThat(log1.getMessage()).isEqualTo("message here: My App cool name!");

        final MessageProcessor processor = iterator.next();

        assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

        final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

        assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/out");
    }

    @Test
    public void inputPropertiesInboundLogAndOutbound() throws Exception {
        final MuleContext muleContext = new Mule(new AbstractModule() {
            @Override
            public void configure() {

                final Properties properties = new Properties();
                properties.setProperty("my.app.name", "My App cool name");
                properties.setProperty("in.folder.path", "/Users/porcelli/test");
                properties.setProperty("out.folder.path", "/Users/porcelli/out");

                propertyResolver(properties);

                flow("MyFlow")
                        .from("file://${in.folder.path}")
                        .log("message here: ${my.app.name}!")
                        .send("file://${out.folder.path}");
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

        final MessageProcessor logProcessor = iterator.next();

        assertThat(logProcessor).isNotNull().isInstanceOf(SimpleCallableJavaComponent.class);

        final SimpleCallableJavaComponent log = (SimpleCallableJavaComponent) logProcessor;

        assertThat(log.getObjectType()).isEqualTo(ExtendedLogComponent.class);

        assertThat(log.getObjectFactory().isSingleton()).isEqualTo(true);

        final ExtendedLogComponent log1 = (ExtendedLogComponent) log.getObjectFactory().getInstance(null);
        final ExtendedLogComponent log2 = (ExtendedLogComponent) log.getObjectFactory().getInstance(null);

        assertThat(log1 == log2).isEqualTo(true);

        assertThat(log1.getLevel()).isEqualTo(LogLevel.INFO);
        assertThat(log1.getMessage()).isEqualTo("message here: My App cool name!");

        final MessageProcessor processor = iterator.next();

        assertThat(processor).isNotNull().isInstanceOf(OutboundEndpoint.class);

        final OutboundEndpoint outboundEndpoint = (OutboundEndpoint) processor;

        assertThat(outboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

        assertThat(outboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

        assertThat(outboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/out");
    }

    @Test(expected = RuntimeException.class)
    public void fileResolverNullInputStream() throws Exception {
        new Mule(new AbstractModule() {
            @Override
            public void configure() {

                propertyResolver((InputStream) null);

                flow("MyFlow")
                        .from("file://${in.folder.path}")
                        .log("message here: ${my.app.name}!")
                        .send("file://${out.folder.path}");
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void fileResolverNullMap() throws Exception {
        new Mule(new AbstractModule() {
            @Override
            public void configure() {

                propertyResolver((Map<String, String>) null);

                flow("MyFlow")
                        .from("file://${in.folder.path}")
                        .log("message here: ${my.app.name}!")
                        .send("file://${out.folder.path}");
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void fileResolverNullProperties() throws Exception {
        new Mule(new AbstractModule() {
            @Override
            public void configure() {

                propertyResolver((Properties) null);

                flow("MyFlow")
                        .from("file://${in.folder.path}")
                        .log("message here: ${my.app.name}!")
                        .send("file://${out.folder.path}");
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void fileResolverNullClasspathBuilder() throws Exception {
        new Mule(new AbstractModule() {
            @Override
            public void configure() {

                propertyResolver((ClasspathBuilder) null);

                flow("MyFlow")
                        .from("file://${in.folder.path}")
                        .log("message here: ${my.app.name}!")
                        .send("file://${out.folder.path}");
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void fileResolverNullFileRefBuilder() throws Exception {
        new Mule(new AbstractModule() {
            @Override
            public void configure() {

                propertyResolver((FileRefBuilder) null);

                flow("MyFlow")
                        .from("file://${in.folder.path}")
                        .log("message here: ${my.app.name}!")
                        .send("file://${out.folder.path}");
            }
        });
    }
}