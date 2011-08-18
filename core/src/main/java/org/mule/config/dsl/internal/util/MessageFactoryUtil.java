/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal.util;

import org.mule.api.MuleContext;
import org.mule.api.transport.MuleMessageFactory;
import org.mule.config.dsl.ConfigurationException;
import org.mule.transport.DefaultMuleMessageFactory;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public final class MessageFactoryUtil {

    private static Map<Class, Class> messageTypes = new HashMap<Class, Class>();
    private static Map<Class, Class<? extends MuleMessageFactory>> cachedMessageTypes = new HashMap<Class, Class<? extends MuleMessageFactory>>();

    static {
        loadType(File.class, "org.mule.transport.file.FileMuleMessageFactory");
        loadType(InputStream.class, "org.mule.transport.file.FileMuleMessageFactory");
        loadType("org.apache.commons.net.ftp.FTPFile", "org.mule.transport.ftp.FtpMuleMessageFactory");
        loadType("org.mule.transport.http.HttpRequest", "org.mule.transport.http.HttpMuleMessageFactory");
        loadType("org.apache.commons.httpclient.HttpMethod", "org.mule.transport.http.HttpMuleMessageFactory");
        loadType("javax.jms.Message", "org.mule.transport.jms.JmsMuleMessageFactory");
        loadType("javax.servlet.http.HttpServletRequest", "org.mule.transport.servlet.ServletMuleMessageFactory");
        loadType("java.net.DatagramPacket", "org.mule.transport.udp.UdpMuleMessageFactory");
        loadType("org.jivesoftware.smack.packet.Packet", "org.mule.transport.xmpp.XmppMuleMessageFactory");
    }

    private MessageFactoryUtil() {
    }

    private static void loadType(String sourceType, String messageFactoryType) {
        try {
            final Class clazz = Class.forName(sourceType);
            loadType(clazz, messageFactoryType);
        } catch (ClassNotFoundException ex) {
        }
    }

    private static void loadType(Class sourceType, String messageFactoryType) {
        try {
            final Class ftpMessageFactory = Class.forName(messageFactoryType);
            messageTypes.put(sourceType, ftpMessageFactory);
        } catch (ClassNotFoundException ex) {
        }
    }


    public static MuleMessageFactory getMessageFactory(Object payload, MuleContext muleContext) {
        if (!cachedMessageTypes.containsKey(payload.getClass())) {
            boolean found = false;
            for (Map.Entry<Class, Class> messageType : messageTypes.entrySet()) {
                if (messageType.getKey().isAssignableFrom(payload.getClass())) {
                    cachedMessageTypes.put(payload.getClass(), messageType.getValue());
                    found = true;
                    break;
                }
            }
            if (!found) {
                cachedMessageTypes.put(payload.getClass(), null);
            }
        }

        Class<? extends MuleMessageFactory> messageFactoryClass = cachedMessageTypes.get(payload.getClass());
        if (messageFactoryClass == null) {
            return new DefaultMuleMessageFactory(muleContext);
        }

        try {
            Constructor<? extends MuleMessageFactory> x = messageFactoryClass.getDeclaredConstructor(MuleContext.class);
            return x.newInstance(muleContext);
        } catch (Exception e) {
            throw new ConfigurationException("Can't create message factory.", e);
        }
    }
}
