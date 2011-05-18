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

public class PipelineBuilderImpl<P extends PipelineBuilder<P>> implements PipelineBuilder<P> {

    private final List<Builder<?>> processorList;
    private final PipelineBuilderImpl parentScope;
    private final MuleContext muleContext;

    public PipelineBuilderImpl(MuleContext muleContext, PipelineBuilderImpl parent) {
        this.processorList = new ArrayList<Builder<?>>();
        this.parentScope = parent;
        this.muleContext = muleContext;
    }

    /* component */

    @Override
    public P execute(Object obj) {
        if (parentScope != null) {
            return (P) parentScope.execute(obj);
        }
        ExecutorBuilderImpl builder = new ExecutorBuilderImpl(this, muleContext, obj);
        processorList.add(builder);
        return (P) builder;
    }

    @Override
    public P execute(Callable obj) {
        if (parentScope != null) {
            return (P) parentScope.execute(obj);
        }

        ExecutorBuilderImpl builder = new ExecutorBuilderImpl(this, muleContext, obj);
        processorList.add(builder);

        return (P) builder;
    }

    @Override
    public ExecutorBuilder execute(Class<?> clazz) {
        if (parentScope != null) {
            return parentScope.execute(clazz);
        }
        ExecutorBuilderImpl builder = new ExecutorBuilderImpl(this, muleContext, clazz);
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
            return (P) parentScope.log(level);
        }

        processorList.add(new ExecutorBuilderImpl(this, muleContext, new SimpleLogComponent(level)));
        return (P) this;
    }

    @Override
    public P log(String message) {
        return log(message, ErrorLevel.INFO);
    }

    @Override
    public P log(String message, ErrorLevel level) {
        if (parentScope != null) {
            return (P) parentScope.log(message, level);
        }

        processorList.add(new ExecutorBuilderImpl(this, muleContext, new ExtendedLogComponent(message, level)));
        return (P) this;
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> P log(E expr) {
        return log(expr, ErrorLevel.INFO);
    }

    @Override
    public <E extends ExpressionEvaluatorBuilder> P log(E expr, ErrorLevel level) {
        if (parentScope != null) {
            return (P) parentScope.log(expr, level);
        }

        processorList.add(new ExecutorBuilderImpl(this, muleContext, new ExpressionLogComponent<E>(expr, level)));
        return (P) this;
    }

    @Override
    public P echo() {
        if (parentScope != null) {
            return (P) parentScope.echo();
        }

        processorList.add(new ExecutorBuilderImpl(this, muleContext, new EchoComponent()));
        return (P) this;
    }

    /* outbound */
    @Override
    public EndPointBuilder.OutboundEndpointBuilder send(String uri) {
        if (parentScope != null) {
            return parentScope.send(uri);
        }
        EndpointBuilderImpl.OutboundEndpointBuilderImpl builder = new EndpointBuilderImpl.OutboundEndpointBuilderImpl(this, muleContext, uri);
        processorList.add(builder);
        return builder;
    }

    /* transform */

    @Override
    public <E extends ExpressionEvaluatorBuilder> P transform(E expr) {
        if (parentScope != null) {
            return (P) parentScope.transform(expr);
        }

        processorList.add(new ExpressionTransformerBuilderImpl(expr));
        return (P) this;
    }

    @Override
    public <T> P transformTo(Class<T> clazz) {
        if (parentScope != null) {
            return (P) parentScope.transformTo(clazz);
        }

        processorList.add(new TransformerBuilderImpl<T>(clazz));
        return (P) this;
    }

    /* filter */

    @Override
    public P filter(CoreExpr.GenericExpressionFilterEvaluatorBuilder expr) {
        if (parentScope != null) {
            return (P) parentScope.filter(expr);
        }

        processorList.add(new ExpressionFilterBuilderImpl(expr));
        return (P) this;
    }


    @Override
    public <E extends ExpressionEvaluatorBuilder> P filter(E expr) {
        if (parentScope != null) {
            return (P) parentScope.filter(expr);
        }

        processorList.add(new ExpressionFilterBuilderImpl(expr));
        return (P) this;
    }

    /* routers */

    @Override
    public AllRouterBuilder all() {
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
            for (Builder builder : processorList) {
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
