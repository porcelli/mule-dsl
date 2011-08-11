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
import org.mule.module.xml.transformer.ObjectToXml;
import org.mule.transport.http.transformers.HttpRequestBodyToParamMap;

import static org.mule.config.dsl.expression.ScriptingExpr.groovy;

/**
 * Example that starts an embedded http server on 8080 port and
 * outputs the input request as XML.
 *
 * @author porcelli
 */
public class OutputInputParamsAsXML {

    public static void main(String... args) throws MuleException {
        Mule myMule = Mule.newInstance(new AbstractModule() { //creates a new mule instance using an anonymous inner AbstractModule based class
            @Override
            protected void configure() {
                flow("OutputParamsAsXML")
                        .from("http://localhost:8080") //embedded http server that listen on 8080 port
                        .filter(groovy("message.payload != \"/favicon.ico\"")) //don't process the browser request for `favicon.ico`
                        .transformWith(HttpRequestBodyToParamMap.class) //transform http request body to a map
                        .transformWith(ObjectToXml.class) //transform the payload (map) to xml
                        .messageProperties()
                            .put("Content-Type", "text/xml"); //sets the output content-type to xml
            }
        });

        myMule.start(); //start mule
    }
}
