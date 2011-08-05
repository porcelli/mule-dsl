/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.example.http;

import org.mule.api.MuleException;
import org.mule.config.dsl.AbstractModule;
import org.mule.config.dsl.Mule;
import org.mule.config.dsl.example.http.support.QueryData;
import org.mule.module.json.transformers.ObjectToJson;
import org.mule.transport.http.transformers.HttpRequestBodyToParamMap;

import static org.mule.config.dsl.expression.CoreExpr.payload;
import static org.mule.config.dsl.expression.ScriptingExpr.groovy;

/**
 * This example starts an embedded http server on 8080 port and, based
 * on request `id` parameter, loads a data and output it as json.
 *
 * @author porcelli
 */
public class OutputRequestDataIdAsJSON {

    public static void main(String... args) throws MuleException {
        Mule.startMuleContext(new AbstractModule() {
            @Override
            protected void configure() {
                flow("FormatOutputFlow")
                        .from("http://localhost:8080") //embedded http server that listen on 8080 port
                        .filter(groovy("message.payload != \"/favicon.ico\"")) //don't process the browser request for `favicon.ico`
                        .transformWith(HttpRequestBodyToParamMap.class) //transform http request body to a map
                        .invoke(QueryData.class) //invokes a java class
                            .methodAnnotatedWith(QueryData.MethodGetPerson.class) //defining the method that should be executed
                                .args(payload()) //with payload (map) as argument
                        .transformWith(ObjectToJson.class) //transform the payload to json
                        .messageProperties()
                            .put("Content-Type", "application/json"); //sets the output content-type to json
            }
        });
    }
}
