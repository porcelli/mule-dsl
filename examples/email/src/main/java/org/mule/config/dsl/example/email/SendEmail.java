/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.example.email;

import org.mule.api.MuleException;
import org.mule.config.dsl.AbstractModule;
import org.mule.config.dsl.Mule;
import org.mule.config.dsl.example.email.support.EmbeddedMailServer;

import java.util.HashMap;
import java.util.Map;

/**
 * Example that shows how to configure and execute a
 * Mule DSL flow to send emails using the SMTP protocol.
 * <p/>
 * <b>Note:</b> this example uses an embedded smtp server, just to answer for smtp protocol.
 *
 * @author porcelli
 */
public class SendEmail {

    public static void main(String... args) throws MuleException {
        final int port = 6060; //defines fake mail port
        EmbeddedMailServer.start(port);  // start fake mail server
        Mule.startMuleContext(new MyEmailModule()); // start mule

        final Map<String, Object> properties = new HashMap<String, Object>(); //map that holds message properties

        properties.put("host", "localhost"); //smtp host server
        properties.put("port", port); //smtp port
        properties.put("user", "user1"); //username
        properties.put("password", "secret"); //password

        properties.put("subject", "Look this new Mule cool feature!"); //email subject
        properties.put("address", "mule_users@company.com"); //email address to send to
        final String mailContent = "Send emails direct from Mule DSL!"; //email content

        Mule.process("SendEmail", mailContent, properties); //executes flow passing as arguments the mailContent alongside message properties
    }

    /**
     * Module that defines a flow that sends email using a smtp server.
     *
     * @author porcelli
     */
    public static class MyEmailModule extends AbstractModule {
        @Override
        protected void configure() {
            flow("SendEmail")
                    .filterBy(String.class)
                    .send("smtp://#[header:user]:#[header:password]@#[header:host]:#[header:port]?address=#[header:address]");
        }
    }
}