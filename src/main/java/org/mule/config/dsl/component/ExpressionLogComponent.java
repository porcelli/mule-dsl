/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.component;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleEventContext;
import org.mule.config.dsl.LogLevel;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotEmpty;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class ExpressionLogComponent extends SimpleLogComponent {

    private static final Log logger = LogFactory.getLog(ExpressionLogComponent.class);

    private final String expression;
    private final String evaluator;

    public ExpressionLogComponent(String expression, String evaluator, LogLevel level) {
        super(level);
        this.expression = checkNotNull(expression, "expression");
        this.evaluator = checkNotEmpty(evaluator, "evaluator");
    }

    @Override
    public Object onCall(MuleEventContext context) throws Exception {
        Object returnMessage = context.getMuleContext().getExpressionManager().evaluate(expression, evaluator, context.getMessage(), false);
        log(returnMessage.toString());
        return context.getMessage();
    }

    @Override
    public Log getLogger() {
        return logger;
    }

    public String getExpression() {
        return expression;
    }

    public String getEvaluator() {
        return evaluator;
    }

    public String getCustomEvaluator() {
        return null;
    }
}
