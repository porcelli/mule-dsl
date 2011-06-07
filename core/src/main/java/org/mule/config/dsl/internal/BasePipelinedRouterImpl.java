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
import org.mule.api.lifecycle.Callable;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.api.transformer.Transformer;
import org.mule.config.dsl.*;
import org.mule.config.dsl.expression.CoreExpr;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;

import java.util.List;

public abstract class BasePipelinedRouterImpl<P extends PipelineBuilder<P>> implements PipelineBuilder<P>, MessageProcessorListBuilder {

    protected final PipelineBuilderImpl<P> pipeline;

    public BasePipelinedRouterImpl() {
        this.pipeline = new PipelineBuilderImpl<P>(null);
    }

    protected abstract P getThis();

    @Override
    public P log() {
        pipeline.log();
        return getThis();
    }

    @Override
    public P log(LogLevel level) {
        pipeline.log(level);
        return getThis();
    }

    @Override
    public P log(String message) {
        pipeline.log(message);
        return getThis();
    }

    @Override
    public P log(String message, LogLevel level) {
        pipeline.log(message, level);
        return getThis();
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> P log(E expr) {
        pipeline.log(expr);
        return getThis();
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> P log(E expr, LogLevel level) {
        pipeline.log(expr, level);
        return getThis();
    }

    @Override
    public P echo() {
        pipeline.echo();
        return getThis();
    }

    @Override
    public ExecutorBuilder<P> execute(Object obj) {
        ExecutorBuilderImpl<P> builder = new ExecutorBuilderImpl<P>(getThis(), obj);
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public P execute(Callable obj) {
        pipeline.execute(obj);
        return getThis();
    }

    @Override
    public ExecutorBuilder<P> execute(Class<?> clazz) {
        ExecutorBuilderImpl<P> builder = new ExecutorBuilderImpl<P>(getThis(), clazz);
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public ExecutorBuilder<P> execute(Class<?> clazz, Scope scope) {
        ExecutorBuilderImpl<P> builder = new ExecutorBuilderImpl<P>(getThis(), clazz, scope);
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public P send(String uri) {
        pipeline.send(uri);
        return getThis();
    }

    @Override
    public P send(String uri, MessageExchangePattern pattern) {
        pipeline.send(uri, pattern);
        return getThis();
    }


    @Override
    public <E extends ExpressionEvaluatorBuilder> P transform(E expr) {
        pipeline.transform(expr);
        return getThis();
    }

    @Override
    public <T> P transformTo(Class<T> clazz) {
        pipeline.transformTo(clazz);
        return getThis();
    }

    @Override
    public <T extends Transformer> P transformWith(Class<T> clazz) {
        pipeline.transformWith(clazz);
        return getThis();
    }

    @Override
    public <T extends Transformer> P transformWith(T obj) {
        pipeline.transformWith(obj);
        return getThis();
    }

    @Override
    public <T extends Transformer> P transformWith(TransformerDefinition<T> obj) {
        pipeline.transformWith(obj);
        return getThis();
    }

    @Override
    public P transformWith(String ref) {
        pipeline.transformWith(ref);
        return getThis();
    }

    @Override
    public P filter(CoreExpr.GenericExpressionFilterEvaluatorBuilder expr) {
        pipeline.filter(expr);
        return getThis();
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> P filter(E expr) {
        pipeline.filter(expr);
        return getThis();
    }

    @Override
    public <T> P filterBy(Class<T> clazz) {
        pipeline.filterBy(clazz);
        return getThis();
    }

    @Override
    public <F extends Filter> P filterWith(Class<F> clazz) {
        pipeline.filterWith(clazz);
        return getThis();
    }

    @Override
    public <F extends Filter> P filterWith(F obj) {
        pipeline.filterWith(obj);
        return getThis();
    }

    @Override
    public <F extends Filter> P filterWith(FilterDefinition<F> obj) {
        pipeline.filterWith(obj);
        return getThis();
    }

    @Override
    public P filterWith(String ref) {
        pipeline.filterWith(ref);
        return getThis();
    }

    @Override
    public BroadcastRouterBuilder<P> broadcast() {
        BroadcastRouterBuilderImpl<P> builder = new BroadcastRouterBuilderImpl<P>(getThis());
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public ChoiceRouterBuilder<P> choice() {
        ChoiceRouterBuilderImpl<P> builder = new ChoiceRouterBuilderImpl<P>(getThis());
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public AsyncRouterBuilder<P> async() {
        AsyncRouterBuilderImpl<P> builder = new AsyncRouterBuilderImpl<P>(getThis());
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
}
