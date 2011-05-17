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

    private Map<String, Log> loggers = new HashMap<String, Log>();
    static LogFactory logFactory = new LogFactory();

    public Log getInstance(Class<?> clazz) throws LogConfigurationException {
        return getInstance(clazz.getName());
    }

    public Log getInstance(String name) throws LogConfigurationException {
        if (!loggers.containsKey(name)) {
            loggers.put(name, new MyTestLogger());
        }

        return loggers.get(name);
    }

    public static Log getLog(Class<?> clazz) {
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
        public void trace(Object message) {
            trace(message, null);
        }

        @Override
        public void trace(Object message, Throwable t) {
            traceExec.add(message.toString());
        }

        @Override
        public void debug(Object message) {
            debug(message, null);
        }

        @Override
        public void debug(Object message, Throwable t) {
            debugExec.add(message.toString());
        }

        @Override
        public void info(Object message) {
            info(message, null);
        }

        @Override
        public void info(Object message, Throwable t) {
            infoExec.add(message.toString());
        }

        @Override
        public void warn(Object message) {
            warn(message, null);
        }

        @Override
        public void warn(Object message, Throwable t) {
            warnExec.add(message.toString());
        }

        @Override
        public void error(Object message) {
            error(message, null);
        }

        @Override
        public void error(Object message, Throwable t) {
            errorExec.add(message.toString());
        }

        @Override
        public void fatal(Object message) {
            fatal(message, null);
        }

        @Override
        public void fatal(Object message, Throwable t) {
            fatalExec.add(message.toString());
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
