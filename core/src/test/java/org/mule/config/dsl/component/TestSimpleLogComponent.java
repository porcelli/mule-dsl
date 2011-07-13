/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.component;

import static org.fest.assertions.Assertions.assertThat;
import static org.mule.config.dsl.LogLevel.DEBUG;
import static org.mule.config.dsl.LogLevel.ERROR;
import static org.mule.config.dsl.LogLevel.FATAL;
import static org.mule.config.dsl.LogLevel.INFO;
import static org.mule.config.dsl.LogLevel.TRACE;
import static org.mule.config.dsl.LogLevel.WARN;

import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.mule.api.config.ConfigurationException;
import org.mule.api.lifecycle.InitialisationException;

public class TestSimpleLogComponent extends BaseComponentTests {

    public TestSimpleLogComponent() throws InitialisationException, ConfigurationException {
        super();
    }

    @Test
    public void testFatal() throws Exception {

        final LogFactory.MyTestLogger testLogger = (LogFactory.MyTestLogger) LogFactory.getLog(SimpleLogComponent.class);

        testLogger.reset();

        final SimpleLogComponent logFatalComponent = new SimpleLogComponent(FATAL);
        logFatalComponent.onCall(getEventContext("MyContent1"));

        assertThat(testLogger.getFatalExec()).isNotEmpty().hasSize(1);

        logFatalComponent.onCall(getEventContext("MyContent2"));
        logFatalComponent.onCall(getEventContext("MyContent3"));

        assertThat(testLogger.getFatalExec()).isNotEmpty().hasSize(3);

        assertThat(testLogger.getFatalExec().get(0)).isEqualTo(getMessage("MyContent1"));

        assertThat(testLogger.getFatalExec().get(1)).isEqualTo(getMessage("MyContent2"));

        assertThat(testLogger.getFatalExec().get(2)).isEqualTo(getMessage("MyContent3"));
    }

    @Test
    public void testError() throws Exception {

        final LogFactory.MyTestLogger testLogger = (LogFactory.MyTestLogger) LogFactory.getLog(SimpleLogComponent.class);
        testLogger.reset();

        final SimpleLogComponent logErrorComponent = new SimpleLogComponent(ERROR);
        logErrorComponent.onCall(getEventContext("MyContent1"));

        assertThat(testLogger.getFatalExec()).isEmpty();
        assertThat(testLogger.getErrorExec()).isNotEmpty().hasSize(1);

        logErrorComponent.onCall(getEventContext("MyContent2"));
        logErrorComponent.onCall(getEventContext("MyContent3"));

        assertThat(testLogger.getFatalExec()).isEmpty();
        assertThat(testLogger.getErrorExec()).isNotEmpty().hasSize(3);

        assertThat(testLogger.getErrorExec().get(0)).isEqualTo(getMessage("MyContent1"));

        assertThat(testLogger.getErrorExec().get(1)).isEqualTo(getMessage("MyContent2"));

        assertThat(testLogger.getErrorExec().get(2)).isEqualTo(getMessage("MyContent3"));
    }

    @Test
    public void testWarn() throws Exception {

        final LogFactory.MyTestLogger testLogger = (LogFactory.MyTestLogger) LogFactory.getLog(SimpleLogComponent.class);
        testLogger.reset();

        final SimpleLogComponent logWarnComponent = new SimpleLogComponent(WARN);
        logWarnComponent.onCall(getEventContext("MyContent1"));

        assertThat(testLogger.getFatalExec()).isEmpty();
        assertThat(testLogger.getErrorExec()).isEmpty();
        assertThat(testLogger.getWarnExec()).isNotEmpty().hasSize(1);

        logWarnComponent.onCall(getEventContext("MyContent2"));
        logWarnComponent.onCall(getEventContext("MyContent3"));

        assertThat(testLogger.getFatalExec()).isEmpty();
        assertThat(testLogger.getErrorExec()).isEmpty();
        assertThat(testLogger.getWarnExec()).isNotEmpty().hasSize(3);

        assertThat(testLogger.getWarnExec().get(0)).isEqualTo(getMessage("MyContent1"));

        assertThat(testLogger.getWarnExec().get(1)).isEqualTo(getMessage("MyContent2"));

        assertThat(testLogger.getWarnExec().get(2)).isEqualTo(getMessage("MyContent3"));
    }

    @Test
    public void testInfo() throws Exception {

        final LogFactory.MyTestLogger testLogger = (LogFactory.MyTestLogger) LogFactory.getLog(SimpleLogComponent.class);
        testLogger.reset();

        final SimpleLogComponent logInfoComponent = new SimpleLogComponent(INFO);
        logInfoComponent.onCall(getEventContext("MyContent1"));

        assertThat(testLogger.getFatalExec()).isEmpty();
        assertThat(testLogger.getErrorExec()).isEmpty();
        assertThat(testLogger.getWarnExec()).isEmpty();
        assertThat(testLogger.getInfoExec()).isNotEmpty().hasSize(1);

        logInfoComponent.onCall(getEventContext("MyContent2"));
        logInfoComponent.onCall(getEventContext("MyContent3"));

        assertThat(testLogger.getFatalExec()).isEmpty();
        assertThat(testLogger.getErrorExec()).isEmpty();
        assertThat(testLogger.getWarnExec()).isEmpty();
        assertThat(testLogger.getInfoExec()).isNotEmpty().hasSize(3);

        assertThat(testLogger.getInfoExec().get(0)).isEqualTo(getMessage("MyContent1"));

        assertThat(testLogger.getInfoExec().get(1)).isEqualTo(getMessage("MyContent2"));

        assertThat(testLogger.getInfoExec().get(2)).isEqualTo(getMessage("MyContent3"));
    }

    @Test
    public void testDebug() throws Exception {

        final LogFactory.MyTestLogger testLogger = (LogFactory.MyTestLogger) LogFactory.getLog(SimpleLogComponent.class);
        testLogger.reset();

        final SimpleLogComponent logDebugComponent = new SimpleLogComponent(DEBUG);
        logDebugComponent.onCall(getEventContext("MyContent1"));

        assertThat(testLogger.getFatalExec()).isEmpty();
        assertThat(testLogger.getErrorExec()).isEmpty();
        assertThat(testLogger.getWarnExec()).isEmpty();
        assertThat(testLogger.getInfoExec()).isEmpty();
        assertThat(testLogger.getDebugExec()).isNotEmpty().hasSize(1);

        logDebugComponent.onCall(getEventContext("MyContent2"));
        logDebugComponent.onCall(getEventContext("MyContent3"));

        assertThat(testLogger.getFatalExec()).isEmpty();
        assertThat(testLogger.getErrorExec()).isEmpty();
        assertThat(testLogger.getWarnExec()).isEmpty();
        assertThat(testLogger.getInfoExec()).isEmpty();
        assertThat(testLogger.getDebugExec()).isNotEmpty().hasSize(3);

        assertThat(testLogger.getDebugExec().get(0)).isEqualTo(getMessage("MyContent1"));

        assertThat(testLogger.getDebugExec().get(1)).isEqualTo(getMessage("MyContent2"));

        assertThat(testLogger.getDebugExec().get(2)).isEqualTo(getMessage("MyContent3"));
    }

    @Test
    public void testTrace() throws Exception {

        final LogFactory.MyTestLogger testLogger = (LogFactory.MyTestLogger) LogFactory.getLog(SimpleLogComponent.class);
        testLogger.reset();

        final SimpleLogComponent logTraceComponent = new SimpleLogComponent(TRACE);
        logTraceComponent.onCall(getEventContext("MyContent1"));

        assertThat(testLogger.getFatalExec()).isEmpty();
        assertThat(testLogger.getErrorExec()).isEmpty();
        assertThat(testLogger.getWarnExec()).isEmpty();
        assertThat(testLogger.getInfoExec()).isEmpty();
        assertThat(testLogger.getDebugExec()).isEmpty();
        assertThat(testLogger.getTraceExec()).isNotEmpty().hasSize(1);

        logTraceComponent.onCall(getEventContext("MyContent2"));
        logTraceComponent.onCall(getEventContext("MyContent3"));

        assertThat(testLogger.getFatalExec()).isEmpty();
        assertThat(testLogger.getErrorExec()).isEmpty();
        assertThat(testLogger.getWarnExec()).isEmpty();
        assertThat(testLogger.getInfoExec()).isEmpty();
        assertThat(testLogger.getDebugExec()).isEmpty();
        assertThat(testLogger.getTraceExec()).isNotEmpty().hasSize(3);

        assertThat(testLogger.getTraceExec().get(0)).isEqualTo(getMessage("MyContent1"));

        assertThat(testLogger.getTraceExec().get(1)).isEqualTo(getMessage("MyContent2"));

        assertThat(testLogger.getTraceExec().get(2)).isEqualTo(getMessage("MyContent3"));
    }

    private String getMessage(final String content) {
        final StringBuilder sb = new StringBuilder("\n");

        sb.append("********************************************************************************")
                .append("\n")
                .append("* Message received in service: null. Content is: '")
                .append(content)
                .append("'                  *")
                .append("\n")
                .append("********************************************************************************");

        return sb.toString();
    }

}