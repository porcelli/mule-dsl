/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal.util;

import org.mule.config.dsl.ExpressionEvaluatorBuilder;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class ExpressionArgsUtil {
    private ExpressionArgsUtil() {
    }

    public static List<String> toListOfStrings(PropertyPlaceholder placeholder, ExpressionEvaluatorBuilder... args) {

        if (args == null) {
            return Collections.emptyList();
        }

        final List<String> resultList = new LinkedList<String>();

        for (ExpressionEvaluatorBuilder arg : args) {
            resultList.add(arg.toString(placeholder));
        }

        return resultList;
    }
}
