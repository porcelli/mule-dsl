/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import java.util.List;

import org.mule.api.MuleContext;
import org.mule.api.processor.MessageProcessor;
import org.mule.config.dsl.PropertyPlaceholder;

/**
 * Extends the marker interface {@link org.mule.config.dsl.Builder} and
 * defines a common contract to deal with the need to build multiple message processors.
 *
 * @author porcelli
 */
public interface MessageProcessorBuilderList extends org.mule.config.dsl.Builder {

    /**
     * Add the given builder to it's internal list of message processor builders.
     *
     * @param builder the builder to be stored
     * @throws NullPointerException if {@code builder} param is null
     */
    void addBuilder(Builder<? extends MessageProcessor> builder) throws NullPointerException;

    /**
     * Getter of message processor builder list
     *
     * @return the message processor builder list
     */
    List<Builder<? extends MessageProcessor>> getBuilders();

    /**
     * Checks if message processor builder's list is empty
     *
     * @return true if empty, otherwise false
     */
    boolean isBuilderListEmpty();

    /**
     * Builds, based on its builder list, a list of message processor using the given parameters.
     *
     * @param muleContext the mule context
     * @param placeholder the property placeholder
     * @return the list of message processors
     * @throws NullPointerException if {@code muleContext} or {@code placeholder} params are null
     */
    List<MessageProcessor> buildMessageProcessorList(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException;

}
