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

public interface SearchMessageProcessorDefinition extends MessageProcessorDefinition {

    SearchMessageProcessorDefinition withLang(String lang);

    SearchMessageProcessorDefinition withLang(ExpressionEvaluatorDefinition langExp);

    SearchMessageProcessorDefinition withLocale(String locale);

    SearchMessageProcessorDefinition withLocale(ExpressionEvaluatorDefinition localeExp);

    SearchMessageProcessorDefinition withRpp(String rpp);

    SearchMessageProcessorDefinition withRpp(ExpressionEvaluatorDefinition rppExp);

    SearchMessageProcessorDefinition withPage(String page);

    SearchMessageProcessorDefinition withPage(ExpressionEvaluatorDefinition pageExp);

    SearchMessageProcessorDefinition withSinceId(String sinceId);

    SearchMessageProcessorDefinition withSinceId(ExpressionEvaluatorDefinition sinceIdExp);

    SearchMessageProcessorDefinition withGeocode(String geocode);

    SearchMessageProcessorDefinition withGeocode(ExpressionEvaluatorDefinition geocodeExp);

    SearchMessageProcessorDefinition withShowUser(String showUser);

    SearchMessageProcessorDefinition withShowUser(ExpressionEvaluatorDefinition showUserExp);

    SearchMessageProcessorDefinition withAccessToken(String accessToken);

    SearchMessageProcessorDefinition withAccessToken(ExpressionEvaluatorDefinition accessTokenExp);

    SearchMessageProcessorDefinition withAccessSecret(String accessSecret);

    SearchMessageProcessorDefinition withAccessSecret(ExpressionEvaluatorDefinition accessSecretExp);

}
