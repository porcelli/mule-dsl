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

public interface PipelineBuilder {
    /* component */

    PipelineBuilder log();

    PipelineBuilder log(String message);

    PipelineBuilder log(String message, ErrorLevel level);

    <E extends ExpressionEvaluatorBuilder> PipelineBuilder log(E expr);

    <E extends ExpressionEvaluatorBuilder> PipelineBuilder log(E expr, ErrorLevel level);

    PipelineBuilder echo();

    PipelineBuilder execute(Object obj);

    ExecutorBuilder execute(Callable obj);

    ExecutorBuilder execute(java.util.concurrent.Callable obj);

    ExecutorBuilder execute(Class<?> clazz);

    /* outbound */
    EndPointBuilder.OutboundEndpointBuilder send(String uri);

    /* transform */

    <E extends ExpressionEvaluatorBuilder> PipelineBuilder transform(E expr);

    PipelineBuilder transformTo(Class<?> clazz);

    /* filter */
    <E extends ExpressionEvaluatorBuilder> PipelineBuilder filter(E expr);

    /* routers */

    PipelineBuilder multicast(PipelineBuilder pipeline);

    RouterBuilder.ChoiceRouterBuilder choice();

    public enum ErrorLevel {
        WARN, INFO, ERROR, FATAL
    }

}
