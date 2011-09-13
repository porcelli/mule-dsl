/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import org.apache.commons.lang.text.StrSubstitutor;
import org.mule.config.dsl.PropertyPlaceholder;

import java.util.Properties;

import static org.mule.config.dsl.util.Preconditions.checkNotNull;

/**
 * Default implementation of {@link PropertyPlaceholder} interface.
 *
 * @author porcelli
 */
public class PropertyPlaceholderImpl implements PropertyPlaceholder {

    private final StrSubstitutor substitutor;

    /**
     * @param properties the properties k-v to be loaded
     * @throws NullPointerException if {@code properties} param is null
     */
    public PropertyPlaceholderImpl(final Properties properties) throws NullPointerException {
        checkNotNull(properties, "properties");

        substitutor = new StrSubstitutor(properties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String replace(final String input) throws IllegalArgumentException {
        checkNotNull(input, "input");

        return substitutor.replace(input);
    }
}
