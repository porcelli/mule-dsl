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

public interface UpdateObjectsMessageProcessorDefinition extends MessageProcessorDefinition {

    UpdateObjectsMessageProcessorDefinition withQuery(Object query);

    UpdateObjectsMessageProcessorDefinition withQuery(ExpressionEvaluatorDefinition queryExp);

    UpdateObjectsMessageProcessorDefinition withQueryAttributes(Map<String, Object> queryAttributes);

    UpdateObjectsMessageProcessorDefinition withQueryAttributes(ExpressionEvaluatorDefinition queryAttributesExp);

    UpdateObjectsMessageProcessorDefinition withElement(Object element);

    UpdateObjectsMessageProcessorDefinition withElement(ExpressionEvaluatorDefinition elementExp);

    UpdateObjectsMessageProcessorDefinition withElementAttributes(Map<String, Object> elementAttributes);

    UpdateObjectsMessageProcessorDefinition withElementAttributes(ExpressionEvaluatorDefinition elementAttributesExp);

    UpdateObjectsMessageProcessorDefinition withUpsert(boolean upsert);

    UpdateObjectsMessageProcessorDefinition withUpsert(ExpressionEvaluatorDefinition upsertExp);

    UpdateObjectsMessageProcessorDefinition withMulti(boolean multi);

    UpdateObjectsMessageProcessorDefinition withMulti(ExpressionEvaluatorDefinition multiExp);

    UpdateObjectsMessageProcessorDefinition withWriteConcern(WriteConcern writeConcern);

    UpdateObjectsMessageProcessorDefinition withWriteConcern(ExpressionEvaluatorDefinition writeConcernExp);


}
