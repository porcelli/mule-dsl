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
import org.mule.config.dsl.ConfigurationException;
import org.mule.config.dsl.ExpressionEvaluatorDefinition;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.expression.ExpressionConfig;
import org.mule.expression.transformers.ExpressionArgument;
import org.mule.expression.transformers.ExpressionTransformer;

import static org.mule.config.dsl.internal.util.NameGenerator.newName;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

/**
 * Internal class that builds a {@link ExpressionTransformer} using a given {@link ExpressionEvaluatorDefinition}.
 *
 * @author porcelli
 * @see org.mule.config.dsl.PipelineBuilder#transform(org.mule.config.dsl.ExpressionEvaluatorDefinition)
 */
public class ExpressionTransformerBuilderImpl<E extends ExpressionEvaluatorDefinition> implements Builder<ExpressionTransformer> {

    private final E expr;

    /**
     * @param expr the expression evalutor definition
     * @throws NullPointerException if {@code expr} param is null
     */
    public ExpressionTransformerBuilderImpl(final E expr) throws NullPointerException {
        this.expr = checkNotNull(expr, "expr");
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ExpressionTransformer build(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        final ExpressionTransformer transformer = new ExpressionTransformer();
        transformer.addArgument(new ExpressionArgument(newName("expr"),
                new ExpressionConfig(placeholder.replace(expr.getExpression()),
                        expr.getEvaluator(),
                        expr.getCustomEvaluator()),
                false));

        return transformer;
    }

}
