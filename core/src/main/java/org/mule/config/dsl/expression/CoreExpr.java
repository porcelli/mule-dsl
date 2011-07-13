/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.expression;

import org.mule.api.routing.filter.Filter;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.routing.filters.RegExFilter;
import org.mule.routing.filters.WildcardFilter;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotEmpty;

/**
 * Holds all core expression evaluators definitions and expose them as static methods.
 *
 * @author porcelli
 */
public final class CoreExpr {

    private static final PayloadExpressionEvaluatorDefinition payloadExpr = new PayloadExpressionEvaluatorDefinition();

    private CoreExpr() {
    }

    /**
     * Returns the payload expression evaluator.
     *
     * @return the payload expression evaluator
     */
    public static PayloadExpressionEvaluatorDefinition payload() {
        return payloadExpr;
    }

    /**
     * Returns a new regex evaluator expression based on given parameter.
     *
     * @param expr the regex expression
     * @return the regex expression evaluator
     * @throws IllegalArgumentException if {@code expr} param is null or empty
     */
    public static RegExExpressionEvaluatorDefinition regex(final String expr) throws IllegalArgumentException {
        checkNotEmpty(expr, "expr");
        return new RegExExpressionEvaluatorDefinition(expr);
    }

    /**
     * Returns a new string evaluator expression based on given parameter.
     *
     * @param expr the string expression
     * @return the string expression evaluator
     * @throws IllegalArgumentException if {@code expr} param is null or empty
     */
    public static StringExpressionEvaluatorDefinition string(final String expr) throws IllegalArgumentException {
        checkNotEmpty(expr, "expr");
        return new StringExpressionEvaluatorDefinition(expr);
    }

    /**
     * Returns a new wildcard evaluator expression based on given parameter.
     *
     * @param expr the wildcard expression
     * @return the wildcard expression evaluator
     * @throws IllegalArgumentException if {@code expr} param is null or empty
     */
    public static WildcardExpressionEvaluatorDefinition wildcard(final String expr) throws IllegalArgumentException {
        checkNotEmpty(expr, "expr");
        return new WildcardExpressionEvaluatorDefinition(expr);
    }

    /**
     * Payload expression evaluator definition.
     *
     * @author porcelli
     * @see org.mule.config.dsl.ExpressionEvaluatorDefinition
     */
    public static class PayloadExpressionEvaluatorDefinition extends BaseEvaluatorDefinition {
        private static final String EVALUATOR = "payload";

        /**
         * Constructor
         */
        private PayloadExpressionEvaluatorDefinition() {
            super(EVALUATOR, null, null);
        }
    }

    /**
     * RegEx expression evaluator definition.
     *
     * @author porcelli
     * @see org.mule.config.dsl.ExpressionEvaluatorDefinition
     */
    public static class RegExExpressionEvaluatorDefinition extends BaseEvaluatorDefinition {
        private static final String EVALUATOR = "regex";

        /**
         * Constructor
         *
         * @param expression the regex expression
         */
        private RegExExpressionEvaluatorDefinition(final String expression) {
            super(EVALUATOR, expression, null);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Filter getFilter(final PropertyPlaceholder placeholder) {
            return new RegExFilter(getExpression(placeholder));
        }
    }

    /**
     * Wildcard expression evaluator definition.
     *
     * @author porcelli
     * @see org.mule.config.dsl.ExpressionEvaluatorDefinition
     */
    public static class WildcardExpressionEvaluatorDefinition extends BaseEvaluatorDefinition {
        private static final String EVALUATOR = "wildcard";

        /**
         * Constructor
         *
         * @param expression the wildcard expression
         */
        private WildcardExpressionEvaluatorDefinition(final String expression) {
            super(EVALUATOR, expression, null);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Filter getFilter(final PropertyPlaceholder placeholder) {
            return new WildcardFilter(getExpression(placeholder));
        }
    }

    /**
     * String expression evaluator definition.
     *
     * @author porcelli
     * @see org.mule.config.dsl.ExpressionEvaluatorDefinition
     */
    public static class StringExpressionEvaluatorDefinition extends BaseEvaluatorDefinition {
        private static final String EVALUATOR = "string";

        /**
         * Constructor
         *
         * @param expression the string expression
         */
        private StringExpressionEvaluatorDefinition(final String expression) {
            super(EVALUATOR, expression, null);
        }
    }


}
