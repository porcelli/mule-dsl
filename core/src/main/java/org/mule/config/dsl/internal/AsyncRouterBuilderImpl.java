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
import org.mule.config.spring.factories.AsyncMessageProcessorsFactoryBean;
import org.mule.processor.AsyncDelegateMessageProcessor;

import java.util.List;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;


public class AsyncRouterBuilderImpl<P extends PipelineBuilder<P>> implements AsyncRouterBuilder<P>, Builder<AsyncDelegateMessageProcessor>, MessageProcessorListBuilder {

    private final P parentScope;
    private final PipelineBuilderImpl<AsyncRouterBuilder<P>> pipeline;

    AsyncRouterBuilderImpl(P parentScope) {
        this.parentScope = checkNotNull(parentScope, "parentScope");
        this.pipeline = new PipelineBuilderImpl<AsyncRouterBuilder<P>>(null);
    }

    @Override
    public P endAsync() {
        return parentScope;
    }

    @Override
    public AsyncRouterBuilder<P> log() {
        pipeline.log();
        return this;
    }

    @Override
    public AsyncRouterBuilder<P> log(LogLevel level) {
        pipeline.log(level);
        return this;
    }

    @Override
    public AsyncRouterBuilder<P> log(String message) {
        pipeline.log(message);
        return this;
    }

    @Override
    public AsyncRouterBuilder<P> log(String message, LogLevel level) {
        pipeline.log(message, level);
        return this;
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> AsyncRouterBuilder<P> log(E expr) {
        pipeline.log(expr);
        return this;
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> AsyncRouterBuilder<P> log(E expr, LogLevel level) {
        pipeline.log(expr, level);
        return this;
    }

    @Override
    public AsyncRouterBuilder<P> echo() {
        pipeline.echo();
        return this;
    }

    @Override
    public ExecutorBuilder<AsyncRouterBuilder<P>> execute(Object obj) {
        ExecutorBuilderImpl<AsyncRouterBuilder<P>> builder = new ExecutorBuilderImpl<AsyncRouterBuilder<P>>(this, obj);
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public AsyncRouterBuilder<P> execute(Callable obj) {
        pipeline.execute(obj);
        return this;
    }

    @Override
    public ExecutorBuilder<AsyncRouterBuilder<P>> execute(Class<?> clazz) {
        ExecutorBuilderImpl<AsyncRouterBuilder<P>> builder = new ExecutorBuilderImpl<AsyncRouterBuilder<P>>(this, clazz);
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public ExecutorBuilder<AsyncRouterBuilder<P>> execute(Class<?> clazz, Scope scope) {
        ExecutorBuilderImpl<AsyncRouterBuilder<P>> builder = new ExecutorBuilderImpl<AsyncRouterBuilder<P>>(this, clazz, scope);
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public AsyncRouterBuilder<P> send(String uri) {
        pipeline.send(uri);
        return this;
    }

    @Override
    public AsyncRouterBuilder<P> send(String uri, MessageExchangePattern pattern) {
        pipeline.send(uri, pattern);
        return this;
    }


    @Override
    public <E extends ExpressionEvaluatorBuilder> AsyncRouterBuilder<P> transform(E expr) {
        pipeline.transform(expr);
        return this;
    }

    @Override
    public <T> AsyncRouterBuilder<P> transformTo(Class<T> clazz) {
        pipeline.transformTo(clazz);
        return this;
    }

    @Override
    public <T extends Transformer> AsyncRouterBuilder<P> transformWith(Class<T> clazz) {
        pipeline.transformWith(clazz);
        return this;
    }

    @Override
    public <T extends Transformer> AsyncRouterBuilder<P> transformWith(T obj) {
        pipeline.transformWith(obj);
        return this;
    }

    @Override
    public <T extends Transformer> AsyncRouterBuilder<P> transformWith(TransformerDefinition<T> obj) {
        pipeline.transformWith(obj);
        return this;
    }

    @Override
    public AsyncRouterBuilder<P> transformWith(String ref) {
        pipeline.transformWith(ref);
        return this;
    }

    @Override
    public AsyncRouterBuilder<P> filter(CoreExpr.GenericExpressionFilterEvaluatorBuilder expr) {
        pipeline.filter(expr);
        return this;
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> AsyncRouterBuilder<P> filter(E expr) {
        pipeline.filter(expr);
        return this;
    }

    @Override
    public <T> AsyncRouterBuilder<P> filterBy(Class<T> clazz) {
        pipeline.filterBy(clazz);
        return this;
    }

    @Override
    public <F extends Filter> AsyncRouterBuilder<P> filterWith(Class<F> clazz) {
        pipeline.filterWith(clazz);
        return this;
    }

    @Override
    public <F extends Filter> AsyncRouterBuilder<P> filterWith(F obj) {
        pipeline.filterWith(obj);
        return this;
    }

    @Override
    public <F extends Filter> AsyncRouterBuilder<P> filterWith(FilterDefinition<F> obj) {
        pipeline.filterWith(obj);
        return this;
    }

    @Override
    public AsyncRouterBuilder<P> filterWith(String ref) {
        pipeline.filterWith(ref);
        return this;
    }

    @Override
    public BroadcastRouterBuilder<AsyncRouterBuilder<P>> broadcast() {
        BroadcastRouterBuilderImpl<AsyncRouterBuilder<P>> builder = new BroadcastRouterBuilderImpl<AsyncRouterBuilder<P>>(this);
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public ChoiceRouterBuilder<AsyncRouterBuilder<P>> choice() {
        ChoiceRouterBuilderImpl<AsyncRouterBuilder<P>> builder = new ChoiceRouterBuilderImpl<AsyncRouterBuilder<P>>(this);
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public AsyncRouterBuilder<AsyncRouterBuilder<P>> async() {
        AsyncRouterBuilderImpl<AsyncRouterBuilder<P>> builder = new AsyncRouterBuilderImpl<AsyncRouterBuilder<P>>(this);
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
    public AsyncDelegateMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        if (!pipeline.isProcessorListEmpty()) {
            try {
                AsyncMessageProcessorsFactoryBean factoryBean = new AsyncMessageProcessorsFactoryBean();
                factoryBean.setMuleContext(muleContext);
                factoryBean.setMessageProcessors(pipeline.buildProcessorList(muleContext, injector, placeholder));
                return (AsyncDelegateMessageProcessor) factoryBean.getObject();
            } catch (Exception e) {
                //TODO handle
                throw new RuntimeException(e);
            }
        }

        throw new RuntimeException();
    }
}
