/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.example.rest;

import org.mule.api.MuleException;
import org.mule.config.dsl.Mule;
import org.mule.config.dsl.example.rest.module.StockQuotesModule;
import org.mule.config.dsl.example.rest.module.StockQuotesRestServiceModule;


/**
 * This example starts an embedded http server on 8080 port
 * that expose a rest service that once invoked query another
 * stock quote rest service, transform its return into a plain old
 * java object (pojo), tweets it and finally returns the payloas
 * (pojo) in json format.
 *
 * @author porcelli
 */
public class RestServerThatTweetsStockQuotes {

    public static void main(String... args) throws MuleException {
        Mule.newInstance(new StockQuotesRestServiceModule(), new StockQuotesModule()).start();
    }

}
