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

    public FileRefBuilder file(String path) {
        return null;
    }

    public FileRefBuilder file(File path) {
        return null;
    }

    public ExpressionBuilder expression(String expression, ExpressionEvaluatorBuilder eval) {
        return null;
    }

    public ExpressionBuilder expression(String expression, Evaluator eval) {
        return null;
    }

    public <E extends ExpressionEvaluator> ExpressionEvaluatorBuilder evaluator(Class<E> evaluator) {
        return null;
    }

    public <E extends ExpressionEvaluator> ExpressionEvaluatorBuilder evaluator(E evaluator) {
        return null;
    }

    /* util methods: filters */

    public ProcessorBuilder filter(String expr, Evaluator evaluator) {
        return null;
    }

    public ProcessorBuilder filter(String expr, ExpressionEvaluatorBuilder evaluator) {
        return null;
    }

    /* util methods: transformers */

    public ProcessorBuilder transform(String expr, Evaluator evaluator) {
        return null;
    }

    public ProcessorBuilder transform(String expr, ExpressionEvaluatorBuilder evaluator) {
        return null;
    }

    public ProcessorBuilder transformTo(Class<?> clazz) {
        return null;
    }

    /* util methods: components */

    public ProcessorBuilder log() {
        return null;
    }

    public ProcessorBuilder log(String message) {
        return null;
    }

    public ProcessorBuilder log(String message, ErrorLevel level) {
        return null;
    }

    public ProcessorBuilder log(ExpressionBuilder expression) {
        return null;
    }

    public ProcessorBuilder log(ExpressionBuilder expression, ErrorLevel level) {
        return null;
    }

    public ProcessorBuilder echo() {
        return null;
    }

    public ProcessorBuilder nil() {
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

    public ThenToInboundEndpointProcessor from(String uri) {
        return null;
    }

    /* outbound */

    public OutboundEndpointProcessor send(String uri) {
        return null;
    }

    /* util methods: routers */

    public ChoiceRouterBuilder choice() {
        return null;
    }

    public ProcessorBuilder multicast(ProcessorBuilder... processors) {
        return null;
    }
}
