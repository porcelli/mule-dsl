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

    /* flow */

    public FlowBuilder flow() {
        return null;
    }

    public FlowBuilder flow(NameBuilder name) {
        return null;
    }

    /* util methods */

    /* util methods: named params  */

    public HostBuilder host(String host) {
        return null;
    }

    public NameBuilder name(String alias) {
        return null;
    }

    public URIBuilder uri(String uri) {
        return null;
    }

    public ExpressionBuilder expression(String expression, ExpressionEvaluator eval) {
        return null;
    }

    public ExpressionEvaluator evaluator(String evaluator) {
        return null;
    }

    public <E extends ExpressionEvaluator> E evaluator(Class<E> evaluator) {
        return null;
    }
}
