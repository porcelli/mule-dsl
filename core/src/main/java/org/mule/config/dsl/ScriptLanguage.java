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
 * Defines a set of common script languages
 *
 * @author porcelli
 */
public enum ScriptLanguage {
    /**
     * Groovy Language
     *
     * @see <a href="http://groovy.codehaus.org">More about Groovy</a>
     */
    GROOVY {
        public String toString() {
            return "groovy";
        }
    }
}
