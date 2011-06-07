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
import org.mule.config.dsl.FilterDefinition;
import org.mule.config.dsl.internal.util.InjectorUtil;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.routing.MessageFilter;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotEmpty;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class FilterDefinitionImpl<F extends Filter> implements FilterDefinition<F>, Builder<MessageFilter> {

    private final String name;
    private final Class<F> clazz;
    private final F obj;

    public FilterDefinitionImpl(String name, Class<F> clazz) {
        this.name = checkNotEmpty(name, "name");
        this.clazz = checkNotNull(clazz, "clazz");
        this.obj = null;
    }

    public FilterDefinitionImpl(String name, F obj) {
        this.name = checkNotEmpty(name, "name");
        this.obj = checkNotNull(obj, "obj");
        this.clazz = (Class<F>) obj.getClass();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<F> getType() {
        return clazz;
    }

    @Override
    public MessageFilter build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        if (obj != null) {
            return new MessageFilter(obj);
        }

        if (InjectorUtil.hasProvider(injector, clazz)) {
            return new MessageFilter(injector.getInstance(clazz));
        }

        try {
            return new MessageFilter(clazz.newInstance());
        } catch (Exception e) {
            //TODO handle here
            throw new RuntimeException(e);
        }
    }
}
