/*
 * $Id: 20811 2011-04-01 14:45:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.approach3;

import java.io.File;
import java.io.InputStream;
import java.lang.annotation.Annotation;

public abstract class AbstractModule extends com.google.inject.AbstractModule {

    public TransformerBuilder defineTransformer(String setHtmlContentType) {
        return null;
    }

    public EndpointProcessor defineEndpoint(String stats) {
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

    public InboundEndpointProcessor from(String from) {
        return null;
    }

    public CustomExecutorBuilder execute(Class<?> clazz) {
        return null;
    }

    public CustomExecutorBuilder execute(Object obj) {
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

    public ProcessorBuilder transformWith(Transformer obj) {
        return null;
    }

    public ProcessorBuilder transformWith(TransformerBuilder obj) {
        return null;
    }

    public ProcessorBuilder multicast(ProcessorBuilder... processors) {
        return null;
    }

    public EndpointProcessor send(String stats) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public <P extends EndpointProcessor> P send(Class<P> clazz) {
        return null;
    }

    public EndpointProcessor send(EndpointProcessor obj) {
        return null;
    }

    public EndpointProcessor sendAndWait(String stats) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public <P extends EndpointProcessor> P sendAndWait(Class<P> clazz) {
        return null;
    }

    public EndpointProcessor sendAndWait(EndpointProcessor obj) {
        return null;
    }


    public <P extends InboundEndpointProcessor> P from(Class<P> from) {
        return null;
    }

    public interface FlowBuilder {
        FlowBuilder in(EndpointProcessor inbound);

        FlowBuilder in(EndpointProcessor... inbounds);

        FlowBuilder process(ProcessorBuilder... processors);
    }

    public interface EndpointProcessor extends ProcessorBuilder {
        EndpointProcessor connectUsing(Connector connector);

        EndpointProcessor connectUsing(String connectorRef);

        <P extends EndpointProcessor> P using(Class<P> endpoint);

        EndpointProcessor using(String endpointRef);

        EndpointProcessor extend(EndpointProcessor endpoint);

    }

    public interface OutboundEndpointProcessor extends EndpointProcessor {
        OutboundEndpointProcessor connectUsing(Connector connector);

        OutboundEndpointProcessor connectUsing(String connectorRef);

        <P extends OutboundEndpointProcessor> P extend(Class<P> endpoint);

        <P extends OutboundEndpointProcessor> P extend(P endpoint);

        OutboundEndpointProcessor extend(String endpointRef);
    }

    public interface InboundEndpointProcessor extends EndpointProcessor {
        InboundEndpointProcessor connectUsing(Connector connector);

        InboundEndpointProcessor connectUsing(String connectorRef);

        <P extends InboundEndpointProcessor> P extend(Class<P> endpoint);

        InboundEndpointProcessor extend(EndpointProcessor endpoint);

        InboundEndpointProcessor processRequest(ProcessorBuilder... processors);

        InboundEndpointProcessor processResponse(ProcessorBuilder... processors);
    }

    public interface ProcessorBuilder {
    }

    public interface CustomExecutorBuilder extends ProcessorBuilder {
        CustomExecutorBuilder methodAnnotatedWith(Class<? extends Annotation> annotationType);

        CustomExecutorBuilder methodAnnotatedWith(Annotation annotation);

        CustomExecutorBuilder asSingleton();
    }

    public interface HTTP_In extends InboundEndpointProcessor {
        HTTPPoll poll(HostBuilder hostBuilder);

        HTTPBuilder listen(HostBuilder hostBuilder);

        public interface HTTPPoll {
            HTTPBuilder every(long time);

            HTTPBuilder every(long time, TimeUnit unit);
        }

        public interface HTTPBuilder extends InboundEndpointProcessor {
            <T extends EndpointProcessor> T as(T x);

            <T extends EndpointProcessor> T as(Class<T> x);
        }
    }

    public interface FTP extends EndpointProcessor {
        FTPPoll poll(HostBuilder hostBuilder);

        public interface FTPPoll {
            EndpointProcessor every(long time);

            EndpointProcessor every(long time, TimeUnit unit);
        }
    }

    public interface FTP_In extends InboundEndpointProcessor {
        FTP_InPoll poll(HostBuilder hostBuilder);

        FTP_InPoll extend(String endpointRef);

        FTP_InPoll extend(EndpointProcessor endpoint);

        public interface FTP_InPoll extends InboundEndpointProcessor {
            FTP_InPoll every(long time);
            FTP_InPoll every(long time, TimeUnit unit);
            FTP_InPoll user(String user);
            FTP_InPoll password(String passw);
        }
    }

    public interface JMS_In extends InboundEndpointProcessor {
        JMSInner using(Connector time);

        InboundEndpointProcessor queue(String queue);

        InboundEndpointProcessor topic(String topic);

        public interface JMSInner extends EndpointProcessor {
            InboundEndpointProcessor queue(String queue);

            InboundEndpointProcessor topic(String topic);
        }
    }

    public interface VM extends EndpointProcessor {
        VM name(String name);
        VM path(String path);
    }

    public interface VM_In extends InboundEndpointProcessor {
        VM_In name(String name);

        VM_In path(String path);
    }

    public interface VM_Out extends OutboundEndpointProcessor {
        VM_Out name(String name);

        VM_Out path(String path);
    }

    public interface SMTPS_Out extends OutboundEndpointProcessor {
        SMTPS_Out user(String s);

        SMTPS_Out password(String s);

        SMTPS_Out host(String s);

        SMTPS_Out from(String s);

        SMTPS_Out subject(String s);
    }


    public interface WS extends InboundEndpointProcessor {
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
