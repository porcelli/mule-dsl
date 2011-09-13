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
 * Interface that defines all debug and output related operations.
 *
 * @author porcelli
 */
public interface PipelineDebugOutputOperations<P extends PipelineBuilder<P>> {
    /* debug & output */

    /**
     * Logs the payload content at INFO level.
     *
     * @return the parameterized builder
     */
    P log();

    /**
     * Logs payload content at given level.
     *
     * @param level the log level to use
     * @return the parameterized builder
     * @throws NullPointerException if {@code expr} param is null
     */
    P log(LogLevel level) throws NullPointerException;

    /**
     * Logs {@code message} parameter at INFO level.
     *
     * @param message the content to be logged
     * @return the parameterized builder
     * @throws IllegalArgumentException if {@code message} param is null or empty
     */
    P log(String message) throws IllegalArgumentException;

    /**
     * Logs the {@code message} parameter at given level.
     *
     * @param message the content to be logged
     * @param level   the log level to use
     * @return the parameterized builder
     * @throws IllegalArgumentException if {@code message} param is null or empty
     * @throws NullPointerException     if {@code level} param is null
     */
    P log(String message, LogLevel level) throws IllegalArgumentException, NullPointerException;

    /**
     * Logs {@code expr} parameter at INFO level.
     *
     * @param expr the expression to be logged
     * @return the parameterized builder
     * @throws NullPointerException if {@code expr} param is null
     */
    <E extends ExpressionEvaluatorDefinition> P log(E expr) throws NullPointerException;

    /**
     * Logs {@code expr} parameter at given level.
     *
     * @param expr  the expression to be logged
     * @param level the log level to use
     * @return the parameterized builder
     * @throws NullPointerException if {@code expr} or {@code level} params are null
     */
    <E extends ExpressionEvaluatorDefinition> P log(E expr, LogLevel level) throws NullPointerException;

    /**
     * Logs message at INFO level and return the payload back as the result.
     *
     * @return the parameterized builder
     * @see org.mule.component.simple.EchoComponent
     */
    P echo();

}
