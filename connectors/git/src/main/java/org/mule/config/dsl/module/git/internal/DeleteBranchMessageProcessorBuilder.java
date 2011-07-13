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
import org.mule.config.dsl.module.git.DeleteBranchMessageProcessorDefinition;
import org.mule.config.dsl.internal.Builder;
import org.mule.module.git.config.DeleteBranchMessageProcessor;

import static org.mule.config.dsl.expression.CoreExpr.string;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class DeleteBranchMessageProcessorBuilder implements DeleteBranchMessageProcessorDefinition, Builder<DeleteBranchMessageProcessor> {

    private final org.mule.module.git.GitConnector object;

    //Required
    private String name = null;
    private ExpressionEvaluatorDefinition nameExp = null;

    //Optional
    private boolean force = false;
    private String overrideDirectory = null;

    private ExpressionEvaluatorDefinition forceExp = null;
    private ExpressionEvaluatorDefinition overrideDirectoryExp = null;

    public DeleteBranchMessageProcessorBuilder(org.mule.module.git.GitConnector object, String name) {
        this.object = object;
        this.name = name;
    }

    public DeleteBranchMessageProcessorBuilder(org.mule.module.git.GitConnector object, ExpressionEvaluatorDefinition nameExp) {
        this.object = object;
        this.nameExp = nameExp;
    }

    @Override
    public DeleteBranchMessageProcessorBuilder withForce(boolean force) {
        this.force = force;
        return this;
    }

    @Override
    public DeleteBranchMessageProcessorBuilder withForce(ExpressionEvaluatorDefinition forceExp) {
        this.forceExp = forceExp;
        return this;
    }

    @Override
    public DeleteBranchMessageProcessorBuilder withOverrideDirectory(String overrideDirectory) {
        this.overrideDirectory = overrideDirectory;
        return this;
    }

    @Override
    public DeleteBranchMessageProcessorBuilder withOverrideDirectory(ExpressionEvaluatorDefinition overrideDirectoryExp) {
        this.overrideDirectoryExp = overrideDirectoryExp;
        return this;
    }

    @Override
    public DeleteBranchMessageProcessor build(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        DeleteBranchMessageProcessor mp = new DeleteBranchMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(object);

        if (nameExp != null) {
            mp.setName(nameExp.toString(placeholder));
        } else {
            mp.setName(name);
        }

        if (forceExp != null) {
            mp.setForce(forceExp.toString());
        } else {
            mp.setForce(string(String.valueOf(force)).toString());
        }

        if (overrideDirectory != null) {
            mp.setOverrideDirectory(overrideDirectoryExp.toString(placeholder));
        } else {
            mp.setOverrideDirectory(overrideDirectory);
        }

        return mp;
    }
}
