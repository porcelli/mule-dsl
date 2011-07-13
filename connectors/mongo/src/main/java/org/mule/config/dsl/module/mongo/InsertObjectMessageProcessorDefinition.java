/*
 * ---------------------------------------------------------------------------
 *  Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 */

package org.mule.config.dsl.module.mongo;

import org.mule.config.dsl.ExpressionEvaluatorDefinition;
import org.mule.config.dsl.MessageProcessorDefinition;
import org.mule.module.mongo.api.WriteConcern;

import java.util.Map;

public interface InsertObjectMessageProcessorDefinition extends MessageProcessorDefinition {

    InsertObjectMessageProcessorDefinition withElement(Object element);

    InsertObjectMessageProcessorDefinition withElement(ExpressionEvaluatorDefinition elementExp);

    InsertObjectMessageProcessorDefinition withElementAttributes(Map<String, Object> elementAttributes);

    InsertObjectMessageProcessorDefinition withElementAttributes(ExpressionEvaluatorDefinition elementAttributesExp);

    InsertObjectMessageProcessorDefinition withWriteConcern(WriteConcern writeConcern);

    InsertObjectMessageProcessorDefinition withWriteConcern(ExpressionEvaluatorDefinition writeConcernExp);
}
