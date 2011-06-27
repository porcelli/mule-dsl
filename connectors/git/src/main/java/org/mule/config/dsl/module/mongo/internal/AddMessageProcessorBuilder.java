/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.module.mongo.internal;

import com.google.inject.Injector;
import org.mule.api.MuleContext;
import org.mule.config.dsl.ExpressionEvaluatorBuilder;
import org.mule.config.dsl.internal.Builder;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.config.dsl.module.mongo.AddMessageProcessorDefinition;
import org.mule.module.git.config.AddMessageProcessor;

public class AddMessageProcessorBuilder implements AddMessageProcessorDefinition, Builder<AddMessageProcessor> {
    private final org.mule.module.git.GitConnector object;

    //Required
    private String filePattern = null;
    private ExpressionEvaluatorBuilder filePatternExpr = null;

    //Optional
    private String overrideDirectory = null;

    private ExpressionEvaluatorBuilder overrideDirectoryExp = null;

    public AddMessageProcessorBuilder(org.mule.module.git.GitConnector object, String filePattern) {
        this.object = object;
        this.filePattern = filePattern;
    }

    public AddMessageProcessorBuilder(org.mule.module.git.GitConnector object, ExpressionEvaluatorBuilder filePatternExpr) {
        this.object = object;
        this.filePatternExpr = filePatternExpr;
    }

    @Override
    public AddMessageProcessorBuilder withOverrideDirectory(String overrideDirectory) {
        this.overrideDirectory = overrideDirectory;
        return this;
    }

    @Override
    public AddMessageProcessorBuilder withOverrideDirectory(ExpressionEvaluatorBuilder overrideDirectoryExp) {
        this.overrideDirectoryExp = overrideDirectoryExp;
        return this;
    }

    @Override
    public AddMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        AddMessageProcessor mp = new AddMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(object);

        if (filePatternExpr != null) {
            mp.setFilePattern(filePatternExpr);
        } else {
            mp.setFilePattern(filePattern);
        }

        if (overrideDirectory != null) {
            mp.setOverrideDirectory(overrideDirectoryExp);
        } else {
            mp.setOverrideDirectory(overrideDirectory);
        }

        return mp;
    }
}
