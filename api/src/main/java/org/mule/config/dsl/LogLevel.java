/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

/**
 * Enum that defines a set of levels recognized by the system, that is
 * <code>TRACE</code>, <code>DEBUG</code>, <code>INFO</code>,
 * <code>WARN</code>, <code>ERROR</code> and <code>FATAL</code>.
 * <p/>
 * <b>Note:</b> Javadoc adapted from apache log4j.
 *
 * @author porcelli
 */

public enum LogLevel {
    /**
     * The <code>TRACE</code> Level designates finer-grained
     * informational events than the <code>DEBUG</code level.
     */
    TRACE,
    /**
     * The <code>DEBUG</code> Level designates fine-grained
     * informational events that are most useful to debug an
     * application.
     */
    DEBUG,
    /**
     * The <code>INFO</code> level designates informational messages
     * that highlight the progress of the application at coarse-grained
     * level.
     */
    INFO,
    /**
     * The <code>WARN</code> level designates potentially harmful situations.
     */
    WARN,
    /**
     * The <code>ERROR</code> level designates error events that
     * might still allow the application to continue running.
     */
    ERROR,
    /**
     * The <code>FATAL</code> level designates very severe error
     * events that will presumably lead the application to abort.
     */
    FATAL
}
