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
import org.mule.config.dsl.module.mongo.CloneMessageProcessorDefinition;
import org.mule.config.dsl.internal.Builder;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.module.git.config.CloneMessageProcessor;

import static org.mule.config.dsl.expression.CoreExpr.string;

public class CloneMessageProcessorBuilder implements CloneMessageProcessorDefinition, Builder<CloneMessageProcessor> {

    private final org.mule.module.git.GitConnector object;

    //Required
    private String uri = null;
    private ExpressionEvaluatorBuilder uriExp = null;

    //Optional
    private boolean bare = false;
    private String remote = "origin";
    private String branch = "HEAD";
    private String overrideDirectory = null;

    private ExpressionEvaluatorBuilder bareExp = null;
    private ExpressionEvaluatorBuilder remoteExp = null;
    private ExpressionEvaluatorBuilder branchExp = null;
    private ExpressionEvaluatorBuilder overrideDirectoryExp = null;

    public CloneMessageProcessorBuilder(org.mule.module.git.GitConnector object, String uri) {
        this.object = object;
        this.uri = uri;
    }

    public CloneMessageProcessorBuilder(org.mule.module.git.GitConnector object, ExpressionEvaluatorBuilder uriExp) {
        this.object = object;
        this.uriExp = uriExp;
    }

    @Override
    public CloneMessageProcessorBuilder withBare(boolean bare) {
        this.bare = bare;
        return this;
    }

    @Override
    public CloneMessageProcessorBuilder withBare(ExpressionEvaluatorBuilder bareExp) {
        this.bareExp = bareExp;
        return this;
    }

    @Override
    public CloneMessageProcessorBuilder withRemote(String remote) {
        this.remote = remote;
        return this;
    }

    @Override
    public CloneMessageProcessorBuilder withRemote(ExpressionEvaluatorBuilder remoteExp) {
        this.remoteExp = remoteExp;
        return this;
    }

    @Override
    public CloneMessageProcessorBuilder withBranch(String branch) {
        this.branch = branch;
        return this;
    }

    @Override
    public CloneMessageProcessorBuilder withBranch(ExpressionEvaluatorBuilder branchExp) {
        this.branchExp = branchExp;
        return this;
    }

    @Override
    public CloneMessageProcessorBuilder withOverrideDirectory(String overrideDirectory) {
        this.overrideDirectory = overrideDirectory;
        return this;
    }

    @Override
    public CloneMessageProcessorBuilder withOverrideDirectory(ExpressionEvaluatorBuilder overrideDirectoryExp) {
        this.overrideDirectoryExp = overrideDirectoryExp;
        return this;
    }

    @Override
    public CloneMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        CloneMessageProcessor mp = new CloneMessageProcessor();

        mp.setObject(object);

        if (uriExp != null) {
            mp.setUri(uriExp);
        } else {
            mp.setUri(uri);
        }

        if (bareExp != null) {
            mp.setBare(bareExp.toString());
        } else {
            mp.setBare(string(String.valueOf(bare)).toString());
        }

        if (remoteExp != null) {
            mp.setRemote(remoteExp);
        } else {
            mp.setRemote(remote);
        }

        if (branchExp != null) {
            mp.setBranch(branchExp.toString());
        } else {
            mp.setBranch(branch);
        }

        if (overrideDirectory != null) {
            mp.setOverrideDirectory(overrideDirectoryExp);
        } else {
            mp.setOverrideDirectory(overrideDirectory);
        }

        return mp;
    }
}
