/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import static org.mule.config.dsl.expression.CoreExpr.string;

public class TestSyntaxRouterChoice {

    public void choiceRouterTest() {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .choice()
                            .when(string(""))
                                .echo()
                                .echo()
                                .echo()
                                .choice()
                                    .when(string(""))
                                        .echo()
                                    .otherwise()
                                        .echo()
                                .endChoice()
                                .echo()
                                .echo()
                            .when(string(""))
                                .echo()
                                .echo()
                            .otherwise()
                                .echo()
                                .echo()
                        .endChoice();

                flow("MyFlow2")
                        .execute((Class<?>)null)
                        .choice()
                            .when(string(""))
                                .echo()
                                .echo()
                                .echo()
                                .choice()
                                    .when(string(""))
                                        .echo()
                                    .otherwise()
                                        .echo()
                                .endChoice()
                                .echo()
                                .echo()
                            .when(string(""))
                                .echo()
                                .echo()
                            .otherwise()
                                .echo()
                                .echo()
                        .endChoice();

                flow("MyFlow2")
                        .execute((Class<?>)null)
                        .choice()
                            .when(string(""))
                                .echo()
                                .echo()
                                .echo()
                                .choice()
                                    .when(string(""))
                                        .echo()
                                    .otherwise()
                                        .echo()
                                .endChoice()
                                .echo()
                                .echo()
                            .otherwise()
                                .echo()
                                .echo()
                        .endChoice();
            }
        });
    }

}
