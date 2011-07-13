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
 * Interface that defines a simple property placeholder mechanism.
 *
 * @author porcelli
 */
public interface PropertyPlaceholder {

    /**
     * Executes the property placeholder on given input text.
     *
     * @param input the input text
     * @return the input text replaced if needed, otherwise the original input
     * @throws NullPointerException if {@code input} param is null
     */
    String replace(String input) throws NullPointerException;
}
