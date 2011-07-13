/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.module.git;

import org.mule.config.dsl.ExpressionEvaluatorDefinition;
import org.mule.config.dsl.MessageProcessorDefinition;

public interface CommitMessageProcessorDefinition extends MessageProcessorDefinition {

    CommitMessageProcessorDefinition withAuthorName(String authorName);

    CommitMessageProcessorDefinition withAuthorName(ExpressionEvaluatorDefinition authorNameExp);

    CommitMessageProcessorDefinition withAuthorEmail(String authorEmail);

    CommitMessageProcessorDefinition withAuthorEmail(ExpressionEvaluatorDefinition authorEmailExp);

    CommitMessageProcessorDefinition withOverrideDirectory(String overrideDirectory);

    CommitMessageProcessorDefinition withOverrideDirectory(ExpressionEvaluatorDefinition overrideDirectoryExp);
}
