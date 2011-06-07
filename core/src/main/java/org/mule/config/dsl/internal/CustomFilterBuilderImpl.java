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

public class CustomFilterBuilderImpl<F extends Filter> implements Builder<MessageFilter> {

    private final Class<F> clazz;
    private final F obj;
    private final String registryRef;

    public CustomFilterBuilderImpl(Class<F> clazz) {
        this.clazz = checkNotNull(clazz, "clazz");
        this.obj = null;
        this.registryRef = null;
    }

    public CustomFilterBuilderImpl(F obj) {
        this.obj = checkNotNull(obj, "obj");
        this.clazz = null;
        this.registryRef = null;
    }

    public CustomFilterBuilderImpl(FilterDefinition<F> objRef) {
        this.registryRef = checkNotEmpty(objRef.getName(), "objRef");
        this.obj = null;
        this.clazz = null;
    }

    public CustomFilterBuilderImpl(String ref) {
        this.registryRef = checkNotEmpty(ref, "ref");
        this.obj = null;
        this.clazz = null;
    }

    @Override
    public MessageFilter build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {

        if (registryRef != null) {
            return muleContext.getRegistry().lookupObject(registryRef);
        }

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