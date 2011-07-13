/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.module.git.internal;

import org.mule.api.MuleContext;
import org.mule.config.dsl.ConfigurationException;
import org.mule.config.dsl.ExpressionEvaluatorDefinition;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.config.dsl.module.git.FetchMessageProcessorDefinition;
import org.mule.config.dsl.internal.Builder;
import org.mule.module.git.config.FetchMessageProcessor;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class FetchMessageProcessorBuilder implements FetchMessageProcessorDefinition, Builder<FetchMessageProcessor> {

    private final org.mule.module.git.GitConnector object;

    //Optional
    private String overrideDirectory = null;

    private ExpressionEvaluatorDefinition overrideDirectoryExp = null;

    public FetchMessageProcessorBuilder(org.mule.module.git.GitConnector object) {
        this.object = object;
    }

    @Override
    public FetchMessageProcessorDefinition withOverrideDirectory(String overrideDirectory) {
        this.overrideDirectory = overrideDirectory;
        return this;
    }

    @Override
    public FetchMessageProcessorDefinition withOverrideDirectory(ExpressionEvaluatorDefinition overrideDirectoryExp) {
        this.overrideDirectoryExp = overrideDirectoryExp;
        return this;
    }

    @Override
    public FetchMessageProcessor build(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        FetchMessageProcessor mp = new FetchMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(object);

        if (overrideDirectory != null) {
            mp.setOverrideDirectory(overrideDirectoryExp.toString(placeholder));
        } else {
            mp.setOverrideDirectory(overrideDirectory);
        }

        return mp;
    }
}
