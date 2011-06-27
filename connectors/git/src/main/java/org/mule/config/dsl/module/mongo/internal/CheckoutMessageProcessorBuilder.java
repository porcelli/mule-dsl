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
import org.mule.config.dsl.module.mongo.CheckoutMessageProcessorDefinition;
import org.mule.config.dsl.internal.Builder;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.module.git.config.CheckoutMessageProcessor;

public class CheckoutMessageProcessorBuilder implements CheckoutMessageProcessorDefinition, Builder<CheckoutMessageProcessor> {

    private final org.mule.module.git.GitConnector object;

    //Required
    private String branch = null;
    private ExpressionEvaluatorBuilder branchExp = null;

    //Optional
    private String startPoint = null;
    private String overrideDirectory = null;

    private ExpressionEvaluatorBuilder startPointExp = null;
    private ExpressionEvaluatorBuilder overrideDirectoryExp = null;

    public CheckoutMessageProcessorBuilder(org.mule.module.git.GitConnector object, String branch) {
        this.object = object;
        this.branch = branch;
    }

    public CheckoutMessageProcessorBuilder(org.mule.module.git.GitConnector object, ExpressionEvaluatorBuilder branchExp) {
        this.object = object;
        this.branchExp = branchExp;
    }

    @Override
    public CheckoutMessageProcessorDefinition withStartPoint(String startPoint) {
        this.startPoint = startPoint;
        return this;
    }

    @Override
    public CheckoutMessageProcessorDefinition withStartPoint(ExpressionEvaluatorBuilder startPointExp) {
        this.startPointExp = startPointExp;
        return this;
    }

    @Override
    public CheckoutMessageProcessorDefinition withOverrideDirectory(String overrideDirectory) {
        this.overrideDirectory = overrideDirectory;
        return this;
    }

    @Override
    public CheckoutMessageProcessorDefinition withOverrideDirectory(ExpressionEvaluatorBuilder overrideDirectoryExp) {
        this.overrideDirectoryExp = overrideDirectoryExp;
        return this;
    }

    @Override
    public CheckoutMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        CheckoutMessageProcessor mp = new CheckoutMessageProcessor();

        mp.setObject(object);

        if (branchExp != null) {
            mp.setBranch(branchExp);
        } else {
            mp.setBranch(branch);
        }

        if (startPointExp != null) {
            mp.setStartPoint(startPointExp);
        } else {
            mp.setStartPoint(startPoint);
        }

        if (overrideDirectory != null) {
            mp.setOverrideDirectory(overrideDirectoryExp);
        } else {
            mp.setOverrideDirectory(overrideDirectory);
        }

        return mp;
    }
}
