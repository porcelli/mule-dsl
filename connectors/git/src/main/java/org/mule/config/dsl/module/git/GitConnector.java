/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.module.git;

import org.mule.config.dsl.ExpressionEvaluatorBuilder;
import org.mule.config.dsl.module.git.internal.*;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotEmpty;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class GitConnector {

    private org.mule.module.git.GitConnector object;

    public GitConnector() {
        object = new org.mule.module.git.GitConnector();
    }

    public CloneMessageProcessorDefinition cloneRepository(String uri) {
        checkNotEmpty(uri, "uri");
        return new CloneMessageProcessorBuilder(object, uri);
    }

    public CloneMessageProcessorDefinition cloneRepository(ExpressionEvaluatorBuilder uriExp) {
        checkNotNull(uriExp, "uri expression");
        return new CloneMessageProcessorBuilder(object, uriExp);
    }

    public AddMessageProcessorDefinition add(String filePattern) {
        checkNotEmpty(filePattern, "filePattern");
        return new AddMessageProcessorBuilder(object, filePattern);
    }

    public AddMessageProcessorDefinition add(ExpressionEvaluatorBuilder filePatternExp) {
        checkNotNull(filePatternExp, "filePattern expression");
        return new AddMessageProcessorBuilder(object, filePatternExp);
    }

    public CommitMessageProcessorDefinition commit(String msg, String committerName, String committerEmail) {
        checkNotEmpty(msg, "msg");
        checkNotEmpty(committerName, "committerName");
        checkNotEmpty(committerEmail, "committerEmail");
        return new CommitMessageProcessorBuilder(object, msg, committerName, committerEmail);
    }

    public CommitMessageProcessorDefinition commit(ExpressionEvaluatorBuilder msgExp, String committerName, String committerEmail) {
        checkNotNull(msgExp, "msg expression");
        checkNotEmpty(committerName, "committerName");
        checkNotEmpty(committerEmail, "committerEmail");
        return new CommitMessageProcessorBuilder(object, msgExp, committerName, committerEmail);
    }

    public CommitMessageProcessorDefinition commit(String msg, ExpressionEvaluatorBuilder committerNameExp, String committerEmail) {
        checkNotEmpty(msg, "msg");
        checkNotNull(committerNameExp, "committerName expression");
        checkNotEmpty(committerEmail, "committerEmail");
        return new CommitMessageProcessorBuilder(object, msg, committerNameExp, committerEmail);
    }

    public CommitMessageProcessorDefinition commit(String msg, String committerName, ExpressionEvaluatorBuilder committerEmailExp) {
        checkNotEmpty(msg, "msg");
        checkNotEmpty(committerName, "committerName");
        checkNotNull(committerEmailExp, "committerEmail expression");
        return new CommitMessageProcessorBuilder(object, msg, committerName, committerEmailExp);
    }

    public CommitMessageProcessorDefinition commit(ExpressionEvaluatorBuilder msgExp, ExpressionEvaluatorBuilder committerNameExp, String committerEmail) {
        checkNotNull(msgExp, "msg expression");
        checkNotNull(committerNameExp, "committerName expression");
        checkNotEmpty(committerEmail, "committerEmail");
        return new CommitMessageProcessorBuilder(object, msgExp, committerNameExp, committerEmail);
    }

    public CommitMessageProcessorDefinition commit(ExpressionEvaluatorBuilder msgExp, String committerName, ExpressionEvaluatorBuilder committerEmailExp) {
        checkNotNull(msgExp, "msg expression");
        checkNotEmpty(committerName, "committerName");
        checkNotNull(committerEmailExp, "committerEmail expression");
        return new CommitMessageProcessorBuilder(object, msgExp, committerName, committerEmailExp);
    }

    public CommitMessageProcessorDefinition commit(String msg, ExpressionEvaluatorBuilder committerNameExp, ExpressionEvaluatorBuilder committerEmailExp) {
        checkNotEmpty(msg, "msg");
        checkNotNull(committerNameExp, "committerName expression");
        checkNotNull(committerEmailExp, "committerEmail expression");
        return new CommitMessageProcessorBuilder(object, msg, committerNameExp, committerEmailExp);
    }

    public CommitMessageProcessorDefinition commit(ExpressionEvaluatorBuilder msgExp, ExpressionEvaluatorBuilder committerNameExp, ExpressionEvaluatorBuilder committerEmailExp) {
        checkNotNull(msgExp, "msg expression");
        checkNotNull(committerNameExp, "committerName expression");
        checkNotNull(committerEmailExp, "committerEmail expression");
        return new CommitMessageProcessorBuilder(object, msgExp, committerNameExp, committerEmailExp);
    }

    public CreateBranchMessageProcessorDefinition createBranch(String name) {
        checkNotEmpty(name, "name");
        return new CreateBranchMessageProcessorBuilder(object, name);
    }

    public CreateBranchMessageProcessorDefinition createBranch(ExpressionEvaluatorBuilder nameExp) {
        checkNotNull(nameExp, "name expression");
        return new CreateBranchMessageProcessorBuilder(object, nameExp);
    }

    public DeleteBranchMessageProcessorDefinition deleteBranch(String name) {
        checkNotEmpty(name, "name");
        return new DeleteBranchMessageProcessorBuilder(object, name);
    }

    public DeleteBranchMessageProcessorDefinition deleteBranch(ExpressionEvaluatorBuilder nameExp) {
        checkNotNull(nameExp, "name expression");
        return new DeleteBranchMessageProcessorBuilder(object, nameExp);
    }

    public PushMessageProcessorDefinition push() {
        return new PushMessageProcessorBuilder(object);
    }

    public PullMessageProcessorDefinition pull() {
        return new PullMessageProcessorBuilder(object);
    }

    public FetchMessageProcessorDefinition fetch() {
        return new FetchMessageProcessorBuilder(object);
    }

    public CheckoutMessageProcessorDefinition checkout(String branchName) {
        checkNotEmpty(branchName, "branchName");
        return new CheckoutMessageProcessorBuilder(object, branchName);
    }

    public CheckoutMessageProcessorDefinition checkout(ExpressionEvaluatorBuilder branchNameExp) {
        checkNotNull(branchNameExp, "branchName expression");
        return new CheckoutMessageProcessorBuilder(object, branchNameExp);
    }

    public String getDirectory() {
        return object.getDirectory();
    }

    public void setDirectory(String directory) {
        checkNotEmpty(directory, "directory");
        object.setDirectory(directory);
    }

    public void setDirectoryAsNull() {
        object.setDirectory(null);
    }

}
