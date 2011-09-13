/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal.util;

import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.context.MuleContextAware;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.processor.MessageProcessorChain;
import org.mule.config.dsl.ConfigurationException;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.config.dsl.internal.Builder;
import org.mule.processor.chain.DefaultMessageProcessorChain;

import java.util.ArrayList;
import java.util.List;

import static org.mule.config.dsl.util.Preconditions.checkNotNull;

/**
 * Utility class that helps create lists of message processor or {@link MessageProcessorChain}.
 *
 * @author porcelli
 */
public final class MessageProcessorUtil {
    private MessageProcessorUtil() {
    }

    /**
     * Creates a {@link MessageProcessorChain} based on given message processor builder list.
     *
     * @param processorList the message processor builder list, null or empty is allowed
     * @param muleContext   the mule context
     * @param placeholder   the property placeholder
     * @return the message processor chain or null in case of {@code processorList} param is null or empty
     * @throws NullPointerException if {@code muleContext} or {@code placeholder} params are null
     */
    public static MessageProcessorChain buildProcessorChain(final List<Builder<? extends MessageProcessor>> processorList, final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException {

        if (processorList == null || processorList.size() == 0) {
            return null;
        }

        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        try {
            return DefaultMessageProcessorChain.from(buildProcessorList(processorList, muleContext, placeholder));
        } catch (final MuleException e) {
            throw new ConfigurationException("Failed to create a MessageProcessorChain.", e);
        }
    }


    /**
     * Creates a list of {@link MessageProcessor} based on given message processor builder list.
     *
     * @param processorList the message processor builder list, null or empty is allowed
     * @param muleContext   the mule context
     * @param placeholder   the property placeholder
     * @return the message processor list or an empty list in case of {@code processorList} param is null or empty
     * @throws NullPointerException if {@code muleContext} or {@code placeholder} params are null
     */
    public static List<MessageProcessor> buildProcessorList(final List<Builder<? extends MessageProcessor>> processorList, final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException {
        if (processorList == null || processorList.size() == 0) {
            return new ArrayList<MessageProcessor>(0);
        }

        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        final List<MessageProcessor> result = new ArrayList<MessageProcessor>(processorList.size());

        for (final Builder<?> activeBuilder : processorList) {
            final MessageProcessor mp = (MessageProcessor) activeBuilder.build(muleContext, placeholder);

            if (mp instanceof MuleContextAware) {
                ((MuleContextAware) mp).setMuleContext(muleContext);
            }

            result.add(mp);
        }

        return result;
    }
}
