/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import com.google.inject.Injector;
import org.mule.api.MuleContext;
import org.mule.api.construct.FlowConstructAware;
import org.mule.api.service.Service;
import org.mule.api.service.ServiceAware;
import org.mule.config.dsl.Scope;
import org.mule.config.dsl.internal.util.InjectorUtil;
import org.mule.object.AbstractObjectFactory;

import static org.mule.config.dsl.Scope.SINGLETON;
import static org.mule.config.dsl.internal.GuiceRegistry.GUICE_INJECTOR_REF;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

/**
 * This is an implementation of the ObjectFactory interface which simply delegates to
 * the Guice {@link Injector}.  Since the delegation happens each time a call to
 * getOrCreate() is made, this will correctly handle guice binds which are
 * non-singletons.
 *
 * @author porcelli
 */
public class MuleContextLookup extends AbstractObjectFactory {
    private final Class<?> clazz;
    private final Scope scope;
    private final boolean singleton;
    private Object instance;

    /**
     * @param clazz       the type to lookup on mule context
     * @param scope       the desired scope
     * @param muleContext the mule context
     * @throws NullPointerException if {@code clazz}, {@code scope} or {@code muleContext} params are null
     */
    public MuleContextLookup(final Class<?> clazz, final Scope scope, final MuleContext muleContext) throws NullPointerException {
        this.clazz = checkNotNull(clazz, "clazz");
        this.scope = checkNotNull(scope, "scope");

        if (muleContext.getRegistry().<Injector>get(GUICE_INJECTOR_REF) != null) {
            this.singleton = InjectorUtil.isSingleton(muleContext.getRegistry().<Injector>get(GUICE_INJECTOR_REF), clazz);
        } else {
            this.singleton = scope.equals(SINGLETON);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialise() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> getObjectClass() {
        return clazz;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getInstance(final MuleContext muleContext) throws Exception {

        if (instance == null) {
            instance = muleContext.getRegistry().lookupObject(clazz);
        } else if (scope.equals(Scope.PROTOTYPE)) {
            instance = muleContext.getRegistry().lookupObject(clazz);
        }

        if (instance instanceof FlowConstructAware) {
            ((FlowConstructAware) instance).setFlowConstruct(flowConstruct);
        }
        if (instance instanceof ServiceAware && flowConstruct instanceof Service) {
            ((ServiceAware) instance).setService((Service) flowConstruct);
        }
        fireInitialisationCallbacks(instance);

        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSingleton() {
        return singleton;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAutoWireObject() {
        return false;
    }
}
