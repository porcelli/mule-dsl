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
import org.mule.transport.email.GmailSmtpConnector;

import javax.mail.PasswordAuthentication;
import java.util.HashMap;
import java.util.Map;

/**
 * s
 * Example that shows how to configure and execute a
 * Mule DSL flow to send emails using GMAIL SMTP server.
 * <p/>
 * <b>Note:</b> you have to set your own gmail credentials to run this example.
 *
 * @author porcelli
 */
public class SendEmailUsingGMail {

    private static final String GMAIL_AUTH_USER = "your_user_name_here@gmail.com";
    private static final String GMAIL_AUTH_PWD = "your_password_here";
    private static final String SEND_MAIL_TO = "me@example.com";

    public static void main(String... args) throws MuleException {
        Mule myMule = new Mule(new MyGmailModule()); //creates a Mule instance
        myMule.start(); //start mule

        final Map<String, Object> properties = new HashMap<String, Object>(); //map that holds message properties
        properties.put("subject", "Look this new Mule cool feature!"); //email subject
        properties.put("address", SEND_MAIL_TO); //email address to send to
        final String mailContent = "Email content here"; //email content

        myMule.flow("SendEmail").process(mailContent, properties); //executes flow passing as arguments the mailContent alongside message properties
    }

    /**
     * Module that defines a flow that sends email using gmail.
     *
     * @author porcelli
     */
    public static class MyGmailModule extends AbstractModule {
        @Override
        protected void configure() {

            //Custom connector necessary to use gmail smtp server
            final GmailSmtpConnector gmail = new GmailSmtpConnector(null);
            gmail.setAuthenticator(new SMTPAuthenticator()); //defines the smtp authentication
            register("GmailConnector", gmail); //register the connector

            flow("SendEmail")
                    .filterBy(String.class)
                    .send("smtp://smtp.gmail.com?address=#[header:address]");
        }
    }

    /**
     * Simple class that authenticates a gmail user.
     *
     * @author porcelli
     */
    public static class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(GMAIL_AUTH_USER, GMAIL_AUTH_PWD);
        }
    }
}