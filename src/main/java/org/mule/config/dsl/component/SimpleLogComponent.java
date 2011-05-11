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
import org.mule.component.simple.LogComponent;
import org.mule.config.dsl.PipelineBuilder;

public class SimpleLogComponent extends LogComponent {
    private static Log logger = LogFactory.getLog(SimpleLogComponent.class);

    private final PipelineBuilder.ErrorLevel level;

    public SimpleLogComponent(PipelineBuilder.ErrorLevel level) {
        super();
        this.level = level;
    }

    @Override
    public void log(String message) {
        switch (level) {
            case ERROR:
                if (logger.isErrorEnabled()) {
                    logger.error(message);
                }
            case FATAL:
                if (logger.isFatalEnabled()) {
                    logger.fatal(message);
                }
            case INFO:
                if (logger.isInfoEnabled()) {
                    logger.info(message);
                }
            case WARN:
                if (logger.isWarnEnabled()) {
                    logger.warn(message);
                }
        }
    }

    public PipelineBuilder.ErrorLevel getLevel() {
        return level;
    }

}
