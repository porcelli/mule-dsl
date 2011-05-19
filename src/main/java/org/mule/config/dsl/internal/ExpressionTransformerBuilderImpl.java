/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import com.google.inject.Injector;
import org.mule.api.MuleContext;
import org.mule.config.dsl.ExpressionEvaluatorBuilder;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.expression.ExpressionConfig;
import org.mule.expression.transformers.ExpressionArgument;
import org.mule.expression.transformers.ExpressionTransformer;

import static org.mule.config.dsl.internal.util.NameGenerator.newName;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class ExpressionTransformerBuilderImpl<E extends ExpressionEvaluatorBuilder> implements Builder<ExpressionTransformer> {

    private final E expr;

    public ExpressionTransformerBuilderImpl(E expr) {
        this.expr = checkNotNull(expr, "expr");
    }


    @Override
    public ExpressionTransformer build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        ExpressionTransformer transformer = new ExpressionTransformer();
        transformer.addArgument(new ExpressionArgument(newName("expr"),
                new ExpressionConfig(placeholder.replace(expr.getExpression()),
                        expr.getEvaluator(),
                        expr.getCustomEvaluator()),
                false));

        return transformer;
    }

}
