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
import org.mule.api.MuleEventContext;
import org.mule.config.dsl.PipelineBuilder;

public class ExtendedLogComponent extends SimpleLogComponent {
    private static final Log logger = LogFactory.getLog(ExtendedLogComponent.class);

    private final String message;

    public ExtendedLogComponent(String message, PipelineBuilder.ErrorLevel level) {
        super(level);
        this.message = message;
    }

    @Override
    public Object onCall(MuleEventContext context) throws Exception {
        log(message);
        return context.getMessage();
    }

    @Override
    public Log getLogger() {
        return logger;
    }

    public String getMessage() {
        return message;
    }

}
