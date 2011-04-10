/*
 * $Id: 20811 2011-03-30 15:49:20Z porcelli $
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

    public TransformerBuilder transformer() {
        return null;
    }

    public TransformerBuilder transformer(String name) {
        return null;
    }

    /* connection */

    /* connector */

    public ConnectorBuilder connector(String name) {
        return null;
    }

    public ConnectorBuilder connector() {
        return null;
    }

    /* endpoint */

    public FlowInboundListenBuilder listen(String s) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public FlowInboundListenBuilder listen(Protocol http, String s) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }


    /* flow */

    public FlowBuilder flow() {
        return null;
    }

    public FlowBuilder flow(String name) {
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

}
