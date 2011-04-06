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

import org.mule.config.dsl.aproach3.AbstractMethodModule;
import org.mule.config.dsl.aproach3.example.bookstore.business.AddBookResponse;
import org.mule.config.dsl.aproach3.example.bookstore.business.CatalogService;
import org.mule.config.dsl.aproach3.example.bookstore.business.CatalogServiceImpl;

public class BookstoreAlternativeExample {

    public static class BookStore extends AbstractMethodModule {
        @Override
        public void configure() {
            usePropertyPlaceholder("email.properties");

            Transformer setHtmlContentType = null;

            newFlow("CatalogService").in(
                    //Public interface
                    from(HTTP.class).listen(host("0.0.0.0").port(8777).path("services/catalog"))
                            .processRequest(execute(CatalogServiceImpl.class)),
                    //Administration interface
                    from("servlet://catalog")
                            .processRequest(execute(CatalogServiceImpl.class))
                            .processResponse(transformWith(AddBookResponse.class), transformWith(setHtmlContentType))
            );
        }
    }
}