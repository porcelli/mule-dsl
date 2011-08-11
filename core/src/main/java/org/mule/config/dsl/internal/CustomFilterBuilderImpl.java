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
import org.mule.api.routing.filter.Filter;
import org.mule.config.dsl.ConfigurationException;
import org.mule.config.dsl.FilterDefinition;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.routing.MessageFilter;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotEmpty;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

/**
 * Internal class that wraps a given {@link Filter} inside a {@link MessageFilter}.
 *
 * @author porcelli
 * @see org.mule.config.dsl.PipelineBuilder#filterWith(Class)
 * @see org.mule.config.dsl.PipelineBuilder#filterWith(org.mule.api.routing.filter.Filter)
 * @see org.mule.config.dsl.PipelineBuilder#filterWith(org.mule.config.dsl.FilterDefinition)
 * @see org.mule.config.dsl.PipelineBuilder#filterWith(String)
 */
public class CustomFilterBuilderImpl<F extends Filter> implements Builder<MessageFilter> {

    private final Class<F> clazz;
    private final F obj;
    private final String registryRef;

    /**
     * @param clazz a filter type
     * @throws NullPointerException if {@code clazz} param is null
     */
    public CustomFilterBuilderImpl(final Class<F> clazz) {
        this.clazz = checkNotNull(clazz, "clazz");
        this.obj = null;
        this.registryRef = null;
    }

    /**
     * @param obj a filter object instance
     * @throws NullPointerException if {@code obj} param is null
     */
    public CustomFilterBuilderImpl(final F obj) throws NullPointerException {
        this.obj = checkNotNull(obj, "obj");
        this.clazz = null;
        this.registryRef = null;
    }

    /**
     * @param objRef the object reference of a global defined filter
     * @throws NullPointerException     if {@code objRef} param is null
     * @throws IllegalArgumentException if {@code objRef.getName()} param is empty or null
     */
    public CustomFilterBuilderImpl(final FilterDefinition objRef) throws NullPointerException, IllegalArgumentException {
        checkNotNull(objRef, "objRef");
        this.registryRef = checkNotEmpty(objRef.getName(), "objRef.name");
        this.obj = null;
        this.clazz = null;
    }

    /**
     * @param ref the name reference of a global defined filter
     * @throws IllegalArgumentException if {@code ref} param is null
     */
    public CustomFilterBuilderImpl(final String ref) throws IllegalArgumentException {
        this.registryRef = checkNotEmpty(ref, "ref");
        this.obj = null;
        this.clazz = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageFilter build(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        if (registryRef != null) {
            return muleContext.getRegistry().lookupObject(registryRef);
        }

        if (obj != null) {
            return new MessageFilter(obj);
        }

        try {
            Filter filter = muleContext.getRegistry().lookupObject(clazz);
            if (filter == null) {
                throw new ConfigurationException("Failed to configure a CustomFilter.");
            }
            return new MessageFilter(filter);
        } catch (final Exception e) {
            throw new ConfigurationException("Failed to configure a CustomFilter.", e);
        }
    }
}