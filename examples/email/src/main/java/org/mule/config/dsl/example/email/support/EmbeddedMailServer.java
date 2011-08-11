/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.example.email.support;

import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.MessageHandlerFactory;
import org.subethamail.smtp.RejectException;
import org.subethamail.smtp.auth.EasyAuthenticationHandlerFactory;
import org.subethamail.smtp.auth.LoginFailedException;
import org.subethamail.smtp.auth.UsernamePasswordValidator;
import org.subethamail.smtp.server.SMTPServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Fake email server to mimic a send mail server (smtp protocol).
 *
 * @author porcelli
 */
public class EmbeddedMailServer {

    static SMTPServer smtpServer = null;

    /**
     * Starts the serer
     *
     * @param port the port to bind the server
     * @throws RuntimeException is server is already started
     */
    public static void start(int port) throws RuntimeException {
        if (smtpServer != null) {
            throw new RuntimeException("Server already started.");
        }
        smtpServer = new SMTPServer(new MyMessageHandlerFactory());
        smtpServer.setAuthenticationHandlerFactory(new EasyAuthenticationHandlerFactory(new UsernamePasswordValidator() {
            @Override
            public void login(String username, String password) throws LoginFailedException {
                if (!(username.equals("user1") && password.equals("secret"))) {
                    throw new LoginFailedException();
                }
            }
        }));
        smtpServer.setPort(port);
        smtpServer.start();
    }

    /**
     * Stops the server
     */
    public static void stop() {
        if (smtpServer != null) {
            smtpServer.stop();
            smtpServer = null;
        }
    }

    /**
     * Prints the sent messages at sysout.
     *
     * @author porcelli
     */
    public static class MyMessageHandlerFactory implements MessageHandlerFactory {
        @Override
        public MessageHandler create(MessageContext ctx) {
            return new Handler();
        }

        class Handler implements MessageHandler {

            @Override
            public void from(String from) throws RejectException {
                System.out.println("FROM:" + from);
            }

            @Override
            public void recipient(String recipient) throws RejectException {
                System.out.println("RECIPIENT:" + recipient);
            }

            @Override
            public void data(InputStream data) throws IOException {
                System.out.println("MAIL DATA");
                System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
                System.out.println(this.convertStreamToString(data));
                System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
            }

            @Override
            public synchronized void done() {
            }

            private String convertStreamToString(InputStream is) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();

                String line = null;
                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return sb.toString();
            }

        }
    }
}
