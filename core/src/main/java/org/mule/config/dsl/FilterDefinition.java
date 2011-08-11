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
 * Interface that holds the global filter definition.
 *
 * @author porcelli
 */
public interface FilterDefinition extends Definition {

    /**
     * Getter of global filter name
     *
     * @return the global filter name
     */
    String getName();
}
