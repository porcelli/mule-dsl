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
import org.mule.config.dsl.module.twitter.StatusesDestroyMessageProcessorDefinition;
import org.mule.ibeans.twitter.config.StatusesDestroyMessageProcessor;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class StatusesDestroyMessageProcessorBuilder implements StatusesDestroyMessageProcessorDefinition, Builder<StatusesDestroyMessageProcessor> {

    private final IBeanTwitterReference reference;

    private String id = null;
    private ExpressionEvaluatorDefinition idExp = null;

    public StatusesDestroyMessageProcessorBuilder(IBeanTwitterReference reference, String id) {
        this.reference = reference;
        this.id = id;
    }

    public StatusesDestroyMessageProcessorBuilder(IBeanTwitterReference reference, ExpressionEvaluatorDefinition idExp) {
        this.reference = reference;
        this.idExp = idExp;
    }

    @Override
    public StatusesDestroyMessageProcessor build(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        StatusesDestroyMessageProcessor mp = new StatusesDestroyMessageProcessor();

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
