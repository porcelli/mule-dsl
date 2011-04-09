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

    public <P extends EndpointExtension<? extends EndpointProcessor>> P endpoint(P from, NameBuilder name) {
        return null;
    }

    public <P extends EndpointExtension<? extends EndpointProcessor>> P endpoint(P from) {
        return null;
    }

    public EndpointProcessor endpoint(String from) {
        return null;
    }

    public void propertyPlaceholder(String fileRef) {
    }

    public void propertyPlaceholder(File s) {
    }

    public void propertyPlaceholder(InputStream resourceAsStream) {
    }

    public FlowBuilder flow() {
        return null;
    }

    public FlowBuilder flow(NameBuilder name) {
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

    public NameBuilder name(String alias) {
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

    public interface NameBuilder {
    }

    public interface HostBuilder {
        HostBuilder port(int port);

        HostBuilder path(String path);
    }

    public <C extends ConnectorBuilder> C connector(C connector, NameBuilder name) {
        return null;
    }

    public <C extends ConnectorBuilder> C connector(C connector) {
        return null;
    }

    public ConnectorBuilder connector(String connector) {
        return null;
    }

    public interface ProcessorBuilder {
    }


    public interface CustomExecutorBuilder extends ProcessorBuilder {
        CustomExecutorBuilder methodAnnotatedWith(Class<? extends Annotation> annotationType);

        CustomExecutorBuilder methodAnnotatedWith(Annotation annotation);

        CustomBindingExecutorBuilder bind(Class<?> clazz);

        CustomExecutorBuilder asSingleton();

        public interface CustomBindingExecutorBuilder extends ProcessorBuilder {
            CustomBindingExecutorBuilder methodAnnotatedWith(Class<? extends Annotation> annotationType);

            CustomBindingExecutorBuilder methodAnnotatedWith(Annotation annotation);

            <P extends EndpointExtension<? extends EndpointProcessor>> P redirectTo(P to);

            EndpointProcessor redirectTo(String to);
        }
    }


    public interface EndpointProcessor extends ProcessorBuilder {
        EndpointProcessor connectWith(ConnectorBuilder connector);

        EndpointProcessor connectWith(String connector);
    }

    public interface OutboundEndpointProcessor extends EndpointProcessor {
    }

    public interface InboundEndpointProcessor extends EndpointProcessor {
        InboundEndpointProcessor processRequest(ProcessorBuilder... processors);

        InboundEndpointProcessor processResponse(ProcessorBuilder... processors);
    }


    public interface ConnectionExtension<Z> {
    }

    public interface ConnectorBuilder {
    }

    public interface EndpointExtension<Z> {
    }

    public interface HTTP<Z> extends EndpointExtension<Z>, ConnectionExtension {

        HTTP<InboundEndpointProcessor> INBOUND = null;
        HTTP<OutboundEndpointProcessor> OUTBOUND = null;
        HTTP<EndpointProcessor> ENDPOINT = null;
        HTTPConnector CONNECTOR = null;

        HTTPPoll<Z> poll(HostBuilder hostBuilder);

        HTTPComplement<Z> extend(EndpointProcessor base);

        HTTPComplement<Z> extend(String base);

        HTTPComplement<Z> listen(HostBuilder hostBuilder);

        public interface HTTPPoll<Z> {
            HTTPComplement<Z> every(long time);

            HTTPComplement<Z> every(long time, TimeUnit unit);
        }

        public interface HTTPComplement<Z> extends SecurityProperties<HTTPComplement<Z>>, EncodingProperties<HTTPComplement<Z>>, EndpointProcessor {

            //Specific to HTTP
            <T extends ExecWrapper<Z>> T using(T x);

            //Specific to HTTP
            HTTPComplement<Z> proxy(String proxy);

            Z then();
        }

        public interface HTTPConnector extends ConnectorBuilder {
        }
    }

    public interface FTP<Z> extends EndpointExtension<Z> {

        FTP<InboundEndpointProcessor> INBOUND = null;
        FTP<OutboundEndpointProcessor> OUTBOUND = null;
        FTP<EndpointProcessor> ENDPOINT = null;

        FTPPoll<Z> poll(HostBuilder hostBuilder);

        FTPComplement<Z> extend(String base);

        FTPComplement<Z> extend(EndpointProcessor base);

        public interface FTPPoll<Z> {
            FTPComplement<Z> every(long time);

            FTPComplement<Z> every(long time, TimeUnit unit);
        }

        public interface FTPComplement<Z> extends SecurityProperties<FTPComplement<Z>>, EncodingProperties<FTPComplement<Z>>, EndpointProcessor {
            Z then();
        }
    }

    public interface JMS<Z> extends EndpointExtension<Z> {

        JMS<InboundEndpointProcessor> INBOUND = null;
        JMS<OutboundEndpointProcessor> OUTBOUND = null;

        JMSComplement<Z> extend(String base);

        JMSComplement<Z> extend(EndpointProcessor base);

        //Specific to JMS
        JMSComplement<Z> queue(String queue);

        //Specific to JMS
        JMSComplement<Z> topic(String topic);

        public interface JMSComplement<Z> extends SecurityProperties<JMSComplement<Z>>, EncodingProperties<JMSComplement<Z>>, EndpointProcessor {


            Z then();
        }
    }

    public interface VM<Z> extends EndpointExtension<Z> {

        VM<InboundEndpointProcessor> INBOUND = null;
        VM<OutboundEndpointProcessor> OUTBOUND = null;
        VM<EndpointProcessor> ENDPOINT = null;
        VMConnector CONNECTOR = null;

        VMComplement<Z> extend(String base);

        VMComplement<Z> extend(EndpointProcessor base);

        //Specific to JMS
        VMComplement<Z> path(String queue);

        //Specific to JMS
        VMComplement<Z> name(String topic);

        public interface VMComplement<Z> extends SecurityProperties<VMComplement<Z>>, EncodingProperties<VMComplement<Z>>, EndpointProcessor {
            Z then();
        }

        public interface VMConnector extends ConnectorBuilder {
        }
    }

    public interface SMTP<Z> extends EndpointExtension<Z> {

        SMTP<OutboundEndpointProcessor> OUTBOUND = null;

        SMTPConnetor CONNECTOR = null;

        SMTP<Z> secure();

        SMTP<Z> extend(EndpointProcessor base);

        SMTP<Z> extend(String base);

        SMTPComplement<Z> user(String s);

        SMTPComplement<Z> password(String s);

        SMTPComplement<Z> host(String s);

        SMTPComplement<Z> from(String s);

        SMTPComplement<Z> subject(String s);

        public interface SMTPComplement<Z> extends SecurityProperties<SMTPComplement<Z>>, EncodingProperties<SMTPComplement<Z>>, EndpointProcessor {
            SMTPComplement<Z> host(String s);

            SMTPComplement<Z> from(String s);

            SMTPComplement<Z> subject(String s);

            Z then();
        }

        public interface SMTPConnetor extends ConnectorBuilder {
            SMTPConnetor secure();
        }
    }

    public interface GMAIL extends SMTP {

    }

    public interface SecurityProperties<T> {
        T user(String user);

        T password(String user);
    }

    public interface EncodingProperties<T> {
        T encode(String user);
    }


    public interface ExecWrapper<Z> {
    }

    public interface WS<Z> extends ExecWrapper<Z> {
        WS<InboundEndpointProcessor> INBOUND = null;
        WS<OutboundEndpointProcessor> OUTBOUND = null;
        WS<EndpointProcessor> ENDPOINT = null;

        WSComplement<Z> with(Class<?> clazz);

        public interface WSComplement<Z> extends EndpointProcessor {
            Z then();
        }
    }


    public <T extends TransformerBuilder> T transformer(Class<T> transformer, NameBuilder name) {
        return null;
    }

    public <T extends TransformerBuilder> T transformer(Class<T> transformer) {
        return null;
    }

    public ProcessorBuilder transformTo(Class<?> clazz) {
        return null;
    }

    public ProcessorBuilder transformWith(Class<? extends Transformer> clazz) {
        return null;
    }

    public <T extends TransformerBuilder> T transformWith(Class<T> clazz) {
        return null;
    }

    public ProcessorBuilder transformWith(Transformer obj) {
        return null;
    }

    public ProcessorBuilder transformWith(String ref) {
        return null;
    }

    public <T extends TransformerBuilder> T transformWith(T obj) {
        return null;
    }

    public interface Transformer {
    }

    public interface TransformerBuilder extends ProcessorBuilder {
    }


    public interface MessagePropertiesTransformer extends TransformerBuilder {
        MessagePropertiesTransformer addMessageProperty(String key, String value);

        MessagePropertiesTransformer deleteMessageProperty(String key);
    }


    public <T extends FilterBuilder> T filterWith(Class<T> obj) {
        return null;
    }

    public ProcessorBuilder filterWith(Class<? extends Filter> clazz) {
        return null;
    }

    public <T extends FilterBuilder> T filterWith(T obj) {
        return null;
    }

    public ProcessorBuilder filterWith(String ref) {
        return null;
    }

    public interface Filter {

    }

    public interface FilterBuilder extends ProcessorBuilder {
    }

    public interface PayloadTypeFilter extends FilterBuilder, LogicFilter<PayloadTypeFilter> {
        PayloadTypeFilter typeToFilter(Class<?> type);
    }

    public interface LogicFilter<T extends FilterBuilder> extends FilterBuilder {
        T or(FilterBuilder builderA, FilterBuilder builderB);

        T and(FilterBuilder builderA, FilterBuilder builderB);

        T not(FilterBuilder builder);
    }


    public ChoiceRouterBuilder choice() {
        return null;
    }

    public MulticastRouterBuilder multicast(OutboundEndpointProcessor... out) {
        return null;
    }

    public RouterBuilder roundRobin(OutboundEndpointProcessor... out) {
        return null;
    }

    public AsyncMulticastRouterBuilder asyncMulticast(ProcessorBuilder... processorBuilders) {
        return null;
    }

    public interface MulticastRouterBuilder extends RouterBuilder {
        RouterBuilder onFirstSuccessful();

        RouterBuilder onFirstSuccessful(ExpressionBuilder ex);
    }

    public interface AsyncMulticastRouterBuilder extends RouterBuilder {
        RouterBuilder with(ThreadProfileBuilder threadProfile);
    }

    public interface ThreadProfileBuilder {
    }

    public interface RouterBuilder extends ProcessorBuilder {
    }

    public interface ChoiceRouterBuilder extends RouterBuilder {
        WhenChoiceBuilder when(ExpressionBuilder x);

        OtherwiseChoiceBuilder otherwise();

        public interface WhenChoiceBuilder {
            ChoiceRouterBuilder then(ProcessorBuilder... processorBuilders);
        }

        public interface OtherwiseChoiceBuilder {
            ProcessorBuilder then(ProcessorBuilder... processorBuilders);
        }
    }

    public ProcessorBuilder log() {
        return null;
    }

    public ProcessorBuilder echo() {
        return null;
    }

    public ProcessorBuilder nil() {
        return null;
    }

    public ProcessorBuilder passThrough() {
        return null;
    }


    public interface ExpressionBuilder {
    }
}
