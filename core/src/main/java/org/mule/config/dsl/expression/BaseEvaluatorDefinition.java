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
import org.mule.config.dsl.ExpressionEvaluatorDefinition;
import org.mule.config.dsl.PropertyPlaceholder;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotEmpty;

/**
 * Abstract class that implements most necessary infrastructure of a {@link ExpressionEvaluatorDefinition}.
 *
 * @author porcelli
 */
public abstract class BaseEvaluatorDefinition implements ExpressionEvaluatorDefinition {
    private final String evaluator;
    private final String expression;
    private final String customEvaluator;

    /**
     * @param evaluator       the expression evaluator
     * @param expression      the expression, null is allowed
     * @param customEvaluator the custom expression evalutor, null is allowed
     * @throws IllegalArgumentException if {@code evaluator} param is null or empty
     */
    public BaseEvaluatorDefinition(final String evaluator, final String expression, final String customEvaluator) throws IllegalArgumentException {
        this.evaluator = checkNotEmpty(evaluator, "evaluator");
        this.expression = expression;
        this.customEvaluator = customEvaluator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEvaluator() {
        return evaluator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCustomEvaluator() {
        return customEvaluator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getExpression() {
        return expression;
    }

    /**
     * Returns the expression, if necessary executes the string substitution
     * using the given property placeholder parameter.
     *
     * @param placeholder the property placeholder, null is allowed
     * @return the expression
     */
    protected String getExpression(final PropertyPlaceholder placeholder) {
        if (placeholder == null) {
            return expression;
        }
        return placeholder.replace(expression);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(final PropertyPlaceholder placeholder) {
        if (expression == null) {
            return "#[" + evaluator + "]";
        }
        if (placeholder != null) {
            return "#[" + evaluator + ":" + placeholder.replace(expression) + "]";
        }
        return "#[" + evaluator + ":" + expression + "]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Filter getFilter(final PropertyPlaceholder placeholder) {
        return null;
    }
}
