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

/**
 * Example that executes a stock quote rest service,
 * transform its return into a plain old java object (pojo) and them tweets it.
 *
 * @author porcelli
 */
public class InvokeAndTweetStockQuotes {

    public static void main(String... args) throws MuleException {
        Mule.startMuleContext(new StockQuotesModule());
        Mule.process("TweetStockQuote", "IBM");
    }

}
