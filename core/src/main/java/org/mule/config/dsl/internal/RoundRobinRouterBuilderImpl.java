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
import org.mule.api.MuleException;
import org.mule.config.dsl.PipelineBuilder;
import org.mule.config.dsl.RoundRobinRouterBuilder;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.routing.RoundRobin;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class RoundRobinRouterBuilderImpl<P extends PipelineBuilder<P>> extends BasePipelinedRouterImpl<RoundRobinRouterBuilder<P>> implements RoundRobinRouterBuilder<P>, Builder<RoundRobin> {

    private final P parentScope;

    public RoundRobinRouterBuilderImpl(P parentScope) {
        super();
        this.parentScope = checkNotNull(parentScope, "parentScope");
    }

    @Override
    public P endRoundRobin() {
        return parentScope;
    }

    @Override
    protected RoundRobinRouterBuilder<P> getThis() {
        return this;
    }

    @Override
    public RoundRobin build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        if (!pipeline.isProcessorListEmpty()) {
            try {
                RoundRobin router = new RoundRobin();
                router.setMuleContext(muleContext);
                router.setRoutes(pipeline.buildProcessorList(muleContext, injector, placeholder));
                return router;
            } catch (MuleException e) {
                //TODO handle
                throw new RuntimeException(e);
            }
        }

        throw new RuntimeException();
    }

}
