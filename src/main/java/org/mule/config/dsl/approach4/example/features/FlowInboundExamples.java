/*
 * $Id: 20811 2011-03-30 16:05:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.approach4.example.features;

import org.mule.config.dsl.approach4.AbstractModule;
import org.mule.config.dsl.approach4.example.bookstore.business.CatalogService;
import org.mule.config.dsl.approach4.example.features.business.MyPojo;
import org.mule.config.dsl.approach4.example.features.business.MyTransformer;

import javax.xml.ws.Service;
import java.util.Map;

import static org.mule.config.dsl.approach4.AbstractModule.TimeUnit.SECONDS;

public class FlowInboundExamples {

    public static class FlowInbounds extends AbstractModule {
        @Override
        public void configure() {
            ConnectorBuilder myConnector = null;

            EndpointProcessor ftp_base = endpoint(FTP.ENDPOINT, name("myName")).poll(host("0.0.0.0").port(22).path("sss")).every(10);

            flow(name("MyFlow")).in(
                    //generic use
                    from("salesforce://login(g1,g2);*query(g3,r1);"),

                    //generic use with process request
                    from("salesforce://login(g1,g2);*query(g3,r1);")
                            .processRequest(transformTo(String.class), filter()),

                    //generic use with process request and response
                    from("salesforce://login(g1,g2);*query(g3,r1);")
                            .processRequest(transformTo(String.class), filter())
                            .processResponse(transformTo(String.class), filter()),

                    //generic use with process request and response + connector
                    from("salesforce://login(g1,g2);*query(g3,r1);")
                            .processRequest(transformTo(String.class), filter())
                            .processResponse(transformTo(String.class), filter())
                            .connectWith(myConnector),

                    //generic use with process request and response + connector reference
                    from("salesforce://login(g1,g2);*query(g3,r1);")
                            .processRequest(transformTo(String.class), filter())
                            .processResponse(transformTo(String.class), filter())
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
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class), filter())
                            .connectWith(myConnector),

                    //protocol specific, now with a process request + connector
                    from(FTP.INBOUND).extend(ftp_base)
                            .then()
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class), filter())
                            .connectWith(myConnector),

                    //protocol specific, now with a process request + connector reference
                    from(FTP.INBOUND).poll(host("0.0.0.0").port(22).path("sss")).every(10, SECONDS)
                            .then()
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class), filter())
                            .connectWith("myConnectorReference"),

                    //different protocol specific
                    from(HTTP.INBOUND).listen(host("0.0.0.0").port(8080).path("sss/zzz")),

                    //protocol specific, now with a process request and response
                    from(HTTP.INBOUND).listen(host("0.0.0.0").port(8080).path("sss/zzz"))
                            .then()
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class), filter())
                            .processResponse(transformWith(MyTransformer.class), transformTo(Map.class), filter()),

                    //protocol specific, now with a process request and response + connector
                    from(HTTP.INBOUND).listen(host("0.0.0.0").port(8080).path("sss/zzz"))
                            .then()
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class), filter())
                            .processResponse(transformWith(MyTransformer.class), transformTo(Map.class), filter()),

                    //protocol specific, now with a process request and response
                    // + extended behavior with WS as an example of protocol specific extension.
                    from(HTTP.INBOUND).listen(host("0.0.0.0").port(8080).path("sss/zzz"))
                            .using(WS.INBOUND).with(Service.class)
                            .then()
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class), filter())
                            .processResponse(transformWith(MyTransformer.class), transformTo(Map.class), filter()),

                    //protocol specific, now with a process request and response + connector
                    // + extended behavior with WS as an example of protocol specific extension.
                    from(HTTP.INBOUND).listen(host("0.0.0.0").port(8080).path("sss/zzz"))
                            .using(WS.INBOUND).with(Service.class)
                            .then()
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class), filter())
                            .processResponse(transformWith(MyTransformer.class), transformTo(Map.class), filter())
                            .connectWith(myConnector),

                    //specific protocols can expose different methods, here http exposes poll
                    from(HTTP.INBOUND).poll(host("0.0.0.0").port(8080).path("sss/zzz"))
                            .every(2, TimeUnit.MINUTES)
                            .using(WS.INBOUND).with(Service.class)
                            .then()
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class), filter())
                            .processResponse(transformWith(MyTransformer.class), transformTo(Map.class), filter())
                            .connectWith(myConnector),

                    //jms specific protocol with an inplicit connector
                    from(JMS.INBOUND).queue("queueName"),

                    //jms specific protocol with an inplicit connector
                    from(JMS.INBOUND).topic("topicName"),

                    //jms specific protocol with an explicit connector
                    from(JMS.INBOUND).queue("queueName")
                            .then()
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class), filter())
                            .connectWith(myConnector)

            ).process(
                    execute(MyPojo.class),
                    filter()
            );
        }
    }
}