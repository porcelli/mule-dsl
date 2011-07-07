/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.module.git.internal;

import com.google.inject.Injector;
import org.mule.api.MuleContext;
import org.mule.config.dsl.ExpressionEvaluatorBuilder;
import org.mule.config.dsl.module.git.PushMessageProcessorDefinition;
import org.mule.config.dsl.internal.Builder;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.module.git.config.PushMessageProcessor;

import static org.mule.config.dsl.expression.CoreExpr.string;

public class PushMessageProcessorBuilder implements PushMessageProcessorDefinition, Builder<PushMessageProcessor> {

    private final org.mule.module.git.GitConnector object;

    //Optional
    private boolean force = false;
    private String remote = "origin";
    private String overrideDirectory = null;

    private ExpressionEvaluatorBuilder forceExp = null;
    private ExpressionEvaluatorBuilder remoteExp = null;
    private ExpressionEvaluatorBuilder overrideDirectoryExp = null;

    public PushMessageProcessorBuilder(org.mule.module.git.GitConnector object) {
        this.object = object;
    }

    @Override
    public PushMessageProcessorDefinition withForce(boolean force) {
        this.force = force;
        return this;
    }

    @Override
    public PushMessageProcessorDefinition withForce(ExpressionEvaluatorBuilder forceExp) {
        this.forceExp = forceExp;
        return this;
    }

    @Override
    public PushMessageProcessorDefinition withRemote(String remote) {
        this.remote = remote;
        return this;
    }

    @Override
    public PushMessageProcessorDefinition withRemote(ExpressionEvaluatorBuilder remoteExp) {
        this.remoteExp = remoteExp;
        return this;
    }

    @Override
    public PushMessageProcessorDefinition withOverrideDirectory(String overrideDirectory) {
        this.overrideDirectory = overrideDirectory;
        return this;
    }

    @Override
    public PushMessageProcessorDefinition withOverrideDirectory(ExpressionEvaluatorBuilder overrideDirectoryExp) {
        this.overrideDirectoryExp = overrideDirectoryExp;
        return this;
    }

    @Override
    public PushMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
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
