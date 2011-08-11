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
import org.mule.api.transformer.Transformer;
import org.mule.config.dsl.ConfigurationException;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.config.dsl.TransformerDefinition;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotEmpty;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

/**
 * Internal class that handle/builds user defined {@link Transformer}.
 *
 * @author porcelli
 * @see org.mule.config.dsl.PipelineBuilder#transformWith(org.mule.api.transformer.Transformer)
 * @see org.mule.config.dsl.PipelineBuilder#transformWith(Class)
 * @see org.mule.config.dsl.PipelineBuilder#transformWith(org.mule.config.dsl.TransformerDefinition)
 * @see org.mule.config.dsl.PipelineBuilder#transformWith(String)
 */
public class CustomTransformerBuilderImpl<T extends Transformer> implements Builder<T> {

    private final Class<T> clazz;
    private final T obj;
    private final String registryRef;

    /**
     * @param clazz a transformer type
     * @throws NullPointerException if {@code clazz} param is null
     */
    public CustomTransformerBuilderImpl(final Class<T> clazz) {
        this.clazz = checkNotNull(clazz, "clazz");
        this.obj = null;
        this.registryRef = null;
    }

    /**
     * @param obj a transformer object instance
     * @throws NullPointerException if {@code obj} param is null
     */
    public CustomTransformerBuilderImpl(final T obj) {
        this.obj = checkNotNull(obj, "obj");
        this.clazz = null;
        this.registryRef = null;
    }

    /**
     * @param objRef the object reference of a global defined transformer
     * @throws NullPointerException     if {@code objRef} param is null
     * @throws IllegalArgumentException if {@code objRef.getName()} param is empty or null
     */
    public CustomTransformerBuilderImpl(final TransformerDefinition objRef) {
        this.registryRef = checkNotEmpty(objRef.getName(), "objRef");
        this.obj = null;
        this.clazz = null;
    }

    /**
     * @param ref the name reference of a global defined transformer
     * @throws IllegalArgumentException if {@code ref} param is null
     */
    public CustomTransformerBuilderImpl(final String ref) {
        this.registryRef = checkNotEmpty(ref, "ref");
        this.obj = null;
        this.clazz = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T build(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        if (registryRef != null) {
            return (T) muleContext.getRegistry().lookupTransformer(registryRef);
        }

        if (obj != null) {
            return obj;
        }

        try {
            return muleContext.getRegistry().lookupObject(clazz);
        } catch (final Exception e) {
            throw new ConfigurationException("Failed to configure a Transformer.", e);
        }
    }
}