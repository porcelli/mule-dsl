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
import org.mule.config.dsl.module.twitter.GetUserMessageProcessorDefinition;
import org.mule.ibeans.twitter.config.GetUserMessageProcessor;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class GetUserMessageProcessorBuilder implements GetUserMessageProcessorDefinition, Builder<GetUserMessageProcessor> {

    private final IBeanTwitterReference reference;

    private String screenName = null;
    private ExpressionEvaluatorDefinition screenNameExp = null;

    public GetUserMessageProcessorBuilder(IBeanTwitterReference reference, String screenName) {
        this.reference = reference;
        this.screenName = screenName;
    }

    public GetUserMessageProcessorBuilder(IBeanTwitterReference reference, ExpressionEvaluatorDefinition screenNameExp) {
        this.reference = reference;
        this.screenNameExp = screenNameExp;
    }

    @Override
    public GetUserMessageProcessor build(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        GetUserMessageProcessor mp = new GetUserMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(reference.getObject(muleContext));

        if (screenNameExp != null) {
            mp.setScreenName(screenNameExp.toString(placeholder));
        } else {
            mp.setScreenName(screenName);
        }

        return mp;
    }
}
