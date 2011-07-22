/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.component;

import org.junit.Test;
import org.mule.api.construct.FlowConstructInvalidException;
import org.mule.config.dsl.AbstractModule;
import org.mule.config.dsl.Mule;

import static org.fest.assertions.Fail.fail;

public class TestInvokerFlowComponent extends BaseComponentTests {

    public TestInvokerFlowComponent() {
        try {
            this.muleContext = Mule.newMuleContext(new AbstractModule() {
                @Override
                public void configure() {
                    flow("Receiver")
                            .executeFlow("xxxx");

                    flow("Receiver2")
                            .executeFlow("Dispatcher");

                    flow("Dispatcher")
                            .log();

                }
            });

        } catch (final Exception e) {
            fail("Can't initialize muleContext.", e);
        }
    }

    @Test(expected = FlowConstructInvalidException.class)
    public void testFlowNotFound() throws Exception {
        final InvokerFlowComponent component = new InvokerFlowComponent("xxx");
        component.process(getEvent("sss"));
    }

    @Test(expected = NullPointerException.class)
    public void testNullEvent() throws Exception {
        final InvokerFlowComponent component = new InvokerFlowComponent("xxx");
        component.process(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullFlowNameEmpty() throws Exception {
        new InvokerFlowComponent("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullFlowNameNull() throws Exception {
        new InvokerFlowComponent(null);
    }

    //TODO test real execution.. problem with endpoits for now

}