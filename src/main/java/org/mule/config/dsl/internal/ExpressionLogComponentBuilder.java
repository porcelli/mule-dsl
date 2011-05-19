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
import org.mule.config.dsl.ErrorLevel;
import org.mule.config.dsl.ExpressionEvaluatorBuilder;
import org.mule.config.dsl.component.ExpressionLogComponent;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class ExpressionLogComponentBuilder implements Builder<ExpressionLogComponent> {

    private final ErrorLevel level;
    private final ExpressionEvaluatorBuilder message;

    ExpressionLogComponentBuilder(ExpressionEvaluatorBuilder message, ErrorLevel level) {
        this.message = checkNotNull(message, "message");
        this.level = checkNotNull(level, "level");
    }

    @Override
    public ExpressionLogComponent build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        return new ExpressionLogComponent(placeholder.replace(message.getExpression()), message.getEvaluator(), level);
    }


}
