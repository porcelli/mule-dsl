/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.module.mongo;

import org.mule.config.dsl.ExpressionEvaluatorBuilder;
import org.mule.config.dsl.MessageProcessorDefinition;

public interface CommitMessageProcessorDefinition extends MessageProcessorDefinition {

    CommitMessageProcessorDefinition withAuthorName(String authorName);

    CommitMessageProcessorDefinition withAuthorName(ExpressionEvaluatorBuilder authorNameExp);

    CommitMessageProcessorDefinition withAuthorEmail(String authorEmail);

    CommitMessageProcessorDefinition withAuthorEmail(ExpressionEvaluatorBuilder authorEmailExp);

    CommitMessageProcessorDefinition withOverrideDirectory(String overrideDirectory);

    CommitMessageProcessorDefinition withOverrideDirectory(ExpressionEvaluatorBuilder overrideDirectoryExp);
}
