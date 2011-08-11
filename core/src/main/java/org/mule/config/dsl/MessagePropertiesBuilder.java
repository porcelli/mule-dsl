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
 * Interface that extends {@link PipelineBuilder} and adds some message properties specific operations.
 *
 * @author porcelli
 */
public interface MessagePropertiesBuilder<P extends PipelineBuilder<P>> extends PipelineBuilder<P> {

    /**
     * Sets a message property value.
     *
     * @param key   the message key
     * @param value the message value
     * @return the current builder
     * @throws IllegalArgumentException if {@code key} param is null or empty
     * @throws NullPointerException     if {@code value} param is null
     */
    MessagePropertiesBuilder<P> put(String key, Object value) throws IllegalArgumentException, NullPointerException;

    /**
     * Sets a message property expression based value.
     *
     * @param key   the message key
     * @param value the message expression based value
     * @return the current builder
     * @throws IllegalArgumentException if {@code key} param is null or empty
     * @throws NullPointerException     if {@code value} param is null
     */
    MessagePropertiesBuilder<P> put(String key, ExpressionEvaluatorDefinition value) throws IllegalArgumentException, NullPointerException;

    /**
     * Renames a message property key based on given params.
     *
     * @param key    the current key name
     * @param newKey the new key name
     * @return the current builder
     * @throws IllegalArgumentException if {@code key} or {@code newKey} params are null or empty
     */
    MessagePropertiesBuilder<P> rename(String key, String newKey) throws IllegalArgumentException;

    /**
     * Renames a message property key using an expression to define the new key name.
     *
     * @param key    the current key name
     * @param newKey the new key expression based name
     * @return the current builder
     * @throws IllegalArgumentException if {@code key} param is null or empty
     * @throws NullPointerException     if {@code newKey} param is null
     */
    MessagePropertiesBuilder<P> rename(String key, ExpressionEvaluatorDefinition newKey) throws IllegalArgumentException;

    /**
     * Removes the given key from message properties.
     *
     * @param key the key to be removed
     * @return the current builder
     * @throws IllegalArgumentException if {@code key} param is null or empty
     */
    MessagePropertiesBuilder<P> remove(String key) throws IllegalArgumentException;

    /**
     * Explicitly delimits the end of the current block and returns back to outer block.
     * It's also possible to delimit the end of the current block implicitly by invoking any
     * {@link PipelineBuilder} method.
     *
     * @return the parameterized builder
     */
    P endMessageProperties();
}
