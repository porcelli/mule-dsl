/*
 * $Id: 20811 2011-03-30 16:05:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.aproach2.example.features;

import org.mule.config.dsl.aproach2.AbstractMethodModule;
import org.mule.config.dsl.aproach2.example.features.business.MyPojo;
import org.mule.config.dsl.aproach2.example.features.business.MyTransformer;

import javax.xml.ws.Service;
import java.util.Map;

import static org.mule.config.dsl.aproach2.AbstractMethodModule.TimeUnit.SECONDS;

public class ProtocolExamples {

    public static class BookStore extends AbstractMethodModule {
        @Override
        public void configure() {
            Connector myConnector = null;

            newFlow("MyFlow").in(
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
                            .connectUsing(myConnector),

                    //generic use with process request and response + connector reference
                    from("salesforce://login(g1,g2);*query(g3,r1);")
                            .processRequest(transformTo(String.class), filter())
                            .processResponse(transformTo(String.class), filter())
                            .connectUsing("myConnectorReference"),

                    //protocol specific, that exposes just poll method
                    from(FTP.poll(host("0.0.0.0").port(22).path("sss/a.txt")).every(10, SECONDS)),

                    //protocol specific, now with a process request + connector
                    from(FTP.poll(host("0.0.0.0").port(22).path("sss")).every(10, SECONDS))
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class), filter())
                            .connectUsing(myConnector),

                    //protocol specific, now with a process request + connector reference
                    from(FTP.poll(host("0.0.0.0").port(22).path("sss")).every(10, SECONDS))
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class), filter())
                            .connectUsing("myConnectorReference"),

                    //different protocol specific
                    from(HTTP.listen(host("0.0.0.0").port(8080).path("sss/zzz"))),

                    //protocol specific, now with a process request and response
                    from(HTTP.listen(host("0.0.0.0").port(8080).path("sss/zzz"))
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class), filter())
                            .processResponse(transformWith(MyTransformer.class), transformTo(Map.class), filter())),

                    //protocol specific, now with a process request and response + connector
                    from(HTTP.listen(host("0.0.0.0").port(8080).path("sss/zzz"))
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class), filter())
                            .processResponse(transformWith(MyTransformer.class), transformTo(Map.class), filter())),

                    //protocol specific, now with a process request and response
                    // + extended behavior using CXF as an example of protocol specific extension.
                    from(HTTP.listen(host("0.0.0.0").port(8080).path("sss/zzz"))
                            .using(CXF.class).with(Service.class)
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class), filter())
                            .processResponse(transformWith(MyTransformer.class), transformTo(Map.class), filter())),

                    //protocol specific, now with a process request and response + connector
                    // + extended behavior using CXF as an example of protocol specific extension.
                    from(HTTP.listen(host("0.0.0.0").port(8080).path("sss/zzz"))
                            .using(CXF.class).with(Service.class)
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class), filter())
                            .processResponse(transformWith(MyTransformer.class), transformTo(Map.class), filter()))
                            .connectUsing(myConnector),

                    //specific protocols can expose different methods, here http exposes poll
                    from(HTTP.poll(host("0.0.0.0").port(8080).path("sss/zzz"))
                            .every(2, TimeUnit.MINUTES)
                            .using(CXF.class).with(Service.class)
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class), filter())
                            .processResponse(transformWith(MyTransformer.class), transformTo(Map.class), filter()))
                            .connectUsing(myConnector),

                    //jms specific protocol with an inplicit connector
                    from(JMS.queue("queueName")),

                    //jms specific protocol with an inplicit connector
                    from(JMS.topic("topicName")),

                    //jms specific protocol with an explicit connector
                    from(JMS.queue("queueName")
                            .connectUsing(myConnector)
                            .processRequest(transformWith(MyTransformer.class), transformTo(Map.class), filter()))

            ).process(
                    execute(MyPojo.class),
                    filter()
            );
        }


    }
}