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
import org.mule.config.dsl.module.git.CommitMessageProcessorDefinition;
import org.mule.config.dsl.internal.Builder;
import org.mule.module.git.config.CommitMessageProcessor;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class CommitMessageProcessorBuilder implements CommitMessageProcessorDefinition, Builder<CommitMessageProcessor> {

    private final org.mule.module.git.GitConnector object;

    //Required
    private String msg = null;
    private String committerName = null;
    private String committerEmail = null;

    private ExpressionEvaluatorDefinition msgExp = null;
    private ExpressionEvaluatorDefinition committerNameExp = null;
    private ExpressionEvaluatorDefinition committerEmailExp = null;

    //Optional
    private String overrideDirectory = null;
    private String authorName = null;
    private String authorEmail = null;

    private ExpressionEvaluatorDefinition overrideDirectoryExp = null;
    private ExpressionEvaluatorDefinition authorNameExp = null;
    private ExpressionEvaluatorDefinition authorEmailExp = null;

    public CommitMessageProcessorBuilder(org.mule.module.git.GitConnector object, String msg, String committerName, String committerEmail) {
        this.object = object;
        this.msg = msg;
        this.committerName = committerName;
        this.committerEmail = committerEmail;
    }

    public CommitMessageProcessorBuilder(org.mule.module.git.GitConnector object, ExpressionEvaluatorDefinition msgExp, ExpressionEvaluatorDefinition committerNameExp, ExpressionEvaluatorDefinition committerEmailExp) {
        this.object = object;
        this.msgExp = msgExp;
        this.committerNameExp = committerNameExp;
        this.committerEmailExp = committerEmailExp;
    }

    public CommitMessageProcessorBuilder(org.mule.module.git.GitConnector object, ExpressionEvaluatorDefinition msgExp, String committerName, String committerEmail) {
        this.object = object;
        this.msgExp = msgExp;
        this.committerName = committerName;
        this.committerEmail = committerEmail;
    }

    public CommitMessageProcessorBuilder(org.mule.module.git.GitConnector object, String msg, ExpressionEvaluatorDefinition committerNameExp, String committerEmail) {
        this.object = object;
        this.msg = msg;
        this.committerNameExp = committerNameExp;
        this.committerEmail = committerEmail;
    }

    public CommitMessageProcessorBuilder(org.mule.module.git.GitConnector object, String msg, String committerName, ExpressionEvaluatorDefinition committerEmailExp) {
        this.object = object;
        this.msg = msg;
        this.committerName = committerName;
        this.committerEmailExp = committerEmailExp;
    }

    public CommitMessageProcessorBuilder(org.mule.module.git.GitConnector object, ExpressionEvaluatorDefinition msgExp, ExpressionEvaluatorDefinition committerNameExp, String committerEmail) {
        this.object = object;
        this.msgExp = msgExp;
        this.committerNameExp = committerNameExp;
        this.committerEmail = committerEmail;
    }

    public CommitMessageProcessorBuilder(org.mule.module.git.GitConnector object, String msg, ExpressionEvaluatorDefinition committerNameExp, ExpressionEvaluatorDefinition committerEmailExp) {
        this.object = object;
        this.msg = msg;
        this.committerNameExp = committerNameExp;
        this.committerEmailExp = committerEmailExp;
    }

    public CommitMessageProcessorBuilder(org.mule.module.git.GitConnector object, ExpressionEvaluatorDefinition msgExp, String committerName, ExpressionEvaluatorDefinition committerEmailExp) {
        this.object = object;
        this.msgExp = msgExp;
        this.committerName = committerName;
        this.committerEmailExp = committerEmailExp;
    }

    @Override
    public CommitMessageProcessorBuilder withAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    @Override
    public CommitMessageProcessorBuilder withAuthorName(ExpressionEvaluatorDefinition authorNameExp) {
        this.authorNameExp = authorNameExp;
        return this;
    }

    @Override
    public CommitMessageProcessorBuilder withAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
        return this;
    }

    @Override
    public CommitMessageProcessorBuilder withAuthorEmail(ExpressionEvaluatorDefinition authorEmailExp) {
        this.authorEmailExp = authorEmailExp;
        return this;
    }

    @Override
    public CommitMessageProcessorBuilder withOverrideDirectory(String overrideDirectory) {
        this.overrideDirectory = overrideDirectory;
        return this;
    }

    @Override
    public CommitMessageProcessorBuilder withOverrideDirectory(ExpressionEvaluatorDefinition overrideDirectoryExp) {
        this.overrideDirectoryExp = overrideDirectoryExp;
        return this;
    }

    @Override
    public CommitMessageProcessor build(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        CommitMessageProcessor mp = new CommitMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(object);

        if (msgExp != null) {
            mp.setMsg(msgExp.toString(placeholder));
        } else {
            mp.setMsg(msg);
        }

        if (committerNameExp != null) {
            mp.setCommitterName(committerNameExp.toString(placeholder));
        } else {
            mp.setCommitterName(committerName);
        }

        if (committerEmailExp != null) {
            mp.setCommitterEmail(committerEmailExp.toString(placeholder));
        } else {
            mp.setCommitterEmail(committerEmail);
        }

        if (authorNameExp != null) {
            mp.setAuthorName(authorNameExp.toString(placeholder));
        } else {
            mp.setAuthorName(authorName);
        }

        if (authorEmailExp != null) {
            mp.setAuthorEmail(authorEmailExp.toString(placeholder));
        } else {
            mp.setAuthorEmail(authorEmail);
        }

        if (overrideDirectory != null) {
            mp.setOverrideDirectory(overrideDirectoryExp.toString(placeholder));
        } else {
            mp.setOverrideDirectory(overrideDirectory);
        }

        return mp;
    }
}
