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
import org.mule.api.DefaultMuleException;
import org.mule.config.dsl.ExpressionEvaluatorBuilder;
import org.mule.config.dsl.expression.CoreExpr;
import org.mule.config.expression.ExpressionFilterParser;
import org.mule.routing.MessageFilter;
import org.mule.routing.filters.ExpressionFilter;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class ExpressionFilterBuilderImpl<E extends ExpressionEvaluatorBuilder> implements Builder<MessageFilter> {

    private final E expr;

    public ExpressionFilterBuilderImpl(E expr) {
        this.expr = checkNotNull(expr, "expr");
    }

    @Override
    public MessageFilter build(Injector injector) {
        final MessageFilter messageFilter;
        if (expr instanceof CoreExpr.GenericFilterExpressionEvaluatorBuilder) {
            final ExpressionFilterParser parser = new ExpressionFilterParser();
            try {
                messageFilter = new MessageFilter(parser.parseFilterString(expr.getExpression()));
            } catch (DefaultMuleException e) {
                //todo handle propert
                throw new RuntimeException(e);
            }
        } else {
            messageFilter = new MessageFilter(new ExpressionFilter(expr.getEvaluator(), expr.getExpression()));
        }

        return messageFilter;
    }
}
