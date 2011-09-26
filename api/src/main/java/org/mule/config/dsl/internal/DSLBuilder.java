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

/**
 * Extends the marker interface {@link org.mule.config.dsl.DSLBuilder} and
 * defines a common contract to build an element (based on parameterized type).
 *
 * @author porcelli
 */
public interface DSLBuilder<T> extends org.mule.config.dsl.DSLBuilder {

    /**
     * Builds the parameterized type based on builder internal state and the given parameters.
     *
     * @param muleContext the mule context
     * @param placeholder the property placeholder
     * @return an instance of parameterized type
     * @throws NullPointerException   if {@code muleContext} or {@code placeholder} params are null
     * @throws ConfigurationException if theres ay problem building the parameterized type
     * @throws IllegalStateException  if the actual builder state is invalid
     */
    T build(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException;
}
