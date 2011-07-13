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
import org.mule.config.dsl.module.twitter.StatusesShowMessageProcessorDefinition;
import org.mule.ibeans.twitter.config.StatusesShowMessageProcessor;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class StatusesShowMessageProcessorBuilder implements StatusesShowMessageProcessorDefinition, Builder<StatusesShowMessageProcessor> {

    private final IBeanTwitterReference reference;

    private String id = null;
    private ExpressionEvaluatorDefinition idExp = null;

    public StatusesShowMessageProcessorBuilder(IBeanTwitterReference reference, String id) {
        this.reference = reference;
        this.id = id;
    }

    public StatusesShowMessageProcessorBuilder(IBeanTwitterReference reference, ExpressionEvaluatorDefinition idExp) {
        this.reference = reference;
        this.idExp = idExp;
    }

    @Override
    public StatusesShowMessageProcessor build(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        StatusesShowMessageProcessor mp = new StatusesShowMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(reference.getObject(muleContext));

        if (idExp != null) {
            mp.setId(idExp.toString(placeholder));
        } else {
            mp.setId(id);
        }

        return mp;
    }
}
