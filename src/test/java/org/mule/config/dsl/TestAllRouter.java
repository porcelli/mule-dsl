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

public class TestAllRouter {

    @Test
    public void simpleBridge() {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .all()
                            .echo()
                            .echo()
                            .echo()
                        .endAll();
            }
        });
//
//        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);
    }

}
