/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.module.mongo;

import org.mule.config.dsl.ExpressionEvaluatorBuilder;
import org.mule.config.dsl.MessageProcessorDefinition;

public interface CreateCollectionMessageProcessorDefinition extends MessageProcessorDefinition {

    CreateCollectionMessageProcessorDefinition withCapped(boolean capped);

    CreateCollectionMessageProcessorDefinition withCapped(ExpressionEvaluatorBuilder cappedExp);

    CreateCollectionMessageProcessorDefinition withMaxObjects(Integer maxObjects);

    CreateCollectionMessageProcessorDefinition withMaxObjects(ExpressionEvaluatorBuilder maxObjectsExp);

    CreateCollectionMessageProcessorDefinition withSize(Integer size);

    CreateCollectionMessageProcessorDefinition withSize(ExpressionEvaluatorBuilder sizeExp);

}
