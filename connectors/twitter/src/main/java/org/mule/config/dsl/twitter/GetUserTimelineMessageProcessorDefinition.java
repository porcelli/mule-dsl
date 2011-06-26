/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.twitter;

import org.mule.config.dsl.ExpressionEvaluatorBuilder;

public interface GetUserTimelineMessageProcessorDefinition {

    GetUserTimelineMessageProcessorDefinition withUserId(String userId);

    GetUserTimelineMessageProcessorDefinition withUserId(ExpressionEvaluatorBuilder userIdExp);

    GetUserTimelineMessageProcessorDefinition withScreenName(String screenName);

    GetUserTimelineMessageProcessorDefinition withScreenName(ExpressionEvaluatorBuilder screenNameExp);

    GetUserTimelineMessageProcessorDefinition withCount(Integer count);

    GetUserTimelineMessageProcessorDefinition withCount(ExpressionEvaluatorBuilder countExp);

    GetUserTimelineMessageProcessorDefinition withSinceId(String sinceId);

    GetUserTimelineMessageProcessorDefinition withSinceId(ExpressionEvaluatorBuilder sinceIdExp);

    GetUserTimelineMessageProcessorDefinition withMaxId(String maxId);

    GetUserTimelineMessageProcessorDefinition withMaxId(ExpressionEvaluatorBuilder maxIdExp);

    GetUserTimelineMessageProcessorDefinition withPage(Integer page);

    GetUserTimelineMessageProcessorDefinition withPage(ExpressionEvaluatorBuilder pageExp);

}
