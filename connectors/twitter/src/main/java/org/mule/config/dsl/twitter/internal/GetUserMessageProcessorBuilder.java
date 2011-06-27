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
import org.mule.config.dsl.twitter.GetUserMessageProcessorDefinition;
import org.mule.ibeans.twitter.config.GetUserMessageProcessor;

public class GetUserMessageProcessorBuilder implements GetUserMessageProcessorDefinition, Builder<GetUserMessageProcessor> {

    private final IBeansTwitterReference reference;

    private String screenName = null;
    private ExpressionEvaluatorBuilder screenNameExp = null;

    public GetUserMessageProcessorBuilder(IBeansTwitterReference reference, String screenName) {
        this.reference = reference;
        this.screenName = screenName;
    }

    public GetUserMessageProcessorBuilder(IBeansTwitterReference reference, ExpressionEvaluatorBuilder screenNameExp) {
        this.reference = reference;
        this.screenNameExp = screenNameExp;
    }

    @Override
    public GetUserMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        GetUserMessageProcessor mp = new GetUserMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(reference.getObject(muleContext));

        if (screenNameExp != null) {
            mp.setScreenName(screenNameExp.toString());
        } else {
            mp.setScreenName(screenName);
        }

        return mp;
    }
}
