/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.apache.commons.logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogFactory {

    private final Map<String, Log> loggers = new HashMap<String, Log>();
    static LogFactory logFactory = new LogFactory();

    public Log getInstance(final Class<?> clazz) throws LogConfigurationException {
        return getInstance(clazz.getName());
    }

    public Log getInstance(final String name) throws LogConfigurationException {
        if (!loggers.containsKey(name)) {
            loggers.put(name, new MyTestLogger());
        }

        return loggers.get(name);
    }

    public static Log getLog(final Class<?> clazz) {
        return logFactory.getInstance(clazz);
    }

    public static class MyTestLogger implements Log {

        private final List<String> debugExec = new ArrayList<String>();
        private final List<String> errorExec = new ArrayList<String>();
        private final List<String> fatalExec = new ArrayList<String>();
        private final List<String> infoExec = new ArrayList<String>();
        private final List<String> warnExec = new ArrayList<String>();
        private final List<String> traceExec = new ArrayList<String>();

        public void reset() {
            debugExec.clear();
            errorExec.clear();
            fatalExec.clear();
            infoExec.clear();
            warnExec.clear();
            traceExec.clear();
        }

        @Override
        public boolean isErrorEnabled() {
            return true;
        }

        @Override
        public boolean isDebugEnabled() {
            return true;
        }

        @Override
        public boolean isFatalEnabled() {
            return true;
        }

        @Override
        public boolean isInfoEnabled() {
            return true;
        }

        @Override
        public boolean isTraceEnabled() {
            return true;
        }

        @Override
        public boolean isWarnEnabled() {
            return true;
        }

        @Override
        public void trace(final Object message) {
            trace(message, null);
        }

        @Override
        public void trace(final Object message, final Throwable t) {
            traceExec.add(message.toString());
//            System.out.println("TRACE: " + message.toString());
        }

        @Override
        public void debug(final Object message) {
            debug(message, null);
        }

        @Override
        public void debug(final Object message, final Throwable t) {
            debugExec.add(message.toString());
//            System.out.println("DEBUG: " + message.toString());
        }

        @Override
        public void info(final Object message) {
            info(message, null);
        }

        @Override
        public void info(final Object message, final Throwable t) {
            infoExec.add(message.toString());
//            System.out.println("INFO: " + message.toString());
        }

        @Override
        public void warn(final Object message) {
            warn(message, null);
        }

        @Override
        public void warn(final Object message, final Throwable t) {
            warnExec.add(message.toString());
//            System.out.println("WARN: " + message.toString());
        }

        @Override
        public void error(final Object message) {
            error(message, null);
        }

        @Override
        public void error(final Object message, final Throwable t) {
            errorExec.add(message.toString());
            System.out.println("ERROR: " + message.toString());
        }

        @Override
        public void fatal(final Object message) {
            fatal(message, null);
        }

        @Override
        public void fatal(final Object message, final Throwable t) {
            fatalExec.add(message.toString());
//            System.out.println("FATAL: " + message.toString());
        }


        public List<String> getDebugExec() {
            return debugExec;
        }

        public List<String> getErrorExec() {
            return errorExec;
        }

        public List<String> getFatalExec() {
            return fatalExec;
        }

        public List<String> getInfoExec() {
            return infoExec;
        }

        public List<String> getWarnExec() {
            return warnExec;
        }

        public List<String> getTraceExec() {
            return traceExec;
        }
    }
}
