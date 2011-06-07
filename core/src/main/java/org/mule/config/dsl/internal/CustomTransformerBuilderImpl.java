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
import org.mule.api.transformer.Transformer;
import org.mule.config.dsl.TransformerDefinition;
import org.mule.config.dsl.internal.util.InjectorUtil;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotEmpty;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class CustomTransformerBuilderImpl<T extends Transformer> implements Builder<T> {

    private final Class<T> clazz;
    private final T obj;
    private final String registryRef;


    public CustomTransformerBuilderImpl(Class<T> clazz) {
        this.clazz = checkNotNull(clazz, "clazz");
        this.obj = null;
        this.registryRef = null;
    }

    public CustomTransformerBuilderImpl(T obj) {
        this.obj = checkNotNull(obj, "obj");
        this.clazz = null;
        this.registryRef = null;
    }

    public CustomTransformerBuilderImpl(TransformerDefinition<T> objRef) {
        this.registryRef = checkNotEmpty(objRef.getName(), "objRef");
        this.obj = null;
        this.clazz = null;
    }

    public CustomTransformerBuilderImpl(String ref) {
        this.registryRef = checkNotEmpty(ref, ref);
        this.obj = null;
        this.clazz = null;
    }

    @Override
    public T build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {

        if (registryRef != null) {
            return (T) muleContext.getRegistry().lookupTransformer(registryRef);
        }

        if (obj != null) {
            return obj;
        }

        if (InjectorUtil.hasProvider(injector, clazz)) {
            return injector.getInstance(clazz);
        }

        try {
            return clazz.newInstance();
        } catch (Exception e) {
            //TODO handle here
            throw new RuntimeException(e);
        }
    }
}