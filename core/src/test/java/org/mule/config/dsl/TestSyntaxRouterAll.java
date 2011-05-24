/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.mule.api.lifecycle.Callable;

import static org.mule.MessageExchangePattern.ONE_WAY;
import static org.mule.config.dsl.Scope.SINGLETON;

public class TestSyntaxRouterAll {

//    @Test
    public void allRouterTest() {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .all()
                            .echo()
                                .all()
                                    .echo()
                                        .all()
                                            .echo()
                                            .execute((Class<?>) null, SINGLETON)
                                            .send(null, ONE_WAY)
                                        .endAll()
                                        .all()
                                            .all()
                                            .endAll()
                                        .endAll()
                                    .echo()
                                .endAll()
                        .endAll()
                        .all()
                        	.echo()
                        		.all()
                        			.echo()
                        		.endAll()
                        		.echo()
                        .endAll();

                flow("MyFlow2")
                	.all()
                        .echo()
                        .all()
                            .echo()
                                .all()
                                    .echo()
                                    .echo()
                                .endAll()
                                .all()
                                    .all()
                                    .endAll()
                                .endAll()
                            .echo()
                        .endAll()
                    .endAll()
                    .all()
                	    .echo()
                		    .all()
                			    .echo()
                		    .endAll()
                		.echo()
                    .endAll();

                flow("MyFlow3")
                        .execute((Class<?>)null)
                        .echo()
                        .all()
                            .execute((Callable) null)
                        .endAll();

                flow("MyFlow4")
                        .send(null)
                        .echo()
                        .all()
                            .execute((Callable) null)
                        .endAll();
            }
        });
    }

}
