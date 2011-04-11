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

import org.mule.config.dsl.method_chain.TempModel.*;

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

    /* util methods: pipeline */

    public FlowProcessBuilder pipeline() {
        return null;
    }

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


}
