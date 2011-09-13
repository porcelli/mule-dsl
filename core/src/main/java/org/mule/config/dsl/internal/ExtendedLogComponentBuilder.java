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
import org.mule.config.dsl.LogLevel;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.config.dsl.component.ExtendedLogComponent;

import static org.mule.config.dsl.util.Preconditions.checkNotNull;

/**
 * Simple {@link ExtendedLogComponent} builder, necessary due the need to use a property plaholder on message.
 *
 * @author porcelli
 * @see org.mule.config.dsl.PipelineBuilder#log(String)
 * @see org.mule.config.dsl.PipelineBuilder#log(String, org.mule.config.dsl.LogLevel)
 */
public class ExtendedLogComponentBuilder implements Builder<ExtendedLogComponent> {

    private final LogLevel level;
    private final String message;

    /**
     * @param message the message
     * @param level   the log level
     * @throws NullPointerException if {@code message} or {@code level} params are null
     */
    ExtendedLogComponentBuilder(final String message, final LogLevel level) throws NullPointerException {
        this.message = checkNotNull(message, "message");
        this.level = checkNotNull(level, "level");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExtendedLogComponent build(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        return new ExtendedLogComponent(placeholder.replace(message), level);
    }
}
