/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

public class TestSyntaxBasic {

    public void notAllowMoreThanAFrom() {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                FlowDefinition fb0 = flow("MyFlow")
                        .from((String) null).echo().echo().echo();

                FlowDefinition fb1 = flow("MyFlow")
                        .from((String) null).echo().echo();

                FlowDefinition fb2 = flow("MyFlow")
                        .from((String) null).echo().echo().async().send("").send("").endAsync();

                FlowDefinition fb3 = flow("MyFlow")
                        .from((String) null).onException().process("");
            }
        });
    }

}
