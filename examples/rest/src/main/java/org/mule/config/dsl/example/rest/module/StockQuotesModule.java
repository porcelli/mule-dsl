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
import org.mule.config.dsl.example.rest.support.TweetIt;
import org.mule.module.xml.transformer.XmlToObject;
import org.mule.module.xml.transformer.XsltTransformer;
import org.mule.transformer.codec.XmlEntityDecoder;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

import static org.mule.config.dsl.expression.CoreExpr.string;

/**
 * Module that defines two different flows that executes a stock quote rest service.
 *
 * @author porcelli
 */
public class StockQuotesModule extends AbstractModule {
    @Override
    protected void configure() {
        flow("GetQuote")
                .transform(string("symbol=#[payload]")) //transform payload to expected service format
                .messageProperties()
                    .put("Content-Type", "application/x-www-form-urlencoded") //define the content type to rest app
                .send("http://www.webservicex.net/stockquote.asmx/GetQuote?method=POST") //uri of rest service + http verb to be used (post)
                .transformWith(XmlEntityDecoder.class) //transform plain text result into a real xml format
                .transformWith(XsltTransformer.class) //transform, using xslt (guice bind), the payload to a xml format of a StockQuote class
                .transformWith(XmlToObject.class); //last, but not least, transform payload (well formated xml) to an instance of StockQuote class

        XsltTransformer xslt = new XsltTransformer(); //defines the xslt transformer configuration
        xslt.setXslt(classpath("rest-stock.xsl").getAsString()); //load xslt content from classpath file
        bind(XsltTransformer.class).toInstance(xslt); //binds XsltTransformer to a specific intance

        flow("TweetStockQuote")
                .executeFlow("GetQuote") //executes the `GetQuote` flow
                .invoke(TweetIt.class); //tweets the payload (StockQuote instance)

        bind(Twitter.class).toInstance(new TwitterFactory().getInstance()); //binds a twitter instance
    }
}
