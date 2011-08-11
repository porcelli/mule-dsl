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
 * Internal implementation of {@link org.mule.config.dsl.TransformerDefinition} interface that, based on its internal state,
 * builds a {@link Transformer} to be registered as a global transformer.
 *
 * @author porcelli
 * @see org.mule.config.dsl.AbstractModule#transformer()
 * @see org.mule.config.dsl.AbstractModule#transformer(String)
 * @see org.mule.config.dsl.Catalog#newTransformer(String)
 */
public class TransformerDefinitionImpl<T extends Transformer> implements TransformerDefinition, Builder<T> {

    private final String name;
    private final Class<T> clazz;
    private final T obj;

    /**
     * @param name  the global transformer name
     * @param clazz the transformer type, Mule will instantiate it at runtime
     * @throws IllegalArgumentException if {@code name} param is empty or null
     * @throws NullPointerException     if {@code clazz} param is null
     */
    public TransformerDefinitionImpl(final String name, final Class<T> clazz) throws IllegalArgumentException, NullPointerException {
        this.name = checkNotEmpty(name, "name");
        this.clazz = checkNotNull(clazz, "clazz");
        this.obj = null;
    }

    /**
     * @param name the global transformer name
     * @param obj  the transformer object
     * @throws IllegalArgumentException if {@code name} param is empty or null
     * @throws NullPointerException     if {@code obj} param is null
     */
    public TransformerDefinitionImpl(final String name, final T obj) throws IllegalArgumentException, NullPointerException {
        this.name = checkNotEmpty(name, "name");
        this.obj = checkNotNull(obj, "obj");
        this.clazz = (Class<T>) obj.getClass();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T build(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        if (obj != null) {
            return obj;
        }

        try {
            return muleContext.getRegistry().lookupObject(clazz);
        } catch (final Exception e) {
            throw new ConfigurationException("Failed to configure a Global TransformerDefinition.", e);
        }
    }
}
