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
import com.google.inject.Scopes;
import org.mule.api.MuleContext;
import org.mule.api.construct.FlowConstructAware;
import org.mule.api.service.Service;
import org.mule.api.service.ServiceAware;
import org.mule.object.AbstractObjectFactory;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

class GuiceLookup extends AbstractObjectFactory {
    private final Injector injector;
    private final Class<?> clazz;

    GuiceLookup(final Injector injector, Class<?> clazz) {
        this.injector = checkNotNull(injector, "injector");
        this.clazz = checkNotNull(clazz, "clazz");
    }

    @Override
    public void initialise() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public Class<?> getObjectClass() {
        return clazz;
    }

    @Override
    public Object getInstance(MuleContext muleContext) throws Exception {
        Object instance = injector.getInstance(clazz);
        if (instance instanceof FlowConstructAware) {
            ((FlowConstructAware) instance).setFlowConstruct(flowConstruct);
        }
        if (instance instanceof ServiceAware && flowConstruct instanceof Service) {
            ((ServiceAware) instance).setService((Service) flowConstruct);
        }
        fireInitialisationCallbacks(instance);
        return instance;
    }

    @Override
    public boolean isSingleton() {
        return Scopes.isSingleton(injector.getBinding(clazz));
    }

    @Override
    public boolean isExternallyManagedLifecycle() {
        return true;
    }

    public boolean isAutoWireObject() {
        return false;
    }
}
