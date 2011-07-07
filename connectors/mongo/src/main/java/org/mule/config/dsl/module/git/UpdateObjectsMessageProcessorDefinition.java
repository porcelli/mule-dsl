/*
 * ---------------------------------------------------------------------------
 *  Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 */

package org.mule.config.dsl.module.git;

import org.mule.config.dsl.ExpressionEvaluatorBuilder;
import org.mule.config.dsl.MessageProcessorDefinition;
import org.mule.module.mongo.api.WriteConcern;

import java.util.Map;

public interface UpdateObjectsMessageProcessorDefinition extends MessageProcessorDefinition {

    UpdateObjectsMessageProcessorDefinition withQuery(Object query);

    UpdateObjectsMessageProcessorDefinition withQuery(ExpressionEvaluatorBuilder queryExp);

    UpdateObjectsMessageProcessorDefinition withQueryAttributes(Map<String, Object> queryAttributes);

    UpdateObjectsMessageProcessorDefinition withQueryAttributes(ExpressionEvaluatorBuilder queryAttributesExp);

    UpdateObjectsMessageProcessorDefinition withElement(Object element);

    UpdateObjectsMessageProcessorDefinition withElement(ExpressionEvaluatorBuilder elementExp);

    UpdateObjectsMessageProcessorDefinition withElementAttributes(Map<String, Object> elementAttributes);

    UpdateObjectsMessageProcessorDefinition withElementAttributes(ExpressionEvaluatorBuilder elementAttributesExp);

    UpdateObjectsMessageProcessorDefinition withUpsert(boolean upsert);

    UpdateObjectsMessageProcessorDefinition withUpsert(ExpressionEvaluatorBuilder upsertExp);

    UpdateObjectsMessageProcessorDefinition withMulti(boolean multi);

    UpdateObjectsMessageProcessorDefinition withMulti(ExpressionEvaluatorBuilder multiExp);

    UpdateObjectsMessageProcessorDefinition withWriteConcern(WriteConcern writeConcern);

    UpdateObjectsMessageProcessorDefinition withWriteConcern(ExpressionEvaluatorBuilder writeConcernExp);


}
