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
import org.mule.config.dsl.internal.Builder;
import org.mule.config.dsl.module.git.AddMessageProcessorDefinition;
import org.mule.module.git.config.AddMessageProcessor;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class AddMessageProcessorBuilder implements AddMessageProcessorDefinition, Builder<AddMessageProcessor> {
    private final org.mule.module.git.GitConnector object;

    //Required
    private String filePattern = null;
    private ExpressionEvaluatorDefinition filePatternExp = null;

    //Optional
    private String overrideDirectory = null;

    private ExpressionEvaluatorDefinition overrideDirectoryExp = null;

    public AddMessageProcessorBuilder(org.mule.module.git.GitConnector object, String filePattern) {
        this.object = object;
        this.filePattern = filePattern;
    }

    public AddMessageProcessorBuilder(org.mule.module.git.GitConnector object, ExpressionEvaluatorDefinition filePatternExp) {
        this.object = object;
        this.filePatternExp = filePatternExp;
    }

    @Override
    public AddMessageProcessorBuilder withOverrideDirectory(String overrideDirectory) {
        this.overrideDirectory = overrideDirectory;
        return this;
    }

    @Override
    public AddMessageProcessorBuilder withOverrideDirectory(ExpressionEvaluatorDefinition overrideDirectoryExp) {
        this.overrideDirectoryExp = overrideDirectoryExp;
        return this;
    }

    @Override
    public AddMessageProcessor build(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        AddMessageProcessor mp = new AddMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(object);

        if (filePatternExp != null) {
            mp.setFilePattern(filePatternExp.toString(placeholder));
        } else {
            mp.setFilePattern(filePattern);
        }

        if (overrideDirectory != null) {
            mp.setOverrideDirectory(overrideDirectoryExp.toString(placeholder));
        } else {
            mp.setOverrideDirectory(overrideDirectory);
        }

        return mp;
    }
}
