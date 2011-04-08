/*
 * $Id: 20811 2011-03-30 16:05:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.approach4.example.features;

import org.mule.config.dsl.approach4.AbstractModule;

public class FlowOutboundExamples {

    public static class FlowOutbounds extends AbstractModule {
        @Override
        public void configure() {
            EndpointProcessor out = endpoint(VM.ENDPOINT, alias("ex_outbound")).path("internal");

            flow("MyFlow").process(
                    //generic
                    send("http://0.0.0.0/service/here"),
                    //referencing an endopoint
                    send("ex_outbound"),
                    //referencing an endopoint using a local variable
                    send(VM.OUTBOUND).extend(out),
                    //protocol specific
                    send(SMTPS.OUTBOUND)
                            .user("${user}")
                            .password("${password}")
                            .host("${host}")
                            .from("${from}")
                            .subject("Your order has been placed!"),
                    //protocol specific
                    send(VM.OUTBOUND).path("outQueue"),

                    //generic - sync
                    sendAndWait("http://0.0.0.0/service/here"),
                    //referencing an endopoint - sync
                    sendAndWait("ex_outbound"),
                    //referencing an endopoint using a local variable - sync
                    sendAndWait(VM.OUTBOUND).extend(out),
                    //protocol specific - sync
                    sendAndWait(SMTPS.OUTBOUND).user("${user}")
                            .password("${password}")
                            .host("${host}")
                            .from("${from}")
                            .subject("Your order has been placed!"),
                    //protocol specific - sync
                    sendAndWait(VM.OUTBOUND).path("outQueue")
            );
        }


    }
}