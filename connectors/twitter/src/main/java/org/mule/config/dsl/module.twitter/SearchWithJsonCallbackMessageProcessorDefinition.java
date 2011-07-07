/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.module.twitter;

import org.mule.config.dsl.ExpressionEvaluatorBuilder;
import org.mule.config.dsl.MessageProcessorDefinition;

public interface SearchWithJsonCallbackMessageProcessorDefinition extends MessageProcessorDefinition {

    SearchWithJsonCallbackMessageProcessorDefinition withLang(String lang);

    SearchWithJsonCallbackMessageProcessorDefinition withLang(ExpressionEvaluatorBuilder langExp);

    SearchWithJsonCallbackMessageProcessorDefinition withLocale(String locale);

    SearchWithJsonCallbackMessageProcessorDefinition withLocale(ExpressionEvaluatorBuilder localeExp);

    SearchWithJsonCallbackMessageProcessorDefinition withRpp(String rpp);

    SearchWithJsonCallbackMessageProcessorDefinition withRpp(ExpressionEvaluatorBuilder rppExp);

    SearchWithJsonCallbackMessageProcessorDefinition withPage(String page);

    SearchWithJsonCallbackMessageProcessorDefinition withPage(ExpressionEvaluatorBuilder pageExp);

    SearchWithJsonCallbackMessageProcessorDefinition withSinceId(String sinceId);

    SearchWithJsonCallbackMessageProcessorDefinition withSinceId(ExpressionEvaluatorBuilder sinceIdExp);

    SearchWithJsonCallbackMessageProcessorDefinition withGeocode(String geocode);

    SearchWithJsonCallbackMessageProcessorDefinition withGeocode(ExpressionEvaluatorBuilder geocodeExp);

    SearchWithJsonCallbackMessageProcessorDefinition withShowUser(String showUser);

    SearchWithJsonCallbackMessageProcessorDefinition withShowUser(ExpressionEvaluatorBuilder showUserExp);
}
