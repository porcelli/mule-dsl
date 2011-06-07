/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.component;

import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.mule.api.config.ConfigurationException;
import org.mule.api.lifecycle.InitialisationException;

import static org.fest.assertions.Assertions.assertThat;
import static org.mule.config.dsl.LogLevel.*;

public class TestExtendedLogComponent extends BaseComponentTests {

    public TestExtendedLogComponent() throws InitialisationException, ConfigurationException {
        super();
    }

    @Test
    public void testFatal() throws Exception {

        LogFactory.MyTestLogger testLogger = (LogFactory.MyTestLogger) LogFactory.getLog(ExtendedLogComponent.class);

        testLogger.reset();

        ExtendedLogComponent logFatalComponent = new ExtendedLogComponent("MyContent", FATAL);
        logFatalComponent.onCall(getEventContext());

        assertThat(testLogger.getFatalExec()).isNotEmpty().hasSize(1);

        logFatalComponent.onCall(getEventContext());
        logFatalComponent.onCall(getEventContext());

        assertThat(testLogger.getFatalExec()).isNotEmpty().hasSize(3);

        assertThat(testLogger.getFatalExec().get(0)).isEqualTo(getMessage("MyContent"));

        assertThat(testLogger.getFatalExec().get(1)).isEqualTo(getMessage("MyContent"));

        assertThat(testLogger.getFatalExec().get(2)).isEqualTo(getMessage("MyContent"));
    }

    @Test
    public void testError() throws Exception {

        LogFactory.MyTestLogger testLogger = (LogFactory.MyTestLogger) LogFactory.getLog(ExtendedLogComponent.class);
        testLogger.reset();

        ExtendedLogComponent logErrorComponent = new ExtendedLogComponent("MyContent", ERROR);
        logErrorComponent.onCall(getEventContext());

        assertThat(testLogger.getFatalExec()).isEmpty();
        assertThat(testLogger.getErrorExec()).isNotEmpty().hasSize(1);

        logErrorComponent.onCall(getEventContext());
        logErrorComponent.onCall(getEventContext());

        assertThat(testLogger.getFatalExec()).isEmpty();
        assertThat(testLogger.getErrorExec()).isNotEmpty().hasSize(3);

        assertThat(testLogger.getErrorExec().get(0)).isEqualTo(getMessage("MyContent"));

        assertThat(testLogger.getErrorExec().get(1)).isEqualTo(getMessage("MyContent"));

        assertThat(testLogger.getErrorExec().get(2)).isEqualTo(getMessage("MyContent"));
    }

    @Test
    public void testWarn() throws Exception {

        LogFactory.MyTestLogger testLogger = (LogFactory.MyTestLogger) LogFactory.getLog(ExtendedLogComponent.class);
        testLogger.reset();

        ExtendedLogComponent logWarnComponent = new ExtendedLogComponent("MyContent", WARN);
        logWarnComponent.onCall(getEventContext());

        assertThat(testLogger.getFatalExec()).isEmpty();
        assertThat(testLogger.getErrorExec()).isEmpty();
        assertThat(testLogger.getWarnExec()).isNotEmpty().hasSize(1);

        logWarnComponent.onCall(getEventContext());
        logWarnComponent.onCall(getEventContext());

        assertThat(testLogger.getFatalExec()).isEmpty();
        assertThat(testLogger.getErrorExec()).isEmpty();
        assertThat(testLogger.getWarnExec()).isNotEmpty().hasSize(3);

        assertThat(testLogger.getWarnExec().get(0)).isEqualTo(getMessage("MyContent"));

        assertThat(testLogger.getWarnExec().get(1)).isEqualTo(getMessage("MyContent"));

        assertThat(testLogger.getWarnExec().get(2)).isEqualTo(getMessage("MyContent"));
    }

    @Test
    public void testInfo() throws Exception {

        LogFactory.MyTestLogger testLogger = (LogFactory.MyTestLogger) LogFactory.getLog(ExtendedLogComponent.class);
        testLogger.reset();

        ExtendedLogComponent logInfoComponent = new ExtendedLogComponent("MyContent", INFO);
        logInfoComponent.onCall(getEventContext());

        assertThat(testLogger.getFatalExec()).isEmpty();
        assertThat(testLogger.getErrorExec()).isEmpty();
        assertThat(testLogger.getWarnExec()).isEmpty();
        assertThat(testLogger.getInfoExec()).isNotEmpty().hasSize(1);

        logInfoComponent.onCall(getEventContext());
        logInfoComponent.onCall(getEventContext());

        assertThat(testLogger.getFatalExec()).isEmpty();
        assertThat(testLogger.getErrorExec()).isEmpty();
        assertThat(testLogger.getWarnExec()).isEmpty();
        assertThat(testLogger.getInfoExec()).isNotEmpty().hasSize(3);

        assertThat(testLogger.getInfoExec().get(0)).isEqualTo(getMessage("MyContent"));

        assertThat(testLogger.getInfoExec().get(1)).isEqualTo(getMessage("MyContent"));

        assertThat(testLogger.getInfoExec().get(2)).isEqualTo(getMessage("MyContent"));
    }

    @Test
    public void testDebug() throws Exception {

        LogFactory.MyTestLogger testLogger = (LogFactory.MyTestLogger) LogFactory.getLog(ExtendedLogComponent.class);
        testLogger.reset();

        ExtendedLogComponent logDebugComponent = new ExtendedLogComponent("MyContent", DEBUG);
        logDebugComponent.onCall(getEventContext());

        assertThat(testLogger.getFatalExec()).isEmpty();
        assertThat(testLogger.getErrorExec()).isEmpty();
        assertThat(testLogger.getWarnExec()).isEmpty();
        assertThat(testLogger.getInfoExec()).isEmpty();
        assertThat(testLogger.getDebugExec()).isNotEmpty().hasSize(1);

        logDebugComponent.onCall(getEventContext());
        logDebugComponent.onCall(getEventContext());

        assertThat(testLogger.getFatalExec()).isEmpty();
        assertThat(testLogger.getErrorExec()).isEmpty();
        assertThat(testLogger.getWarnExec()).isEmpty();
        assertThat(testLogger.getInfoExec()).isEmpty();
        assertThat(testLogger.getDebugExec()).isNotEmpty().hasSize(3);

        assertThat(testLogger.getDebugExec().get(0)).isEqualTo(getMessage("MyContent"));

        assertThat(testLogger.getDebugExec().get(1)).isEqualTo(getMessage("MyContent"));

        assertThat(testLogger.getDebugExec().get(2)).isEqualTo(getMessage("MyContent"));
    }

    @Test
    public void testTrace() throws Exception {

        LogFactory.MyTestLogger testLogger = (LogFactory.MyTestLogger) LogFactory.getLog(ExtendedLogComponent.class);
        testLogger.reset();

        ExtendedLogComponent logTraceComponent = new ExtendedLogComponent("MyContent", TRACE);
        logTraceComponent.onCall(getEventContext());

        assertThat(testLogger.getFatalExec()).isEmpty();
        assertThat(testLogger.getErrorExec()).isEmpty();
        assertThat(testLogger.getWarnExec()).isEmpty();
        assertThat(testLogger.getInfoExec()).isEmpty();
        assertThat(testLogger.getDebugExec()).isEmpty();
        assertThat(testLogger.getTraceExec()).isNotEmpty().hasSize(1);

        logTraceComponent.onCall(getEventContext());
        logTraceComponent.onCall(getEventContext());

        assertThat(testLogger.getFatalExec()).isEmpty();
        assertThat(testLogger.getErrorExec()).isEmpty();
        assertThat(testLogger.getWarnExec()).isEmpty();
        assertThat(testLogger.getInfoExec()).isEmpty();
        assertThat(testLogger.getDebugExec()).isEmpty();
        assertThat(testLogger.getTraceExec()).isNotEmpty().hasSize(3);

        assertThat(testLogger.getTraceExec().get(0)).isEqualTo(getMessage("MyContent"));

        assertThat(testLogger.getTraceExec().get(1)).isEqualTo(getMessage("MyContent"));

        assertThat(testLogger.getTraceExec().get(2)).isEqualTo(getMessage("MyContent"));
    }

    private String getMessage(String content) {
        return content;
    }

}