/*
 * ---------------------------------------------------------------------------
 *  Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;

public class TestInvokeCustomMP {

    @Test(expected = RuntimeException.class)
    public void simpleAnonymousMP() {
        final MessageProcessor mp = new MessageProcessor() {
            @Override
            public MuleEvent process(final MuleEvent event) throws MuleException {
                return null;
            }
        };

        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(mp);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void simpleCustomMPClazz() {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(MyCustomMP.class);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void simpleCustomMPClazz2() {
        final MyCustomMP myCustomMP = new MyCustomMP();
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .invoke(myCustomMP);
            }
        });
    }

    public static class MyCustomMP implements MessageProcessor {

        @Override
        public MuleEvent process(final MuleEvent event) throws MuleException {
            return null;
        }
    }
}
