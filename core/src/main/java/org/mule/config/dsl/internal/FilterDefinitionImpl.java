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
 * Internal implementation of {@link org.mule.config.dsl.FilterDefinition} interface that, based on its internal state,
 * builds a {@link MessageFilter} to be registered as a global filter.
 *
 * @author porcelli
 * @see org.mule.config.dsl.AbstractModule#filter()
 * @see org.mule.config.dsl.AbstractModule#filter(String)
 * @see org.mule.config.dsl.Catalog#newFilter(String)
 */
public class FilterDefinitionImpl<F extends Filter> implements FilterDefinition, Builder<MessageFilter> {

    private final String name;
    private final Class<F> clazz;
    private final F obj;

    /**
     * @param name  the global filter name
     * @param clazz the filter type, Mule will instantiate it at runtime
     * @throws IllegalArgumentException if {@code name} param is empty or null
     * @throws NullPointerException     if {@code clazz} param is null
     */
    public FilterDefinitionImpl(final String name, final Class<F> clazz) throws IllegalArgumentException, NullPointerException {
        this.name = checkNotEmpty(name, "name");
        this.clazz = checkNotNull(clazz, "clazz");
        this.obj = null;
    }

    /**
     * @param name the global filter name
     * @param obj  the filter object
     * @throws IllegalArgumentException if {@code name} param is empty or null
     * @throws NullPointerException     if {@code obj} param is null
     */
    public FilterDefinitionImpl(final String name, final F obj) {
        this.name = checkNotEmpty(name, "name");
        this.obj = checkNotNull(obj, "obj");
        this.clazz = (Class<F>) obj.getClass();
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
    public MessageFilter build(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        if (obj != null) {
            return new MessageFilter(obj);
        }

        try {
            return new MessageFilter(muleContext.getRegistry().lookupObject(clazz));
        } catch (final Exception e) {
            throw new ConfigurationException("Failed to configure a Global FilterDefinition.", e);
        }
    }
}
