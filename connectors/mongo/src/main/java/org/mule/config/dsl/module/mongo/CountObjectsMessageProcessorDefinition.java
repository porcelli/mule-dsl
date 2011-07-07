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

import java.util.Map;

public interface CountObjectsMessageProcessorDefinition extends MessageProcessorDefinition {

    CountObjectsMessageProcessorDefinition withQuery(Object query);

    CountObjectsMessageProcessorDefinition withQuery(ExpressionEvaluatorBuilder queryExp);

    CountObjectsMessageProcessorDefinition withQueryAttributes(Map<String, Object> queryAttributes);

    CountObjectsMessageProcessorDefinition withQueryAttributes(ExpressionEvaluatorBuilder queryAttributesExp);

}
