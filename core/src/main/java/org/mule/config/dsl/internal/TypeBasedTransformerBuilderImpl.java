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
import org.mule.config.dsl.ConfigurationException;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.transformer.simple.AutoTransformer;
import org.mule.transformer.types.SimpleDataType;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

/**
 * Internal class that builds a {@link AutoTransformer} based on given type.
 *
 * @author porcelli
 * @see {@link org.mule.config.dsl.PipelineBuilder#transformTo(Class)}
 */
public class TypeBasedTransformerBuilderImpl<T> implements Builder<AutoTransformer> {

    private final Class<T> clazz;

    /**
     * @param clazz the type to be trasformed to
     * @throws NullPointerException if {@code clazz} param is null
     */
    public TypeBasedTransformerBuilderImpl(final Class<T> clazz) throws NullPointerException {
        this.clazz = checkNotNull(clazz, "clazz");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AutoTransformer build(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        final AutoTransformer transformer = new AutoTransformer();
        transformer.setReturnDataType(new SimpleDataType<T>(clazz));
        return transformer;
    }
}
