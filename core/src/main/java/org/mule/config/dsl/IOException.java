/*
 * ---------------------------------------------------------------------------
 *  Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.mule.config.i18n.Message;

/**
 * Signals that an I/O exception of some sort has occurred.
 *
 * @author porcelli
 */
public class IOException extends DSLException {

    private static final long serialVersionUID = -8390893797284558707L;

    public IOException(final Message message) {
        super(message);
    }

    public IOException(final String message) {
        super(message);
    }

    public IOException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public IOException(final Message message, final Throwable cause) {
        super(message, cause);
    }

    public IOException(final Throwable cause) {
        super(cause);
    }
}
