/*
 * $Id: 20811 2011-03-30 16:05:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl;

public class EchoExampleTest {
    public static void main(String... args) {
        Flow f = Mule.createFlow()
                .listen("http://localhost:65082/services/EchoUMO")
                    .using(CFX.class)
                        .withClass(org.mule.example.echo.Echo.class)
                .then()
                    .execute(org.mule.example.echo.Echo.class).asSingleton();

        f.start();
        f.stop();
    }
}