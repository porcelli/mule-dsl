/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.mule.config.i18n.Message;

/**
 * Thrown when an attempt to reference a non existent flow.
 *
 * @author porcelli
 */
public class FlowNotFoundException extends DSLException {

    /**
     * * @param message the exception message
     */
    public FlowNotFoundException(final Message message) {
        super(message);
    }

    /**
     * @param message the exception message
     */
    public FlowNotFoundException(final String message) {
        super(message);
    }

    /**
     * @param message the exception message
     * @param cause   the exception that cause this exception to be thrown
     */
    public FlowNotFoundException(final Message message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message the exception message
     * @param cause   the exception that cause this exception to be thrown
     */
    public FlowNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
