/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import org.mule.api.MuleContext;
import org.mule.api.exception.MessagingExceptionHandler;
import org.mule.config.dsl.PropertyPlaceholder;

import java.util.List;

/**
 * Extends the marker interface {@link org.mule.config.dsl.Builder} and
 * defines a common contract to deal with the need to build multiples messaging exception handlers.
 * <p/>
 * <b>Important Note:</b> The actual version of Mule core supports only one {@link MessagingExceptionHandler}
 * per flow, but prbably soon should provide more than one.
 *
 * @author porcelli
 */
public interface MessagingExceptionHandlerBuilderList extends org.mule.config.dsl.Builder {

    /**
     * Add the given builder to it's internal list of messaging exception handlers.
     *
     * @param builder the builder to be stored
     * @throws NullPointerException if {@code builder} param is null
     */
    void addExceptionBuilder(Builder<? extends MessagingExceptionHandler> builder) throws NullPointerException;

    /**
     * Getter of messaging exception builder list
     *
     * @return the messaging exception builder list
     */
    List<Builder<? extends MessagingExceptionHandler>> getExceptionBuilders();

    /**
     * Checks if messaging exception builder's list is empty
     *
     * @return true if empty, otherwise false
     */
    boolean isExceptionBuilderListEmpty();

    /**
     * Builds, based on its builder list, a list of messaging exception handlers using the given parameters.
     *
     * @param muleContext the mule context
     * @param placeholder the property placeholder
     * @return the list of messaging exception handlers
     * @throws NullPointerException if {@code muleContext} or {@code placeholder} params are null
     */
    List<MessagingExceptionHandler> buildExceptionHandlerList(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException;

}
