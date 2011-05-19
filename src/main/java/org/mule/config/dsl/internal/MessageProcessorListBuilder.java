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
import org.mule.api.processor.MessageProcessor;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;

import java.util.List;

public interface MessageProcessorListBuilder {

    void addToProcessorList(Builder<?> builder);

    List<MessageProcessor> buildProcessorList(Injector injector, PropertyPlaceholder placeholder);

    boolean isProcessorListEmpty();

    List<Builder<?>> getProcessorList();
}
