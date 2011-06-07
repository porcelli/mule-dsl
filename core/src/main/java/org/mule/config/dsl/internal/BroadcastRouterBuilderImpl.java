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
import org.mule.api.routing.filter.Filter;
import org.mule.api.transformer.Transformer;
import org.mule.config.dsl.*;
import org.mule.config.dsl.expression.CoreExpr;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.routing.outbound.MulticastingRouter;

import java.util.List;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;


public class BroadcastRouterBuilderImpl<P extends PipelineBuilder<P>> implements BroadcastRouterBuilder<P>, Builder<MulticastingRouter>, MessageProcessorListBuilder {

    private final P parentScope;
    private final PipelineBuilderImpl<BroadcastRouterBuilder<P>> pipeline;

    BroadcastRouterBuilderImpl(P parentScope) {
        this.parentScope = checkNotNull(parentScope, "parentScope");
        this.pipeline = new PipelineBuilderImpl<BroadcastRouterBuilder<P>>(null);
    }

    @Override
    public P endBroadcast() {
        return parentScope;
    }

    @Override
    public BroadcastRouterBuilder<P> log() {
        pipeline.log();
        return this;
    }

    @Override
    public BroadcastRouterBuilder<P> log(LogLevel level) {
        pipeline.log(level);
        return this;
    }

    @Override
    public BroadcastRouterBuilder<P> log(String message) {
        pipeline.log(message);
        return this;
    }

    @Override
    public BroadcastRouterBuilder<P> log(String message, LogLevel level) {
        pipeline.log(message, level);
        return this;
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> BroadcastRouterBuilder<P> log(E expr) {
        pipeline.log(expr);
        return this;
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> BroadcastRouterBuilder<P> log(E expr, LogLevel level) {
        pipeline.log(expr, level);
        return this;
    }

    @Override
    public BroadcastRouterBuilder<P> echo() {
        pipeline.echo();
        return this;
    }

    @Override
    public ExecutorBuilder<BroadcastRouterBuilder<P>> execute(Object obj) {
        ExecutorBuilderImpl<BroadcastRouterBuilder<P>> builder = new ExecutorBuilderImpl<BroadcastRouterBuilder<P>>(this, obj);
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public BroadcastRouterBuilder<P> execute(Callable obj) {
        pipeline.execute(obj);
        return this;
    }

    @Override
    public ExecutorBuilder<BroadcastRouterBuilder<P>> execute(Class<?> clazz) {
        ExecutorBuilderImpl<BroadcastRouterBuilder<P>> builder = new ExecutorBuilderImpl<BroadcastRouterBuilder<P>>(this, clazz);
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public ExecutorBuilder<BroadcastRouterBuilder<P>> execute(Class<?> clazz, Scope scope) {
        ExecutorBuilderImpl<BroadcastRouterBuilder<P>> builder = new ExecutorBuilderImpl<BroadcastRouterBuilder<P>>(this, clazz, scope);
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public BroadcastRouterBuilder<P> send(String uri) {
        pipeline.send(uri);
        return this;
    }

    @Override
    public BroadcastRouterBuilder<P> send(String uri, MessageExchangePattern pattern) {
        pipeline.send(uri, pattern);
        return this;
    }


    @Override
    public <E extends ExpressionEvaluatorBuilder> BroadcastRouterBuilder<P> transform(E expr) {
        pipeline.transform(expr);
        return this;
    }

    @Override
    public <T> BroadcastRouterBuilder<P> transformTo(Class<T> clazz) {
        pipeline.transformTo(clazz);
        return this;
    }

    @Override
    public <T extends Transformer> BroadcastRouterBuilder<P> transformWith(Class<T> clazz) {
        pipeline.transformWith(clazz);
        return this;
    }

    @Override
    public <T extends Transformer> BroadcastRouterBuilder<P> transformWith(T obj) {
        pipeline.transformWith(obj);
        return this;
    }

    @Override
    public <T extends Transformer> BroadcastRouterBuilder<P> transformWith(TransformerDefinition<T> obj) {
        pipeline.transformWith(obj);
        return this;
    }

    @Override
    public BroadcastRouterBuilder<P> transformWith(String ref) {
        pipeline.transformWith(ref);
        return this;
    }

    @Override
    public BroadcastRouterBuilder<P> filter(CoreExpr.GenericExpressionFilterEvaluatorBuilder expr) {
        pipeline.filter(expr);
        return this;
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> BroadcastRouterBuilder<P> filter(E expr) {
        pipeline.filter(expr);
        return this;
    }

    @Override
    public <T> BroadcastRouterBuilder<P> filterBy(Class<T> clazz) {
        pipeline.filterBy(clazz);
        return this;
    }

    @Override
    public <F extends Filter> BroadcastRouterBuilder<P> filterWith(Class<F> clazz) {
        pipeline.filterWith(clazz);
        return this;
    }

    @Override
    public <F extends Filter> BroadcastRouterBuilder<P> filterWith(F obj) {
        pipeline.filterWith(obj);
        return this;
    }

    @Override
    public <F extends Filter> BroadcastRouterBuilder<P> filterWith(FilterDefinition<F> obj) {
        pipeline.filterWith(obj);
        return this;
    }

    @Override
    public BroadcastRouterBuilder<P> filterWith(String ref) {
        pipeline.filterWith(ref);
        return this;
    }

    @Override
    public BroadcastRouterBuilder<BroadcastRouterBuilder<P>> broadcast() {
        BroadcastRouterBuilderImpl<BroadcastRouterBuilder<P>> builder = new BroadcastRouterBuilderImpl<BroadcastRouterBuilder<P>>(this);
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public ChoiceRouterBuilder<BroadcastRouterBuilder<P>> choice() {
        ChoiceRouterBuilderImpl<BroadcastRouterBuilder<P>> builder = new ChoiceRouterBuilderImpl<BroadcastRouterBuilder<P>>(this);
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public AsyncRouterBuilder<BroadcastRouterBuilder<P>> async() {
        //TODO
        return null;
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
