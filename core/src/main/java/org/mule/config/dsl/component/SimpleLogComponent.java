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
import org.mule.component.simple.LogComponent;
import org.mule.config.dsl.LogLevel;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;


/**
 * Extents core {@link LogComponent} and enables define a log level.
 *
 * @author porcelli
 */
public class SimpleLogComponent extends LogComponent {
    private static final Log logger = LogFactory.getLog(SimpleLogComponent.class);

    private final LogLevel level;

    /**
     * @param level the log level
     * @throws NullPointerException if {@code level} param is null
     */
    public SimpleLogComponent(final LogLevel level) throws NullPointerException {
        super();
        this.level = checkNotNull(level, "level");
    }

    /**
     * Logs the given message using defined level.
     *
     * @param message the message to be logged
     * @throws NullPointerException if {@code message} param is null
     */
    @Override
    public void log(final String message) {
        checkNotNull(message, "message");
        switch (level) {
            case FATAL:
                if (getLogger().isFatalEnabled()) {
                    getLogger().fatal(message);
                }
            case ERROR:
                if (getLogger().isErrorEnabled()) {
                    getLogger().error(message);
                }
            case WARN:
                if (getLogger().isWarnEnabled()) {
                    getLogger().warn(message);
                }
            case INFO:
                if (getLogger().isInfoEnabled()) {
                    getLogger().info(message);
                }
            case DEBUG:
                if (getLogger().isDebugEnabled()) {
                    getLogger().debug(message);
                }
            case TRACE:
                if (getLogger().isTraceEnabled()) {
                    getLogger().trace(message);
                }
        }
    }

    /**
     * Getter of internal logger.
     *
     * @return the logger
     */
    public Log getLogger() {
        return logger;
    }

    /**
     * Getter of log level.
     *
     * @return the log level
     */
    public LogLevel getLevel() {
        return level;
    }

}
