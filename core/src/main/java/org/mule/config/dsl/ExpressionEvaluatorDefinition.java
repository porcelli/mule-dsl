/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.mule.api.MuleContext;
import org.mule.api.routing.filter.Filter;

/**
 * Interface that stores the common expression evaluator configuration.
 *
 * @author porcelli
 */
public interface ExpressionEvaluatorDefinition extends Definition {

    /**
     * Getter of evaluattor name
     *
     * @return the evaluator name
     */
    String getEvaluator();

    /**
     * Getter of custom evaluattor name
     *
     * @return the custom evaluator name
     */
    String getCustomEvaluator();

    /**
     * Getter of expression
     *
     * @return the expression
     */
    String getExpression();

    /**
     * Getter of the expression filter.
     *
     * @param muleContext the mule context
     * @param placeholder the property placeholder
     * @return the expression filter
     */
    Filter getFilter(MuleContext muleContext, PropertyPlaceholder placeholder);

    /**
     * A string formatted version of the actual expression definition.
     *
     * @param placeholder the property placeholder
     * @return the string format of expression evaluator
     */
    String toString(PropertyPlaceholder placeholder);
}
