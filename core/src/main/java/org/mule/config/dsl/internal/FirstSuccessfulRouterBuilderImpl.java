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
import org.mule.api.MuleException;
import org.mule.config.dsl.ConfigurationException;
import org.mule.config.dsl.FirstSuccessfulRouterBuilder;
import org.mule.config.dsl.PipelineBuilder;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.routing.FirstSuccessful;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

/**
 * Internal implementation of {@link org.mule.config.dsl.FirstSuccessfulRouterBuilder} interfaces that,
 * based on its internal state, builds a {@link FirstSuccessful}.
 *
 * @author porcelli
 * @see org.mule.config.dsl.PipelineBuilder#firstSuccessful()
 */
public class FirstSuccessfulRouterBuilderImpl<P extends PipelineBuilder<P>> extends BasePipelinedRouterImpl<FirstSuccessfulRouterBuilder<P>, P> implements FirstSuccessfulRouterBuilder<P>, Builder<FirstSuccessful> {

    /**
     * @param parentScope the parent scope
     * @throws NullPointerException if {@code parentScope} param is null
     */
    public FirstSuccessfulRouterBuilderImpl(final P parentScope) throws NullPointerException {
        super(parentScope);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P endFirstSuccessful() {
        return parentScope;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected FirstSuccessfulRouterBuilder<P> getThis() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FirstSuccessful build(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        if (!pipeline.isBuilderListEmpty()) {
            try {
                final FirstSuccessful router = new FirstSuccessful();
                router.setMuleContext(muleContext);
                router.setRoutes(pipeline.buildMessageProcessorList(muleContext, placeholder));
                return router;
            } catch (final MuleException e) {
                throw new ConfigurationException("Failed to configure a FirstSuccessful.", e);
            }
        }

        throw new IllegalStateException("Router is empty, it's necessary to have at least one operation inside it.");
    }
}
