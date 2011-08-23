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
import org.mule.config.dsl.PipelineBuilder;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.config.dsl.RoundRobinRouterBuilder;
import org.mule.routing.RoundRobin;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

/**
 * Internal implementation of {@link org.mule.config.dsl.RoundRobinRouterBuilder} interfaces that,
 * based on its internal state, builds a {@link RoundRobin}.
 *
 * @author porcelli
 * @see org.mule.config.dsl.PipelineBuilder#roundRobin()
 */
public class RoundRobinRouterBuilderImpl<P extends PipelineBuilder<P>> extends BasePipelinedRouterImpl<RoundRobinRouterBuilder<P>, P> implements RoundRobinRouterBuilder<P>, Builder<RoundRobin> {

    /**
     * @param parentScope the parent scope
     * @throws NullPointerException if {@code parentScope} param is null
     */
    public RoundRobinRouterBuilderImpl(final P parentScope) throws NullPointerException {
        super(parentScope);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P endRoundRobin() {
        return parentScope;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected RoundRobinRouterBuilder<P> getThis() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoundRobin build(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        if (!pipeline.isBuilderListEmpty()) {
            try {
                final RoundRobin router = new RoundRobin();
                router.setMuleContext(muleContext);
                router.setRoutes(pipeline.buildMessageProcessorList(muleContext, placeholder));
                return router;
            } catch (final MuleException e) {
                throw new ConfigurationException("Failed to configure a RoundRobin.", e);
            }
        }

        throw new IllegalStateException("Router is empty, it's necessary to have at least one operation inside it.");
    }

}
