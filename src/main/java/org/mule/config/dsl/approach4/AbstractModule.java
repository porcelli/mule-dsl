/*
 * $Id: 20811 2011-04-01 14:45:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.approach4;

import java.io.File;
import java.io.InputStream;
import java.lang.annotation.Annotation;

public abstract class AbstractModule extends com.google.inject.AbstractModule {

    public TransformerBuilder defineTransformer(String setHtmlContentType) {
        return null;
    }

    public <P extends EndpointExtension<? extends EndpointProcessor>> P defineEndpoint(P from, AliasBuilder alias) {
        return null;
    }

    public <P extends EndpointExtension<? extends EndpointProcessor>> P defineEndpoint(P from) {
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

    public AliasBuilder alias(String alias) {
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
        return null;
    }

    public EndpointProcessor sendAndWait(String stats) {
        return null;
    }

    public <P extends EndpointExtension<OutboundEndpointProcessor>> P send(P from) {
        return null;
    }

    public <P extends EndpointExtension<OutboundEndpointProcessor>> P sendAndWait(P from) {
        return null;
    }

    public <P extends EndpointExtension<InboundEndpointProcessor>> P from(P from) {
        return null;
    }

    public interface FlowBuilder {
        FlowBuilder in(EndpointProcessor inbound);

        FlowBuilder in(EndpointProcessor... inbounds);

        FlowBuilder process(ProcessorBuilder... processors);
    }


    public enum TimeUnit {
        MILISECONDS, SECONDS, MINUTES, HOURS, DAYS
    }

    public interface AliasBuilder {
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


    public interface ProcessorBuilder {
    }


    public interface CustomExecutorBuilder extends ProcessorBuilder {
        CustomExecutorBuilder methodAnnotatedWith(Class<? extends Annotation> annotationType);

        CustomExecutorBuilder methodAnnotatedWith(Annotation annotation);

        CustomExecutorBuilder asSingleton();
    }


    public interface EndpointProcessor extends ProcessorBuilder {
        EndpointProcessor connectWith(Connector connector);

        EndpointProcessor connectWith(String connector);
    }

    public interface OutboundEndpointProcessor extends EndpointProcessor {
    }

    public interface InboundEndpointProcessor extends EndpointProcessor {
        InboundEndpointProcessor processRequest(ProcessorBuilder... processors);

        InboundEndpointProcessor processResponse(ProcessorBuilder... processors);
    }

    public interface EndpointExtension<Z extends EndpointProcessor> {
    }

    public interface HTTP<Z extends EndpointProcessor> extends EndpointExtension<Z> {

        HTTP<InboundEndpointProcessor> INBOUND = null;
        HTTP<OutboundEndpointProcessor> OUTBOUND = null;

        HTTPPoll<Z> poll(HostBuilder hostBuilder);

        HTTPComplement<Z> extend(EndpointProcessor base);

        HTTPComplement<Z> extend(String base);

        HTTPComplement<Z> listen(HostBuilder hostBuilder);

        public interface HTTPPoll<Z extends EndpointProcessor> {
            HTTPComplement<Z> every(long time);

            HTTPComplement<Z> every(long time, TimeUnit unit);
        }

        public interface HTTPComplement<Z extends EndpointProcessor> extends SecurityProperties<HTTPComplement<Z>>, EncodingProperties<HTTPComplement<Z>>, EndpointProcessor {

            //Specific to HTTP
            <T extends ExecWrapper<Z>> T using(T x);

            //Specific to HTTP
            HTTPComplement<Z> proxy(String proxy);

            Z then();
        }
    }

    public interface FTP<Z extends EndpointProcessor> extends EndpointExtension<Z> {

        FTP<InboundEndpointProcessor> INBOUND = null;
        FTP<OutboundEndpointProcessor> OUTBOUND = null;
        FTP<EndpointProcessor> ENDPOINT = null;

        FTPPoll<Z> poll(HostBuilder hostBuilder);

        FTPComplement<Z> extend(String base);

        FTPComplement<Z> extend(EndpointProcessor base);

        public interface FTPPoll<Z extends EndpointProcessor> {
            FTPComplement<Z> every(long time);

            FTPComplement<Z> every(long time, TimeUnit unit);
        }

        public interface FTPComplement<Z extends EndpointProcessor> extends SecurityProperties<FTPComplement<Z>>, EncodingProperties<FTPComplement<Z>>, EndpointProcessor {
            Z then();
        }
    }

    public interface JMS<Z extends EndpointProcessor> extends EndpointExtension<Z> {

        JMS<InboundEndpointProcessor> INBOUND = null;
        JMS<OutboundEndpointProcessor> OUTBOUND = null;

        JMSComplement<Z> extend(String base);

        JMSComplement<Z> extend(EndpointProcessor base);

        //Specific to JMS
        JMSComplement<Z> queue(String queue);

        //Specific to JMS
        JMSComplement<Z> topic(String topic);

        public interface JMSComplement<Z extends EndpointProcessor> extends SecurityProperties<JMSComplement<Z>>, EncodingProperties<JMSComplement<Z>>, EndpointProcessor {


            Z then();
        }
    }

    public interface VM<Z extends EndpointProcessor> extends EndpointExtension<Z> {

        VM<InboundEndpointProcessor> INBOUND = null;
        VM<OutboundEndpointProcessor> OUTBOUND = null;
        VM<EndpointProcessor> ENDPOINT = null;

        VMComplement<Z> extend(String base);

        VMComplement<Z> extend(EndpointProcessor base);

        //Specific to JMS
        VMComplement<Z> path(String queue);

        //Specific to JMS
        VMComplement<Z> name(String topic);

        public interface VMComplement<Z extends EndpointProcessor> extends SecurityProperties<VMComplement<Z>>, EncodingProperties<VMComplement<Z>>, EndpointProcessor {
            Z then();
        }
    }

    public interface SMTPS<Z extends EndpointProcessor> extends EndpointExtension<Z> {

        SMTPS<OutboundEndpointProcessor> OUTBOUND = null;

        SMTPS<Z> extend(EndpointProcessor base);

        SMTPS<Z> extend(String base);

        SMTPSComplement<Z> user(String s);

        SMTPSComplement<Z> password(String s);

        SMTPSComplement<Z> host(String s);

        SMTPSComplement<Z> from(String s);

        SMTPSComplement<Z> subject(String s);

        public interface SMTPSComplement<Z extends EndpointProcessor> extends SecurityProperties<SMTPSComplement<Z>>, EncodingProperties<SMTPSComplement<Z>>, EndpointProcessor {
            SMTPSComplement<Z> host(String s);

            SMTPSComplement<Z> from(String s);

            SMTPSComplement<Z> subject(String s);

            Z then();
        }

    }

    public interface SecurityProperties<T> {
        T user(String user);

        T password(String user);
    }

    public interface EncodingProperties<T> {
        T encode(String user);
    }


    public interface ExecWrapper<Z extends EndpointProcessor> {
    }

    public interface WS<Z extends EndpointProcessor> extends ExecWrapper<Z> {
        WS<InboundEndpointProcessor> INBOUND = null;

        WSComplement<Z> with(Class<?> clazz);

        public interface WSComplement<Z extends EndpointProcessor> extends EndpointProcessor {
            Z then();
        }
    }


}
