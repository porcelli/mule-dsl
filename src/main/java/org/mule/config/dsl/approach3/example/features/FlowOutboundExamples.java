/*
 * $Id: 20811 2011-03-30 16:05:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.approach3.example.features;

import org.mule.config.dsl.approach3.AbstractModule;

public class FlowOutboundExamples {

    public static class FlowOutbounds extends AbstractModule {
        @Override
        public void configure() {
            EndpointProcessor out = defineEndpoint("ex_outbound").using(VM.class).path("internal");

            newFlow("MyFlow").process(
                    //generic
                    send("http://0.0.0.0/service/here"),
                    //referencing an endopoint
                    send("ex_outbound"),
                    //referencing an endopoint using a local variable
                    send(out),
                    //protocol specific
                    send(SMTPS_Out.class).user("${user}")
                            .password("${password}")
                            .host("${host}")
                            .from("${from}")
                            .subject("Your order has been placed!"),
                    //protocol specific
                    send(VM.class).path("outQueue"),

                    //generic - sync
                    sendAndWait("http://0.0.0.0/service/here"),
                    //referencing an endopoint - sync
                    sendAndWait("ex_outbound"),
                    //referencing an endopoint using a local variable - sync
                    sendAndWait(out),
                    //protocol specific - sync
                    sendAndWait(SMTPS_Out.class).user("${user}")
                            .password("${password}")
                            .host("${host}")
                            .from("${from}")
                            .subject("Your order has been placed!"),
                    //protocol specific - sync
                    sendAndWait(VM_Out.class).path("outQueue")
            );
        }


    }
}