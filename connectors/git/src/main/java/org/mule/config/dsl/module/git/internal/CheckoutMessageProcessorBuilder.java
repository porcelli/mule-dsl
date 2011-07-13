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
import org.mule.config.dsl.module.git.CheckoutMessageProcessorDefinition;
import org.mule.config.dsl.internal.Builder;
import org.mule.module.git.config.CheckoutMessageProcessor;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class CheckoutMessageProcessorBuilder implements CheckoutMessageProcessorDefinition, Builder<CheckoutMessageProcessor> {

    private final org.mule.module.git.GitConnector object;

    //Required
    private String branch = null;
    private ExpressionEvaluatorDefinition branchExp = null;

    //Optional
    private String startPoint = null;
    private String overrideDirectory = null;

    private ExpressionEvaluatorDefinition startPointExp = null;
    private ExpressionEvaluatorDefinition overrideDirectoryExp = null;

    public CheckoutMessageProcessorBuilder(org.mule.module.git.GitConnector object, String branch) {
        this.object = object;
        this.branch = branch;
    }

    public CheckoutMessageProcessorBuilder(org.mule.module.git.GitConnector object, ExpressionEvaluatorDefinition branchExp) {
        this.object = object;
        this.branchExp = branchExp;
    }

    @Override
    public CheckoutMessageProcessorDefinition withStartPoint(String startPoint) {
        this.startPoint = startPoint;
        return this;
    }

    @Override
    public CheckoutMessageProcessorDefinition withStartPoint(ExpressionEvaluatorDefinition startPointExp) {
        this.startPointExp = startPointExp;
        return this;
    }

    @Override
    public CheckoutMessageProcessorDefinition withOverrideDirectory(String overrideDirectory) {
        this.overrideDirectory = overrideDirectory;
        return this;
    }

    @Override
    public CheckoutMessageProcessorDefinition withOverrideDirectory(ExpressionEvaluatorDefinition overrideDirectoryExp) {
        this.overrideDirectoryExp = overrideDirectoryExp;
        return this;
    }

    @Override
    public CheckoutMessageProcessor build(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        CheckoutMessageProcessor mp = new CheckoutMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(object);

        if (branchExp != null) {
            mp.setBranch(branchExp.toString(placeholder));
        } else {
            mp.setBranch(branch);
        }

        if (startPointExp != null) {
            mp.setStartPoint(startPointExp.toString(placeholder));
        } else {
            mp.setStartPoint(startPoint);
        }

        if (overrideDirectory != null) {
            mp.setOverrideDirectory(overrideDirectoryExp.toString(placeholder));
        } else {
            mp.setOverrideDirectory(overrideDirectory);
        }

        return mp;
    }
}
