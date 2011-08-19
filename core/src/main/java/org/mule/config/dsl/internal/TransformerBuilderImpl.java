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
import org.mule.config.dsl.TransformerBuilder;
import org.mule.config.dsl.TransformerDefinition;

import static org.mule.config.dsl.internal.util.Preconditions.*;

/**
 * Internal implementation of {@link TransformerBuilder} interface that, based on its internal state,
 * builds a {@link Transformer} to be registered as a global tranformer.
 * <p/>
 * <b>Note:</b> The build itself is delegated to {@link TransformerDefinitionImpl}, responsible to
 * hold global transformer configuration.
 *
 * @author porcelli
 * @see org.mule.config.dsl.AbstractModule#transformer()
 * @see org.mule.config.dsl.AbstractModule#transformer(String)
 * @see org.mule.config.dsl.Catalog#newTransformer(String)
 */
public class TransformerBuilderImpl implements TransformerBuilder, Builder<Transformer> {

    private final String name;
    private TransformerDefinitionImpl definition;

    /**
     * @param name the global transformer name
     * @throws IllegalArgumentException if {@code name} param is empty or null
     */
    public TransformerBuilderImpl(final String name) throws IllegalArgumentException {
        this.name = checkNotEmpty(name, "name");
    }

    /**
     * Getter of global transformer name
     *
     * @return the global transformer name
     */
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Transformer> TransformerDefinition with(final T obj) throws NullPointerException {
        checkNotNull(obj, "obj");
        this.definition = new TransformerDefinitionImpl<T>(name, obj);
        return definition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Transformer> TransformerDefinition with(final Class<T> clazz) throws NullPointerException {
        checkNotNull(clazz, "clazz");
        this.definition = new TransformerDefinitionImpl<T>(name, clazz);
        return definition;
    }

    /**
     * Builds a {@link Transformer} based on builder internal state and the given parameters.
     *
     *
     * @param muleContext the mule context
     * @param placeholder the property placeholder
     * @return an instance of message filter
     * @throws NullPointerException  if {@code muleContext} or {@code placeholder} params are null
     * @throws IllegalStateException if transformer type or instance is not provided
     */
    @Override
    public Transformer build(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");
        checkState(this.definition != null, "Can't build without a transformer type or instance.");

        return definition.build(muleContext, placeholder);
    }
}
