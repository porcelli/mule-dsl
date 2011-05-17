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
import static org.mule.config.dsl.PipelineBuilder.ErrorLevel.*;
import static org.mule.config.dsl.expression.CoreExpr.string;

public class TestExpressionLogComponent extends BaseComponentTests {

    public TestExpressionLogComponent() throws InitialisationException, ConfigurationException {
        super();
    }

    @Test
    public void testFatal() throws Exception {

        LogFactory.MyTestLogger testLogger = (LogFactory.MyTestLogger) LogFactory.getLog(ExpressionLogComponent.class);

        testLogger.reset();

        ExpressionLogComponent logFatalComponent = new ExpressionLogComponent(string("payload content: #[mule:message.payload()]"), FATAL);
        logFatalComponent.onCall(getEventContext("MyContent1"));

        assertThat(testLogger.getFatalExec()).isNotEmpty().hasSize(1);

        logFatalComponent.onCall(getEventContext("MyContent2"));
        logFatalComponent.onCall(getEventContext("MyContent3"));

        assertThat(testLogger.getFatalExec()).isNotEmpty().hasSize(3);

        assertThat(testLogger.getFatalExec().get(0)).isEqualTo(getMessage("payload content: MyContent1"));

        assertThat(testLogger.getFatalExec().get(1)).isEqualTo(getMessage("payload content: MyContent2"));

        assertThat(testLogger.getFatalExec().get(2)).isEqualTo(getMessage("payload content: MyContent3"));
    }

    @Test
    public void testError() throws Exception {

        LogFactory.MyTestLogger testLogger = (LogFactory.MyTestLogger) LogFactory.getLog(ExpressionLogComponent.class);
        testLogger.reset();

        ExpressionLogComponent logErrorComponent = new ExpressionLogComponent(string("payload content: #[mule:message.payload()]"), ERROR);
        logErrorComponent.onCall(getEventContext("MyContent1"));

        assertThat(testLogger.getFatalExec()).isEmpty();
        assertThat(testLogger.getErrorExec()).isNotEmpty().hasSize(1);

        logErrorComponent.onCall(getEventContext("MyContent2"));
        logErrorComponent.onCall(getEventContext("MyContent3"));

        assertThat(testLogger.getFatalExec()).isEmpty();
        assertThat(testLogger.getErrorExec()).isNotEmpty().hasSize(3);

        assertThat(testLogger.getErrorExec().get(0)).isEqualTo(getMessage("payload content: MyContent1"));

        assertThat(testLogger.getErrorExec().get(1)).isEqualTo(getMessage("payload content: MyContent2"));

        assertThat(testLogger.getErrorExec().get(2)).isEqualTo(getMessage("payload content: MyContent3"));
    }

    @Test
    public void testWarn() throws Exception {

        LogFactory.MyTestLogger testLogger = (LogFactory.MyTestLogger) LogFactory.getLog(ExpressionLogComponent.class);
        testLogger.reset();

        ExpressionLogComponent logWarnComponent = new ExpressionLogComponent(string("payload content: #[mule:message.payload()]"), WARN);
        logWarnComponent.onCall(getEventContext("MyContent1"));

        assertThat(testLogger.getFatalExec()).isEmpty();
        assertThat(testLogger.getErrorExec()).isEmpty();
        assertThat(testLogger.getWarnExec()).isNotEmpty().hasSize(1);

        logWarnComponent.onCall(getEventContext("MyContent2"));
        logWarnComponent.onCall(getEventContext("MyContent3"));

        assertThat(testLogger.getFatalExec()).isEmpty();
        assertThat(testLogger.getErrorExec()).isEmpty();
        assertThat(testLogger.getWarnExec()).isNotEmpty().hasSize(3);

        assertThat(testLogger.getWarnExec().get(0)).isEqualTo(getMessage("payload content: MyContent1"));

        assertThat(testLogger.getWarnExec().get(1)).isEqualTo(getMessage("payload content: MyContent2"));

        assertThat(testLogger.getWarnExec().get(2)).isEqualTo(getMessage("payload content: MyContent3"));
    }

    @Test
    public void testInfo() throws Exception {

        LogFactory.MyTestLogger testLogger = (LogFactory.MyTestLogger) LogFactory.getLog(ExpressionLogComponent.class);
        testLogger.reset();

        ExpressionLogComponent logInfoComponent = new ExpressionLogComponent(string("payload content: #[mule:message.payload()]"), INFO);
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

        assertThat(testLogger.getInfoExec().get(0)).isEqualTo(getMessage("payload content: MyContent1"));

        assertThat(testLogger.getInfoExec().get(1)).isEqualTo(getMessage("payload content: MyContent2"));

        assertThat(testLogger.getInfoExec().get(2)).isEqualTo(getMessage("payload content: MyContent3"));
    }

    @Test
    public void testDebug() throws Exception {

        LogFactory.MyTestLogger testLogger = (LogFactory.MyTestLogger) LogFactory.getLog(ExpressionLogComponent.class);
        testLogger.reset();

        ExpressionLogComponent logDebugComponent = new ExpressionLogComponent(string("payload content: #[mule:message.payload()]"), DEBUG);
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

        assertThat(testLogger.getDebugExec().get(0)).isEqualTo(getMessage("payload content: MyContent1"));

        assertThat(testLogger.getDebugExec().get(1)).isEqualTo(getMessage("payload content: MyContent2"));

        assertThat(testLogger.getDebugExec().get(2)).isEqualTo(getMessage("payload content: MyContent3"));
    }

    @Test
    public void testTrace() throws Exception {

        LogFactory.MyTestLogger testLogger = (LogFactory.MyTestLogger) LogFactory.getLog(ExpressionLogComponent.class);
        testLogger.reset();

        ExpressionLogComponent logTraceComponent = new ExpressionLogComponent(string("payload content: #[mule:message.payload()]"), TRACE);
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

        assertThat(testLogger.getTraceExec().get(0)).isEqualTo(getMessage("payload content: MyContent1"));

        assertThat(testLogger.getTraceExec().get(1)).isEqualTo(getMessage("payload content: MyContent2"));

        assertThat(testLogger.getTraceExec().get(2)).isEqualTo(getMessage("payload content: MyContent3"));
    }

    private String getMessage(String content) {
        return content;
    }

}