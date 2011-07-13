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

public interface GetFriendsTimelineMessageProcessorDefinition extends MessageProcessorDefinition {

    GetFriendsTimelineMessageProcessorDefinition withCount(Integer count);

    GetFriendsTimelineMessageProcessorDefinition withCount(ExpressionEvaluatorDefinition countExp);

    GetFriendsTimelineMessageProcessorDefinition withSinceId(String sinceId);

    GetFriendsTimelineMessageProcessorDefinition withSinceId(ExpressionEvaluatorDefinition sinceIdExp);

    GetFriendsTimelineMessageProcessorDefinition withMaxId(String maxId);

    GetFriendsTimelineMessageProcessorDefinition withMaxId(ExpressionEvaluatorDefinition maxIdExp);

    GetFriendsTimelineMessageProcessorDefinition withPage(Integer page);

    GetFriendsTimelineMessageProcessorDefinition withPage(ExpressionEvaluatorDefinition pageExp);

}
