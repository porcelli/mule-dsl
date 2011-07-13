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
import org.mule.config.dsl.module.git.PushMessageProcessorDefinition;
import org.mule.config.dsl.internal.Builder;
import org.mule.module.git.config.PushMessageProcessor;

import static org.mule.config.dsl.expression.CoreExpr.string;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class PushMessageProcessorBuilder implements PushMessageProcessorDefinition, Builder<PushMessageProcessor> {

    private final org.mule.module.git.GitConnector object;

    //Optional
    private boolean force = false;
    private String remote = "origin";
    private String overrideDirectory = null;

    private ExpressionEvaluatorDefinition forceExp = null;
    private ExpressionEvaluatorDefinition remoteExp = null;
    private ExpressionEvaluatorDefinition overrideDirectoryExp = null;

    public PushMessageProcessorBuilder(org.mule.module.git.GitConnector object) {
        this.object = object;
    }

    @Override
    public PushMessageProcessorDefinition withForce(boolean force) {
        this.force = force;
        return this;
    }

    @Override
    public PushMessageProcessorDefinition withForce(ExpressionEvaluatorDefinition forceExp) {
        this.forceExp = forceExp;
        return this;
    }

    @Override
    public PushMessageProcessorDefinition withRemote(String remote) {
        this.remote = remote;
        return this;
    }

    @Override
    public PushMessageProcessorDefinition withRemote(ExpressionEvaluatorDefinition remoteExp) {
        this.remoteExp = remoteExp;
        return this;
    }

    @Override
    public PushMessageProcessorDefinition withOverrideDirectory(String overrideDirectory) {
        this.overrideDirectory = overrideDirectory;
        return this;
    }

    @Override
    public PushMessageProcessorDefinition withOverrideDirectory(ExpressionEvaluatorDefinition overrideDirectoryExp) {
        this.overrideDirectoryExp = overrideDirectoryExp;
        return this;
    }

    @Override
    public PushMessageProcessor build(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        PushMessageProcessor mp = new PushMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(object);

        if (forceExp != null) {
            mp.setForce(forceExp.toString());
        } else {
            mp.setForce(string(String.valueOf(force)).toString());
        }

        if (remoteExp != null) {
            mp.setRemote(remoteExp.toString(placeholder));
        } else {
            mp.setRemote(remote);
        }

        if (overrideDirectory != null) {
            mp.setOverrideDirectory(overrideDirectoryExp.toString(placeholder));
        } else {
            mp.setOverrideDirectory(overrideDirectory);
        }

        return mp;
    }
}
