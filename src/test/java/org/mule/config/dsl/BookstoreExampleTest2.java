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

import org.mule.config.domain.Connector;
import org.mule.config.domain.Transformer;
import org.mule.config.dsl.example.bookstore.*;

public class BookstoreExampleTest2 {
    public static void main(String... args) {
        Application bookstoreApp = Mule.createApplication(new BookStore());
        bookstoreApp.start();
        Flow dwFlow = bookstoreApp.getFlow("DataWarehouse");
        dwFlow.stop();
    }

    public static class BookStore extends AbstractModule {
        @Override
        public void configure() {
            usePropertyPlaceholder("email.properties");

            defineEndpoint("stats")
                    .protocol(Protocol.VM)
                    .path("statistics");

            Transformer t = defineTransformer()
                    .extend(MessageProcessorTransformer.class)
                        .addProperty("Content-Type", "text/html")
                        .removeProperty("content-type");

            newFlow("CatalogService")
                    .from(listen(HTTPProtocol.class, "0.0.0.0")
                                .onPort(8777)
                                .onPath("/services/order")
                             .using(CXF.class)
                                .withClass(OrderService.class),
                          listen("servlet://catalog")
                             .transform(t)
                             .response())
                    .execute(CatalogServiceImpl.class).asSingleton();

            newFlow("OrderService")
                    .listen(HTTPProtocol.class, "0.0.0.0")
                            .onPort(8777)
                            .onPath("/services/order")
                        .using(CXF.class)
                            .withClass(OrderService.class)
                    .then()
                        .execute(OrderServiceImpl.class)
                            .methodAnnotatedWith(SomeDirectMethod.class)
                            .asSingleton();
                        .send(Protocol.VM, "emailNotification")
                        .send(Protocol.VM, "dataWarehouse");

            Connector mySMTPConn = newConnector()
                    .extend(SMTPConnector.class)
                        .user("${user}")
                        .password("${password}")
                        .host("${host}");

            newFlow("EmailNotificationService")
                    .listen(Protocol.VM, "emailNotification")
                    .then()
                        .transformWith(OrderToEmailTransformer.class)
                        .transformWith(StringToEmailTransformer.class)
                        .send(mySMTPConn)
                            .from("${from}")
                            .subject("Your order has been placed!");

            newFlow("DataWarehouse")
                    .listen(Protocol.VM, "dataWarehouse")
                    .then()
                        .execute(DataWarehouse.class)
                            .methodAnnotatedWith(MethodNames.named("MyMethodProcessor"))
                        .transformWith(HtmlContentTypeTransformer.class)
                        .send("stats");
        }
    }
}