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
import org.mule.module.xml.transformer.ObjectToXml;

import static com.google.inject.name.Names.named;
import static org.mule.config.dsl.expression.ScriptingExpr.groovy;


/**
 * This example starts an embedded http server on 8080 port,
 * invokes a java class to load a random data and format
 * its output based on request `format` parameter.
 *
 * @author porcelli
 */
public class FormatOutputBasedOnRequestParam {

    public static void main(String... args) throws MuleException {
        Mule myMule = new Mule(new AbstractModule() { //creates a new mule instance using an anonymous inner AbstractModule based class
            @Override
            protected void configure() {
                flow("myFormatBasedOutputFlow")
                        .from("http://localhost:8080") //embedded http server that listen on 8080 port
                        .filter(groovy("message.payload != \"/favicon.ico\"")) //don't process the browser request for `favicon.ico`
                        .invoke(QueryData.class) //invokes a java class
                            .methodAnnotatedWith(named("randomData")) //defining the method that should be executed
                                .withoutArgs() //without args
                        .choice() //router based on flow content
                            .when(groovy("message.getInboundProperty(\"format\", \"xml\").toLowerCase() == \"xml\"")) //if format is xml or omitted (default)
                                .transformWith(ObjectToXml.class) //transform payload (class Person) to XML
                                .messageProperties()
                                    .put("Content-Type", "text/xml") //sets the output content-type to xml
                                .endMessageProperties()
                            .when(groovy("message.getInboundProperty(\"format\", \"\").toLowerCase() == \"json\"")) //if format is json
                                .transformWith(ObjectToJson.class) //transform payload (class Person) to JSON
                                .messageProperties()
                                    .put("Content-Type", "application/json") //sets the output content-type to json
                                .endMessageProperties()
                            .otherwise() //format param is not supported
                                .invoke(QueryData.class).methodName("error").withoutArgs() //invokes the "error" method
                                .messageProperties()
                                    .put("Content-Type", "text/plain") //sets the output content-type to text
                                .endMessageProperties()
                        .endChoice();

                bind(QueryData.class).toInstance(new QueryData()); //guice bind
            }
        });

        myMule.start(); //start mule
    }
}
