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
import org.mule.config.dsl.FilterBuilder;
import org.mule.config.dsl.FilterDefinition;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.routing.MessageFilter;

import static org.mule.config.dsl.util.Preconditions.*;

/**
 * Internal implementation of {@link FilterBuilder} interface that, based on its internal state,
 * builds a {@link MessageFilter} to be registered as a global filter.
 * <p/>
 * <b>Note:</b> The build itself is delegated to {@link FilterDefinitionImpl}, responsible to
 * hold global filter configuration.
 *
 * @author porcelli
 * @see org.mule.config.dsl.AbstractModule#filter()
 * @see org.mule.config.dsl.AbstractModule#filter(String)
 * @see org.mule.config.dsl.Catalog#newFilter(String)
 */
public class FilterBuilderImpl implements FilterBuilder, Builder<MessageFilter> {

    private final String name;
    private FilterDefinitionImpl<?> definition;

    /**
     * @param name the global filter name
     * @throws IllegalArgumentException if {@code name} param is empty or null
     */
    public FilterBuilderImpl(final String name) throws IllegalArgumentException {
        this.name = checkNotEmpty(name, "name");
    }

    /**
     * Getter of global filter name
     *
     * @return the global filter name
     */
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <F extends Filter> FilterDefinition with(final F obj) throws NullPointerException {
        checkNotNull(obj, "obj");
        this.definition = new FilterDefinitionImpl<F>(name, obj);
        return definition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <F extends Filter> FilterDefinition with(final Class<F> clazz) throws NullPointerException {
        checkNotNull(clazz, "clazz");
        this.definition = new FilterDefinitionImpl<F>(name, clazz);
        return definition;
    }

    /**
     * Builds a {@link MessageFilter} based on builder internal state and the given parameters.
     *
     *
     * @param muleContext the mule context
     * @param placeholder the property placeholder
     * @return an instance of message filter
     * @throws NullPointerException  if {@code muleContext} or {@code placeholder} params are null
     * @throws IllegalStateException if filter type or instance is not provided
     */
    @Override
    public MessageFilter build(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");
        checkState(this.definition != null, "Can't build without a filter type or instance.");

        return definition.build(muleContext, placeholder);
    }
}
