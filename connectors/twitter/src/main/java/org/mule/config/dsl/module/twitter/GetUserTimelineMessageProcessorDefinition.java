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

public interface GetUserTimelineMessageProcessorDefinition extends MessageProcessorDefinition {

    GetUserTimelineMessageProcessorDefinition withUserId(String userId);

    GetUserTimelineMessageProcessorDefinition withUserId(ExpressionEvaluatorDefinition userIdExp);

    GetUserTimelineMessageProcessorDefinition withScreenName(String screenName);

    GetUserTimelineMessageProcessorDefinition withScreenName(ExpressionEvaluatorDefinition screenNameExp);

    GetUserTimelineMessageProcessorDefinition withCount(Integer count);

    GetUserTimelineMessageProcessorDefinition withCount(ExpressionEvaluatorDefinition countExp);

    GetUserTimelineMessageProcessorDefinition withSinceId(String sinceId);

    GetUserTimelineMessageProcessorDefinition withSinceId(ExpressionEvaluatorDefinition sinceIdExp);

    GetUserTimelineMessageProcessorDefinition withMaxId(String maxId);

    GetUserTimelineMessageProcessorDefinition withMaxId(ExpressionEvaluatorDefinition maxIdExp);

    GetUserTimelineMessageProcessorDefinition withPage(Integer page);

    GetUserTimelineMessageProcessorDefinition withPage(ExpressionEvaluatorDefinition pageExp);

}
