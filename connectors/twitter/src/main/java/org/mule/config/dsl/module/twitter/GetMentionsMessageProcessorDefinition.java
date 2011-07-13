/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.module.twitter;

import org.mule.config.dsl.ExpressionEvaluatorDefinition;
import org.mule.config.dsl.MessageProcessorDefinition;

public interface GetMentionsMessageProcessorDefinition extends MessageProcessorDefinition {

    GetMentionsMessageProcessorDefinition withCount(Integer count);

    GetMentionsMessageProcessorDefinition withCount(ExpressionEvaluatorDefinition countExp);

    GetMentionsMessageProcessorDefinition withSinceId(String sinceId);

    GetMentionsMessageProcessorDefinition withSinceId(ExpressionEvaluatorDefinition sinceIdExp);

    GetMentionsMessageProcessorDefinition withMaxId(String maxId);

    GetMentionsMessageProcessorDefinition withMaxId(ExpressionEvaluatorDefinition maxIdExp);

    GetMentionsMessageProcessorDefinition withPage(Integer page);

    GetMentionsMessageProcessorDefinition withPage(ExpressionEvaluatorDefinition pageExp);

}
