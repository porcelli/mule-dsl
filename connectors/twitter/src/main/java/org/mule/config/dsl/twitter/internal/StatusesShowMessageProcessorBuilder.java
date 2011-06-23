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
import org.mule.config.dsl.twitter.StatusesShowMessageProcessorDefinition;
import org.mule.ibeans.twitter.config.StatusesShowMessageProcessor;

public class StatusesShowMessageProcessorBuilder implements StatusesShowMessageProcessorDefinition, Builder<StatusesShowMessageProcessor> {

    private final IBeansTwitterReference reference;

    private String id = null;
    private ExpressionEvaluatorBuilder idExp = null;

    public StatusesShowMessageProcessorBuilder(IBeansTwitterReference reference, String id) {
        this.reference = reference;
        this.id = id;
    }

    public StatusesShowMessageProcessorBuilder(IBeansTwitterReference reference, ExpressionEvaluatorBuilder idExp) {
        this.reference = reference;
        this.idExp = idExp;
    }

    @Override
    public StatusesShowMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        StatusesShowMessageProcessor mp = new StatusesShowMessageProcessor();

        mp.setObject(reference.getObject(muleContext));

        if (idExp != null) {
            mp.setId(idExp.toString());
        } else {
            mp.setId(id);
        }

        return mp;
    }
}
