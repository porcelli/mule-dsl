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

public class TestAllRouterSyntax {

    //@Test
    public void allRouterTet() {
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
                                            .execute((Class<?>) null).asSingleton()
                                            .send(null).asOneWay()
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

                flow("MyFlow")
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

                flow("MyFlow")
                        .execute((Class<?>)null)
                        .echo()
                        .all()
                            .execute((Callable) null)
                        .endAll();

                flow("MyFlow")
                        .send(null).asOneWay()
                        .echo()
                        .all()
                            .execute((Callable) null)
                        .endAll();
            }
        });
    }

}
