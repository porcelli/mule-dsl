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

import java.util.List;
import java.util.Map;

public interface FindObjectsMessageProcessorDefinition extends MessageProcessorDefinition {

    FindObjectsMessageProcessorDefinition withQuery(Object query);

    FindObjectsMessageProcessorDefinition withQuery(ExpressionEvaluatorBuilder queryExp);

    FindObjectsMessageProcessorDefinition withQueryAttributes(Map<String, Object> queryAttributes);

    FindObjectsMessageProcessorDefinition withQueryAttributes(ExpressionEvaluatorBuilder queryAttributesExp);

    FindObjectsMessageProcessorDefinition withFieldsRef(Object fieldsRef);

    FindObjectsMessageProcessorDefinition withFieldsRef(ExpressionEvaluatorBuilder fieldsRefExp);

    FindObjectsMessageProcessorDefinition withFields(List<String> fields);

    FindObjectsMessageProcessorDefinition withFields(ExpressionEvaluatorBuilder fieldsExp);

}
