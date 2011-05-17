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
import org.mule.config.dsl.expression.CoreExpr;

public interface PipelineBuilder {
    /* component */

    PipelineBuilder log();

    PipelineBuilder log(ErrorLevel level);

    PipelineBuilder log(String message);

    PipelineBuilder log(String message, ErrorLevel level);

    <E extends ExpressionEvaluatorBuilder> PipelineBuilder log(E expr);

    <E extends ExpressionEvaluatorBuilder> PipelineBuilder log(E expr, ErrorLevel level);

    PipelineBuilder echo();

    PipelineBuilder execute(Object obj);

    PipelineBuilder execute(Callable obj);

    ExecutorBuilder execute(Class<?> clazz);

    /* outbound */
    EndPointBuilder.OutboundEndpointBuilder send(String uri);

    /* transform */

    <E extends ExpressionEvaluatorBuilder> PipelineBuilder transform(E expr);

    <T> PipelineBuilder transformTo(Class<T> clazz);


    /* filter */
    PipelineBuilder filter(CoreExpr.GenericExpressionFilterEvaluatorBuilder expr);

    <E extends ExpressionEvaluatorBuilder> PipelineBuilder filter(E expr);

    /* routers */

    PipelineBuilder multicast();

    RouterBuilder.ChoiceRouterBuilder choice();

    public enum ErrorLevel {
        WARN, INFO, ERROR, FATAL
    }

}
