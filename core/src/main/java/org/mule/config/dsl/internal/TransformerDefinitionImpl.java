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

public class TransformerDefinitionImpl<T extends Transformer> implements TransformerDefinition<T>, Builder<T> {

    private final String name;
    private final Class<T> clazz;
    private final T obj;

    public TransformerDefinitionImpl(String name, Class<T> clazz) {
        this.name = checkNotEmpty(name, "name");
        this.clazz = checkNotNull(clazz, "clazz");
        this.obj = null;
    }

    public TransformerDefinitionImpl(String name, T obj) {
        this.name = checkNotEmpty(name, "name");
        this.obj = checkNotNull(obj, "obj");
        this.clazz = (Class<T>) obj.getClass();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<T> getType() {
        return clazz;
    }

    @Override
    public T build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
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
