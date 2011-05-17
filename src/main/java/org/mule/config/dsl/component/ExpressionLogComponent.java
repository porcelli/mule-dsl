/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.component;

import org.mule.api.MuleEventContext;
import org.mule.config.dsl.ExpressionEvaluatorBuilder;
import org.mule.config.dsl.PipelineBuilder;

public class ExpressionLogComponent<E extends ExpressionEvaluatorBuilder> extends SimpleLogComponent {

    private final E message;

    public ExpressionLogComponent(E message, PipelineBuilder.ErrorLevel level) {
        super(level);
        this.message = message;
    }

    @Override
    public Object onCall(MuleEventContext context) throws Exception {
        Object returnMessage = context.getMuleContext().getExpressionManager().evaluate(message.getExpression(), message.getEvaluator(), context.getMessage(), false);
        log(returnMessage.toString());
        return context.getMessage();
    }

    public E getMessageExpression() {
        return message;
    }


}
