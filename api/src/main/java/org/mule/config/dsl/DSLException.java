/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.mule.api.MuleException;
import org.mule.config.DefaultMuleConfiguration;
import org.mule.config.ExceptionHelper;
import org.mule.config.i18n.CoreMessages;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.MessageFactory;
import org.mule.util.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class DSLException extends RuntimeException {

    private static final long serialVersionUID = 2340975800429988301L;
    private final Map info = new HashMap();
    private int errorCode = -1;
    private String message = null;
    private Message i18nMessage;

    /**
     * @param message the exception message
     */
    public DSLException(final Message message) {
        super();
        setMessage(message);
    }

    /**
     * @param message the exception message
     */
    public DSLException(final String message) {
        super();
        setMessage(message);
    }

    /**
     * @param message the exception message
     * @param cause   the exception that cause this exception to be thrown
     */
    public DSLException(final Message message, final Throwable cause) {
        super(ExceptionHelper.unwrap(cause));
        setMessage(message);
    }

    /**
     * @param message the exception message
     * @param cause   the exception that cause this exception to be thrown
     */
    public DSLException(final String message, final Throwable cause) {
        super(ExceptionHelper.unwrap(cause));
        setMessage(message);
    }


    /**
     * @param cause the exception that cause this exception to be thrown
     */
    public DSLException(final Throwable cause) {
        super(ExceptionHelper.unwrap(cause));
        if (cause != null) {
            setMessage(MessageFactory.createStaticMessage(cause.getMessage() + " ("
                    + cause.getClass().getName() + ")"));
        } else {
            initialise();
        }
    }

    protected void setMessage(final Message message) {
        initialise();
        this.message = message.getMessage();
        i18nMessage = message;
    }

    protected void setMessage(final String message) {
        initialise();
        this.message = message;
        if (i18nMessage == null) {
            i18nMessage = MessageFactory.createStaticMessage(message);
        }
    }

    public int getExceptionCode() {
        return errorCode;
    }

    public Message getI18nMessage() {
        return i18nMessage;
    }

    public int getMessageCode() {
        return (i18nMessage == null ? 0 : i18nMessage.getCode());
    }

    public void addInfo(final String name, final Object info) {
        this.info.put(name, info);
    }

    protected void appendMessage(final String s) {
        message += s;
    }

    protected void prependMessage(final String s) {
        message = message + ". " + s;
    }

    protected void setExceptionCode(final int code) {
        errorCode = code;
    }

    @Override
    public final String getMessage() {
        return message;
    }

    protected void initialise() {
        setExceptionCode(ExceptionHelper.getErrorCode(getClass()));
        final String javadoc = ExceptionHelper.getJavaDocUrl(getClass());
        final String doc = ExceptionHelper.getDocUrl(getClass());
        if (javadoc != null) {
            // info.put(ClassHelper.getClassName(getClass()) + " JavaDoc", javadoc);
            info.put("JavaDoc", javadoc);
        }
        if (doc != null) {
            // info.put(ClassHelper.getClassName(getClass()) + " Other Doc", doc);
            info.put("Other Doc", doc);
        }
    }

    public String getDetailedMessage() {
        if (DefaultMuleConfiguration.verboseExceptions) {
            return getVerboseMessage();
        } else {
            return getSummaryMessage();
        }
    }

    public String getVerboseMessage() {
        final MuleException e = ExceptionHelper.getRootMuleException(this);
        if (!e.equals(this)) {
            return getMessage();
        }
        final StringBuffer buf = new StringBuffer(1024);
        buf.append(org.apache.commons.lang.SystemUtils.LINE_SEPARATOR).append(StringUtils.repeat('*', 80)).append(
                org.apache.commons.lang.SystemUtils.LINE_SEPARATOR);
        buf.append("Message               : ").append(message).append(org.apache.commons.lang.SystemUtils.LINE_SEPARATOR);
        buf.append("Type                  : ")
                .append(getClass().getName())
                .append(org.apache.commons.lang.SystemUtils.LINE_SEPARATOR);
        buf.append("Code                  : ").append("MULE_ERROR-").append(
                getExceptionCode() + getMessageCode()).append(org.apache.commons.lang.SystemUtils.LINE_SEPARATOR);
        // buf.append("Msg Code :
        // ").append(getMessageCode()).append(SystemUtils.LINE_SEPARATOR);

        final Map info = ExceptionHelper.getExceptionInfo(this);
        for (final Iterator iterator = info.keySet().iterator(); iterator.hasNext(); ) {
            final String s = (String) iterator.next();
            final int pad = 22 - s.length();
            buf.append(s);
            if (pad > 0) {
                buf.append(StringUtils.repeat(' ', pad));
            }
            buf.append(": ");
            buf.append(info.get(s)).append(org.apache.commons.lang.SystemUtils.LINE_SEPARATOR);
        }

        // print exception stack
        buf.append(StringUtils.repeat('*', 80)).append(org.apache.commons.lang.SystemUtils.LINE_SEPARATOR);
        buf.append(CoreMessages.exceptionStackIs()).append(org.apache.commons.lang.SystemUtils.LINE_SEPARATOR);
        buf.append(org.apache.commons.lang.StringUtils.abbreviate(ExceptionHelper.getExceptionStack(this), 5000));

        buf.append(StringUtils.repeat('*', 80)).append(org.apache.commons.lang.SystemUtils.LINE_SEPARATOR);
        buf.append(CoreMessages.rootStackTrace()).append(org.apache.commons.lang.SystemUtils.LINE_SEPARATOR);
        final Throwable root = ExceptionHelper.getRootException(this);
        final StringWriter w = new StringWriter();
        final PrintWriter p = new PrintWriter(w);
        root.printStackTrace(p);
        buf.append(org.apache.commons.lang.StringUtils.abbreviate(w.toString(), 5000)).append(org.apache.commons.lang.SystemUtils.LINE_SEPARATOR);
        buf.append(StringUtils.repeat('*', 80)).append(org.apache.commons.lang.SystemUtils.LINE_SEPARATOR);

        return buf.toString();
    }

    public String getSummaryMessage() {
        final MuleException e = ExceptionHelper.getRootMuleException(this);
        if (!e.equals(this)) {
            return getMessage();
        }
        final StringBuffer buf = new StringBuffer(1024);
        buf.append(org.apache.commons.lang.SystemUtils.LINE_SEPARATOR).append(StringUtils.repeat('*', 80)).append(
                org.apache.commons.lang.SystemUtils.LINE_SEPARATOR);
        buf.append("Message               : ").append(message).append(org.apache.commons.lang.SystemUtils.LINE_SEPARATOR);
        buf.append("Code                  : ").append("MULE_ERROR-").append(
                getExceptionCode() + getMessageCode()).append(org.apache.commons.lang.SystemUtils.LINE_SEPARATOR);
        // print exception stack
        buf.append(StringUtils.repeat('-', 80)).append(org.apache.commons.lang.SystemUtils.LINE_SEPARATOR);
        buf.append(CoreMessages.exceptionStackIs()).append(org.apache.commons.lang.SystemUtils.LINE_SEPARATOR);
        buf.append(org.apache.commons.lang.StringUtils.abbreviate(ExceptionHelper.getExceptionStack(this), 5000));

        buf.append(StringUtils.repeat('-', 80)).append(org.apache.commons.lang.SystemUtils.LINE_SEPARATOR);
        buf.append(CoreMessages.rootStackTrace()).append(org.apache.commons.lang.SystemUtils.LINE_SEPARATOR);
        final Throwable root = ExceptionHelper.getRootException(this);
        final Throwable rootSummary = ExceptionHelper.summarise(root, 3);
        final StringWriter w = new StringWriter();
        final PrintWriter p = new PrintWriter(w);
        rootSummary.printStackTrace(p);
        buf.append(org.apache.commons.lang.StringUtils.abbreviate(w.toString(), 5000));
        buf.append(
                "    + "
                        + root.getStackTrace().length
                        + " more (set debug level logging or '-Dmule.verbose.exceptions=true' for everything)")
                .append(org.apache.commons.lang.SystemUtils.LINE_SEPARATOR);
        buf.append(StringUtils.repeat('*', 80)).append(org.apache.commons.lang.SystemUtils.LINE_SEPARATOR);

        return buf.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DSLException)) {
            return false;
        }

        final DSLException exception = (DSLException) o;

        if (errorCode != exception.errorCode) {
            return false;
        }
        if (i18nMessage != null ? !i18nMessage.equals(exception.i18nMessage) : exception.i18nMessage != null) {
            return false;
        }
        if (message != null ? !message.equals(exception.message) : exception.message != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = errorCode;
        result = 29 * result + (message != null ? message.hashCode() : 0);
        result = 29 * result + (i18nMessage != null ? i18nMessage.hashCode() : 0);
        return result;
    }

    public Map getInfo() {
        return info;
    }
}
