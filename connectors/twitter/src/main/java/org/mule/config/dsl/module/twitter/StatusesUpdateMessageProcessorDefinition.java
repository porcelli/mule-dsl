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

public interface StatusesUpdateMessageProcessorDefinition extends MessageProcessorDefinition {

    StatusesUpdateMessageProcessorDefinition withReplyId(String replyId);

    StatusesUpdateMessageProcessorDefinition withReplyId(ExpressionEvaluatorDefinition replyIdExp);

    StatusesUpdateMessageProcessorDefinition withLatitude(double latitude);

    StatusesUpdateMessageProcessorDefinition withLatitude(ExpressionEvaluatorDefinition latitudeExp);

    StatusesUpdateMessageProcessorDefinition withLongitude(double longitude);

    StatusesUpdateMessageProcessorDefinition withLongitude(ExpressionEvaluatorDefinition longitudeExp);

    StatusesUpdateMessageProcessorDefinition withPlaceId(String placeId);

    StatusesUpdateMessageProcessorDefinition withPlaceId(ExpressionEvaluatorDefinition placeIdExp);

    StatusesUpdateMessageProcessorDefinition withDisplayCoordinates(boolean displayCoordinates);

    StatusesUpdateMessageProcessorDefinition withDisplayCoordinates(ExpressionEvaluatorDefinition displayCoordinatesExp);

    StatusesUpdateMessageProcessorDefinition withTrimUser(boolean trimUser);

    StatusesUpdateMessageProcessorDefinition withTrimUser(ExpressionEvaluatorDefinition trimUserExp);

}
