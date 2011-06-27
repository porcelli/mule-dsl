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
import org.mule.config.dsl.MessageProcessorDefinition;

public interface StatusesUpdateMessageProcessorDefinition extends MessageProcessorDefinition {

    StatusesUpdateMessageProcessorDefinition withReplyId(String replyId);

    StatusesUpdateMessageProcessorDefinition withReplyId(ExpressionEvaluatorBuilder replyIdExp);

    StatusesUpdateMessageProcessorDefinition withLatitude(double latitude);

    StatusesUpdateMessageProcessorDefinition withLatitude(ExpressionEvaluatorBuilder latitudeExp);

    StatusesUpdateMessageProcessorDefinition withLongitude(double longitude);

    StatusesUpdateMessageProcessorDefinition withLongitude(ExpressionEvaluatorBuilder longitudeExp);

    StatusesUpdateMessageProcessorDefinition withPlaceId(String placeId);

    StatusesUpdateMessageProcessorDefinition withPlaceId(ExpressionEvaluatorBuilder placeIdExp);

    StatusesUpdateMessageProcessorDefinition withDisplayCoordinates(boolean displayCoordinates);

    StatusesUpdateMessageProcessorDefinition withDisplayCoordinates(ExpressionEvaluatorBuilder displayCoordinatesExp);

    StatusesUpdateMessageProcessorDefinition withTrimUser(boolean trimUser);

    StatusesUpdateMessageProcessorDefinition withTrimUser(ExpressionEvaluatorBuilder trimUserExp);

}
