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
import org.mule.api.MuleEventContext;
import org.mule.api.MuleException;
import org.mule.api.config.ConfigurationException;
import org.mule.api.lifecycle.Callable;

public class TestExecutorFlow {

    @Test
    public void simpleBridge() throws MuleException, ConfigurationException, InterruptedException {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                Simple2 x = new Simple2();

                flow().from("file:///Users/porcelli/test")
                        .execute(x)
                        .execute(Simple.class);

                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(SimpleCallable.class)
                        .execute(String.class)
                        .transformTo(String.class)
                        .execute(Simple.class)
                        .execute(x);

                bind(Simple.class).to(Simple3.class);
            }
        });
    }

    public static class SimpleCallable implements Callable {

        @Override
        public Object onCall(MuleEventContext muleEventContext) throws Exception {
            System.out.println("HERE!");
            return muleEventContext.getMessage().getPayload();
        }
    }

    public static interface Simple {
        void execute(String string);
    }

    public static class Simple2 implements Simple {
        public void execute(String string) {
            System.out.println("SIMPLE 2! : " + string);
        }
    }

    public static class Simple3 implements Simple {
        public void execute(String string) {
            System.out.println("SIMPLE 3! : " + string);
        }
    }

}
