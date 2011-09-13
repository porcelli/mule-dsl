/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.mule.api.MuleContext;
import org.mule.api.transport.Connector;
import org.mule.config.dsl.ConfigurationException;
import org.mule.config.dsl.ConnectorBuilder;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.transport.AbstractConnector;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.mule.config.dsl.util.Preconditions.*;
import static org.mule.config.dsl.internal.util.PrivateAccessorHack.executeHiddenMethod;
import static org.mule.config.dsl.internal.util.PrivateAccessorHack.setPrivateFieldValue;

/**
 * Internal implementation of {@link ConnectorBuilder} interface that, based on its internal state,
 * builds a {@link Connector} to be registered as global.
 *
 * @author porcelli
 * @see org.mule.config.dsl.AbstractModule#connector()
 * @see org.mule.config.dsl.AbstractModule#connector(String)
 * @see org.mule.config.dsl.Catalog#newConnector(String)
 */
public class ConnectorBuilderImpl implements ConnectorBuilder, Builder<Connector> {

    private final String name;
    private final ConnectorMethodInterceptor interceptor;
    private Connector proxy;

    /**
     * @param name the global connector name
     * @throws IllegalArgumentException if {@code name} param is empty or null
     */
    public ConnectorBuilderImpl(final String name) throws IllegalArgumentException {
        this.name = checkNotEmpty(name, "name");
        this.interceptor = new ConnectorMethodInterceptor();
    }

    /**
     * Getter of global connector name
     *
     * @return the global connector name
     */
    public String getName() {
        return name;
    }

    /**
     * Creates a connector (proxy) based on given type to be registered as global
     *
     * @param clazz the connector type
     * @return connector (proxy) to be configured
     * @throws NullPointerException   if {@code clazz} param is null
     * @throws ConfigurationException if {@code clazz} param is an abstract class or can't be created
     * @see org.mule.config.dsl.AbstractModule#connector()
     * @see org.mule.config.dsl.AbstractModule#connector(String)
     * @see org.mule.config.dsl.Catalog#newConnector(String)
     * @see org.mule.api.transport.Connector
     */
    @Override
    public <C extends Connector> C with(Class<C> clazz) throws NullPointerException, ConfigurationException {
        checkNotNull(clazz, "clazz");

        if (clazz.isInterface()) {
            throw new ConfigurationException("Can't use interface.");
        }

        if (Modifier.isAbstract(clazz.getModifiers())) {
            throw new ConfigurationException("Can't use an abstract class.");
        }

        Enhancer en = new Enhancer();
        en.setSuperclass(clazz);
        en.setCallback(interceptor);

        try {
            proxy = (Connector) en.create(new Class[]{MuleContext.class}, new Object[]{null});
        } catch (Exception e) {
            throw new ConfigurationException("Can't create connector.", e);
        }
        interceptor.startFilter();

        return (C) proxy;
    }

    /**
     * Inject {@link MuleContext} into proxy and returns it
     *
     * @param muleContext the mule context
     * @param placeholder the property placeholder
     * @return the connector (proxy)
     * @throws NullPointerException   if {@code muleContext} or {@code placeholder} params are null
     * @throws IllegalStateException  if connector type or instance is not provided
     * @throws ConfigurationException if can't configure the global connector
     */
    @Override
    public Connector build(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");
        checkState(this.proxy != null, "Can't build without a connector type.");

        interceptor.stopFilter();
        setPrivateFieldValue(AbstractConnector.class, proxy, "muleContext", muleContext);
        executeHiddenMethod(AbstractConnector.class, proxy, "updateCachedNotificationHandler");
        return proxy;
    }

    /**
     * Method interceptor that, if enabled, filters any method other than simple setters.
     *
     * @author porcelli
     */
    private final class ConnectorMethodInterceptor implements MethodInterceptor {
        boolean filter = false;

        /**
         * Starts filtering
         */
        public synchronized void startFilter() {
            filter = true;
        }

        /**
         * Stops filtering
         */
        public synchronized void stopFilter() {
            filter = false;
        }

        /**
         * Checks if filtering is enabled.
         *
         * @return true if enabled, otherwise false
         */
        private synchronized boolean isFiltering() {
            return filter;
        }

        /**
         * Intercepts every method execution and checks if can be executed.
         *
         * @param obj    "this", the enhanced object
         * @param method intercepted Method
         * @param args   argument array; primitive types are wrapped
         * @param proxy  used to invoke super (non-intercepted method); may be called
         *               as many times as needed
         * @return any value compatible with the signature of the proxied method. Method returning void will ignore this value.
         * @throws Throwable              any exception may be thrown; if so, super method will not be invoked
         * @throws ConfigurationException if executed a method other than setter - in case that filter is enabled
         */
        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable, ConfigurationException {
            if (!isFiltering()) {
                return proxy.invokeSuper(obj, args);
            }
            if (Modifier.isPrivate(method.getModifiers()) || Modifier.isProtected(method.getModifiers())) {
                return proxy.invokeSuper(obj, args);
            }
            if (method.getName().startsWith("set") && !method.getName().equals("setName")) {
                return proxy.invokeSuper(obj, args);
            }
            throw new ConfigurationException("Unable to execute methods other than setters.");
        }

    }
}
