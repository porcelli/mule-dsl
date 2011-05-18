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
import org.mule.component.simple.EchoComponent;
import org.mule.config.dsl.*;
import org.mule.config.dsl.component.ExpressionLogComponent;
import org.mule.config.dsl.component.ExtendedLogComponent;
import org.mule.config.dsl.component.SimpleLogComponent;
import org.mule.config.dsl.expression.CoreExpr;

import java.util.ArrayList;
import java.util.List;

public abstract class PipelineBuilderImpl<P extends PipelineBuilder<P>> implements PipelineBuilder<P> {

    private final List<Builder<?>> processorList;
    protected final PipelineBuilderImpl<P> parentScope;
    private final MuleContext muleContext;

    public PipelineBuilderImpl(MuleContext muleContext, PipelineBuilderImpl<P> parent) {
        this.processorList = new ArrayList<Builder<?>>();
        this.parentScope = parent;
        this.muleContext = muleContext;
    }

    protected abstract P getThis();

    /* component */

    @Override
    public P execute(Object obj) {
        if (parentScope != null) {
            return parentScope.execute(obj);
        }
        ExecutorBuilderImpl<P> builder = new ExecutorBuilderImpl<P>(this, muleContext, obj);
        processorList.add(builder);

        return getThis();
    }

    @Override
    public P execute(Callable obj) {
        if (parentScope != null) {
            return parentScope.execute(obj);
        }

        ExecutorBuilderImpl<P> builder = new ExecutorBuilderImpl<P>(this, muleContext, obj);
        processorList.add(builder);

        return getThis();
    }

    @Override
    public ExecutorBuilder<P> execute(Class<?> clazz) {
        if (parentScope != null) {
            return parentScope.execute(clazz);
        }
        ExecutorBuilderImpl<P> builder = new ExecutorBuilderImpl<P>(this, muleContext, clazz);
        processorList.add(builder);

        return builder;
    }

    @Override
    public P log() {
        return log(ErrorLevel.INFO);
    }

    @Override
    public P log(ErrorLevel level) {
        if (parentScope != null) {
            return parentScope.log(level);
        }

        processorList.add(new ExecutorBuilderImpl<P>(this, muleContext, new SimpleLogComponent(level)));
        return getThis();
    }

    @Override
    public P log(String message) {
        return log(message, ErrorLevel.INFO);
    }

    @Override
    public P log(String message, ErrorLevel level) {
        if (parentScope != null) {
            return parentScope.log(message, level);
        }

        processorList.add(new ExecutorBuilderImpl<P>(this, muleContext, new ExtendedLogComponent(message, level)));
        return getThis();
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> P log(E expr) {
        return log(expr, ErrorLevel.INFO);
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> P log(
            E expr, ErrorLevel level) {
        if (parentScope != null) {
            return parentScope.log(expr, level);
        }

        processorList.add(new ExecutorBuilderImpl<P>(this, muleContext, new ExpressionLogComponent(expr, level)));
        return getThis();
    }

    @Override
    public P echo() {
        if (parentScope != null) {
            return parentScope.echo();
        }

        processorList.add(new ExecutorBuilderImpl<P>(this, muleContext, new EchoComponent()));
        return getThis();
    }

    /* outbound */
    @Override
    public OutboundEndpointBuilder<P> send(String uri) {
        if (parentScope != null) {
            return parentScope.send(uri);
        }
        OutboundEndpointBuilderImpl<P> builder = new OutboundEndpointBuilderImpl<P>(this, muleContext, uri);
        processorList.add(builder);

        return builder;
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

        processorList.add(new TransformerBuilderImpl<T>(clazz));
        return getThis();
    }

    /* filter */

    @Override
    public P filter(
            CoreExpr.GenericExpressionFilterEvaluatorBuilder expr) {
        if (parentScope != null) {
            return parentScope.filter(expr);
        }

        processorList.add(new ExpressionFilterBuilderImpl(expr));
        return getThis();
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> P filter(
            E expr) {
        if (parentScope != null) {
            return parentScope.filter(expr);
        }

        processorList.add(new ExpressionFilterBuilderImpl(expr));
        return getThis();
    }

    /* routers */

    @Override
    public AllRouterBuilder<P> all() {
        return null;
    }

    @Override
    public RouterBuilder.ChoiceRouterBuilder choice() {
        return null;
    }

    public List<MessageProcessor> buildProcessorList(Injector injector) {
        if (parentScope != null) {
            return parentScope.buildProcessorList(injector);
        }

        List<MessageProcessor> result = new ArrayList<MessageProcessor>();

        if (!isProcessorListEmpty()) {
            for (Builder<?> builder : processorList) {
                result.add((MessageProcessor) builder.build(injector));
            }
        }

        return result;
    }

    public boolean isProcessorListEmpty() {
        if (parentScope != null) {
            return parentScope.isProcessorListEmpty();
        }

        if (processorList != null && processorList.size() > 0) {
            return false;
        }
        return true;
    }

}
