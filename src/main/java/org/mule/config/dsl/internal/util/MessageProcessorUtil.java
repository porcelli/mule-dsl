/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal.util;

import com.google.inject.Injector;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.processor.MessageProcessorChain;
import org.mule.config.dsl.internal.Builder;
import org.mule.processor.chain.DefaultMessageProcessorChain;

import java.util.ArrayList;
import java.util.List;

public final class MessageProcessorUtil {
    private MessageProcessorUtil() {
    }

    public static MessageProcessorChain buildProcessorChain(List<Builder<?>> processorList, Injector injector) {

        if (processorList == null || processorList.size() == 0) {
            return null;
        }

        return buildProcessorChain(buildProcessorList(processorList, injector));
    }


    public static List<MessageProcessor> buildProcessorList(List<Builder<?>> processorList, Injector injector) {
        if (processorList == null || processorList.size() == 0) {
            return new ArrayList<MessageProcessor>(0);
        }

        List<MessageProcessor> result = new ArrayList<MessageProcessor>(processorList.size());

        for (Builder<?> activeBuilder : processorList) {
            result.add((MessageProcessor) activeBuilder.build(injector));
        }

        return result;
    }

    public static MessageProcessorChain buildProcessorChain(List<MessageProcessor> messageProcessors) {
        try {
            return DefaultMessageProcessorChain.from(messageProcessors);
        } catch (MuleException e) {
            //TODO handle
            throw new RuntimeException(e);
        }
    }

}
