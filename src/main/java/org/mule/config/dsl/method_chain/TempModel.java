/*
 * $Id: 20811 2011-04-01 14:45:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.method_chain;

import java.lang.annotation.Annotation;

public interface TempModel {

    public interface FlowProcessBuilder {
        /* component */

        FlowProcessBuilder log();

        FlowProcessBuilder echo();

        FlowProcessBuilder nil();

        FlowProcessBuilder passThrough();

        CustomExecutorBuilder execute(Object obj);

        CustomExecutorBuilder execute(Class<?> clazz);

        /* outbound */

        EndpointProcessor send(URIBuilder uri);

        EndpointProcessor sendAndWait(URIBuilder uri);

        EndpointProcessor send(RefBuilder ref);

        EndpointProcessor sendAndWait(RefBuilder ref);

        <P extends EndpointExtension<OutboundEndpointProcessor>> P send(P from);

        <P extends EndpointExtension<OutboundEndpointProcessor>> P sendAndWait(P from);

        /* filter */

        FlowProcessBuilder filterWith(RefBuilder ref);

        <FB extends FilterBuilder> FB filterWith(FB filter);

        <F extends Filter> FlowProcessBuilder filterWith(Class<F> clazz);

        <F extends Filter> FlowProcessBuilder filterWith(F clazz);


        /* transform */

        FlowProcessBuilder transformTo(Class<?> clazz);

        FlowProcessBuilder transformWith(RefBuilder ref);

        <T extends Transformer> FlowProcessBuilder transformWith(T obj);

        <T extends Transformer> FlowProcessBuilder transformWith(Class<T> clazz);

        <TB extends TransformerBuilder> TB transformWith(TB obj);

        /* aggregate */

        FlowProcessBuilder aggregateWith(RefBuilder ref);

        <AB extends AggregatorBuilder> AB aggregateWith(AB aggregator);

        <A extends Aggregator> FlowProcessBuilder aggregateWith(A aggregator);

        <A extends Aggregator> FlowProcessBuilder aggregateWith(Class<A> aggregator);

        /* split */

        FlowProcessBuilder splitWith(RefBuilder ref);

        <SB extends SplitterBuilder> SB splitWith(SB splitter);

        <S extends Splitter> FlowProcessBuilder splitWith(S splitter);

        <S extends Splitter> FlowProcessBuilder splitWith(Class<S> splitter);

    }

    public interface FlowBuilder extends FlowProcessBuilder {
        InboundEndpointProcessor from(URIBuilder uri);

        InboundEndpointProcessor from(RefBuilder ref);

        <P extends EndpointExtension<InboundEndpointProcessor>> P from(P from);
    }

    public interface ProcessorBuilder {
    }

    public interface Aggregator {

    }

    public interface AggregatorBuilder extends ProcessorBuilder {
        AggregatorTimoutBuilder timeout(int timeout);

        public interface AggregatorTimoutBuilder {
            AggregatorBuilder failOnTimeout();
        }
    }

    public interface CollectionAggregatorBuilder extends AggregatorBuilder, Correlational<CollectionAggregatorBuilder> {
    }

    public interface MessageChunkAggregatorBuilder extends AggregatorBuilder, Correlational<MessageChunkAggregatorBuilder> {
        MessageChunkAggregatorBuilder size(int size);
    }

    public interface Splitter {

    }

    public interface SplitterBuilder extends ProcessorBuilder {
    }

    public interface CollectionSplitterBuilder extends SplitterBuilder, Correlational<CollectionSplitterBuilder> {
    }

    public interface MessageChunkSplitterBuilder extends SplitterBuilder, Correlational<CollectionSplitterBuilder> {
        MessageChunkSplitterBuilder size(int size);
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

    public interface Transformer {
    }

    public interface ByteArrayToObject extends Transformer {
    }

    public interface BodyToParameterMapTransformer extends Transformer {
    }

    public interface ObjectToStringTransformer extends Transformer {
    }

    public interface TransformerBuilder extends FlowProcessBuilder {
    }

    public interface MessagePropertiesTransformer extends TransformerBuilder {
        MessagePropertiesTransformer addMessageProperty(String key, String value);

        MessagePropertiesTransformer deleteMessageProperty(String key);
    }

    public interface CustomExecutorBuilder extends FlowProcessBuilder {
        CustomExecutorBuilder methodAnnotatedWith(Class<? extends Annotation> annotationType);

        CustomExecutorBuilder methodAnnotatedWith(Annotation annotation);

        CustomExecutorBuilder asSingleton();
    }

    public interface EndpointProcessor extends FlowBuilder {
        EndpointProcessor connectWith(ConnectorBuilder connector);

        EndpointProcessor connectWith(String connector);
    }

    public interface OutboundEndpointProcessor extends EndpointProcessor {
    }

    public interface InboundEndpointProcessor extends EndpointProcessor {
        InboundEndpointProcessor processRequest(FlowProcessBuilder pipeline);

        InboundEndpointProcessor processResponse(FlowProcessBuilder pipeline);
    }

    public interface ConnectionExtension {
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

    public interface ExecWrapper<Z> {
    }

    public interface WS<Z> extends ExecWrapper<Z> {
        WS<InboundEndpointProcessor> INBOUND = null;
        WS<OutboundEndpointProcessor> OUTBOUND = null;
        WS<EndpointProcessor> ENDPOINT = null;

        WSComplement<Z> with(Class<?> clazz);

        public interface WSComplement<Z> extends EndpointProcessor {
            WSComplement<Z> setProperty(String name, Object value);

            WSComplement<Z> asSingleton();

            Z then();
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

    public interface GMAIL<Z> extends SMTP<Z> {

    }

    public interface RouterBuilder extends ProcessorBuilder {
    }

    public interface MulticastRouterBuilder extends RouterBuilder {
        RouterBuilder onFirstSuccessful();

        RouterBuilder onFirstSuccessful(ExpressionBuilder ex);
    }

    public interface AsyncMulticastRouterBuilder extends RouterBuilder {
        RouterBuilder with(ThreadProfileBuilder threadProfile);
    }


    public interface ChoiceRouterBuilder extends RouterBuilder {
        WhenChoiceBuilder when(ExpressionBuilder x);

        OtherwiseChoiceBuilder otherwise();

        public interface WhenChoiceBuilder {
            ChoiceRouterBuilder then(FlowProcessBuilder pipeline);
        }

        public interface OtherwiseChoiceBuilder {
            ProcessorBuilder then(FlowProcessBuilder pipeline);
        }
    }


    public enum TimeUnit {
        MILISECONDS, SECONDS, MINUTES, HOURS, DAYS
    }

    public interface Correlational<T> {
        T correlationID(String id);

        T correlationID(ExpressionBuilder id);
    }

    public interface SecurityProperties<T> {
        T user(String user);

        T password(String user);
    }

    public interface EncodingProperties<T> {
        T encode(String user);
    }

    public interface ThreadProfileBuilder {
    }

    public interface ExpressionBuilder {
    }

    public interface NameBuilder {
    }

    public interface URIBuilder {
    }

    public interface RefBuilder {
    }

    public interface HostBuilder {
        HostBuilder port(int port);

        HostBuilder path(String path);
    }

}
