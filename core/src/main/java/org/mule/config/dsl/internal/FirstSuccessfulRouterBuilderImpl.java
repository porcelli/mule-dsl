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
import org.mule.config.dsl.FirstSuccessfulRouterBuilder;
import org.mule.config.dsl.PipelineBuilder;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.routing.FirstSuccessful;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class FirstSuccessfulRouterBuilderImpl<P extends PipelineBuilder<P>> extends BasePipelinedRouterImpl<FirstSuccessfulRouterBuilder<P>> implements FirstSuccessfulRouterBuilder<P>, Builder<FirstSuccessful> {

    private final P parentScope;

    public FirstSuccessfulRouterBuilderImpl(P parentScope) {
        super();
        this.parentScope = checkNotNull(parentScope, "parentScope");
    }

    @Override
    public P endFirstSuccessful() {
        return parentScope;
    }

    @Override
    protected FirstSuccessfulRouterBuilder<P> getThis() {
        return this;
    }

    @Override
    public FirstSuccessful build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        if (!pipeline.isProcessorListEmpty()) {
            try {
                FirstSuccessful router = new FirstSuccessful();
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
