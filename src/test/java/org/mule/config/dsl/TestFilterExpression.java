/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.junit.Test;
import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.config.ConfigurationException;

import static org.mule.config.dsl.expression.CoreExpr.generic;

public class TestFilterExpression {

    @Test
    public void testSimpleFilterExpression() throws MuleException, ConfigurationException, InterruptedException {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .transformTo(String.class)
                        .filter(generic("#[wildcard:foo*]"))
                        .log();
            }
        });

        muleContext.start();

        Thread.sleep(10000);

    }
}