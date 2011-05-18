/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import com.google.inject.Injector;
import org.mule.api.lifecycle.Callable;
import org.mule.config.dsl.*;
import org.mule.config.dsl.expression.CoreExpr;
import org.mule.routing.outbound.FilteringOutboundRouter;


public class AllRouterBuilderImpl<P extends PipelineBuilder<P>> implements AllRouterBuilder<P>, Builder<FilteringOutboundRouter> {


    @Override
    public P endAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AllRouterBuilder<P> log() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AllRouterBuilder<P> log(ErrorLevel level) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AllRouterBuilder<P> log(String message) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AllRouterBuilder<P> log(String message, ErrorLevel level) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> AllRouterBuilder<P> log(E expr) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> AllRouterBuilder<P> log(E expr, ErrorLevel level) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AllRouterBuilder<P> echo() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AllRouterBuilder<P> execute(Object obj) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AllRouterBuilder<P> execute(Callable obj) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ExecutorBuilder<AllRouterBuilder<P>> execute(Class<?> clazz) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public OutboundEndpointBuilder<AllRouterBuilder<P>> send(String uri) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> AllRouterBuilder<P> transform(E expr) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T> AllRouterBuilder<P> transformTo(Class<T> clazz) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AllRouterBuilder<P> filter(CoreExpr.GenericExpressionFilterEvaluatorBuilder expr) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> AllRouterBuilder<P> filter(E expr) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AllRouterBuilder<AllRouterBuilder<P>> all() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ChoiceRouterBuilder choice() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public FilteringOutboundRouter build(Injector injector) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
