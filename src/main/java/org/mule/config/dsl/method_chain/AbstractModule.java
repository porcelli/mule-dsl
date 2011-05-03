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

    /* property placeholder */


    public void propertyResolver(FileRefBuilder fileRef) {
    }

    public void propertyResolver(ClasspathBuilder classpathRef) {
    }

    public void propertyResolver(InputStream inputStream) {
    }

    /* flow */

    public FlowBuilder flow() {
        return null;
    }

    public FlowBuilder flow(String name) {
        return null;
    }

    /* util methods */

    public FlowProcessBuilder pipeline() {
        return null;
    }

    /* util methods: named params  */

    public NameBuilder name(String alias) {
        return null;
    }

    public URIBuilder uri(String uri) {
        return null;
    }

    public ClasspathBuilder classpath(String classpath) {
        return null;
    }

    public FileRefBuilder file(File path) {
        return null;
    }

    public ExpressionBuilder expression(String expression, Evaluator evaluator) {
        return null;
    }

    public ExpressionBuilder expression(String expression, ExpressionEvaluatorBuilder eval) {
        return null;
    }

    public <E extends ExpressionEvaluator> ExpressionEvaluatorBuilder evaluator(Class<E> evaluator) {
        return null;
    }

    public <E extends ExpressionEvaluator> ExpressionEvaluatorBuilder evaluator(E evaluator) {
        return null;
    }

}
