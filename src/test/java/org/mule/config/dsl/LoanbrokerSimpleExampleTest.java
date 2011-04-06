/*
 * $Id: 20811 2011-03-30 16:05:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl;

public class LoanbrokerSimpleExampleTest {
    public static void main(String... args) {
    }

    public static class LoanbrokerSimple extends AbstractModule {
        @Override
        public void configure() {

            newFlow("loan-broker-sync")
                .sources("HttpUrlCustomerRequests", "CustomerRequests")
                .execute(DefaultLoanBroker.class)
                .filterBy("payload.lenders.endpoint!=null")
                    .evaluatedBy(ExpressionEvaluator.Groovy)
                .sendRecipientList("payload.lenders.endpoint")
                    .evaluatedBy(ExpressionEvaluator.Groovy)
                .execute(LowestQuoteProcessor.class);

            newSimpleService("TheCreditAgencyService")
                .type(JAX_WS.class)
                    .listen("http://localhost:18080/mule/TheCreditAgencyService")
                .execute(DefaultCreditAgency.class);

            newSimpleService("bank1")
                .type(JAX_WS.class)
                    .listen("http://localhost:10080/mule/TheBank1")
                .execute(DefaultCreditAgency.class).asSingleton()
                    .setProperty("bankName", "Bank #1");

            newSimpleService("bank2")
                .type(JAX_WS.class)
                    .listen("http://localhost:10080/mule/TheBank2")
                .execute(DefaultCreditAgency.class).asSingleton()
                    .setProperty("bankName", "Bank #2");

            newSimpleService("bank3")
                .type(JAX_WS.class)
                    .listen("http://localhost:10080/mule/TheBank3")
                .execute(DefaultCreditAgency.class).asSingleton()
                    .setProperty("bankName", "Bank #3");

            newSimpleService("bank4")
                .type(JAX_WS.class)
                    .listen("http://localhost:10080/mule/TheBank4")
                .execute(DefaultCreditAgency.class).asSingleton()
                    .setProperty("bankName", "Bank #4");

            newSimpleService("bank5")
                .type(JAX_WS.class)
                    .listen("http://localhost:10080/mule/TheBank5")
                .execute(DefaultCreditAgency.class).asSingleton()
                    .setProperty("bankName", "Bank #5");


            newEndpoint("CustomerRequests")
                .protocol(HTTP.class)
                    .address("http://localhost:11080")
                .response()
                    .transformWith(ByteArrayToObject.class);
        }
    }
}