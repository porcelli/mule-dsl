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
import org.mule.config.dsl.module.twitter.GetFriendsTimelineMessageProcessorDefinition;
import org.mule.ibeans.twitter.config.GetFriendsTimelineMessageProcessor;

import static org.mule.config.dsl.expression.CoreExpr.string;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class GetFriendsTimelineMessageProcessorBuilder implements GetFriendsTimelineMessageProcessorDefinition, Builder<GetFriendsTimelineMessageProcessor> {

    private final IBeanTwitterReference reference;

    private Integer count = null;
    private String sinceId = null;
    private String maxId = null;
    private Integer page = null;

    private ExpressionEvaluatorDefinition countExp = null;
    private ExpressionEvaluatorDefinition sinceIdExp = null;
    private ExpressionEvaluatorDefinition maxIdExp = null;
    private ExpressionEvaluatorDefinition pageExp = null;

    public GetFriendsTimelineMessageProcessorBuilder(IBeanTwitterReference reference) {
        this.reference = reference;
    }

    @Override
    public GetFriendsTimelineMessageProcessorDefinition withCount(Integer count) {
        this.count = count;
        return this;
    }

    @Override
    public GetFriendsTimelineMessageProcessorDefinition withCount(ExpressionEvaluatorDefinition countExp) {
        this.countExp = countExp;
        return this;
    }

    @Override
    public GetFriendsTimelineMessageProcessorDefinition withSinceId(String sinceId) {
        this.sinceId = sinceId;
        return this;
    }

    @Override
    public GetFriendsTimelineMessageProcessorDefinition withSinceId(ExpressionEvaluatorDefinition sinceIdExp) {
        this.sinceIdExp = sinceIdExp;
        return this;
    }

    @Override
    public GetFriendsTimelineMessageProcessorDefinition withMaxId(String maxId) {
        this.maxId = maxId;
        return this;
    }

    @Override
    public GetFriendsTimelineMessageProcessorDefinition withMaxId(ExpressionEvaluatorDefinition maxIdExp) {
        this.maxIdExp = maxIdExp;
        return this;
    }

    @Override
    public GetFriendsTimelineMessageProcessorDefinition withPage(Integer page) {
        this.page = page;
        return this;
    }

    @Override
    public GetFriendsTimelineMessageProcessorDefinition withPage(ExpressionEvaluatorDefinition pageExp) {
        this.pageExp = pageExp;
        return this;
    }

    @Override
    public GetFriendsTimelineMessageProcessor build(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        GetFriendsTimelineMessageProcessor mp = new GetFriendsTimelineMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(reference.getObject(muleContext));

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
