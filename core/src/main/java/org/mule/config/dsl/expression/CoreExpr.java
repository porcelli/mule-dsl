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
import org.mule.config.dsl.ExpressionEvaluatorBuilder;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.routing.filters.RegExFilter;
import org.mule.routing.filters.WildcardFilter;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public final class CoreExpr {

    private CoreExpr() {
    }

    public static GenericExpressionFilterEvaluatorBuilder generic(String expr) {
        return new GenericExpressionFilterEvaluatorBuilder(expr);
    }

    public static PayloadExpressionEvaluatorBuilder payload() {
        return new PayloadExpressionEvaluatorBuilder();
    }

    public static RegExExpressionEvaluatorBuilder regex(String expr) {
        return new RegExExpressionEvaluatorBuilder(expr);
    }

    public static StringExpressionEvaluatorBuilder string(String expr) {
        return new StringExpressionEvaluatorBuilder(expr);
    }

    public static WildcardExpressionEvaluatorBuilder wildcard(String expr) {
        return new WildcardExpressionEvaluatorBuilder(expr);
    }

    public static class GenericExpressionFilterEvaluatorBuilder extends BaseEvaluatorBuilder {
        private static final String EVALUATOR = "generic-filter";

        public GenericExpressionFilterEvaluatorBuilder(String expression) {
            super(EVALUATOR, expression, null);
        }
    }

    public static class PayloadExpressionEvaluatorBuilder extends BaseEvaluatorBuilder implements ExpressionEvaluatorBuilder {
        private static final String EVALUATOR = "payload";

        public PayloadExpressionEvaluatorBuilder() {
            super(EVALUATOR, null, null);
        }
    }

    public static class RegExExpressionEvaluatorBuilder extends BaseEvaluatorBuilder implements ExpressionEvaluatorBuilder {
        private static final String EVALUATOR = "regex";

        public RegExExpressionEvaluatorBuilder(String expression) {
            super(EVALUATOR, expression, null);
        }

        @Override
        public Filter getFilter(PropertyPlaceholder placeholder) {
            return new RegExFilter(placeholder.replace(getExpression()));
        }
    }

    public static class WildcardExpressionEvaluatorBuilder extends BaseEvaluatorBuilder implements ExpressionEvaluatorBuilder {
        private static final String EVALUATOR = "wildcard";

        public WildcardExpressionEvaluatorBuilder(String expression) {
            super(EVALUATOR, expression, null);
        }

        @Override
        public Filter getFilter(PropertyPlaceholder placeholder) {
            return new WildcardFilter(placeholder.replace(getExpression()));
        }
    }

    public static class StringExpressionEvaluatorBuilder extends BaseEvaluatorBuilder implements ExpressionEvaluatorBuilder {
        private static final String EVALUATOR = "string";

        public StringExpressionEvaluatorBuilder(String expression) {
            super(EVALUATOR, expression, null);
        }
    }


    public static abstract class BaseEvaluatorBuilder {
        private final String evaluator;
        private final String expression;
        private final String customEvaluator;

        public BaseEvaluatorBuilder(String evaluator, String expression, String customEvaluator) {
            this.evaluator = checkNotNull(evaluator, "evaluator");
            this.expression = expression;
            this.customEvaluator = customEvaluator;
        }

        public String getEvaluator() {
            return evaluator;
        }

        public String getCustomEvaluator() {
            return customEvaluator;
        }

        public String getExpression() {
            return expression;
        }

        public String toString(PropertyPlaceholder placeholder) {
            if (expression == null) {
                return "#[" + evaluator + "]";
            }
            return "#[" + evaluator + ":" + placeholder.replace(expression) + "]";
        }

        public Filter getFilter(PropertyPlaceholder placeholder) {
            return null;
        }
    }

}
