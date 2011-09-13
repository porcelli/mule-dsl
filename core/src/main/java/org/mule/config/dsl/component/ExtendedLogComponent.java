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

import static org.mule.config.dsl.util.Preconditions.checkNotNull;

/**
 * Extentds {@link SimpleLogComponent} enabling the use of a custom string based message.
 *
 * @author porcelli
 */
public class ExtendedLogComponent extends SimpleLogComponent {
    private static final Log logger = LogFactory.getLog(ExtendedLogComponent.class);

    private final String message;

    /**
     * @param message the message to be logged
     * @param level   the log level
     * @throws NullPointerException if {@code message} or {@code level} param are null
     */
    public ExtendedLogComponent(final String message, final LogLevel level) throws NullPointerException {
        super(level);
        this.message = checkNotNull(message, "message");
    }

    /**
     * Logs the message and returns the original message.
     *
     * @param context the mule context
     * @return the original message
     * @throws Exception if the event fails to process properly.
     */
    @Override
    public Object onCall(final MuleEventContext context) throws Exception {
        log(message);
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
     * Getter of the message
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

}
