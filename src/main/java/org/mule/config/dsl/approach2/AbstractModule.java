/*
 * $Id: 20811 2011-04-01 14:45:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.approach2;

import java.io.File;
import java.io.InputStream;
import java.lang.annotation.Annotation;

public abstract class AbstractModule extends com.google.inject.AbstractModule {


    public OutboundExecutorBuilder send(EndpointProcessor emailNotification) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public OutboundExecutorBuilder send(String stats) {
        return null;
    }

    public OutboundExecutorBuilder sendAndWait(EndpointProcessor emailNotification) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public OutboundExecutorBuilder sendAndWait(String stats) {
        return null;
    }

    public TransformerBuilder newTransformer(String setHtmlContentType) {
        return null;
    }

    public void usePropertyPlaceholder(String fileRef) {
    }

    public void usePropertyPlaceholder(File s) {
    }

    public void usePropertyPlaceholder(InputStream resourceAsStream) {
    }

    public FlowBuilder newFlow(String myFlow) {
        return null;
    }

    public EndpointProcessor defineEndpoint(String stats) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }


    public InboundEndpointBuilder from(String from) {
        return null;
    }

    public CustomExecutorBuilder execute(Class<?> clazz) {
        return null;
    }

    public CustomExecutorBuilder execute(Object myObj) {
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

    public ProcessorBuilder transformWith(Transformer transformer) {
        return null;
    }

    public <P extends EndpointProcessor> P from(P from) {
        return null;
    }

    public interface FlowBuilder {
        FlowBuilder in(EndpointProcessor inbound);

        FlowBuilder in(EndpointProcessor... inbounds);

        FlowBuilder process(ProcessorBuilder processor);

        FlowBuilder process(ProcessorBuilder... processors);
    }

    public interface EndpointProcessor {
        EndpointProcessor connectUsing(Connector connector);

        EndpointProcessor connectUsing(String connectorRef);

        <P extends EndpointProcessor> P using(EndpointProcessor endpoint);
    }


    public interface OutboundEndpointProcessor extends EndpointProcessor {
    }

    public interface InboundEndpointBuilder extends EndpointProcessor {
        InboundEndpointBuilder processRequest(ProcessorBuilder... processors);

        InboundEndpointBuilder processResponse(ProcessorBuilder... processors);
    }

    public interface ProcessorBuilder {
    }

    public interface CustomExecutorBuilder extends ProcessorBuilder {
        CustomExecutorBuilder methodAnnotatedWith(Class<? extends Annotation> annotationType);

        CustomExecutorBuilder methodAnnotatedWith(Annotation annotation);

        CustomExecutorBuilder asSingleton();
    }

    public interface OutboundExecutorBuilder extends ProcessorBuilder {
    }


    public abstract static class VM {
        public static EndpointProcessor path(String path) {
            return null;
        }
    }

    public abstract static class SMTPS {
        public static SMTPSBuilder user(String s) {
            return null;
        }

        public static SMTPSBuilder password(String s) {
            return null;
        }

        public static SMTPSBuilder host(String s) {
            return null;
        }

        public static SMTPSBuilder from(String s) {
            return null;
        }

        public static SMTPSBuilder subject(String s) {
            return null;
        }


        public interface SMTPSBuilder extends OutboundEndpointProcessor {
            SMTPSBuilder user(String s);

            SMTPSBuilder password(String s);

            SMTPSBuilder host(String s);

            SMTPSBuilder from(String s);

            SMTPSBuilder subject(String s);
        }

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
            <T extends InboundEndpointBuilder> T as(T x);

            <T extends InboundEndpointBuilder> T as(Class<T> x);
        }
    }

    public abstract static class FTP {
        public static FTPInboundBuilder using(String inboundRef) {
            return null;
        }

        public static FTPInboundBuilder using(EndpointProcessor endpoint) {
            return null;
        }

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

    public abstract static class WS {
        public static InboundEndpointBuilder with(Class<?> clazz) {
            return null;
        }
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

    public interface TransformerBuilder extends Transformer {
        <P extends TransformerBuilder> P extend(Class<P> transoformer);
    }


    public interface MessagePropertiesTransformer extends TransformerBuilder {
        MessagePropertiesTransformer addMessageProperty(String key, String value);

        MessagePropertiesTransformer deleteMessageProperty(String key);
    }

    public ConnectorBuilder newConnector(String connectorRef) {
        return null;
    }

    public interface Connector {

    }

    public interface ConnectorBuilder extends Connector {
        <P extends ConnectorBuilder> P extend(Class<P> transoformer);
    }


    public interface VM_Connector extends ConnectorBuilder {
    }

    public interface SMTP_Connector extends ConnectorBuilder {
    }

    public interface GMail_Connector extends SMTP_Connector {
    }

}
