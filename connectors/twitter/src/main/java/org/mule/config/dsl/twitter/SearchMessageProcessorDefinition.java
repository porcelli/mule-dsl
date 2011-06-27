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

public interface SearchMessageProcessorDefinition extends MessageProcessorDefinition {

    SearchMessageProcessorDefinition withLang(String lang);

    SearchMessageProcessorDefinition withLang(ExpressionEvaluatorBuilder langExp);

    SearchMessageProcessorDefinition withLocale(String locale);

    SearchMessageProcessorDefinition withLocale(ExpressionEvaluatorBuilder localeExp);

    SearchMessageProcessorDefinition withRpp(String rpp);

    SearchMessageProcessorDefinition withRpp(ExpressionEvaluatorBuilder rppExp);

    SearchMessageProcessorDefinition withPage(String page);

    SearchMessageProcessorDefinition withPage(ExpressionEvaluatorBuilder pageExp);

    SearchMessageProcessorDefinition withSinceId(String sinceId);

    SearchMessageProcessorDefinition withSinceId(ExpressionEvaluatorBuilder sinceIdExp);

    SearchMessageProcessorDefinition withGeocode(String geocode);

    SearchMessageProcessorDefinition withGeocode(ExpressionEvaluatorBuilder geocodeExp);

    SearchMessageProcessorDefinition withShowUser(String showUser);

    SearchMessageProcessorDefinition withShowUser(ExpressionEvaluatorBuilder showUserExp);

    SearchMessageProcessorDefinition withAccessToken(String accessToken);

    SearchMessageProcessorDefinition withAccessToken(ExpressionEvaluatorBuilder accessTokenExp);

    SearchMessageProcessorDefinition withAccessSecret(String accessSecret);

    SearchMessageProcessorDefinition withAccessSecret(ExpressionEvaluatorBuilder accessSecretExp);

}
