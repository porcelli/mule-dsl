/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import org.mule.api.MuleContext;
import org.mule.config.dsl.AsyncRouterBuilder;
import org.mule.config.dsl.ConfigurationException;
import org.mule.config.dsl.PipelineBuilder;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.config.spring.factories.AsyncMessageProcessorsFactoryBean;
import org.mule.processor.AsyncDelegateMessageProcessor;

import static org.mule.config.dsl.util.Preconditions.checkNotNull;

/**
 * Internal implementation of {@link AsyncRouterBuilder} interface that, based on its internal state,
 * builds a {@link AsyncDelegateMessageProcessor}.
 *
 * @author porcelli
 * @see org.mule.config.dsl.PipelineBuilder#async()
 */
public class AsyncRouterBuilderImpl<P extends PipelineBuilder<P>> extends BasePipelinedRouterImpl<AsyncRouterBuilder<P>, P> implements AsyncRouterBuilder<P>, Builder<AsyncDelegateMessageProcessor> {

    /**
     * @param parentScope the parent scope
     * @throws NullPointerException if {@code parentScope} param is null
     */
    public AsyncRouterBuilderImpl(final P parentScope) throws NullPointerException {
        super(parentScope);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P endAsync() {
        return parentScope;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AsyncDelegateMessageProcessor build(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        if (!pipeline.isBuilderListEmpty()) {
            try {
                final AsyncMessageProcessorsFactoryBean factoryBean = new AsyncMessageProcessorsFactoryBean();
                factoryBean.setMuleContext(muleContext);
                factoryBean.setMessageProcessors(pipeline.buildMessageProcessorList(muleContext, placeholder));
                return (AsyncDelegateMessageProcessor) factoryBean.getObject();
            } catch (final Exception e) {
                throw new ConfigurationException("Failed to configure an AsyncDelegateMessageProcessor.", e);
            }
        }

        throw new IllegalStateException("Router is empty, it's necessary to have at least one operation inside it.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AsyncRouterBuilderImpl<P> getThis() {
        return this;
    }
}
