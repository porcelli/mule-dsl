/*
 * $Id: 20811 2011-03-30 16:05:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.aproach3.example.bookstore;

import org.mule.config.dsl.aproach1.AbstractModule;
import org.mule.config.dsl.aproach3.AbstractMethodModule;
import org.mule.config.dsl.aproach3.example.bookstore.business.*;

public class BookstoreExample {

    public static class BookStore extends AbstractMethodModule {
        @Override
        public void configure() {
            usePropertyPlaceholder("email.properties");

            Transformer setHtmlContentType = null;

            newFlow("CatalogService").in(
                    //Public interface
                    from(HTTP.class).listen(host("0.0.0.0").port(8777).path("services/catalog"))
                            .using(CXF.class).with(CatalogService.class),
                    //Administration interface
                    from("servlet://catalog")
                            .processResponse(transformWith(AddBookResponse.class), transformWith(setHtmlContentType))
            ).process(
                    execute(CatalogServiceImpl.class).asSingleton()
            );

            newFlow("OrderService").in(
                    from(HTTP.class).listen(host("0.0.0.0").port(8777).path("services/order"))
                            .using(CXF.class).with(OrderService.class)
            ).process(
                    execute(OrderServiceImpl.class).asSingleton(),
                    sendOneWay(VM.class).path("emailNotification"),
                    sendOneWay(VM.class).path("dataWarehouse")
            );


            newFlow("EmailNotificationService").in(
                    from(VM.class).path("emailNotification")
            ).process(
                    transformWith(OrderToEmailTransformer.class),
                    transformWith(StringToEmailTransformer.class),
                    sendOneWay(SMTPS.class)
                            .user("${user}")
                            .password("${password}")
                            .host("${host}")
                            .from("${from}")
                            .subject("Your order has been placed!")
            );

            newFlow("DataWarehouse").in(
                    from(VM.class).path("dataWarehouse")
            ).process(
                    execute(DataWarehouse.class).asSingleton(),
                    transformWith(setHtmlContentType),
                    sendOneWay("stats")
            );

        }
    }
}