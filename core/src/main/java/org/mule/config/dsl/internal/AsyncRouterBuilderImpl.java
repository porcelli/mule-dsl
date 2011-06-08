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
import org.mule.config.dsl.AsyncRouterBuilder;
import org.mule.config.dsl.PipelineBuilder;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.config.spring.factories.AsyncMessageProcessorsFactoryBean;
import org.mule.processor.AsyncDelegateMessageProcessor;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;


public class AsyncRouterBuilderImpl<P extends PipelineBuilder<P>> extends BasePipelinedRouterImpl<AsyncRouterBuilder<P>> implements AsyncRouterBuilder<P>, Builder<AsyncDelegateMessageProcessor> {

    private final P parentScope;

    public AsyncRouterBuilderImpl(P parentScope) {
        super();
        this.parentScope = checkNotNull(parentScope, "parentScope");
    }

    @Override
    public P endAsync() {
        return parentScope;
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

    @Override
    protected AsyncRouterBuilderImpl<P> getThis() {
        return this;
    }
}
