/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import static org.mule.config.dsl.ExchangePattern.ONE_WAY;
import static org.mule.config.dsl.Scope.SINGLETON;
import static org.mule.config.dsl.expression.CoreExpr.regex;

import org.mule.api.lifecycle.Callable;

public class TestSyntaxRouterAll {

//    @Test
    public void allRouterTest() {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")

                        .choice()
                            .when(regex("boo*"))
                                .send("10.0.0")
                            .when(regex("xoo*"))
                                .broadcast()
                                    .send("aaa")
                                    .send("bbb")
                                .endBroadcast()
                                .choice()
                                   .when(null)
                                        .endChoice()
                            .endChoice()

                        .broadcast()
                            .echo()
                                .broadcast()
                                    .echo()
                                        .broadcast()
                                            .echo()
                                            .execute((Class<?>) null, SINGLETON)
                                            .send(null, ONE_WAY)
                                        .endBroadcast()
                                        .broadcast()
                                            .broadcast()
                                            .endBroadcast()
                                        .endBroadcast()
                                    .echo()
                                .endBroadcast()
                        .endBroadcast()
                        .broadcast()
                        	.echo()
                        		.broadcast()
                        			.echo()
                        		.endBroadcast()
                        		.echo()
                        .endBroadcast();

                flow("MyFlow2")
                	.broadcast()
                        .echo()
                        .broadcast()
                            .echo()
                                .broadcast()
                                    .echo()
                                    .echo()
                                .endBroadcast()
                                .broadcast()
                                    .broadcast()
                                    .endBroadcast()
                                .endBroadcast()
                            .echo()
                        .endBroadcast()
                    .endBroadcast()
                    .broadcast()
                	    .echo()
                		    .broadcast()
                			    .echo()
                		    .endBroadcast()
                		.echo()
                    .endBroadcast();

                flow("MyFlow3")
                        .execute((Class<?>) null)
                        .echo()
                        .broadcast()
                            .execute((Callable) null).withDefaultArg()
                        .endBroadcast();

                flow("MyFlow4")
                        .send(null)
                        .echo()
                        .broadcast()
                            .execute((Callable) null)
                            .execute(String.class).withDefaultArg()
                        .endBroadcast();
            }
        });
    }

}
