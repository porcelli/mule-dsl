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

import org.mule.config.dsl.approach4.TempModel.*;

import java.io.File;
import java.io.InputStream;

public abstract class AbstractModule extends com.google.inject.AbstractModule {

    /* elements definition/declaration */

    /* property placeholder usage */

    public void propertyPlaceholder(String fileRef) {
    }

    public void propertyPlaceholder(File s) {
    }

    public void propertyPlaceholder(InputStream resourceAsStream) {
    }

    /* transformer */

    public <T extends TransformerBuilder> T transformer(Class<T> transformer) {
        return null;
    }

    public <T extends TransformerBuilder> T transformer(Class<T> transformer, NameBuilder name) {
        return null;
    }

    /* connection */

    /* connector */

    public ConnectorBuilder connector(String connector) {
        return null;
    }

    public <C extends ConnectorBuilder> C connector(C connector) {
        return null;
    }

    public <C extends ConnectorBuilder> C connector(C connector, NameBuilder name) {
        return null;
    }

    /* endpoint */

    public EndpointProcessor endpoint(String from) {
        return null;
    }

    public <P extends EndpointExtension<? extends EndpointProcessor>> P endpoint(P from) {
        return null;
    }

    public <P extends EndpointExtension<? extends EndpointProcessor>> P endpoint(P from, NameBuilder name) {
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

    public RefBuilder ref(RefBuilder ref) {
        return null;
    }

    public HostBuilder host(String host) {
        return null;
    }

    public NameBuilder name(String alias) {
        return null;
    }

    /* util methods: filters */

    public ProcessorBuilder filterWith(RefBuilder ref) {
        return null;
    }

    public ProcessorBuilder filterWith(Class<? extends Filter> clazz) {
        return null;
    }

    public <T extends FilterBuilder> T filterWith(Class<T> obj) {
        return null;
    }

    public <T extends FilterBuilder> T filterWith(T obj) {
        return null;
    }

    /* util methods: transformers */

    public ProcessorBuilder transformTo(Class<?> clazz) {
        return null;
    }

    public ProcessorBuilder transformWith(RefBuilder ref) {
        return null;
    }

    public ProcessorBuilder transformWith(Transformer obj) {
        return null;
    }

    public ProcessorBuilder transformWith(Class<? extends Transformer> clazz) {
        return null;
    }

    public <T extends TransformerBuilder> T transformWith(T obj) {
        return null;
    }

    public <T extends TransformerBuilder> T transformWith(Class<T> clazz) {
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

    public InboundEndpointProcessor from(String from) {
        return null;
    }

    public <P extends EndpointExtension<InboundEndpointProcessor>> P from(P from) {
        return null;
    }

    /* outbound */

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
