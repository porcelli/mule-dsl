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
    public InnerWhenChoiceBuilder<P> log(LogLevel level) {
        pipeline.log(level);
        return this;
    }

    @Override
    public InnerWhenChoiceBuilder<P> log(String message) {
        pipeline.log(message);
        return this;
    }

    @Override
    public InnerWhenChoiceBuilder<P> log(String message, LogLevel level) {
        pipeline.log(message, level);
        return this;
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> InnerWhenChoiceBuilder<P> log(E expr) {
        pipeline.log(expr);
        return this;
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> InnerWhenChoiceBuilder<P> log(E expr, LogLevel level) {
        pipeline.log(expr, level);
        return this;
    }

    @Override
    public InnerWhenChoiceBuilder<P> echo() {
        pipeline.echo();
        return this;
    }

    @Override
    public ExecutorBuilder<InnerWhenChoiceBuilder<P>> execute(Object obj) {
        ExecutorBuilderImpl<InnerWhenChoiceBuilder<P>> builder = new ExecutorBuilderImpl<InnerWhenChoiceBuilder<P>>(this, obj);
        pipeline.addToProcessorList(builder);

        return builder;
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
    public ExecutorBuilder<InnerWhenChoiceBuilder<P>> execute(Class<?> clazz, Scope scope) {
        ExecutorBuilderImpl<InnerWhenChoiceBuilder<P>> builder = new ExecutorBuilderImpl<InnerWhenChoiceBuilder<P>>(this, clazz, scope);
        pipeline.addToProcessorList(builder);

        return builder;
    }

    @Override
    public InnerWhenChoiceBuilder<P> send(String uri) {
        pipeline.send(uri);
        return this;
    }

    @Override
    public InnerWhenChoiceBuilder<P> send(String uri, MessageExchangePattern pattern) {
        pipeline.send(uri, pattern);
        return this;
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
    public <T extends Transformer> InnerWhenChoiceBuilder<P> transformWith(Class<T> clazz) {
        pipeline.transformWith(clazz);
        return this;
    }

    @Override
    public <T extends Transformer> InnerWhenChoiceBuilder<P> transformWith(T obj) {
        pipeline.transformWith(obj);
        return this;
    }

    @Override
    public <T extends Transformer> InnerWhenChoiceBuilder<P> transformWith(TransformerDefinition<T> obj) {
        pipeline.transformWith(obj);
        return this;
    }

    @Override
    public InnerWhenChoiceBuilder<P> transformWith(String ref) {
        pipeline.transformWith(ref);
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
    public <T> InnerWhenChoiceBuilder<P> filterBy(Class<T> clazz) {
        pipeline.filterBy(clazz);
        return this;
    }

    @Override
    public <F extends Filter> InnerWhenChoiceBuilder<P> filterWith(Class<F> clazz) {
        pipeline.filterWith(clazz);
        return this;
    }

    @Override
    public <F extends Filter> InnerWhenChoiceBuilder<P> filterWith(F obj) {
        pipeline.filterWith(obj);
        return this;
    }

    @Override
    public <F extends Filter> InnerWhenChoiceBuilder<P> filterWith(FilterDefinition<F> obj) {
        pipeline.filterWith(obj);
        return this;
    }

    @Override
    public InnerWhenChoiceBuilder<P> filterWith(String ref) {
        pipeline.filterWith(ref);
        return this;
    }

    @Override
    public BroadcastRouterBuilder<InnerWhenChoiceBuilder<P>> broadcast() {
        BroadcastRouterBuilderImpl<InnerWhenChoiceBuilder<P>> builder = new BroadcastRouterBuilderImpl<InnerWhenChoiceBuilder<P>>(this);
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
    public AsyncRouterBuilder<InnerWhenChoiceBuilder<P>> async() {
        AsyncRouterBuilderImpl<InnerWhenChoiceBuilder<P>> builder = new AsyncRouterBuilderImpl<InnerWhenChoiceBuilder<P>>(this);
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
        public OtherwiseChoiceBuilder<P> log(LogLevel level) {
            ChoiceRouterBuilderImpl.this.log(level);
            return this;
        }

        @Override
        public OtherwiseChoiceBuilder<P> log(String message) {
            ChoiceRouterBuilderImpl.this.log(message);
            return this;
        }

        @Override
        public OtherwiseChoiceBuilder<P> log(String message, LogLevel level) {
            ChoiceRouterBuilderImpl.this.log(message, level);
            return this;
        }

        @Override
        public <E extends ExpressionEvaluatorBuilder> OtherwiseChoiceBuilder<P> log(E expr) {
            ChoiceRouterBuilderImpl.this.log(expr);
            return this;
        }

        @Override
        public <E extends ExpressionEvaluatorBuilder> OtherwiseChoiceBuilder<P> log(E expr, LogLevel level) {
            ChoiceRouterBuilderImpl.this.log(expr, level);
            return this;
        }

        @Override
        public OtherwiseChoiceBuilder<P> echo() {
            ChoiceRouterBuilderImpl.this.echo();
            return this;
        }

        @Override
        public ExecutorBuilder<OtherwiseChoiceBuilder<P>> execute(Object obj) {
            ExecutorBuilderImpl<OtherwiseChoiceBuilder<P>> builder = new ExecutorBuilderImpl<OtherwiseChoiceBuilder<P>>(this, obj);
            pipeline.addToProcessorList(builder);

            return builder;
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
        public ExecutorBuilder<OtherwiseChoiceBuilder<P>> execute(Class<?> clazz, Scope scope) {
            ExecutorBuilderImpl<OtherwiseChoiceBuilder<P>> builder = new ExecutorBuilderImpl<OtherwiseChoiceBuilder<P>>(this, clazz, scope);
            pipeline.addToProcessorList(builder);

            return builder;
        }

        @Override
        public OtherwiseChoiceBuilder<P> send(String uri) {
            ChoiceRouterBuilderImpl.this.send(uri);
            return this;
        }

        @Override
        public OtherwiseChoiceBuilder<P> send(String uri, MessageExchangePattern pattern) {
            ChoiceRouterBuilderImpl.this.send(uri, pattern);
            return this;
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
        public <T extends Transformer> OtherwiseChoiceBuilder<P> transformWith(Class<T> clazz) {
            ChoiceRouterBuilderImpl.this.transformWith(clazz);
            return this;
        }

        @Override
        public <T extends Transformer> OtherwiseChoiceBuilder<P> transformWith(T obj) {
            ChoiceRouterBuilderImpl.this.transformWith(obj);
            return this;
        }

        @Override
        public <T extends Transformer> OtherwiseChoiceBuilder<P> transformWith(TransformerDefinition<T> obj) {
            ChoiceRouterBuilderImpl.this.transformWith(obj);
            return this;
        }

        @Override
        public OtherwiseChoiceBuilder<P> transformWith(String ref) {
            ChoiceRouterBuilderImpl.this.transformWith(ref);
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
        public <T> OtherwiseChoiceBuilder<P> filterBy(Class<T> clazz) {
            ChoiceRouterBuilderImpl.this.filterBy(clazz);
            return this;
        }

        @Override
        public <F extends Filter> OtherwiseChoiceBuilder<P> filterWith(Class<F> clazz) {
            ChoiceRouterBuilderImpl.this.filterWith(clazz);
            return this;
        }

        @Override
        public <F extends Filter> OtherwiseChoiceBuilder<P> filterWith(F obj) {
            ChoiceRouterBuilderImpl.this.filterWith(obj);
            return this;
        }

        @Override
        public <F extends Filter> OtherwiseChoiceBuilder<P> filterWith(FilterDefinition<F> obj) {
            ChoiceRouterBuilderImpl.this.filterWith(obj);
            return this;
        }

        @Override
        public OtherwiseChoiceBuilder<P> filterWith(String ref) {
            ChoiceRouterBuilderImpl.this.filterWith(ref);
            return this;
        }

        @Override
        public BroadcastRouterBuilder<OtherwiseChoiceBuilder<P>> broadcast() {
            BroadcastRouterBuilderImpl<OtherwiseChoiceBuilder<P>> builder = new BroadcastRouterBuilderImpl<OtherwiseChoiceBuilder<P>>(this);
            pipeline.addToProcessorList(builder);

            return builder;
        }

        @Override
        public ChoiceRouterBuilder<OtherwiseChoiceBuilder<P>> choice() {
            ChoiceRouterBuilderImpl<OtherwiseChoiceBuilder<P>> builder = new ChoiceRouterBuilderImpl<OtherwiseChoiceBuilder<P>>(this);
            pipeline.addToProcessorList(builder);

            return builder;
        }

        @Override
        public AsyncRouterBuilder<OtherwiseChoiceBuilder<P>> async() {
            AsyncRouterBuilderImpl<OtherwiseChoiceBuilder<P>> builder = new AsyncRouterBuilderImpl<OtherwiseChoiceBuilder<P>>(this);
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
