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
import org.mule.config.dsl.module.twitter.internal.*;
import org.mule.ibeans.twitter.TwitterBase;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotEmpty;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class TwitterConnector {

    private IBeanTwitterReference reference;

    public TwitterConnector() {
        reference = new IBeanTwitterReference();
    }

    public StatusesUpdateMessageProcessorDefinition statusesUpdate(String status) {
        checkNotEmpty(status, "status");
        return new StatusesUpdateMessageProcessorBuilder(reference, status);
    }

    public StatusesUpdateMessageProcessorDefinition statusesUpdate(ExpressionEvaluatorDefinition statusExp) {
        checkNotNull(statusExp, "status expression");
        return new StatusesUpdateMessageProcessorBuilder(reference, statusExp);
    }

    public StatusesShowMessageProcessorDefinition statusesShow(String id) {
        checkNotEmpty(id, "id");
        return new StatusesShowMessageProcessorBuilder(reference, id);
    }

    public StatusesShowMessageProcessorDefinition statusesShow(ExpressionEvaluatorDefinition idExp) {
        checkNotNull(idExp, "id expression");
        return new StatusesShowMessageProcessorBuilder(reference, idExp);
    }

    public StatusesDestroyMessageProcessorDefinition statusesDestroy(String id) {
        checkNotEmpty(id, "id");
        return new StatusesDestroyMessageProcessorBuilder(reference, id);
    }

    public StatusesDestroyMessageProcessorDefinition statusesDestroy(ExpressionEvaluatorDefinition idExp) {
        checkNotNull(idExp, "id expression");
        return new StatusesDestroyMessageProcessorBuilder(reference, idExp);
    }

    public SearchMessageProcessorDefinition search(String query) {
        checkNotEmpty(query, "query");
        return new SearchMessageProcessorBuilder(reference, query);
    }

    public SearchMessageProcessorDefinition search(ExpressionEvaluatorDefinition queryExp) {
        checkNotNull(queryExp, "query expression");
        return new SearchMessageProcessorBuilder(reference, queryExp);
    }

    public SearchWithJsonCallbackMessageProcessorDefinition searchWithJsonCallback(String query, String callback) {
        checkNotEmpty(query, "query");
        checkNotEmpty(callback, "callback");
        return new SearchWithJsonCallbackMessageProcessorBuilder(reference, query, callback);
    }

    public SearchWithJsonCallbackMessageProcessorDefinition searchWithJsonCallback(String query, ExpressionEvaluatorDefinition callbackExp) {
        checkNotEmpty(query, "query");
        checkNotNull(callbackExp, "callback expression");
        return new SearchWithJsonCallbackMessageProcessorBuilder(reference, query, callbackExp);
    }

    public SearchWithJsonCallbackMessageProcessorDefinition searchWithJsonCallback(ExpressionEvaluatorDefinition queryExp, String callback) {
        checkNotNull(queryExp, "query expression");
        checkNotEmpty(callback, "callback");
        return new SearchWithJsonCallbackMessageProcessorBuilder(reference, queryExp, callback);
    }

    public SearchWithJsonCallbackMessageProcessorDefinition searchWithJsonCallback(ExpressionEvaluatorDefinition queryExp, ExpressionEvaluatorDefinition callbackExp) {
        checkNotNull(queryExp, "query expression");
        checkNotNull(callbackExp, "callback expression");
        return new SearchWithJsonCallbackMessageProcessorBuilder(reference, queryExp, callbackExp);
    }

    public GetUserMessageProcessorDefinition getUser(String screenName) {
        checkNotEmpty(screenName, "screenName");
        return new GetUserMessageProcessorBuilder(reference, screenName);
    }

    public GetUserMessageProcessorDefinition getUser(ExpressionEvaluatorDefinition screenNameExp) {
        checkNotNull(screenNameExp, "screenName expression");
        return new GetUserMessageProcessorBuilder(reference, screenNameExp);
    }

    public GetUserTimelineMessageProcessorDefinition getUserTimeline() {
        return new GetUserTimelineMessageProcessorBuilder(reference);
    }

    public GetMentionsMessageProcessorDefinition getMentions() {
        return new GetMentionsMessageProcessorBuilder(reference);
    }

    public GetFriendsTimelineMessageProcessorDefinition getFriendsTimeline() {
        return new GetFriendsTimelineMessageProcessorBuilder(reference);
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
        checkNotNull(format, "format");
        reference.setFormat(format);
    }

    public void setConsumerKey(String consumerKey) {
        checkNotEmpty(consumerKey, "consumerKey");
        reference.setConsumerKey(consumerKey);
    }

    public void setConsumerSecret(String consumerSecret) {
        checkNotEmpty(consumerSecret, "consumerSecret");
        reference.setConsumerSecret(consumerSecret);
    }

    public void setOathToken(String oathToken) {
        checkNotEmpty(oathToken, "oathToken");
        reference.setOathToken(oathToken);
    }

    public void setOathTokenSecret(String oathTokenSecret) {
        checkNotEmpty(oathTokenSecret, "oathTokenSecret");
        reference.setOathTokenSecret(oathTokenSecret);
    }

    public void setFormatAsNull() {
        reference.setFormat(null);
    }

    public void setConsumerKeyAsNull() {
        reference.setConsumerKey(null);
    }

    public void setConsumerSecretAsNull() {
        reference.setConsumerSecret(null);
    }

    public void setOathTokenAsNull() {
        reference.setOathToken(null);
    }

    public void setOathTokenSecretAsNull() {
        reference.setOathTokenSecret(null);
    }


}
