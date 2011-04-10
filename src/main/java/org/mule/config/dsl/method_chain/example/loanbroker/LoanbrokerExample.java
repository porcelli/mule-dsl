/*
 * $Id: 20811 2011-03-30 16:05:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.method_chain.example.loanbroker;

import org.mule.config.dsl.method_chain.AbstractModule;
import org.mule.config.dsl.method_chain.TempModel.*;
import org.mule.config.dsl.method_chain.example.loanbroker.business.*;

public class LoanbrokerExample {

    //This Synchronous variant of loan broker example is modeled on the Enterprise integration Patterns book sample.
    //See: http://www.eaipatterns.com/ComposedMessagingExample.html
    public static class LoanBroker extends AbstractModule {
        @Override
        public void configure() {
            //The main loan broker flow that:
            //  i) Receives a customer request
            //  ii) Performs a lookup of the customer credit profile using a component binding
            //  iii) Determines the bank that should be used to request quotes using 'DefaultLender'
            //  iv) Uses an recipient list router to send quote requests to the selected banks and aggregates responses
            //  v) Selects the lowest quote from the list of quotes returned using the 'LowestQuoteProcessor'
            flow(name("loan-broker-sync"))
                   .from(ref("HttpUrlCustomerRequests"))
                    //This endpoint is used by a test case to post java objects directly
                   .from(ref("CustomerRequests"))
             .execute(DefaultLoanBroker.class) //TODO .bind(CreditAgencyService.class).redirectTo("CreditAgency")
                //TODO filterWith(SCRIPT),
             .execute(LowestQuoteProcessor.class);

            //The credit agency service will get the credit profile for a customer
            flow(name("TheCreditAgencyService"))
                   .from(HTTP.INBOUND).listen(host("localhost").port(18080).path("/mule/TheCreditAgencyService"))
                            .using(WS.INBOUND).with(DefaultCreditAgency.class);

            //These are mock bank services that represent remote bank loan services. One or more of these are
            //invoked synchronously by the loan broker.
            flow(name("bank1"))
                   .from(HTTP.INBOUND).listen(host("localhost").port(10080).path("/mule/TheBank1"))
                            .using(WS.INBOUND).with(Bank.class)
                            .asSingleton()
                            .setProperty("bankName", "Bank #1");

            flow(name("bank2"))
                   .from(HTTP.INBOUND).listen(host("localhost").port(20080).path("/mule/TheBank2"))
                            .using(WS.INBOUND).with(Bank.class)
                            .asSingleton()
                            .setProperty("bankName", "Bank #2");

            flow(name("bank3"))
                   .from(HTTP.INBOUND).listen(host("localhost").port(30080).path("/mule/TheBank3"))
                            .using(WS.INBOUND).with(Bank.class)
                            .asSingleton()
                            .setProperty("bankName", "Bank #3");

            flow(name("bank4"))
                   .from(HTTP.INBOUND).listen(host("localhost").port(40080).path("/mule/TheBank4"))
                            .using(WS.INBOUND).with(Bank.class)
                            .asSingleton()
                            .setProperty("bankName", "Bank #4");

            flow(name("bank5"))
                   .from(HTTP.INBOUND).listen(host("localhost").port(50080).path("/mule/TheBank5"))
                            .using(WS.INBOUND).with(Bank.class)
                            .asSingleton()
                            .setProperty("bankName", "Bank #5");

            //Global Endpoints
            endpoint(HTTP.INBOUND, name("CustomerRequests")).listen(host("localhost").port(11080))
                    .then()
                    .processRequest(pipeline().transformWith(ByteArrayToObject.class));

            endpoint(HTTP.INBOUND, name("HttpUrlCustomerRequests"))
                    .listen(host("0.0.0.0").port(11081))
                    .then()
                    .processRequest(
                            //Translate request params into properties map
                            pipeline().transformWith(BodyToParameterMapTransformer.class)/*,
                           TODO transformWith(SCRIPT)*/)
                    .processResponse(
                            //If there's an exception payload, return a short message to the requestor, otherwise original payload
                            //TODO transformerWith(SCRIPT),
                            pipeline().transformWith(ObjectToStringTransformer.class));

            endpoint(HTTP.ENDPOINT, name("CreditAgency"))
                    .listen(host("localhost").port(18080).path("mule/TheCreditAgencyService?method=getCreditProfile"))
                    .using(WS.ENDPOINT).with(CreditAgencyService.class);

            endpoint(HTTP.ENDPOINT, name("Bank1"))
                    .listen(host("localhost").port(10080).path("mule/TheBank1?method=getLoanQuote"))
                    .using(WS.ENDPOINT).with(BankService.class);

            endpoint(HTTP.ENDPOINT, name("Bank2"))
                    .listen(host("localhost").port(20080).path("mule/TheBank2?method=getLoanQuote"))
                    .using(WS.ENDPOINT).with(BankService.class);

            endpoint(HTTP.ENDPOINT, name("Bank3"))
                    .listen(host("localhost").port(30080).path("mule/TheBank3?method=getLoanQuote"))
                    .using(WS.ENDPOINT).with(BankService.class);

            endpoint(HTTP.ENDPOINT, name("Bank4"))
                    .listen(host("localhost").port(40080).path("mule/TheBank4?method=getLoanQuote"))
                    .using(WS.ENDPOINT).with(BankService.class);

            endpoint(HTTP.ENDPOINT, name("Bank5"))
                    .listen(host("localhost").port(50080).path("mule/TheBank5?method=getLoanQuote"))
                    .using(WS.ENDPOINT).with(BankService.class);
        }
    }
}