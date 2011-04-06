/*
 * $Id: 20811 2011-04-01 12:47:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl;

import org.mule.config.domain.Transformer;
import org.mule.config.dsl.example.bookstore.*;

public class BookstoreExampleTestXXX {

        public static void main(String... args) {
        Application bookstoreApp = Mule.createApplication(new BookStore());
        bookstoreApp.start();
        Flow dwFlow = bookstoreApp.getFlow("DataWarehouse");
        dwFlow.stop();
    }

    public static class BookStore extends AbstractModule {
        @Override
        public void configure() {
            newFlow("CatalogService").in(
                        from(VM).listen().queue("sss"),
                        from(HTTP).listen().on("0.0.0.0").port(8080).path("/s/s/s/s")
                                .transform()
                                .transform()
                                .filter()
                                .response()
                                    .transform()
                                    .transform()
                                    .filter())
                    .execute()
                    .filter()
                    .tranform();

            newFlow("CatalogService").in(
                        from(HTTP).listen().on("0.0.0.0").port(8080).path("/s/s/s/s")
                                .transform()
                                .transform()
                                .filter()
                                .response()
                                    .transform()
                                    .transform()
                                    .filter(),
                        from(HTTP).poll().at("0.0.0.0").port(8080).path("/s/s/s/s").every(10, SECONDS)
                                .transform()
                                .transform()
                                .filter()
                                .response()
                                    .transform()
                                    .transform()
                                    .filter(),
                        from(FTP).poll().at("0.0.0.0").port(8080).path("/s/s/s/s").every(10, SECONDS)
                                .transform()
                                .transform()
                                .filter())
                    .execute()
                    .filter()
                    .tranform();

        }

        private FlowInboundListenBuilder listen(String s) {
            return null;  //To change body of created methods use File | Settings | File Templates.
        }

        private FlowInboundListenBuilder listen(Protocol http, String s) {
            return null;  //To change body of created methods use File | Settings | File Templates.
        }
    }
}

