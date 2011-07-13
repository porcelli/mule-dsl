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

import java.util.List;
import java.util.Map;

public interface FindOneObjectMessageProcessorDefinition extends MessageProcessorDefinition {

    FindOneObjectMessageProcessorDefinition withQuery(Object query);

    FindOneObjectMessageProcessorDefinition withQuery(ExpressionEvaluatorDefinition queryExp);

    FindOneObjectMessageProcessorDefinition withQueryAttributes(Map<String, Object> queryAttributes);

    FindOneObjectMessageProcessorDefinition withQueryAttributes(ExpressionEvaluatorDefinition queryAttributesExp);

    FindOneObjectMessageProcessorDefinition withFieldsRef(Object fieldsRef);

    FindOneObjectMessageProcessorDefinition withFieldsRef(ExpressionEvaluatorDefinition fieldsRefExp);

    FindOneObjectMessageProcessorDefinition withFields(List<String> fields);

    FindOneObjectMessageProcessorDefinition withFields(ExpressionEvaluatorDefinition fieldsExp);

}
