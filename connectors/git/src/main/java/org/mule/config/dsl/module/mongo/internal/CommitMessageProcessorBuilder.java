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
import org.mule.config.dsl.module.mongo.CommitMessageProcessorDefinition;
import org.mule.config.dsl.internal.Builder;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.module.git.config.CommitMessageProcessor;

public class CommitMessageProcessorBuilder implements CommitMessageProcessorDefinition, Builder<CommitMessageProcessor> {

    private final org.mule.module.git.GitConnector object;

    //Required
    private String msg = null;
    private String committerName = null;
    private String committerEmail = null;

    private ExpressionEvaluatorBuilder msgExp = null;
    private ExpressionEvaluatorBuilder committerNameExp = null;
    private ExpressionEvaluatorBuilder committerEmailExp = null;

    //Optional
    private String overrideDirectory = null;
    private String authorName = null;
    private String authorEmail = null;

    private ExpressionEvaluatorBuilder overrideDirectoryExp = null;
    private ExpressionEvaluatorBuilder authorNameExp = null;
    private ExpressionEvaluatorBuilder authorEmailExp = null;

    public CommitMessageProcessorBuilder(org.mule.module.git.GitConnector object, String msg, String committerName, String committerEmail) {
        this.object = object;
        this.msg = msg;
        this.committerName = committerName;
        this.committerEmail = committerEmail;
    }

    public CommitMessageProcessorBuilder(org.mule.module.git.GitConnector object, ExpressionEvaluatorBuilder msgExp, ExpressionEvaluatorBuilder committerNameExp, ExpressionEvaluatorBuilder committerEmailExp) {
        this.object = object;
        this.msgExp = msgExp;
        this.committerNameExp = committerNameExp;
        this.committerEmailExp = committerEmailExp;
    }

    public CommitMessageProcessorBuilder(org.mule.module.git.GitConnector object, ExpressionEvaluatorBuilder msgExp, String committerName, String committerEmail) {
        this.object = object;
        this.msgExp = msgExp;
        this.committerName = committerName;
        this.committerEmail = committerEmail;
    }

    public CommitMessageProcessorBuilder(org.mule.module.git.GitConnector object, String msg, ExpressionEvaluatorBuilder committerNameExp, String committerEmail) {
        this.object = object;
        this.msg = msg;
        this.committerNameExp = committerNameExp;
        this.committerEmail = committerEmail;
    }

    public CommitMessageProcessorBuilder(org.mule.module.git.GitConnector object, String msg, String committerName, ExpressionEvaluatorBuilder committerEmailExp) {
        this.object = object;
        this.msg = msg;
        this.committerName = committerName;
        this.committerEmailExp = committerEmailExp;
    }

    public CommitMessageProcessorBuilder(org.mule.module.git.GitConnector object, ExpressionEvaluatorBuilder msgExp, ExpressionEvaluatorBuilder committerNameExp, String committerEmail) {
        this.object = object;
        this.msgExp = msgExp;
        this.committerNameExp = committerNameExp;
        this.committerEmail = committerEmail;
    }

    public CommitMessageProcessorBuilder(org.mule.module.git.GitConnector object, String msg, ExpressionEvaluatorBuilder committerNameExp, ExpressionEvaluatorBuilder committerEmailExp) {
        this.object = object;
        this.msg = msg;
        this.committerNameExp = committerNameExp;
        this.committerEmailExp = committerEmailExp;
    }

    public CommitMessageProcessorBuilder(org.mule.module.git.GitConnector object, ExpressionEvaluatorBuilder msgExp, String committerName, ExpressionEvaluatorBuilder committerEmailExp) {
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
    public CommitMessageProcessorBuilder withAuthorName(ExpressionEvaluatorBuilder authorNameExp) {
        this.authorNameExp = authorNameExp;
        return this;
    }

    @Override
    public CommitMessageProcessorBuilder withAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
        return this;
    }

    @Override
    public CommitMessageProcessorBuilder withAuthorEmail(ExpressionEvaluatorBuilder authorEmailExp) {
        this.authorEmailExp = authorEmailExp;
        return this;
    }

    @Override
    public CommitMessageProcessorBuilder withOverrideDirectory(String overrideDirectory) {
        this.overrideDirectory = overrideDirectory;
        return this;
    }

    @Override
    public CommitMessageProcessorBuilder withOverrideDirectory(ExpressionEvaluatorBuilder overrideDirectoryExp) {
        this.overrideDirectoryExp = overrideDirectoryExp;
        return this;
    }

    @Override
    public CommitMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        CommitMessageProcessor mp = new CommitMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(object);

        if (msgExp != null) {
            mp.setMsg(msgExp);
        } else {
            mp.setMsg(msg);
        }

        if (committerNameExp != null) {
            mp.setCommitterName(committerNameExp);
        } else {
            mp.setCommitterName(committerName);
        }

        if (committerEmailExp != null) {
            mp.setCommitterEmail(committerEmailExp);
        } else {
            mp.setCommitterEmail(committerEmail);
        }

        if (authorNameExp != null) {
            mp.setAuthorName(authorNameExp);
        } else {
            mp.setAuthorName(authorName);
        }

        if (authorEmailExp != null) {
            mp.setAuthorEmail(authorEmailExp);
        } else {
            mp.setAuthorEmail(authorEmail);
        }

        if (overrideDirectory != null) {
            mp.setOverrideDirectory(overrideDirectoryExp);
        } else {
            mp.setOverrideDirectory(overrideDirectory);
        }

        return mp;
    }
}
