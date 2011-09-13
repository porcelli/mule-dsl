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
 * Defines how and when java componenets are instantiated.
 *
 * @author porcelli
 * @see <a href="http://www.mulesoft.org/documentation/display/MULE3USER/Object+Scopes">More about Mule DSL Modules</a>
 */
public enum Scope {
    /**
     * A new instance of the class every time it is referenced. This is the default scope.
     */
    PROTOTYPE,
    /**
     * Only one instance of the class.
     */
    SINGLETON
}
