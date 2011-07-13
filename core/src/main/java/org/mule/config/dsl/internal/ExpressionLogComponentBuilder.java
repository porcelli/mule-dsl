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
import org.mule.config.dsl.LogLevel;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.config.dsl.component.ExpressionLogComponent;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

/**
 * Simple {@link ExpressionLogComponent} builder, necessary due the need to use a property plaholder on expression message.
 *
 * @author porcelli
 * @see org.mule.config.dsl.PipelineBuilder#log(org.mule.config.dsl.ExpressionEvaluatorDefinition)
 * @see org.mule.config.dsl.PipelineBuilder#log(org.mule.config.dsl.ExpressionEvaluatorDefinition, org.mule.config.dsl.LogLevel)
 */
public class ExpressionLogComponentBuilder implements Builder<ExpressionLogComponent> {

    private final LogLevel level;
    private final ExpressionEvaluatorDefinition message;

    /**
     * @param message the message expresison
     * @param level   the log level
     * @throws NullPointerException if {@code message} or {@code level} params are null
     */
    ExpressionLogComponentBuilder(final ExpressionEvaluatorDefinition message, final LogLevel level) throws NullPointerException {
        this.message = checkNotNull(message, "message");
        this.level = checkNotNull(level, "level");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExpressionLogComponent build(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        return new ExpressionLogComponent(placeholder.replace(message.getExpression()), message.getEvaluator(), level);
    }


}
