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

public interface SearchWithJsonCallbackMessageProcessorDefinition extends MessageProcessorDefinition {

    SearchWithJsonCallbackMessageProcessorDefinition withLang(String lang);

    SearchWithJsonCallbackMessageProcessorDefinition withLang(ExpressionEvaluatorDefinition langExp);

    SearchWithJsonCallbackMessageProcessorDefinition withLocale(String locale);

    SearchWithJsonCallbackMessageProcessorDefinition withLocale(ExpressionEvaluatorDefinition localeExp);

    SearchWithJsonCallbackMessageProcessorDefinition withRpp(String rpp);

    SearchWithJsonCallbackMessageProcessorDefinition withRpp(ExpressionEvaluatorDefinition rppExp);

    SearchWithJsonCallbackMessageProcessorDefinition withPage(String page);

    SearchWithJsonCallbackMessageProcessorDefinition withPage(ExpressionEvaluatorDefinition pageExp);

    SearchWithJsonCallbackMessageProcessorDefinition withSinceId(String sinceId);

    SearchWithJsonCallbackMessageProcessorDefinition withSinceId(ExpressionEvaluatorDefinition sinceIdExp);

    SearchWithJsonCallbackMessageProcessorDefinition withGeocode(String geocode);

    SearchWithJsonCallbackMessageProcessorDefinition withGeocode(ExpressionEvaluatorDefinition geocodeExp);

    SearchWithJsonCallbackMessageProcessorDefinition withShowUser(String showUser);

    SearchWithJsonCallbackMessageProcessorDefinition withShowUser(ExpressionEvaluatorDefinition showUserExp);
}
