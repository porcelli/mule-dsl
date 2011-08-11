/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.mule.api.routing.filter.Filter;

/**
 * Builder interface to configure Global Filters.
 *
 * @author porcelli
 */
public interface FilterBuilder extends Builder {

    /**
     * Getter of global filter name
     *
     * @return the global filter name
     */
    String getName();

    /**
     * Sets global filter to use the given filter object.
     *
     * @param obj the filter instance
     * @return the already configured filter definition
     * @throws NullPointerException if {@code obj} param is null
     * @see org.mule.config.dsl.AbstractModule#filter()
     * @see org.mule.config.dsl.AbstractModule#filter(String)
     * @see org.mule.config.dsl.Catalog#newFilter(String)
     * @see Filter
     * @see Builder
     * @see Definition
     */
    <F extends Filter> FilterDefinition with(F obj) throws NullPointerException;

    /**
     * Sets global filter to use the given filter type.
     *
     * @param clazz the filter type, Mule will instantiate an object at runtime
     * @return the already configured filter definition
     * @throws NullPointerException if {@code clazz} param is null
     * @see org.mule.config.dsl.AbstractModule#filter()
     * @see org.mule.config.dsl.AbstractModule#filter(String)
     * @see org.mule.config.dsl.Catalog#newFilter(String)
     * @see Filter
     * @see Builder
     * @see Definition
     */
    <F extends Filter> FilterDefinition with(Class<F> clazz) throws NullPointerException;
}
