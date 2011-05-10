/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.mule.api.lifecycle.Callable;

public abstract class PipelineBuilder {
    /* component */

    public PipelineBuilder log() {
        return null;
    }

    public PipelineBuilder log(String message) {
        return null;
    }

    public PipelineBuilder log(String message, ErrorLevel level) {
        return null;
    }

    public <E extends ExpressionEvaluatorBuilder> PipelineBuilder log(E expr) {
        return null;
    }

    public <E extends ExpressionEvaluatorBuilder> PipelineBuilder log(E expr, ErrorLevel level) {
        return null;
    }

    public PipelineBuilder echo() {
        return null;
    }

    public PipelineBuilder nil() {
        return null;
    }

    public ExecutorBuilder execute(Object obj) {
        return null;
    }

    public ExecutorBuilder execute(Callable obj) {
        return null;
    }

    public ExecutorBuilder execute(Class<?> clazz) {
        return null;
    }

    /* outbound */
    public EndpointBuilder.OutboundEndpointBuilder send(String uri) {
        return null;
    }

    /* transform */

    public <E extends ExpressionEvaluatorBuilder> PipelineBuilder transform(E expr) {
        return null;
    }

    public PipelineBuilder transformTo(Class<?> clazz) {
        return null;
    }

    /* filter */
    public <E extends ExpressionEvaluatorBuilder> PipelineBuilder filter(E expr) {
        return null;
    }

    /* routers */

    public PipelineBuilder multicast(PipelineBuilder pipeline) {
        return null;
    }

    public RouterBuilder.ChoiceRouterBuilder choice() {
        return null;
    }

    public enum ErrorLevel {
        WARN, INFO, ERROR, FATAL
    }

}
