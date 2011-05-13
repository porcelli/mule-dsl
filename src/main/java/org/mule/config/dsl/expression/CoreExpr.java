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

//    public static GenericExpressionEvaluatorBuilder xpath(String expr) {
//        return null;
//    }

//    public static GenericExpressionEvaluatorBuilder bean(String expr) {
//        return null;
//    }

//    public static GroovyExpressionEvaluatorBuilder groovy() {
//        return null;
//    }

//    public static class GroovyExpressionEvaluatorBuilder implements ExpressionEvaluatorBuilder {
//        public ExpressionEvaluatorBuilder file(String filePath);
//
//        public ExpressionEvaluatorBuilder file(File file);
//
//        public ExpressionEvaluatorBuilder classpath(String classpath);
//
//        public ExpressionEvaluatorBuilder expr(String expression) {
//            return this;
//        }
//    }

    public static GenericFilterExpressionEvaluatorBuilder generic(String expr) {
        return new GenericFilterExpressionEvaluatorBuilder(expr);
    }

    public static RegExExpressionEvaluatorBuilder regex(String expr) {
        return new RegExExpressionEvaluatorBuilder(expr);
    }

    public static class GenericFilterExpressionEvaluatorBuilder implements ExpressionEvaluatorBuilder {
        private static final String EVALUATOR = "generic-filter";
        private final String expression;

        public GenericFilterExpressionEvaluatorBuilder(String expression) {
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
        public String getExpression() {
            return expression;
        }
    }
}
