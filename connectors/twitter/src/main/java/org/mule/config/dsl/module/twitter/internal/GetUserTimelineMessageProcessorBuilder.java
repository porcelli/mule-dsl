/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.module.twitter.internal;

import org.mule.api.MuleContext;
import org.mule.config.dsl.ConfigurationException;
import org.mule.config.dsl.ExpressionEvaluatorDefinition;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.config.dsl.internal.Builder;
import org.mule.config.dsl.module.twitter.GetUserTimelineMessageProcessorDefinition;
import org.mule.ibeans.twitter.config.GetUserTimelineMessageProcessor;

import static org.mule.config.dsl.expression.CoreExpr.string;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class GetUserTimelineMessageProcessorBuilder implements GetUserTimelineMessageProcessorDefinition, Builder<GetUserTimelineMessageProcessor> {

    private final IBeanTwitterReference reference;

    private String userId = null;
    private String screenName = null;
    private Integer count = null;
    private String sinceId = null;
    private String maxId = null;
    private Integer page = null;

    private ExpressionEvaluatorDefinition userIdExp = null;
    private ExpressionEvaluatorDefinition screenNameExp = null;
    private ExpressionEvaluatorDefinition countExp = null;
    private ExpressionEvaluatorDefinition sinceIdExp = null;
    private ExpressionEvaluatorDefinition maxIdExp = null;
    private ExpressionEvaluatorDefinition pageExp = null;

    public GetUserTimelineMessageProcessorBuilder(IBeanTwitterReference reference) {
        this.reference = reference;
    }

    @Override
    public GetUserTimelineMessageProcessorDefinition withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public GetUserTimelineMessageProcessorDefinition withUserId(ExpressionEvaluatorDefinition userIdExp) {
        this.userIdExp = userIdExp;
        return this;
    }

    @Override
    public GetUserTimelineMessageProcessorDefinition withScreenName(String screenName) {
        this.screenName = screenName;
        return this;
    }

    @Override
    public GetUserTimelineMessageProcessorDefinition withScreenName(ExpressionEvaluatorDefinition screenNameExp) {
        this.screenNameExp = screenNameExp;
        return this;
    }

    @Override
    public GetUserTimelineMessageProcessorDefinition withCount(Integer count) {
        this.count = count;
        return this;
    }

    @Override
    public GetUserTimelineMessageProcessorDefinition withCount(ExpressionEvaluatorDefinition countExp) {
        this.countExp = countExp;
        return this;
    }

    @Override
    public GetUserTimelineMessageProcessorDefinition withSinceId(String sinceId) {
        this.sinceId = sinceId;
        return this;
    }

    @Override
    public GetUserTimelineMessageProcessorDefinition withSinceId(ExpressionEvaluatorDefinition sinceIdExp) {
        this.sinceIdExp = sinceIdExp;
        return this;
    }

    @Override
    public GetUserTimelineMessageProcessorDefinition withMaxId(String maxId) {
        this.maxId = maxId;
        return this;
    }

    @Override
    public GetUserTimelineMessageProcessorDefinition withMaxId(ExpressionEvaluatorDefinition maxIdExp) {
        this.maxIdExp = maxIdExp;
        return this;
    }

    @Override
    public GetUserTimelineMessageProcessorDefinition withPage(Integer page) {
        this.page = page;
        return this;
    }

    @Override
    public GetUserTimelineMessageProcessorDefinition withPage(ExpressionEvaluatorDefinition pageExp) {
        this.pageExp = pageExp;
        return this;
    }

    @Override
    public GetUserTimelineMessageProcessor build(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        GetUserTimelineMessageProcessor mp = new GetUserTimelineMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(reference.getObject(muleContext));

        if (userIdExp != null) {
            mp.setUserId(userIdExp.toString(placeholder));
        } else {
            mp.setUserId(userId);
        }

        if (screenNameExp != null) {
            mp.setScreenName(screenNameExp.toString(placeholder));
        } else {
            mp.setScreenName(screenName);
        }

        if (countExp != null) {
            mp.setCount(countExp.toString(placeholder));
        } else if (count != null) {
            mp.setCount(string(count.toString()));
        } else {
            mp.setCount(null);
        }

        if (sinceIdExp != null) {
            mp.setSinceId(sinceIdExp.toString(placeholder));
        } else {
            mp.setSinceId(sinceId);
        }

        if (maxIdExp != null) {
            mp.setMaxId(maxIdExp.toString(placeholder));
        } else {
            mp.setMaxId(maxId);
        }

        if (pageExp != null) {
            mp.setPage(pageExp.toString(placeholder));
        } else if (page != null) {
            mp.setPage(string(page.toString()));
        } else {
            mp.setPage(null);
        }

        return mp;
    }
}
