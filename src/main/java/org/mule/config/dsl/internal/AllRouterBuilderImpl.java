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
import org.mule.MessageExchangePattern;
import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.lifecycle.Callable;
import org.mule.api.processor.MessageProcessor;
import org.mule.config.dsl.*;
import org.mule.config.dsl.expression.CoreExpr;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.routing.outbound.MulticastingRouter;

import java.util.List;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;


public class AllRouterBuilderImpl<P extends PipelineBuilder<P>> implements AllRouterBuilder<P>, Builder<MulticastingRouter>, MessageProcessorListBuilder {

    private final P parentScope;
    private final PipelineBuilderImpl<AllRouterBuilder<P>> pipeline;

    AllRouterBuilderImpl(P parentScope) {
        this.parentScope = checkNotNull(parentScope, "parentScope");
        this.pipeline = new PipelineBuilderImpl<AllRouterBuilder<P>>(null);
    }

    @Override
    public P endAll() {
        return parentScope;
    }

    @Override
    public AllRouterBuilder<P> log() {
        pipeline.log();
        return this;
    }

    @Override
    public AllRouterBuilder<P> log(LogLevel level) {
        pipeline.log(level);
        return this;
    }

    @Override
    public AllRouterBuilder<P> log(String message) {
        pipeline.log(message);
        return this;
    }

    @Override
    public AllRouterBuilder<P> log(String message, LogLevel level) {
        pipeline.log(message, level);
        return this;
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> AllRouterBuilder<P> log(E expr) {
        pipeline.log(expr);
        return this;
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> AllRouterBuilder<P> log(E expr, LogLevel level) {
        pipeline.log(expr, level);
        return this;
    }

    @Override
    public AllRouterBuilder<P> echo() {
        pipeline.echo();
        return this;
    }

    @Override
    public AllRouterBuilder<P> execute(Object obj) {
        pipeline.execute(obj);
        return this;
    }

    @Override
    public AllRouterBuilder<P> execute(Callable obj) {
        pipeline.execute(obj);
        return this;
    }

    @Override
    public AllRouterBuilder<P> execute(Class<?> clazz) {
        pipeline.execute(clazz);
        return this;
    }

    @Override
    public AllRouterBuilder<P> execute(Class<?> clazz, Scope scope) {
        pipeline.execute(clazz, scope);
        return this;
    }

    @Override
    public AllRouterBuilder<P> send(String uri) {
        pipeline.send(uri);
        return this;
    }

    @Override
    public AllRouterBuilder<P> send(String uri, MessageExchangePattern pattern) {
        pipeline.send(uri, pattern);
        return this;
    }


    @Override
    public <E extends ExpressionEvaluatorBuilder> AllRouterBuilder<P> transform(E expr) {
        pipeline.transform(expr);
        return this;
    }

    @Override
    public <T> AllRouterBuilder<P> transformTo(Class<T> clazz) {
        pipeline.transformTo(clazz);
        return this;
    }

    @Override
    public AllRouterBuilder<P> filter(CoreExpr.GenericExpressionFilterEvaluatorBuilder expr) {
        pipeline.filter(expr);
        return this;
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> AllRouterBuilder<P> filter(E expr) {
        pipeline.filter(expr);
        return this;
    }

    @Override
    public AllRouterBuilder<AllRouterBuilder<P>> all() {
        AllRouterBuilderImpl<AllRouterBuilder<P>> builder = new AllRouterBuilderImpl<AllRouterBuilder<P>>(this);
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public ChoiceRouterBuilder<AllRouterBuilder<P>> choice() {
        ChoiceRouterBuilderImpl<AllRouterBuilder<P>> builder = new ChoiceRouterBuilderImpl<AllRouterBuilder<P>>(this);
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public void addToProcessorList(Builder<?> builder) {
        pipeline.addToProcessorList(builder);
    }

    @Override
    public List<MessageProcessor> buildProcessorList(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        return pipeline.buildProcessorList(muleContext, injector, placeholder);
    }

    @Override
    public boolean isProcessorListEmpty() {
        return pipeline.isProcessorListEmpty();
    }

    @Override
    public List<Builder<?>> getProcessorList() {
        return pipeline.getProcessorList();
    }

    @Override
    public MulticastingRouter build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        if (!pipeline.isProcessorListEmpty()) {
            try {
                MulticastingRouter router = new MulticastingRouter();
                router.setMuleContext(muleContext);
                router.setRoutes(pipeline.buildProcessorList(muleContext, injector, placeholder));
                return router;
            } catch (MuleException e) {
                //TODO handle
                throw new RuntimeException(e);
            }
        }

        throw new RuntimeException();
    }
}
