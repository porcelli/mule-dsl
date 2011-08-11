/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.example.rest.module;

import org.mule.config.dsl.AbstractModule;
import org.mule.module.json.transformers.ObjectToJson;
import org.mule.transport.restlet.UriTemplateFilter;

/**
 * Module that defines a flow that starts an embedded http server on 8080 port that expose a rest service,
 * that consumes other services (using other already defined flow) and return the payload in json format.
 */
public class StockQuotesRestServiceModule extends AbstractModule {
    @Override
    protected void configure() {

        UriTemplateFilter restFilter = new UriTemplateFilter(); //defines a rest filter
        restFilter.setPattern("/stock/{set-payload.id}"); //defines the rest pattern - note that it sets the id to payload
        restFilter.setVerbs("GET"); //define the supported http verb

        flow("LocalRestServiceGetQuote")
                .from("http://localhost:8080") //embedded http server that listen on 8080 port
                .filterWith(restFilter) //filter the request using the rest filter
                .executeFlow("TweetStockQuote") //executes the "TweetStockQuote" flow
                .transformWith(ObjectToJson.class) //transform tha payload (StockQuote instance) to json
                .messageProperties()
                    .put("Content-Type", "application/json"); //sets the output content-type to json
    }
}
