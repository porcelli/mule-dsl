/*
 * $Id: 20811 2011-03-30 16:05:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.vargars.example.loanbroker;

import org.mule.config.dsl.vargars.AbstractModule;
import org.mule.config.dsl.vargars.TempModel.*;
import org.mule.config.dsl.vargars.example.loanbroker.business.*;

public class LoanbrokerExample {

    public static class LoanBroker extends AbstractModule {
        @Override
        public void configure() {

            flow(name("loan-broker-sync")).in(
                    from(ref("HttpUrlCustomerRequests")),
                    from(ref("CustomerRequests"))
            ).process(
                    execute(DefaultLoanBroker.class).bind(CreditAgencyService.class)
                            .redirectTo("CreditAgency"),
                    //TODO filterWith(SCRIPT),
                    execute(LowestQuoteProcessor.class)
            );

            flow(name("TheCreditAgencyService")).in(
                    from(HTTP.INBOUND).listen(host("localhost").port(18080).path("/mule/TheCreditAgencyService"))
                            .using(WS.INBOUND).with(DefaultCreditAgency.class)
            );

            flow(name("bank1")).in(
                    from(HTTP.INBOUND).listen(host("localhost").port(10080).path("/mule/TheBank1"))
                            .using(WS.INBOUND).with(Bank.class)
                            .asSingleton()
                            .setProperty("bankName", "Bank #1")
            );

            flow(name("bank2")).in(
                    from(HTTP.INBOUND).listen(host("localhost").port(20080).path("/mule/TheBank2"))
                            .using(WS.INBOUND).with(Bank.class)
                            .asSingleton()
                            .setProperty("bankName", "Bank #2")
            );

            flow(name("bank3")).in(
                    from(HTTP.INBOUND).listen(host("localhost").port(30080).path("/mule/TheBank3"))
                            .using(WS.INBOUND).with(Bank.class)
                            .asSingleton()
                            .setProperty("bankName", "Bank #3")
            );

            flow(name("bank4")).in(
                    from(HTTP.INBOUND).listen(host("localhost").port(40080).path("/mule/TheBank4"))
                            .using(WS.INBOUND).with(Bank.class)
                            .asSingleton()
                            .setProperty("bankName", "Bank #4")
            );

            flow(name("bank5")).in(
                    from(HTTP.INBOUND).listen(host("localhost").port(50080).path("/mule/TheBank5"))
                            .using(WS.INBOUND).with(Bank.class)
                            .asSingleton()
                            .setProperty("bankName", "Bank #5")
            );

            endpoint(HTTP.INBOUND, name("CustomerRequests")).listen(host("localhost").port(11080))
                    .then()
                    .processRequest(transformWith(ByteArrayToObject.class));

            endpoint(HTTP.INBOUND, name("HttpUrlCustomerRequests"))
                    .listen(host("0.0.0.0").port(11081))
                    .then()
                    .processRequest(
                            transformWith(BodyToParameterMapTransformer.class)/*,
                           TODO transformWith(SCRIPT)*/)
                    .processResponse(
                            //TODO transformerWith(SCRIPT),
                            transformWith(ObjectToStringTransformer.class));

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