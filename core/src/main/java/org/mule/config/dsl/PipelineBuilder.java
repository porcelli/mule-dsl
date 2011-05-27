/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.mule.MessageExchangePattern;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transformer.Transformer;
import org.mule.config.dsl.expression.CoreExpr;
import org.mule.routing.MessageFilter;

public interface PipelineBuilder<P extends PipelineBuilder<P>> {
    /* component */

    P log();

    P log(LogLevel level);

    P log(String message);

    P log(String message, LogLevel level);

    <E extends ExpressionEvaluatorBuilder> P log(E expr);

    <E extends ExpressionEvaluatorBuilder> P log(E expr, LogLevel level);

    P echo();

    P execute(Object obj);

    P execute(Callable obj);

    P execute(Class<?> clazz);

    P execute(Class<?> clazz, Scope scope);

    /* outbound */
    P send(String uri);

    P send(String uri, MessageExchangePattern pattern);

    /* transform */

    <E extends ExpressionEvaluatorBuilder> P transform(E expr);

    <T> P transformTo(Class<T> clazz);

    <T extends Transformer> P transformWith(Class<T> clazz);

    <T extends Transformer> P transformWith(T obj);

    /* filter */
    P filter(CoreExpr.GenericExpressionFilterEvaluatorBuilder expr);

    <E extends ExpressionEvaluatorBuilder> P filter(E expr);

    <T> P filterBy(Class<T> clazz);

    <F extends MessageFilter> P filterWith(Class<F> clazz);

    <F extends MessageFilter> P filterWith(F obj);

    /* routers */

    AllRouterBuilder<P> all();

    ChoiceRouterBuilder<P> choice();
}