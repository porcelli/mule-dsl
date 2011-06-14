/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.mule.api.routing.filter.Filter;
import org.mule.api.transformer.Transformer;
import org.mule.config.dsl.expression.CoreExpr;

public interface PipelineBuilder<P extends PipelineBuilder<P>> {
    /* component */

    P log();

    P log(LogLevel level);

    P log(String message);

    P log(String message, LogLevel level);

    <E extends ExpressionEvaluatorBuilder> P log(E expr);

    <E extends ExpressionEvaluatorBuilder> P log(E expr, LogLevel level);

    P echo();

    ExecutorBuilder<P> execute(Object obj);

    ExecutorBuilder<P> execute(Class<?> clazz);

    ExecutorBuilder<P> execute(Class<?> clazz, Scope scope);

    /* typed MP builder */

//    <C extends CustomBuilder<P>> C process(Class<C> clazz);

    /* outbound */
    P send(String uri);

    P send(String uri, ExchangePattern pattern);

    /* transform */

    <E extends ExpressionEvaluatorBuilder> P transform(E expr);

    <T> P transformTo(Class<T> clazz);

    <T extends Transformer> P transformWith(Class<T> clazz);

    <T extends Transformer> P transformWith(T obj);

    <T extends Transformer> P transformWith(TransformerDefinition<T> obj);

    P transformWith(String ref);

    /* filter */
    P filter(CoreExpr.GenericExpressionFilterEvaluatorBuilder expr);

    <E extends ExpressionEvaluatorBuilder> P filter(E expr);

    <T> P filterBy(Class<T> clazz);

    <F extends Filter> P filterWith(Class<F> clazz);

    <F extends Filter> P filterWith(F obj);

    <F extends Filter> P filterWith(FilterDefinition<F> obj);

    P filterWith(String ref);

    /* routers */

    BroadcastRouterBuilder<P> broadcast();

    ChoiceRouterBuilder<P> choice();

    AsyncRouterBuilder<P> async();

    FirstSuccessfulRouterBuilder<P> firstSuccessful();

    RoundRobinRouterBuilder<P> roundRobin();

}