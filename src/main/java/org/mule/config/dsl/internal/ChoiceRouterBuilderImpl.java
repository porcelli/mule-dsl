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
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.routing.ChoiceRouter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mule.config.dsl.internal.util.MessageProcessorUtil.buildProcessorChain;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class ChoiceRouterBuilderImpl<P extends PipelineBuilder<P>> implements ChoiceRouterBuilder<P>, InnerWhenChoiceBuilder<P>, Builder<ChoiceRouter>, MessageProcessorListBuilder {

    private final P parentScope;
    private final PipelineBuilderImpl<P> pipeline;
    private final LinkedList<Route> choiceElements;

    private ExpressionEvaluatorBuilder lastWhenExpr = null;

    ChoiceRouterBuilderImpl(P parentScope) {
        this.parentScope = checkNotNull(parentScope, "parentScope");
        this.pipeline = new PipelineBuilderImpl<P>(null);
        this.choiceElements = new LinkedList<Route>();
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> InnerWhenChoiceBuilder<P> when(E expr) {
        if (lastWhenExpr != null) {
            choiceElements.add(new Route(lastWhenExpr, pipeline.getProcessorList()));
            pipeline.getProcessorList().clear();
        }
        lastWhenExpr = checkNotNull(expr, "expr");
        return this;
    }

    @Override
    public OtherwiseChoiceBuilder<P> otherwise() {
        if (lastWhenExpr != null) {
            choiceElements.add(new Route(lastWhenExpr, pipeline.getProcessorList()));
            pipeline.getProcessorList().clear();
        }
        lastWhenExpr = null;

        return new OtherwiseChoiceBuilderImpl<P>();
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
        ExecutorBuilderImpl<InnerWhenChoiceBuilder<P>> builder = new ExecutorBuilderImpl<InnerWhenChoiceBuilder<P>>(this, clazz);
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public OutboundEndpointBuilder<InnerWhenChoiceBuilder<P>> send(String uri) {
        OutboundEndpointBuilderImpl<InnerWhenChoiceBuilder<P>> builder = new OutboundEndpointBuilderImpl<InnerWhenChoiceBuilder<P>>(this, uri);
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
        AllRouterBuilderImpl<InnerWhenChoiceBuilder<P>> builder = new AllRouterBuilderImpl<InnerWhenChoiceBuilder<P>>(this);
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public ChoiceRouterBuilder<InnerWhenChoiceBuilder<P>> choice() {
        ChoiceRouterBuilderImpl<InnerWhenChoiceBuilder<P>> builder = new ChoiceRouterBuilderImpl<InnerWhenChoiceBuilder<P>>(this);
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
    public ChoiceRouter build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        if (lastWhenExpr != null) {
            choiceElements.add(new Route(lastWhenExpr, pipeline.getProcessorList()));
            pipeline.getProcessorList().clear();
        } else if (pipeline.getProcessorList().size() > 0) {
            choiceElements.add(new Route(null, pipeline.getProcessorList()));
        }

        ChoiceRouter choiceRouter = new ChoiceRouter();
        for (Route activeRoute : choiceElements) {
            if (activeRoute.getExpr() != null) {
                choiceRouter.addRoute(buildProcessorChain(activeRoute.getProcessorList(), muleContext, injector, placeholder), activeRoute.getExpr().getFilter(placeholder));
            } else {
                choiceRouter.setDefaultRoute(buildProcessorChain(activeRoute.getProcessorList(), muleContext, injector, placeholder));
            }
        }

        return choiceRouter;
    }

    public class OtherwiseChoiceBuilderImpl<P extends PipelineBuilder<P>> implements OtherwiseChoiceBuilder<P> {
        @Override
        @SuppressWarnings("unchecked")
        public P endChoice() {
            return (P) parentScope;
        }

        @Override
        public OtherwiseChoiceBuilder<P> log() {
            ChoiceRouterBuilderImpl.this.log();
            return this;
        }

        @Override
        public OtherwiseChoiceBuilder<P> log(ErrorLevel level) {
            ChoiceRouterBuilderImpl.this.log(level);
            return this;
        }

        @Override
        public OtherwiseChoiceBuilder<P> log(String message) {
            ChoiceRouterBuilderImpl.this.log(message);
            return this;
        }

        @Override
        public OtherwiseChoiceBuilder<P> log(String message, ErrorLevel level) {
            ChoiceRouterBuilderImpl.this.log(message, level);
            return this;
        }

        @Override
        public <E extends ExpressionEvaluatorBuilder> OtherwiseChoiceBuilder<P> log(E expr) {
            ChoiceRouterBuilderImpl.this.log(expr);
            return this;
        }

        @Override
        public <E extends ExpressionEvaluatorBuilder> OtherwiseChoiceBuilder<P> log(E expr, ErrorLevel level) {
            ChoiceRouterBuilderImpl.this.log(expr, level);
            return this;
        }

        @Override
        public OtherwiseChoiceBuilder<P> echo() {
            ChoiceRouterBuilderImpl.this.echo();
            return this;
        }

        @Override
        public OtherwiseChoiceBuilder<P> execute(Object obj) {
            ChoiceRouterBuilderImpl.this.execute(obj);
            return this;
        }

        @Override
        public OtherwiseChoiceBuilder<P> execute(Callable obj) {
            ChoiceRouterBuilderImpl.this.execute(obj);
            return this;
        }

        @Override
        public ExecutorBuilder<OtherwiseChoiceBuilder<P>> execute(Class<?> clazz) {
            ExecutorBuilderImpl<OtherwiseChoiceBuilder<P>> builder = new ExecutorBuilderImpl<OtherwiseChoiceBuilder<P>>(this, clazz);
            pipeline.addToProcessorList(builder);

            return builder;
        }

        @Override
        public OutboundEndpointBuilder<OtherwiseChoiceBuilder<P>> send(String uri) {
            OutboundEndpointBuilderImpl<OtherwiseChoiceBuilder<P>> builder = new OutboundEndpointBuilderImpl<OtherwiseChoiceBuilder<P>>(this, uri);
            pipeline.addToProcessorList(builder);

            return builder;
        }

        @Override
        public <E extends ExpressionEvaluatorBuilder> OtherwiseChoiceBuilder<P> transform(E expr) {
            ChoiceRouterBuilderImpl.this.transform(expr);
            return this;
        }

        @Override
        public <T> OtherwiseChoiceBuilder<P> transformTo(Class<T> clazz) {
            ChoiceRouterBuilderImpl.this.transformTo(clazz);
            return this;
        }

        @Override
        public OtherwiseChoiceBuilder<P> filter(GenericExpressionFilterEvaluatorBuilder expr) {
            ChoiceRouterBuilderImpl.this.filter(expr);
            return this;
        }

        @Override
        public <E extends ExpressionEvaluatorBuilder> OtherwiseChoiceBuilder<P> filter(E expr) {
            ChoiceRouterBuilderImpl.this.filter(expr);
            return this;
        }

        @Override
        public AllRouterBuilder<OtherwiseChoiceBuilder<P>> all() {
            AllRouterBuilderImpl<OtherwiseChoiceBuilder<P>> builder = new AllRouterBuilderImpl<OtherwiseChoiceBuilder<P>>(this);
            pipeline.addToProcessorList(builder);

            return builder;
        }

        @Override
        public ChoiceRouterBuilder<OtherwiseChoiceBuilder<P>> choice() {
            ChoiceRouterBuilderImpl<OtherwiseChoiceBuilder<P>> builder = new ChoiceRouterBuilderImpl<OtherwiseChoiceBuilder<P>>(this);
            pipeline.addToProcessorList(builder);

            return builder;
        }
    }

    private static class Route {
        final ExpressionEvaluatorBuilder expr;

        final List<Builder<?>> processorList;

        Route(ExpressionEvaluatorBuilder expr, List<Builder<?>> processorList) {
            this.expr = expr;
            this.processorList = new ArrayList<Builder<?>>(processorList);
        }

        ExpressionEvaluatorBuilder getExpr() {
            return expr;
        }

        List<Builder<?>> getProcessorList() {
            return processorList;
        }
    }
}
