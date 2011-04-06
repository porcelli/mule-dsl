/*
 * $Id: 20811 2011-04-01 14:45:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.aproach2;

import com.google.inject.AbstractModule;

import javax.xml.transform.Transformer;
import java.lang.annotation.Annotation;

public abstract class AbstractMethodModule extends AbstractModule {

    public FlowBuilder newFlow(String myFlow) {
        return null;
    }

    public InboundEndpointBuilder from(String from) {
        return null;
    }

    public ProcessorBuilder execute(Class<?> clazz) {
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

    public <P extends InboundEndpointBuilder> P from(P from) {
        return null;
    }

    public interface FlowBuilder {
        FlowBuilder in(InboundEndpointBuilder inbound);

        FlowBuilder in(InboundEndpointBuilder... inbounds);

        FlowBuilder process(ProcessorBuilder processor);

        FlowBuilder process(ProcessorBuilder... processors);
    }

    public interface InboundEndpointBuilder {
        <T extends InboundEndpointBuilder> T using(T x);

        <T extends InboundEndpointBuilder> T using(Class<T> x);

        InboundEndpointBuilder processRequest(ProcessorBuilder... processors);

        InboundEndpointBuilder processResponse(ProcessorBuilder... processors);
    }

    public interface ProcessorBuilder {
        ProcessorBuilder methodAnnotatedWith(Class<? extends Annotation> annotationType);
        ProcessorBuilder methodAnnotatedWith(Annotation annotation);
        ProcessorBuilder asSingleton();
    }

    public abstract static class FTP {
        public static FTPInboundBuilder poll(HostBuilder hostBuilder) {
            return null;
        }

        public interface FTPInboundBuilder extends InboundEndpointBuilder {
            abstract FTPInboundBuilder every(long time);

            abstract FTPInboundBuilder every(long time, TimeUnit unit);
        }
    }

    public abstract static class JMS {
        public static JMSInboundBuilder using(Connector time) {
            return null;
        }

        public static JMSInboundBuilder queue(String queue) {
            return null;
        }

        public static JMSInboundBuilder topic(String topic) {
            return null;
        }

        public interface JMSInboundBuilder extends InboundEndpointBuilder {
            JMSInboundBuilder using(Connector time);

            JMSInboundBuilder queue(String queue);

            JMSInboundBuilder topic(String topic);
        }

    }

    public interface Connector {
    }

    public interface CXF extends InboundEndpointBuilder {
        InboundEndpointBuilder with(Class<?> clazz);
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
