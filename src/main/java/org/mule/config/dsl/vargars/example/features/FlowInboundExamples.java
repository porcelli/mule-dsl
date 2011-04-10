/*
 * $Id: 20811 2011-03-30 16:05:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.vargars.example.features;

import org.mule.config.dsl.vargars.AbstractModule;
import org.mule.config.dsl.vargars.TempModel.*;
import org.mule.config.dsl.vargars.example.bookstore.business.CatalogService;
import org.mule.config.dsl.vargars.example.features.business.MyPojo;
import org.mule.config.dsl.vargars.example.features.business.MyTransformer;

import javax.xml.ws.Service;
import java.util.Map;

import static org.mule.config.dsl.vargars.TempModel.TimeUnit.SECONDS;

public class FlowInboundExamples {

    public static class FlowInbounds extends AbstractModule {
        @Override
        public void configure() {
            ConnectorBuilder myConnector = null;

            EndpointProcessor ftp_base = endpoint(FTP.ENDPOINT, name("myName")).poll(host("0.0.0.0").port(22).path("sss")).every(10);

            flow(name("MyFlow")).in(
                    //generic use
                    from(uri("salesforce://login(g1,g2);*query(g3,r1);")),

                    //generic use with process request
                    from(uri("salesforce://login(g1,g2);*query(g3,r1);"))
                            .processRequest(transformTo(String.class)),

                    //generic use with process request and response
                    from(uri("salesforce://login(g1,g2);*query(g3,r1);"))
                            .processRequest(transformTo(String.class))
                            .processResponse(transformTo(String.class)),

                    //generic use with process request and response + connector
                    from(uri("salesforce://login(g1,g2);*query(g3,r1);"))
                            .processRequest(transformTo(String.class))
                            .processResponse(transformTo(String.class))
                            .connectWith(myConnector),

                    //generic use with process request and response + connector reference
                    from(uri("salesforce://login(g1,g2);*query(g3,r1);"))
                            .processRequest(transformTo(String.class))
                            .processResponse(transformTo(String.class))
                            .connectWith("myConnectorReference"),

                    //protocol specific, that exposes just poll method
                    from(FTP.INBOUND).poll(host("0.0.0.0").port(22).path("sss/a.txt")).every(10, SECONDS),

                    //protocol specific, using an already defined endpoint (reference)
                    from(FTP.INBOUND).extend("ftp_base"),

                    //protocol specific, using an already defined endpoint
                    from(FTP.INBOUND).extend(ftp_base),

                    from(HTTP.INBOUND).listen(host("0.0.0.0").port(8777).path("services/catalog"))
                            .using(WS.INBOUND).with(CatalogService.class),

                    //protocol specific, now with a process request + connector
                    from(FTP.INBOUND).poll(host("0.0.0.0").port(22).path("sss")).every(10, SECONDS)
                            .then()
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class))
                            .connectWith(myConnector),

                    //protocol specific, now with a process request + connector
                    from(FTP.INBOUND).extend(ftp_base)
                            .then()
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class))
                            .connectWith(myConnector),

                    //protocol specific, now with a process request + connector reference
                    from(FTP.INBOUND).poll(host("0.0.0.0").port(22).path("sss")).every(10, SECONDS)
                            .then()
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class))
                            .connectWith("myConnectorReference"),

                    //different protocol specific
                    from(HTTP.INBOUND).listen(host("0.0.0.0").port(8080).path("sss/zzz")),

                    //protocol specific, now with a process request and response
                    from(HTTP.INBOUND).listen(host("0.0.0.0").port(8080).path("sss/zzz"))
                            .then()
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class))
                            .processResponse(transformWith(MyTransformer.class), transformTo(Map.class)),

                    //protocol specific, now with a process request and response + connector
                    from(HTTP.INBOUND).listen(host("0.0.0.0").port(8080).path("sss/zzz"))
                            .then()
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class))
                            .processResponse(transformWith(MyTransformer.class), transformTo(Map.class)),

                    //protocol specific, now with a process request and response
                    // + extended behavior with WS as an example of protocol specific extension.
                    from(HTTP.INBOUND).listen(host("0.0.0.0").port(8080).path("sss/zzz"))
                            .using(WS.INBOUND).with(Service.class)
                            .then()
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class))
                            .processResponse(transformWith(MyTransformer.class), transformTo(Map.class)),

                    //protocol specific, now with a process request and response + connector
                    // + extended behavior with WS as an example of protocol specific extension.
                    from(HTTP.INBOUND).listen(host("0.0.0.0").port(8080).path("sss/zzz"))
                            .using(WS.INBOUND).with(Service.class)
                            .then()
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class))
                            .processResponse(transformWith(MyTransformer.class), transformTo(Map.class))
                            .connectWith(myConnector),

                    //specific protocols can expose different methods, here http exposes poll
                    from(HTTP.INBOUND).poll(host("0.0.0.0").port(8080).path("sss/zzz"))
                            .every(2, TimeUnit.MINUTES)
                            .using(WS.INBOUND).with(Service.class)
                            .then()
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class))
                            .processResponse(transformWith(MyTransformer.class), transformTo(Map.class))
                            .connectWith(myConnector),

                    //jms specific protocol with an inplicit connector
                    from(JMS.INBOUND).queue("queueName"),

                    //jms specific protocol with an inplicit connector
                    from(JMS.INBOUND).topic("topicName"),

                    //jms specific protocol with an explicit connector
                    from(JMS.INBOUND).queue("queueName")
                            .then()
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class))
                            .connectWith(myConnector)

            ).process(
                    execute(MyPojo.class)
            );
        }
    }
}