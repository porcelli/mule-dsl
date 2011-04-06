/*
 * $Id: 20811 2011-03-30 16:05:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.method2.example.bookstore;

import javax.xml.ws.Service;
import java.util.Map;

import static org.mule.config.dsl.method2.example.bookstore.AbstractMethodModule.TimeUnit.MINUTES;
import static org.mule.config.dsl.method2.example.bookstore.AbstractMethodModule.TimeUnit.SECONDS;

public class BookstoreExampleTestReal {

    //This aproach is easy to extend (producing additional protocols) and provides auto-complete
    public static class BookStore extends AbstractMethodModule {
        @Override
        public void configure() {
            Connector someConnectorHere = null;

            newFlow("MyFlow").in(
                    from(FTP.class).poll(host("0.0.0.0").port(22).path("sss")).every(10),
                    from(FTP.class).poll(host("0.0.0.0").port(22).path("sss")).every(10, SECONDS)
                            .using(CXF.class).with(Service.class)
                            .process(transformWith(MyTransformer.class), transformTo(Map.class), filter()),

                    from(HTTP.class).listen(host("0.0.0.0").port(22).path("sss"))
                            .using(CXF.class).with(Service.class)
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class), filter())
                            .processResponse(transformWith(MyTransformer.class), transformTo(Map.class), filter()),

                    from(HTTP.class).poll(host("0.0.0.0").port(22).path("sss")).every(10, MINUTES)
                            .using(CXF.class).with(Service.class)
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class), filter())
                            .processResponse(transformWith(MyTransformer.class), transformTo(Map.class), filter()),

                    from(JMS.class).queue("sss")
                            .process(transformTo(String.class), transformTo(Map.class), filter()),

                    from(JMS.class).using(someConnectorHere).queue("sss")
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