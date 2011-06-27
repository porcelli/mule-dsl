/*
 * ---------------------------------------------------------------------------
 *  Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 */

package org.mule.config.dsl.module.mongo;

import org.mule.config.dsl.ExpressionEvaluatorBuilder;
import org.mule.config.dsl.MessageProcessorDefinition;
import org.mule.module.mongo.api.WriteConcern;

import java.util.Map;

public interface SaveObjectMessageProcessorDefinition extends MessageProcessorDefinition {

    SaveObjectMessageProcessorDefinition withElement(Object element);

    SaveObjectMessageProcessorDefinition withElement(ExpressionEvaluatorBuilder elementExp);

    SaveObjectMessageProcessorDefinition withElementAttributes(Map<String, Object> elementAttributes);

    SaveObjectMessageProcessorDefinition withElementAttributes(ExpressionEvaluatorBuilder elementAttributesExp);

    SaveObjectMessageProcessorDefinition withWriteConcern(WriteConcern writeConcern);

    SaveObjectMessageProcessorDefinition withWriteConcern(ExpressionEvaluatorBuilder writeConcernExp);
}
