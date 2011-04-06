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
import java.io.File;
import java.lang.annotation.Annotation;

public abstract class AbstractMethodModule extends AbstractModule {

    public void usePropertyPlaceholder(String fileRef) {

    }

    public void usePropertyPlaceholder(File s) {

    }

    public FlowBuilder newFlow(String myFlow) {
        return null;
    }

    public InboundEndpointBuilder from(String from) {
        return null;
    }

    public ProcessorBuilder execute(Class<?> clazz) {
        return null;
    }

    public ProcessorBuilder execute(Object myObj) {
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
        InboundEndpointBuilder processRequest(ProcessorBuilder... processors);
        InboundEndpointBuilder processResponse(ProcessorBuilder... processors);
        InboundEndpointBuilder connectUsing(Connector connector);
        InboundEndpointBuilder connectUsing(String connectorRef);
    }

    public interface ProcessorBuilder {
        ProcessorBuilder methodAnnotatedWith(Class<? extends Annotation> annotationType);
        ProcessorBuilder methodAnnotatedWith(Annotation annotation);
        ProcessorBuilder asSingleton();
    }

    public abstract static class HTTP {
        public static HTTPPoll poll(HostBuilder hostBuilder) {
            return null;
        }

        public static HTTPInboundBuilder listen(HostBuilder hostBuilder) {
            return null;
        }

        public interface HTTPPoll extends HTTPInboundBuilder {
            HTTPInboundBuilder every(long time);

            HTTPInboundBuilder every(long time, TimeUnit unit);
        }

        public interface HTTPInboundBuilder extends InboundEndpointBuilder {
            <T extends InboundEndpointBuilder> T using(T x);

            <T extends InboundEndpointBuilder> T using(Class<T> x);
        }
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
        public static InboundEndpointBuilder queue(String queue) {
            return null;
        }

        public static InboundEndpointBuilder topic(String topic) {
            return null;
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
