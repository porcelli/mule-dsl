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

/**
 * Extentds {@link SimpleLogComponent} enabling the use of a expression based message.
 *
 * @author porcelli
 */
public class ExpressionLogComponent extends SimpleLogComponent {

    private static final Log logger = LogFactory.getLog(ExpressionLogComponent.class);

    private final String expression;
    private final String evaluator;

    /**
     * @param expression the expression
     * @param evaluator  the expression evaluator
     * @param level      the log level
     * @throws IllegalArgumentException if {@code evaluator} param is empty or null
     * @throws NullPointerException     if {@code expression} or {@code level} param are null
     */
    public ExpressionLogComponent(final String expression, final String evaluator, final LogLevel level) throws IllegalArgumentException, NullPointerException {
        super(level);
        this.evaluator = checkNotEmpty(evaluator, "evaluator");
        this.expression = checkNotNull(expression, "expression");
    }

    /**
     * Logs the expression and returns the original message.
     *
     * @param context the mule context
     * @return the original message
     * @throws Exception if the event fails to process properly.
     */
    @Override
    public Object onCall(final MuleEventContext context) throws Exception {
        final Object returnMessage = context.getMuleContext().getExpressionManager().evaluate(expression, evaluator, context.getMessage(), false);
        log(returnMessage.toString());
        return context.getMessage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Log getLogger() {
        return logger;
    }

    /**
     * Getter of expression
     *
     * @return the expression
     */
    public String getExpression() {
        return expression;
    }

    /**
     * Getter of expression evaluator
     *
     * @return the expression evaluator
     */
    public String getEvaluator() {
        return evaluator;
    }

    /**
     * Getter of custom expresison evaluator. Forces return  {@code null}.
     *
     * @return null
     */
    public String getCustomEvaluator() {
        return null;
    }
}
