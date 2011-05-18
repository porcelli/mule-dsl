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
import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.lifecycle.Callable;
import org.mule.api.processor.MessageProcessor;
import org.mule.config.dsl.*;
import org.mule.config.dsl.expression.CoreExpr;
import org.mule.routing.outbound.MulticastingRouter;

import java.util.List;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;


public class AllRouterBuilderImpl<P extends PipelineBuilder<P>> implements AllRouterBuilder<P>, Builder<MulticastingRouter>, MessageProcessorListBuilder {

    private final P parentScope;
    private final PipelineBuilderImpl<AllRouterBuilder<P>> pipeline;
    private final MuleContext muleContext;

    AllRouterBuilderImpl(final MuleContext muleContext, P parentScope) {
        this.parentScope = checkNotNull(parentScope, "parentScope");
        this.muleContext = checkNotNull(muleContext, "muleContext");
        this.pipeline = new PipelineBuilderImpl<AllRouterBuilder<P>>(this.muleContext, null);
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
    public AllRouterBuilder<P> log(ErrorLevel level) {
        pipeline.log(level);
        return this;
    }

    @Override
    public AllRouterBuilder<P> log(String message) {
        pipeline.log(message);
        return this;
    }

    @Override
    public AllRouterBuilder<P> log(String message, ErrorLevel level) {
        pipeline.log(message, level);
        return this;
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> AllRouterBuilder<P> log(E expr) {
        pipeline.log(expr);
        return this;
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> AllRouterBuilder<P> log(E expr, ErrorLevel level) {
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
    public ExecutorBuilder<AllRouterBuilder<P>> execute(Class<?> clazz) {
        ExecutorBuilderImpl<AllRouterBuilder<P>> builder = new ExecutorBuilderImpl<AllRouterBuilder<P>>(this, muleContext, clazz);
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public OutboundEndpointBuilder<AllRouterBuilder<P>> send(String uri) {
        OutboundEndpointBuilderImpl<AllRouterBuilder<P>> builder = new OutboundEndpointBuilderImpl<AllRouterBuilder<P>>(this, muleContext, uri);
        pipeline.addToProcessorList(builder);

        return builder;
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
        AllRouterBuilderImpl<AllRouterBuilder<P>> builder = new AllRouterBuilderImpl<AllRouterBuilder<P>>(muleContext, this);
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public ChoiceRouterBuilder<AllRouterBuilder<P>> choice() {
        ChoiceRouterBuilderImpl<AllRouterBuilder<P>> builder = new ChoiceRouterBuilderImpl<AllRouterBuilder<P>>(muleContext, this);
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public void addToProcessorList(Builder<?> builder) {
        pipeline.addToProcessorList(builder);
    }

    @Override
    public List<MessageProcessor> buildProcessorList(Injector injector) {
        return pipeline.buildProcessorList(injector);
    }

    @Override
    public boolean isProcessorListEmpty() {
        return pipeline.isProcessorListEmpty();
    }

    @Override
    public MulticastingRouter build(Injector injector) {
        if (!pipeline.isProcessorListEmpty()) {
            try {
                MulticastingRouter router = new MulticastingRouter();
                router.setMuleContext(muleContext);
                router.setRoutes(pipeline.buildProcessorList(injector));
                return router;
            } catch (MuleException e) {
                //TODO handle
                throw new RuntimeException(e);
            }
        }

        throw new RuntimeException();
    }
}
