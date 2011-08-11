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
import org.mule.config.dsl.BroadcastRouterBuilder;
import org.mule.config.dsl.ConfigurationException;
import org.mule.config.dsl.PipelineBuilder;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.routing.outbound.MulticastingRouter;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

/**
 * Internal implementation of {@link org.mule.config.dsl.BroadcastRouterBuilder} interface that, based on its internal state,
 * builds a {@link MulticastingRouter}.
 *
 * @author porcelli
 * @see org.mule.config.dsl.PipelineBuilder#broadcast()
 */
public class BroadcastRouterBuilderImpl<P extends PipelineBuilder<P>> extends BasePipelinedRouterImpl<BroadcastRouterBuilder<P>> implements BroadcastRouterBuilder<P>, Builder<MulticastingRouter> {

    private final P parentScope;

    /**
     * @param parentScope the parent scope
     * @throws NullPointerException if {@code parentScope} param is null
     */
    public BroadcastRouterBuilderImpl(final P parentScope) throws NullPointerException {
        super();
        this.parentScope = checkNotNull(parentScope, "parentScope");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P endBroadcast() {
        return parentScope;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MulticastingRouter build(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        if (!pipeline.isBuilderListEmpty()) {
            try {
                final MulticastingRouter router = new MulticastingRouter();
                router.setMuleContext(muleContext);
                router.setRoutes(pipeline.buildMessageProcessorList(muleContext, placeholder));
                return router;
            } catch (final MuleException e) {
                throw new ConfigurationException("Failed to configure a MulticastingRouter.", e);
            }
        }

        throw new IllegalStateException("Router is empty, it's necessary to have at least one operation inside it.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BroadcastRouterBuilder<P> getThis() {
        return this;
    }
}
