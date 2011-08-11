/*
 * ---------------------------------------------------------------------------
 *  Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 */

package org.mule.config.dsl.component;

import java.util.Collections;
import java.util.List;

import org.mule.processor.InvokerMessageProcessor;

/**
 * Extends core {@link InvokerMessageProcessor} and exposes it's internal state thru getters.
 * <p/>
 * <b>Note: </b> This extension is just to enable tests.
 *
 * @author porcelli
 */
public class InvokerMessageProcessorAdaptor extends InvokerMessageProcessor {

    /**
     * Getter of argument list
     *
     * @return the argument list
     */
    public List<?> getArguments() {
        return Collections.unmodifiableList(arguments);
    }

    /**
     * Getter of object to be invoked.
     *
     * @return the object to be invoked
     */
    public Object getObject() {
        return object;
    }

    /**
     * Getter of object type.
     *
     * @return the object type
     */
    public Class<?> getObjectType() {
        return objectType;
    }

    /**
     * Getter of method name to be invoked.
     *
     * @return the method name
     */
    public String getMethodName() {
        return methodName;
    }

}
