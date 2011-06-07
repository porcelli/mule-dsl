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
import org.mule.api.routing.filter.Filter;
import org.mule.config.dsl.FilterBuilder;
import org.mule.config.dsl.FilterDefinition;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.routing.MessageFilter;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotEmpty;

public class FilterBuilderImpl implements FilterBuilder {

    private final String name;
    private FilterDefinition<? extends Filter> definition;

    public FilterBuilderImpl(String name) {
        this.name = checkNotEmpty(name, "name");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public <F extends Filter> FilterDefinition<F> with(F obj) {
        this.definition = new FilterDefinitionImpl<F>(name, obj);
        return (FilterDefinition<F>) definition;
    }

    @Override
    public <F extends Filter> FilterDefinition<F> with(Class<F> clazz) {
        this.definition = new FilterDefinitionImpl<F>(name, clazz);
        return (FilterDefinition<F>) definition;
    }

    public MessageFilter build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        return ((FilterDefinitionImpl) definition).build(muleContext, injector, placeholder);
    }
}
