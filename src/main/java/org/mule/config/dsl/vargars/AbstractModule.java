/*
 * $Id: 20811 2011-04-01 14:45:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.vargars;

import org.mule.config.dsl.vargars.TempModel.*;

import java.io.File;
import java.io.InputStream;

public abstract class AbstractModule {

    public abstract void configure();

    /* elements definition/declaration */

    /* property placeholder usage */

    public void propertyPlaceholder(String fileRef) {
    }

    public void propertyPlaceholder(File s) {
    }

    public void propertyPlaceholder(InputStream resourceAsStream) {
    }

    /* transformer */

    public <TB extends TransformerBuilder> TB transformer(Class<TB> transformer) {
        return null;
    }

    public <TB extends TransformerBuilder> TB transformer(Class<TB> transformer, NameBuilder name) {
        return null;
    }

    /* connection */

    /* connector */

    public ConnectorBuilder connector(String connector) {
        return null;
    }

    public <CB extends ConnectorBuilder> CB connector(CB connector) {
        return null;
    }

    public <CB extends ConnectorBuilder> CB connector(CB connector, NameBuilder name) {
        return null;
    }

    /* endpoint */

    public EndpointProcessor endpoint(String from) {
        return null;
    }

    public <E extends EndpointExtension<? extends EndpointProcessor>> E endpoint(E from) {
        return null;
    }

    public <E extends EndpointExtension<? extends EndpointProcessor>> E endpoint(E from, NameBuilder name) {
        return null;
    }

    /* flow */

    public FlowBuilder flow() {
        return null;
    }

    public FlowBuilder flow(NameBuilder name) {
        return null;
    }

    /* util methods */

    /* util methods: named params  */

    public RefBuilder ref(String ref) {
        return null;
    }

    public HostBuilder host(String host) {
        return null;
    }

    public NameBuilder name(String alias) {
        return null;
    }

    public URIBuilder uri(String uri) {
        return null;
    }

    /* util methods: splitter */

    public ProcessorBuilder splitWith(RefBuilder ref) {
        return null;
    }

    public <SB extends SplitterBuilder> SB splitWith(SB splitter) {
        return null;
    }

    public <S extends Splitter> ProcessorBuilder splitWith(S splitter) {
        return null;
    }

    public <S extends Splitter> ProcessorBuilder splitWith(Class<S> splitter) {
        return null;
    }

    /* util methods: aggregator */

    public ProcessorBuilder aggregateWith(RefBuilder ref) {
        return null;
    }

    public <AB extends AggregatorBuilder> AB aggregateWith(AB aggregator) {
        return null;
    }

    public <A extends Aggregator> ProcessorBuilder aggregateWith(A aggregator) {
        return null;
    }

    public <A extends Aggregator> ProcessorBuilder aggregateWith(Class<A> aggregator) {
        return null;
    }

    /* util methods: filters */

    public ProcessorBuilder filterWith(RefBuilder ref) {
        return null;
    }

    public <FB extends FilterBuilder> FB filterWith(FB filter) {
        return null;
    }

    public <F extends Filter> ProcessorBuilder filterWith(F clazz) {
        return null;
    }

    public <F extends Filter> ProcessorBuilder filterWith(Class<F> clazz) {
        return null;
    }


    /* util methods: transformers */

    public ProcessorBuilder transformTo(Class<?> clazz) {
        return null;
    }

    public ProcessorBuilder transformWith(RefBuilder ref) {
        return null;
    }

    public <TB extends TransformerBuilder> TB transformWith(TB obj) {
        return null;
    }

    public <T extends Transformer> ProcessorBuilder transformWith(T obj) {
        return null;
    }

    public <T extends Transformer> ProcessorBuilder transformWith(Class<T> clazz) {
        return null;
    }

    /* util methods: components */

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

    public CustomExecutorBuilder execute(Object obj) {
        return null;
    }

    public CustomExecutorBuilder execute(Class<?> clazz) {
        return null;
    }

    /* util methods: endpoints */

    /* inbound */

    public ThenToInboundEndpointProcessor from(URIBuilder uri) {
        return null;
    }

    public ThenToInboundEndpointProcessor from(RefBuilder ref) {
        return null;
    }

    public <E extends EndpointExtension<InboundEndpointProcessor>> E from(E from) {
        return null;
    }

    /* outbound */

    public EndpointProcessor send(URIBuilder uri) {
        return null;
    }

    public EndpointProcessor sendAndWait(URIBuilder uri) {
        return null;
    }

    public EndpointProcessor send(RefBuilder ref) {
        return null;
    }

    public EndpointProcessor sendAndWait(RefBuilder ref) {
        return null;
    }

    public <E extends EndpointExtension<OutboundEndpointProcessor>> E send(E from) {
        return null;
    }

    public <E extends EndpointExtension<OutboundEndpointProcessor>> E sendAndWait(E from) {
        return null;
    }

    /* util methods: routers */

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

    public ProcessorBuilder multicast(ProcessorBuilder... processors) {
        return null;
    }
}
