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
import org.mule.config.dsl.module.twitter.StatusesDestroyMessageProcessorDefinition;
import org.mule.ibeans.twitter.config.StatusesDestroyMessageProcessor;

public class StatusesDestroyMessageProcessorBuilder implements StatusesDestroyMessageProcessorDefinition, Builder<StatusesDestroyMessageProcessor> {

    private final IBeansTwitterReference reference;

    private String id = null;
    private ExpressionEvaluatorBuilder idExp = null;

    public StatusesDestroyMessageProcessorBuilder(IBeansTwitterReference reference, String id) {
        this.reference = reference;
        this.id = id;
    }

    public StatusesDestroyMessageProcessorBuilder(IBeansTwitterReference reference, ExpressionEvaluatorBuilder idExp) {
        this.reference = reference;
        this.idExp = idExp;
    }

    @Override
    public StatusesDestroyMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
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
