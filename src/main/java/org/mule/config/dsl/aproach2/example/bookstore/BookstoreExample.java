/*
 * $Id: 20811 2011-03-30 16:05:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.aproach2.example.bookstore;

import org.mule.config.dsl.aproach2.AbstractMethodModule;
import org.mule.config.dsl.aproach2.example.features.business.MyPojo;
import org.mule.config.dsl.aproach2.example.features.business.MyTransformer;

import javax.xml.ws.Service;
import java.io.File;
import java.util.Map;

import static org.mule.config.dsl.aproach2.AbstractMethodModule.TimeUnit.SECONDS;

public class BookstoreExample {

    public static class BookStore extends AbstractMethodModule {
        @Override
        public void configure() {
            usePropertyPlaceholder("email.properties");
        }
    }
}