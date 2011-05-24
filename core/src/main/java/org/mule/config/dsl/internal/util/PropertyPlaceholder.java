/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal.util;

import org.apache.commons.lang.text.StrSubstitutor;

import java.util.Properties;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class PropertyPlaceholder {

    private final StrSubstitutor substitutor;

    public PropertyPlaceholder(final Properties properties) {
        checkNotNull(properties, "properties");

        substitutor = new StrSubstitutor(properties);
    }

    public String replace(String input) {
        checkNotNull(input, "properties");

        return substitutor.replace(input);
    }
}
