/*
 * $Id: 20811 2011-03-30 16:05:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.approach4.example.loanbroker;

import org.mule.config.dsl.approach4.AbstractModule;
import org.mule.config.dsl.approach4.example.bookstore.business.*;
import org.mule.config.dsl.approach4.example.loanbroker.business.CreditAgencyService;
import org.mule.config.dsl.approach4.example.loanbroker.business.DefaultLoanBroker;

public class LoanbrokerExample {

    public static class LoanBroker extends AbstractModule {
        @Override
        public void configure() {
            endpoint(HTTP.ENDPOINT, name("CreditAgency"))
                    .poll(host("localhost").port(18080).path("mule/TheCreditAgencyService?method=getCreditProfile")).every(0)
                    .using(WS.ENDPOINT).with(CreditAgencyService.class);

            flow().in(
                    from("HttpUrlCustomerRequests"),
                    from("CustomerRequests")
            ).process(
                    execute(DefaultLoanBroker.class).bind(CreditAgencyService.class)
                            .redirectTo("CreditAgency")
            );

            flow().in(
                    from("HttpUrlCustomerRequests"),
                    from("CustomerRequests")
            ).process(
                    execute(DefaultLoanBroker.class).bind(CreditAgencyService.class)
                            .redirectTo(HTTP.INBOUND)
                            .poll(host("localhost").port(18080).path("mule/TheCreditAgencyService?method=getCreditProfile")).every(0)
                            .using(WS.INBOUND).with(CreditAgencyService.class)
            );

        }
    }
}