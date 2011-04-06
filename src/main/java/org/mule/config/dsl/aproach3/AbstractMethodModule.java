/*
 * $Id: 20811 2011-04-01 14:45:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.aproach3;

import com.google.inject.AbstractModule;
import java.lang.annotation.Annotation;

public abstract class AbstractMethodModule extends AbstractModule {

    public FlowBuilder newFlow(String myFlow) {
        return null;
    }

    public InboundEndpointProcessor from(String from) {
        return null;
    }

    public ProcessorBuilder execute(Class<?> clazz) {
        return null;
    }

    public ProcessorBuilder execute(Object obj) {
        return null;
    }

    public HostBuilder host(String host) {
        return null;
    }

    public ProcessorBuilder filter() {
        return null;
    }

    public ProcessorBuilder transformTo(Class<?> clazz) {
        return null;
    }

    public ProcessorBuilder transformWith(Class<? extends Transformer> clazz) {
        return null;
    }

    public ProcessorBuilder multicast(ProcessorBuilder... processors) {
        return null;
    }

    public ProcessorBuilder send() {
        return null;
    }

    public <P extends InboundEndpointBuilder> P from(Class<P> from) {
        return null;
    }

    public interface FlowBuilder {
        FlowBuilder in(InboundEndpointBuilder inbound);

        FlowBuilder in(InboundEndpointProcessor... inbounds);

        FlowBuilder process(ProcessorBuilder... processors);
    }

    public interface InboundEndpointBuilder {
    }

    public interface InboundEndpointProcessor {
        <T extends InboundEndpointProcessor> T using(T x);

        <T extends InboundEndpointProcessor> T using(Class<T> x);

        InboundEndpointProcessor processRequest(ProcessorBuilder... processors);

        InboundEndpointProcessor processResponse(ProcessorBuilder... processors);
    }

    public interface ProcessorBuilder {
        ProcessorBuilder methodAnnotatedWith(Class<? extends Annotation> annotationType);
        ProcessorBuilder methodAnnotatedWith(Annotation annotation);
        ProcessorBuilder asSingleton();
    }

    public interface HTTP extends InboundEndpointBuilder {
        HTTPPoll poll(HostBuilder hostBuilder);

        InboundEndpointProcessor listen(HostBuilder hostBuilder);

        public interface HTTPPoll {
            InboundEndpointProcessor every(long time);

            InboundEndpointProcessor every(long time, TimeUnit unit);
        }
    }

    public interface FTP extends InboundEndpointBuilder {
        FTPPoll poll(HostBuilder hostBuilder);

        public interface FTPPoll {
            InboundEndpointProcessor every(long time);

            InboundEndpointProcessor every(long time, TimeUnit unit);
        }
    }

    public interface JMS extends InboundEndpointBuilder {
        JMS using(Connector time);

        JMSIn queue(String queue);

        JMSIn topic(String topic);

        public interface JMSIn extends InboundEndpointProcessor{

        }
    }

    public interface Connector {
    }

    public interface CXF extends InboundEndpointProcessor {
        InboundEndpointProcessor with(Class<?> clazz);
    }

    public enum TimeUnit {
        MILISECONDS, SECONDS, MINUTES, HOURS, DAYS
    }

    public interface HostBuilder {
        HostBuilder port(int port);

        HostBuilder path(String path);
    }

    public interface Transformer {
    }
}