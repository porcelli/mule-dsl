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
import org.mule.config.dsl.twitter.internal.IBeansTwitterReference;
import org.mule.config.dsl.twitter.internal.StatusesDestroyMessageProcessorBuilder;
import org.mule.config.dsl.twitter.internal.StatusesShowMessageProcessorBuilder;
import org.mule.config.dsl.twitter.internal.StatusesUpdateMessageProcessorBuilder;
import org.mule.ibeans.twitter.TwitterBase;

public class TwitterConnector {

    private IBeansTwitterReference reference;

    public TwitterConnector() {
        reference = new IBeansTwitterReference();
    }

    public StatusesUpdateMessageProcessorDefinition statusesUpdate(String status) {
        return new StatusesUpdateMessageProcessorBuilder(reference, status);
    }

    public StatusesUpdateMessageProcessorDefinition statusesUpdate(ExpressionEvaluatorBuilder statusExp) {
        return new StatusesUpdateMessageProcessorBuilder(reference, statusExp);
    }

    public StatusesShowMessageProcessorDefinition statusesShow(String id) {
        return new StatusesShowMessageProcessorBuilder(reference, id);
    }

    public StatusesShowMessageProcessorDefinition statusesShow(ExpressionEvaluatorBuilder idExp) {
        return new StatusesShowMessageProcessorBuilder(reference, idExp);
    }

    public StatusesDestroyMessageProcessorDefinition statusesDestroy(String id) {
        return new StatusesDestroyMessageProcessorBuilder(reference, id);
    }

    public StatusesDestroyMessageProcessorDefinition statusesDestroy(ExpressionEvaluatorBuilder idExp) {
        return new StatusesDestroyMessageProcessorBuilder(reference, idExp);
    }

    public TwitterBase.FORMAT getFormat() {
        return reference.getFormat();
    }

    public String getConsumerKey() {
        return reference.getConsumerKey();
    }

    public String getConsumerSecret() {
        return reference.getConsumerSecret();
    }

    public String getOathToken() {
        return reference.getOathToken();
    }

    public String getOathTokenSecret() {
        return reference.getOathToken();
    }

    public void setFormat(TwitterBase.FORMAT format) {
        reference.setFormat(format);
    }

    public void setConsumerKey(String consumerKey) {
        reference.setConsumerKey(consumerKey);
    }

    public void setConsumerSecret(String consumerSecret) {
        reference.setConsumerSecret(consumerSecret);
    }

    public void setOathToken(String oathToken) {
        reference.setOathToken(oathToken);
    }

    public void setOathTokenSecret(String oathTokenSecret) {
        reference.setOathTokenSecret(oathTokenSecret);
    }
}
