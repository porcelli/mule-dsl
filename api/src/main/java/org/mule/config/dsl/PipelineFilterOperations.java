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
 * Interface that defines all filter's related operations.
 *
 * @author porcelli
 */
public interface PipelineFilterOperations<P extends PipelineBuilder<P>> {

    /* filter */

    /**
     * Filters messages by the given type, allowing only matched to continue in the flow.
     *
     * @param clazz the type to be filtered by
     * @return the parameterized builder
     * @throws NullPointerException if {@code clazz} param is null
     * @see org.mule.routing.filters.PayloadTypeFilter
     */
    <T> P filterBy(Class<T> clazz) throws NullPointerException;

    /**
     * Filters messages by the given expression, allowing only matched to continue in the flow.
     *
     * @param expr the expression to be filtered by
     * @return the parameterized builder
     * @throws NullPointerException if {@code expr} param is null
     * @see org.mule.routing.filters.ExpressionFilter
     */
    <E extends ExpressionEvaluatorDefinition> P filter(E expr) throws NullPointerException;

    /**
     * Filters messages by given filter, allowing only matched to continue in the flow.
     *
     * @param obj the filter object
     * @return the parameterized builder
     * @throws NullPointerException if {@code obj} param is null
     * @see org.mule.api.routing.filter.Filter
     */
    <F extends Filter> P filterWith(F obj) throws NullPointerException;

    /**
     * Filters messages by given filter, allowing only matched to continue in the flow.
     *
     * @param clazz the filter type, Mule will instantiate it at runtime
     * @return the parameterized builder
     * @throws NullPointerException if {@code clazz} param is null
     * @see Filter
     */
    <F extends Filter> P filterWith(Class<F> clazz) throws NullPointerException;

    /**
     * Filters messages by using the given global filter reference, allowing only
     * matched to continue in the flow.
     *
     * @param obj the global filter object reference
     * @return the parameterized builder
     * @throws NullPointerException if {@code obj} param is null
     * @see Catalog#newFilter(String)
     * @see FilterDefinition
     */
    P filterWith(FilterDefinition obj) throws NullPointerException;

    /**
     * Filters messages by using the given global filter reference, allowing only
     * matched to continue in the flow.
     *
     * @param ref the global filter unique identifier
     * @return the parameterized builder
     * @throws IllegalArgumentException if {@code ref} param is null or empty
     * @see Catalog#newFilter(String)
     */
    P filterWith(String ref) throws IllegalArgumentException;


}
