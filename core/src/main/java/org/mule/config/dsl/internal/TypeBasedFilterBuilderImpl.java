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
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.routing.MessageFilter;
import org.mule.routing.filters.PayloadTypeFilter;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class TypeBasedFilterBuilderImpl implements Builder<MessageFilter> {

    private final Class<?> clazz;

    public TypeBasedFilterBuilderImpl(Class<?> clazz) {
        this.clazz = checkNotNull(clazz, "clazz");
    }

    @Override
    public MessageFilter build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        final PayloadTypeFilter filter = new PayloadTypeFilter();
        filter.setExpectedType(clazz);
        return new MessageFilter(filter);
    }
}
