/*
 * $Id: 20811 2011-03-30 16:05:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.method.example.bookstore;

import com.sun.istack.internal.Nullable;

import javax.xml.ws.Service;
import java.util.Map;

import static org.mule.config.dsl.method.example.bookstore.AbstractMethodModule.TimeUnit.SECONDS;

public class BookstoreExampleTestReal {

    //It provides a better read expirience (from), but it does no provide auto-complete
    public static class BookStore extends AbstractMethodModule {
        @Override
        public void configure() {
            Connector someConnectorHere = null;

            newFlow("MyFlow").in(
                    from(FTP.poll(host("0.0.0.0").port(22).path("sss")).every(10, TimeUnit.SECONDS))
                            .using(CXF.class).with(Service.class)
                            .process(transformWith(MyTransformer.class), transformTo(Map.class), filter()),

                    from(JMS.queue("sss"))
                            .process(transformTo(String.class), transformTo(Map.class), filter()),

                    from(JMS.using(someConnectorHere).queue("sss"))
                            .process(transformWith(MyTransformer.class), transformTo(String.class), filter()),

                    from("salesforce://xxxx.com/sss")
                            .process(transformTo(String.class), filter())
            ).process(
                    execute(MyPojo.class),
                    filter(),
                    multicast(send(), send(), send())
            );
        }

        private ProcessorBuilder multicast(ProcessorBuilder... processors) {
            return null;
        }

        private ProcessorBuilder send() {
            return null;
        }

    }
}