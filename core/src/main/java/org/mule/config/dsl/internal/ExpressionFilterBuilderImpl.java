/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import org.mule.api.MuleContext;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.config.dsl.ConfigurationException;
import org.mule.config.dsl.ExpressionEvaluatorDefinition;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.routing.MessageFilter;
import org.mule.routing.filters.ExpressionFilter;

import static org.mule.config.dsl.util.Preconditions.checkNotNull;

/**
 * Internal class that wraps a {@link ExpressionFilter} inside a {@link MessageFilter}.
 *
 * @author porcelli
 * @see org.mule.config.dsl.PipelineBuilder#filter(org.mule.config.dsl.ExpressionEvaluatorDefinition)
 */
public class ExpressionFilterBuilderImpl implements DSLBuilder<MessageProcessor> {

    private final ExpressionEvaluatorDefinition objExpr;

    /**
     * @param expr the expression evaluator definition
     * @throws NullPointerException if {@code expr} param is null
     */
    public ExpressionFilterBuilderImpl(final ExpressionEvaluatorDefinition expr) throws NullPointerException {
        this.objExpr = checkNotNull(expr, "expr");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageProcessor build(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        Filter filter = objExpr.getFilter(muleContext, placeholder);
        if (filter == null) {
            return new MessageFilter(new ExpressionFilter(objExpr.getEvaluator(), placeholder.replace(objExpr.getExpression())));
        } else if (filter instanceof MessageProcessor) {
            return (MessageProcessor) filter;
        }

        return new MessageFilter(filter);
    }
}
