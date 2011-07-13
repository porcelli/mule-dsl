/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal.util;

import org.mule.config.dsl.ExpressionEvaluatorDefinition;
import org.mule.config.dsl.PropertyPlaceholder;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class to handle expression based args list.
 *
 * @author porcelli
 */
public final class ExpressionArgsUtil {
    private ExpressionArgsUtil() {
    }

    /**
     * Converts the given expression evaluator arryay to a list of strings.
     *
     * @param placeholder the property placeholder
     * @param args        the array to be converted to string list
     * @return the list of strings, an empty list if input is null
     */
    public static List<String> toListOfStrings(final PropertyPlaceholder placeholder, final ExpressionEvaluatorDefinition... args) {

        if (args == null) {
            return Collections.emptyList();
        }

        final List<String> resultList = new LinkedList<String>();

        for (final ExpressionEvaluatorDefinition arg : args) {
            resultList.add(arg.toString(placeholder));
        }

        return resultList;
    }
}
