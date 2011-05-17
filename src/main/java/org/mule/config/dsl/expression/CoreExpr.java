/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.expression;

import org.mule.config.dsl.ExpressionEvaluatorBuilder;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public final class CoreExpr {

    private CoreExpr() {
    }

    public static GenericExpressionFilterEvaluatorBuilder generic(String expr) {
        return new GenericExpressionFilterEvaluatorBuilder(expr);
    }

    public static RegExExpressionEvaluatorBuilder regex(String expr) {
        return new RegExExpressionEvaluatorBuilder(expr);
    }

    public static StringExpressionEvaluatorBuilder string(String expr) {
        return new StringExpressionEvaluatorBuilder(expr);
    }

    public static RegExExpressionEvaluatorBuilder wildcard(String expr) {
        return new RegExExpressionEvaluatorBuilder(expr);
    }

    public static class GenericExpressionFilterEvaluatorBuilder {
        private static final String EVALUATOR = "generic-filter";
        private final String expression;

        public GenericExpressionFilterEvaluatorBuilder(String expression) {
            this.expression = checkNotNull(expression, "expr");
        }

        public String getEvaluator() {
            return EVALUATOR;
        }

        public String getExpression() {
            return expression;
        }
    }

    public static class RegExExpressionEvaluatorBuilder implements ExpressionEvaluatorBuilder {
        private static final String EVALUATOR = "regex";
        private final String expression;

        public RegExExpressionEvaluatorBuilder(String expression) {
            this.expression = checkNotNull(expression, "expr");
        }

        @Override
        public String getEvaluator() {
            return EVALUATOR;
        }

        @Override
        public String getCustomEvaluator() {
            return null;
        }

        @Override
        public String getExpression() {
            return expression;
        }
    }

    public static class WildcardExpressionEvaluatorBuilder implements ExpressionEvaluatorBuilder {
        private static final String EVALUATOR = "wildcard";
        private final String expression;

        public WildcardExpressionEvaluatorBuilder(String expression) {
            this.expression = checkNotNull(expression, "expr");
        }

        @Override
        public String getEvaluator() {
            return EVALUATOR;
        }

        @Override
        public String getCustomEvaluator() {
            return null;
        }

        @Override
        public String getExpression() {
            return expression;
        }
    }

    public static class StringExpressionEvaluatorBuilder implements ExpressionEvaluatorBuilder {
        private static final String EVALUATOR = "string";
        private final String expression;

        public StringExpressionEvaluatorBuilder(String expression) {
            this.expression = checkNotNull(expression, "expr");
        }

        @Override
        public String getEvaluator() {
            return EVALUATOR;
        }

        @Override
        public String getCustomEvaluator() {
            return null;
        }

        @Override
        public String getExpression() {
            return expression;
        }
    }

}
