/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.twitter.internal;

import com.google.inject.Injector;
import org.mule.api.MuleContext;
import org.mule.config.dsl.ExpressionEvaluatorBuilder;
import org.mule.config.dsl.internal.Builder;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.config.dsl.twitter.GetUserTimelineMessageProcessorDefinition;
import org.mule.ibeans.twitter.config.GetUserTimelineMessageProcessor;

import static org.mule.config.dsl.expression.CoreExpr.string;

public class GetUserTimelineMessageProcessorBuilder implements GetUserTimelineMessageProcessorDefinition, Builder<GetUserTimelineMessageProcessor> {

    private final IBeansTwitterReference reference;

    private String userId = null;
    private String screenName = null;
    private Integer count = null;
    private String sinceId = null;
    private String maxId = null;
    private Integer page = null;

    private ExpressionEvaluatorBuilder userIdExp = null;
    private ExpressionEvaluatorBuilder screenNameExp = null;
    private ExpressionEvaluatorBuilder countExp = null;
    private ExpressionEvaluatorBuilder sinceIdExp = null;
    private ExpressionEvaluatorBuilder maxIdExp = null;
    private ExpressionEvaluatorBuilder pageExp = null;

    public GetUserTimelineMessageProcessorBuilder(IBeansTwitterReference reference) {
        this.reference = reference;
    }

    @Override
    public GetUserTimelineMessageProcessorDefinition withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public GetUserTimelineMessageProcessorDefinition withUserId(ExpressionEvaluatorBuilder userIdExp) {
        this.userIdExp = userIdExp;
        return this;
    }

    @Override
    public GetUserTimelineMessageProcessorDefinition withScreenName(String screenName) {
        this.screenName = screenName;
        return this;
    }

    @Override
    public GetUserTimelineMessageProcessorDefinition withScreenName(ExpressionEvaluatorBuilder screenNameExp) {
        this.screenNameExp = screenNameExp;
        return this;
    }

    @Override
    public GetUserTimelineMessageProcessorDefinition withCount(Integer count) {
        this.count = count;
        return this;
    }

    @Override
    public GetUserTimelineMessageProcessorDefinition withCount(ExpressionEvaluatorBuilder countExp) {
        this.countExp = countExp;
        return this;
    }

    @Override
    public GetUserTimelineMessageProcessorDefinition withSinceId(String sinceId) {
        this.sinceId = sinceId;
        return this;
    }

    @Override
    public GetUserTimelineMessageProcessorDefinition withSinceId(ExpressionEvaluatorBuilder sinceIdExp) {
        this.sinceIdExp = sinceIdExp;
        return this;
    }

    @Override
    public GetUserTimelineMessageProcessorDefinition withMaxId(String maxId) {
        this.maxId = maxId;
        return this;
    }

    @Override
    public GetUserTimelineMessageProcessorDefinition withMaxId(ExpressionEvaluatorBuilder maxIdExp) {
        this.maxIdExp = maxIdExp;
        return this;
    }

    @Override
    public GetUserTimelineMessageProcessorDefinition withPage(Integer page) {
        this.page = page;
        return this;
    }

    @Override
    public GetUserTimelineMessageProcessorDefinition withPage(ExpressionEvaluatorBuilder pageExp) {
        this.pageExp = pageExp;
        return this;
    }

    @Override
    public GetUserTimelineMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        GetUserTimelineMessageProcessor mp = new GetUserTimelineMessageProcessor();

        mp.setObject(reference.getObject(muleContext));

        if (userIdExp != null) {
            mp.setUserId(userIdExp.toString());
        } else {
            mp.setUserId(userId);
        }

        if (screenNameExp != null) {
            mp.setScreenName(screenNameExp.toString());
        } else {
            mp.setScreenName(screenName);
        }

        if (countExp != null) {
            mp.setCount(countExp.toString());
        } else if (count != null) {
            mp.setCount(string(count.toString()));
        } else {
            mp.setCount(null);
        }

        if (sinceIdExp != null) {
            mp.setSinceId(sinceIdExp.toString());
        } else {
            mp.setSinceId(sinceId);
        }

        if (maxIdExp != null) {
            mp.setMaxId(maxIdExp.toString());
        } else {
            mp.setMaxId(maxId);
        }

        if (pageExp != null) {
            mp.setPage(pageExp.toString());
        } else if (page != null) {
            mp.setPage(string(page.toString()));
        } else {
            mp.setPage(null);
        }

        return mp;
    }
}
