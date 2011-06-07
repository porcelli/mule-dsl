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
import org.mule.component.simple.EchoComponent;
import org.mule.config.dsl.*;
import org.mule.config.dsl.component.SimpleLogComponent;
import org.mule.config.dsl.expression.CoreExpr;
import org.mule.config.dsl.internal.util.MessageProcessorUtil;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;

import java.util.ArrayList;
import java.util.List;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class PipelineBuilderImpl<P extends PipelineBuilder<P>> implements PipelineBuilder<P>, MessageProcessorListBuilder {

    protected final List<Builder<?>> processorList;
    protected final P parentScope;

    public PipelineBuilderImpl(P parent) {
        this.processorList = new ArrayList<Builder<?>>();
        this.parentScope = parent;
    }

    @SuppressWarnings("unchecked")
    protected P getThis() {
        return (P) this;
    }

    /* component */

    @Override
    public ExecutorBuilder<P> execute(Object obj) {
        if (parentScope != null) {
            return parentScope.execute(obj);
        }
        ExecutorBuilderImpl<P> builder = new ExecutorBuilderImpl<P>(getThis(), obj);
        processorList.add(builder);

        return builder;
    }

    @Override
    public P execute(Callable obj) {
        if (parentScope != null) {
            return parentScope.execute(obj);
        }

        processorList.add(new ExecutorBuilderImpl<P>(getThis(), obj));

        return getThis();
    }

    @Override
    public ExecutorBuilder<P> execute(Class<?> clazz) {
        return execute(clazz, Scope.PROTOTYPE);
    }

    @Override
    public ExecutorBuilder<P> execute(Class<?> clazz, Scope scope) {
        if (parentScope != null) {
            return parentScope.execute(clazz, scope);
        }

        ExecutorBuilderImpl<P> builder = new ExecutorBuilderImpl<P>(getThis(), clazz, scope);
        processorList.add(builder);

        return builder;
    }

    @Override
    public P log() {
        return log(LogLevel.INFO);
    }

    @Override
    public P log(LogLevel level) {
        if (parentScope != null) {
            return parentScope.log(level);
        }

        processorList.add(new ExecutorBuilderImpl<P>(getThis(), new SimpleLogComponent(level)));
        return getThis();
    }

    @Override
    public P log(String message) {
        return log(message, LogLevel.INFO);
    }

    @Override
    public P log(String message, LogLevel level) {
        if (parentScope != null) {
            return parentScope.log(message, level);
        }

        processorList.add(new ExecutorBuilderImpl<P>(getThis(), new ExtendedLogComponentBuilder(message, level)));
        return getThis();
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> P log(E expr) {
        return log(expr, LogLevel.INFO);
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> P log(
            E expr, LogLevel level) {
        if (parentScope != null) {
            return parentScope.log(expr, level);
        }

        processorList.add(new ExecutorBuilderImpl<P>(getThis(), new ExpressionLogComponentBuilder(expr, level)));

        return getThis();
    }

    @Override
    public P echo() {
        if (parentScope != null) {
            return parentScope.echo();
        }

        processorList.add(new ExecutorBuilderImpl<P>(getThis(), new EchoComponent()));
        return getThis();
    }

    /* outbound */
    @Override
    public P send(String uri) {
        return send(uri, null);
    }

    @Override
    public P send(String uri, MessageExchangePattern pattern) {
        if (parentScope != null) {
            return parentScope.send(uri);
        }

        processorList.add(new OutboundEndpointBuilderImpl(uri, pattern));

        return getThis();
    }

    /* transform */

    @Override
    public <E extends ExpressionEvaluatorBuilder> P transform(
            E expr) {
        if (parentScope != null) {
            return parentScope.transform(expr);
        }

        processorList.add(new ExpressionTransformerBuilderImpl<E>(expr));
        return getThis();
    }

    @Override
    public <T> P transformTo(Class<T> clazz) {
        if (parentScope != null) {
            return parentScope.transformTo(clazz);
        }

        processorList.add(new TypeBasedTransformerBuilderImpl<T>(clazz));
        return getThis();
    }

    @Override
    public <T extends Transformer> P transformWith(Class<T> clazz) {
        if (parentScope != null) {
            return parentScope.transformWith(clazz);
        }

        processorList.add(new CustomTransformerBuilderImpl<T>(clazz));
        return getThis();
    }

    @Override
    public <T extends Transformer> P transformWith(T obj) {
        if (parentScope != null) {
            return parentScope.transformWith(obj);
        }

        processorList.add(new CustomTransformerBuilderImpl<T>(obj));
        return getThis();
    }

    @Override
    public <T extends Transformer> P transformWith(TransformerDefinition<T> obj) {
        if (parentScope != null) {
            return parentScope.transformWith(obj);
        }

        processorList.add(new CustomTransformerBuilderImpl<T>(obj));
        return getThis();
    }

    @Override
    public P transformWith(String ref) {
        if (parentScope != null) {
            return parentScope.transformWith(ref);
        }

        processorList.add(new CustomTransformerBuilderImpl(ref));
        return getThis();
    }

    /* filter */

    @Override
    public P filter(CoreExpr.GenericExpressionFilterEvaluatorBuilder expr) {
        if (parentScope != null) {
            return parentScope.filter(expr);
        }

        processorList.add(new ExpressionFilterBuilderImpl(expr));
        return getThis();
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> P filter(E expr) {
        if (parentScope != null) {
            return parentScope.filter(expr);
        }

        processorList.add(new ExpressionFilterBuilderImpl(expr));
        return getThis();
    }

    @Override
    public <T> P filterBy(Class<T> clazz) {
        if (parentScope != null) {
            return parentScope.filterBy(clazz);
        }

        processorList.add(new TypeBasedFilterBuilderImpl(clazz));
        return getThis();
    }

    @Override
    public <F extends Filter> P filterWith(Class<F> clazz) {
        if (parentScope != null) {
            return parentScope.filterWith(clazz);
        }

        processorList.add(new CustomFilterBuilderImpl<F>(clazz));
        return getThis();
    }

    @Override
    public <F extends Filter> P filterWith(F obj) {
        if (parentScope != null) {
            return parentScope.filterWith(obj);
        }

        processorList.add(new CustomFilterBuilderImpl<F>(obj));
        return getThis();
    }

    @Override
    public <F extends Filter> P filterWith(FilterDefinition<F> obj) {
        if (parentScope != null) {
            return parentScope.filterWith(obj);
        }

        processorList.add(new CustomFilterBuilderImpl<F>(obj));
        return getThis();
    }

    @Override
    public P filterWith(String ref) {
        if (parentScope != null) {
            return parentScope.filterWith(ref);
        }

        processorList.add(new CustomFilterBuilderImpl(ref));
        return getThis();
    }

    /* routers */

    @Override
    public BroadcastRouterBuilder<P> broadcast() {
        if (parentScope != null) {
            return parentScope.broadcast();
        }
        BroadcastRouterBuilderImpl<P> builder = new BroadcastRouterBuilderImpl<P>(getThis());
        processorList.add(builder);

        return builder;
    }

    @Override
    public ChoiceRouterBuilder<P> choice() {
        if (parentScope != null) {
            return parentScope.choice();
        }
        ChoiceRouterBuilderImpl<P> builder = new ChoiceRouterBuilderImpl<P>(getThis());
        processorList.add(builder);

        return builder;
    }

    @Override
    public AsyncRouterBuilder<P> async() {
        //TODO
        return null;
    }


    @Override
    public void addToProcessorList(Builder<?> builder) {
        checkNotNull(builder, "builder");
        processorList.add(builder);
    }

    @Override
    public List<MessageProcessor> buildProcessorList(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        if (parentScope != null && parentScope instanceof MessageProcessorListBuilder) {
            return ((MessageProcessorListBuilder) parentScope).buildProcessorList(muleContext, injector, placeholder);
        }

        return MessageProcessorUtil.buildProcessorList(processorList, muleContext, injector, placeholder);
    }

    @Override
    public boolean isProcessorListEmpty() {
        if (parentScope != null && parentScope instanceof MessageProcessorListBuilder) {
            return ((MessageProcessorListBuilder) parentScope).isProcessorListEmpty();
        }

        if (processorList != null && processorList.size() > 0) {
            return false;
        }
        return true;
    }

    @Override
    public List<Builder<?>> getProcessorList() {
        return processorList;
    }

}
