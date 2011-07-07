/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.module.twitter.internal;

import com.google.inject.Injector;
import org.mule.api.MuleContext;
import org.mule.config.dsl.ExpressionEvaluatorBuilder;
import org.mule.config.dsl.internal.Builder;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.config.dsl.module.twitter.GetMentionsMessageProcessorDefinition;
import org.mule.ibeans.twitter.config.GetMentionsMessageProcessor;

import static org.mule.config.dsl.expression.CoreExpr.string;

public class GetMentionsMessageProcessorBuilder implements GetMentionsMessageProcessorDefinition, Builder<GetMentionsMessageProcessor> {

    private final IBeansTwitterReference reference;

    private Integer count = null;
    private String sinceId = null;
    private String maxId = null;
    private Integer page = null;

    private ExpressionEvaluatorBuilder countExp = null;
    private ExpressionEvaluatorBuilder sinceIdExp = null;
    private ExpressionEvaluatorBuilder maxIdExp = null;
    private ExpressionEvaluatorBuilder pageExp = null;

    public GetMentionsMessageProcessorBuilder(IBeansTwitterReference reference) {
        this.reference = reference;
    }

    @Override
    public GetMentionsMessageProcessorDefinition withCount(Integer count) {
        this.count = count;
        return this;
    }

    @Override
    public GetMentionsMessageProcessorDefinition withCount(ExpressionEvaluatorBuilder countExp) {
        this.countExp = countExp;
        return this;
    }

    @Override
    public GetMentionsMessageProcessorDefinition withSinceId(String sinceId) {
        this.sinceId = sinceId;
        return this;
    }

    @Override
    public GetMentionsMessageProcessorDefinition withSinceId(ExpressionEvaluatorBuilder sinceIdExp) {
        this.sinceIdExp = sinceIdExp;
        return this;
    }

    @Override
    public GetMentionsMessageProcessorDefinition withMaxId(String maxId) {
        this.maxId = maxId;
        return this;
    }

    @Override
    public GetMentionsMessageProcessorDefinition withMaxId(ExpressionEvaluatorBuilder maxIdExp) {
        this.maxIdExp = maxIdExp;
        return this;
    }

    @Override
    public GetMentionsMessageProcessorDefinition withPage(Integer page) {
        this.page = page;
        return this;
    }

    @Override
    public GetMentionsMessageProcessorDefinition withPage(ExpressionEvaluatorBuilder pageExp) {
        this.pageExp = pageExp;
        return this;
    }

    @Override
    public GetMentionsMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        GetMentionsMessageProcessor mp = new GetMentionsMessageProcessor();

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
