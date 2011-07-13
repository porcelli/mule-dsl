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
import org.mule.config.dsl.module.git.CloneMessageProcessorDefinition;
import org.mule.config.dsl.internal.Builder;
import org.mule.module.git.config.CloneMessageProcessor;

import static org.mule.config.dsl.expression.CoreExpr.string;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class CloneMessageProcessorBuilder implements CloneMessageProcessorDefinition, Builder<CloneMessageProcessor> {

    private final org.mule.module.git.GitConnector object;

    //Required
    private String uri = null;
    private ExpressionEvaluatorDefinition uriExp = null;

    //Optional
    private boolean bare = false;
    private String remote = "origin";
    private String branch = "HEAD";
    private String overrideDirectory = null;

    private ExpressionEvaluatorDefinition bareExp = null;
    private ExpressionEvaluatorDefinition remoteExp = null;
    private ExpressionEvaluatorDefinition branchExp = null;
    private ExpressionEvaluatorDefinition overrideDirectoryExp = null;

    public CloneMessageProcessorBuilder(org.mule.module.git.GitConnector object, String uri) {
        this.object = object;
        this.uri = uri;
    }

    public CloneMessageProcessorBuilder(org.mule.module.git.GitConnector object, ExpressionEvaluatorDefinition uriExp) {
        this.object = object;
        this.uriExp = uriExp;
    }

    @Override
    public CloneMessageProcessorBuilder withBare(boolean bare) {
        this.bare = bare;
        return this;
    }

    @Override
    public CloneMessageProcessorBuilder withBare(ExpressionEvaluatorDefinition bareExp) {
        this.bareExp = bareExp;
        return this;
    }

    @Override
    public CloneMessageProcessorBuilder withRemote(String remote) {
        this.remote = remote;
        return this;
    }

    @Override
    public CloneMessageProcessorBuilder withRemote(ExpressionEvaluatorDefinition remoteExp) {
        this.remoteExp = remoteExp;
        return this;
    }

    @Override
    public CloneMessageProcessorBuilder withBranch(String branch) {
        this.branch = branch;
        return this;
    }

    @Override
    public CloneMessageProcessorBuilder withBranch(ExpressionEvaluatorDefinition branchExp) {
        this.branchExp = branchExp;
        return this;
    }

    @Override
    public CloneMessageProcessorBuilder withOverrideDirectory(String overrideDirectory) {
        this.overrideDirectory = overrideDirectory;
        return this;
    }

    @Override
    public CloneMessageProcessorBuilder withOverrideDirectory(ExpressionEvaluatorDefinition overrideDirectoryExp) {
        this.overrideDirectoryExp = overrideDirectoryExp;
        return this;
    }

    @Override
    public CloneMessageProcessor build(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        CloneMessageProcessor mp = new CloneMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(object);

        if (uriExp != null) {
            mp.setUri(uriExp.toString(placeholder));
        } else {
            mp.setUri(uri);
        }

        if (bareExp != null) {
            mp.setBare(bareExp.toString());
        } else {
            mp.setBare(string(String.valueOf(bare)).toString());
        }

        if (remoteExp != null) {
            mp.setRemote(remoteExp.toString(placeholder));
        } else {
            mp.setRemote(remote);
        }

        if (branchExp != null) {
            mp.setBranch(branchExp.toString());
        } else {
            mp.setBranch(branch);
        }

        if (overrideDirectory != null) {
            mp.setOverrideDirectory(overrideDirectoryExp.toString(placeholder));
        } else {
            mp.setOverrideDirectory(overrideDirectory);
        }

        return mp;
    }
}
