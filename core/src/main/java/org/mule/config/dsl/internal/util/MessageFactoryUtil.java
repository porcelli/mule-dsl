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

/**
 * Utility class responsible to resolve and build type specific {@link MuleMessageFactory}s.
 *
 * @author porcelli
 */
public final class MessageFactoryUtil {

    private static Map<Class, Class> messageTypes = new HashMap<Class, Class>();
    private static Map<Class, Class<? extends MuleMessageFactory>> cachedMessageTypes = new HashMap<Class, Class<? extends MuleMessageFactory>>();

    /**
     * Pre-register all already know types and its message factory
     */
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

    /**
     * Returns the message factory associated with given payload type.
     *
     * @param payload     the payload
     * @param muleContext the mule context
     * @return the proper message factory
     */
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

    /**
     * Loads and register the pair into an available map of message factories.
     * <p/>
     * If type or message factory is not available, it just skips it.
     *
     * @param type               the type
     * @param messageFactoryType the message factory to given {@code type}
     */
    private static void loadType(String type, String messageFactoryType) {
        try {
            final Class clazz = Class.forName(type);
            loadType(clazz, messageFactoryType);
        } catch (ClassNotFoundException ex) {
        }
    }

    /**
     * Loads and register the pair into an available map of message factories.
     * <p/>
     * If message factory is not available, it just skips it.
     *
     * @param type               the clazz type
     * @param messageFactoryType the message factory to given {@code type}
     */
    private static void loadType(Class<?> type, String messageFactoryType) {
        try {
            final Class messageFactory = Class.forName(messageFactoryType);
            messageTypes.put(type, messageFactory);
        } catch (ClassNotFoundException ex) {
        }
    }

}
