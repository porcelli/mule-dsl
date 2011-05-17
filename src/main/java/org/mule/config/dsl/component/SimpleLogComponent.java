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
    private static final Log logger = LogFactory.getLog(SimpleLogComponent.class);

    private final PipelineBuilder.ErrorLevel level;

    public SimpleLogComponent(PipelineBuilder.ErrorLevel level) {
        super();
        this.level = level;
    }

    @Override
    public void log(String message) {
        switch (level) {
            case FATAL:
                if (getLogger().isFatalEnabled()) {
                    getLogger().fatal(message);
                }
            case ERROR:
                if (getLogger().isErrorEnabled()) {
                    getLogger().error(message);
                }
            case WARN:
                if (getLogger().isWarnEnabled()) {
                    getLogger().warn(message);
                }
            case INFO:
                if (getLogger().isInfoEnabled()) {
                    getLogger().info(message);
                }
            case DEBUG:
                if (getLogger().isDebugEnabled()) {
                    getLogger().debug(message);
                }
            case TRACE:
                if (getLogger().isTraceEnabled()) {
                    getLogger().trace(message);
                }
        }
    }

    public Log getLogger() {
        return logger;
    }

    public PipelineBuilder.ErrorLevel getLevel() {
        return level;
    }

}
