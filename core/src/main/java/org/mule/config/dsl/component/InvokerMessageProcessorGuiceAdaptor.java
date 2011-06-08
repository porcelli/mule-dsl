/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.component;

import com.google.inject.Injector;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.config.dsl.internal.util.InjectorUtil;
import org.mule.processor.InvokerMessageProcessor;

import java.util.Collections;
import java.util.List;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class InvokerMessageProcessorGuiceAdaptor extends InvokerMessageProcessor {

    private final Injector injector;

    public InvokerMessageProcessorGuiceAdaptor(Injector injector) {
        this.injector = checkNotNull(injector, "injector");
    }

    @Override
    protected void lookupObjectInstance() throws InitialisationException {
        try {
            super.lookupObjectInstance();
        } catch (Exception e) {
            if (InjectorUtil.hasProvider(injector, objectType)) {
                object = injector.getInstance(objectType);
            } else {
                try {
                    object = objectType.newInstance();
                } catch (Exception ex) {
                    throw new InitialisationException(ex, null);
                }
            }
        }
    }

    public List<?> getArguments() {
        return Collections.unmodifiableList(arguments);
    }

    public Object getObject() {
        return object;
    }

    public Class<?> getObjectType() {
        return objectType;
    }

    public String getMethodName() {
        return methodName;
    }

}
