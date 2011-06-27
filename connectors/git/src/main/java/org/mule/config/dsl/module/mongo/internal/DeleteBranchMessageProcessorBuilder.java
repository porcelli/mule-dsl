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
import org.mule.config.dsl.module.mongo.DeleteBranchMessageProcessorDefinition;
import org.mule.config.dsl.internal.Builder;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.module.git.config.DeleteBranchMessageProcessor;

import static org.mule.config.dsl.expression.CoreExpr.string;

public class DeleteBranchMessageProcessorBuilder implements DeleteBranchMessageProcessorDefinition, Builder<DeleteBranchMessageProcessor> {

    private final org.mule.module.git.GitConnector object;

    //Required
    private String name = null;
    private ExpressionEvaluatorBuilder nameExp = null;

    //Optional
    private boolean force = false;
    private String overrideDirectory = null;

    private ExpressionEvaluatorBuilder forceExp = null;
    private ExpressionEvaluatorBuilder overrideDirectoryExp = null;

    public DeleteBranchMessageProcessorBuilder(org.mule.module.git.GitConnector object, String name) {
        this.object = object;
        this.name = name;
    }

    public DeleteBranchMessageProcessorBuilder(org.mule.module.git.GitConnector object, ExpressionEvaluatorBuilder nameExp) {
        this.object = object;
        this.nameExp = nameExp;
    }

    @Override
    public DeleteBranchMessageProcessorBuilder withForce(boolean force) {
        this.force = force;
        return this;
    }

    @Override
    public DeleteBranchMessageProcessorBuilder withForce(ExpressionEvaluatorBuilder forceExp) {
        this.forceExp = forceExp;
        return this;
    }

    @Override
    public DeleteBranchMessageProcessorBuilder withOverrideDirectory(String overrideDirectory) {
        this.overrideDirectory = overrideDirectory;
        return this;
    }

    @Override
    public DeleteBranchMessageProcessorBuilder withOverrideDirectory(ExpressionEvaluatorBuilder overrideDirectoryExp) {
        this.overrideDirectoryExp = overrideDirectoryExp;
        return this;
    }

    @Override
    public DeleteBranchMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        DeleteBranchMessageProcessor mp = new DeleteBranchMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(object);

        if (nameExp != null) {
            mp.setName(nameExp);
        } else {
            mp.setName(name);
        }

        if (forceExp != null) {
            mp.setForce(forceExp.toString());
        } else {
            mp.setForce(string(String.valueOf(force)).toString());
        }

        if (overrideDirectory != null) {
            mp.setOverrideDirectory(overrideDirectoryExp);
        } else {
            mp.setOverrideDirectory(overrideDirectory);
        }

        return mp;
    }
}
