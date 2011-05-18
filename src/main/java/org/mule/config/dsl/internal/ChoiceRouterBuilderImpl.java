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
import org.mule.api.lifecycle.Callable;
import org.mule.api.processor.MessageProcessor;
import org.mule.config.dsl.*;
import org.mule.config.dsl.ChoiceRouterBuilder.InnerWhenChoiceBuilder;
import org.mule.config.dsl.expression.CoreExpr.GenericExpressionFilterEvaluatorBuilder;
import org.mule.routing.ChoiceRouter;

import java.util.List;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class ChoiceRouterBuilderImpl<P extends PipelineBuilder<P>> implements ChoiceRouterBuilder<P>, InnerWhenChoiceBuilder<P>, Builder<ChoiceRouter>, MessageProcessorListBuilder {

    private final P parentScope;
    private final PipelineBuilderImpl<P> pipeline;
    private final MuleContext muleContext;

    ChoiceRouterBuilderImpl(final MuleContext muleContext, P parentScope) {
        this.parentScope = checkNotNull(parentScope, "parentScope");
        this.muleContext = checkNotNull(muleContext, "muleContext");
        this.pipeline = new PipelineBuilderImpl<P>(this.muleContext, null);
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> InnerWhenChoiceBuilder<P> when(E expr) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public InnerWhenChoiceBuilder<P> otherwise() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public P endChoice() {
        return parentScope;
    }

    @Override
    public InnerWhenChoiceBuilder<P> log() {
        pipeline.log();
        return this;
    }

    @Override
    public InnerWhenChoiceBuilder<P> log(ErrorLevel level) {
        pipeline.log(level);
        return this;
    }

    @Override
    public InnerWhenChoiceBuilder<P> log(String message) {
        pipeline.log(message);
        return this;
    }

    @Override
    public InnerWhenChoiceBuilder<P> log(String message, ErrorLevel level) {
        pipeline.log(message, level);
        return this;
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> InnerWhenChoiceBuilder<P> log(E expr) {
        pipeline.log(expr);
        return this;
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> InnerWhenChoiceBuilder<P> log(E expr, ErrorLevel level) {
        pipeline.log(expr, level);
        return this;
    }

    @Override
    public InnerWhenChoiceBuilder<P> echo() {
        pipeline.echo();
        return this;
    }

    @Override
    public InnerWhenChoiceBuilder<P> execute(Object obj) {
        pipeline.execute(obj);
        return this;
    }

    @Override
    public InnerWhenChoiceBuilder<P> execute(Callable obj) {
        pipeline.execute(obj);
        return this;
    }

    @Override
    public ExecutorBuilder<InnerWhenChoiceBuilder<P>> execute(Class<?> clazz) {
        ExecutorBuilderImpl<InnerWhenChoiceBuilder<P>> builder = new ExecutorBuilderImpl<InnerWhenChoiceBuilder<P>>(this, muleContext, clazz);
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public OutboundEndpointBuilder<InnerWhenChoiceBuilder<P>> send(String uri) {
        OutboundEndpointBuilderImpl<InnerWhenChoiceBuilder<P>> builder = new OutboundEndpointBuilderImpl<InnerWhenChoiceBuilder<P>>(this, muleContext, uri);
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> InnerWhenChoiceBuilder<P> transform(E expr) {
        pipeline.transform(expr);
        return this;
    }

    @Override
    public <T> InnerWhenChoiceBuilder<P> transformTo(Class<T> clazz) {
        pipeline.transformTo(clazz);
        return this;
    }

    @Override
    public InnerWhenChoiceBuilder<P> filter(GenericExpressionFilterEvaluatorBuilder expr) {
        pipeline.filter(expr);
        return this;
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> InnerWhenChoiceBuilder<P> filter(E expr) {
        pipeline.filter(expr);
        return this;
    }

    @Override
    public AllRouterBuilder<InnerWhenChoiceBuilder<P>> all() {
        AllRouterBuilderImpl<InnerWhenChoiceBuilder<P>> builder = new AllRouterBuilderImpl<InnerWhenChoiceBuilder<P>>(muleContext, this);
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public ChoiceRouterBuilder<InnerWhenChoiceBuilder<P>> choice() {
        ChoiceRouterBuilderImpl<InnerWhenChoiceBuilder<P>> builder = new ChoiceRouterBuilderImpl<InnerWhenChoiceBuilder<P>>(muleContext, this);
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
    public ChoiceRouter build(Injector injector) {
        return null;
    }

}
