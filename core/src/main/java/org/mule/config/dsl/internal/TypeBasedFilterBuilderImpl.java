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
import org.mule.routing.MessageFilter;
import org.mule.routing.filters.PayloadTypeFilter;

import static org.mule.config.dsl.util.Preconditions.checkNotNull;

/**
 * Internal class that wraps a {@link PayloadTypeFilter} inside a {@link MessageFilter}.
 *
 * @author porcelli
 * @see org.mule.config.dsl.PipelineBuilder#filterBy(Class)
 */
public class TypeBasedFilterBuilderImpl implements Builder<MessageFilter> {

    private final Class<?> clazz;

    /**
     * @param clazz the type to be filtered by
     * @throws NullPointerException if {@code clazz} param is null
     */
    public TypeBasedFilterBuilderImpl(final Class<?> clazz) throws NullPointerException {
        this.clazz = checkNotNull(clazz, "clazz");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageFilter build(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        final PayloadTypeFilter filter = new PayloadTypeFilter();
        filter.setExpectedType(clazz);
        return new MessageFilter(filter);
    }
}
