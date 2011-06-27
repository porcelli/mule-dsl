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

public interface CloneMessageProcessorDefinition extends MessageProcessorDefinition {

    CloneMessageProcessorDefinition withBare(boolean bare);

    CloneMessageProcessorDefinition withBare(ExpressionEvaluatorBuilder bareExp);

    CloneMessageProcessorDefinition withRemote(String remote);

    CloneMessageProcessorDefinition withRemote(ExpressionEvaluatorBuilder remoteExp);

    CloneMessageProcessorDefinition withBranch(String branch);

    CloneMessageProcessorDefinition withBranch(ExpressionEvaluatorBuilder branchExp);

    CloneMessageProcessorDefinition withOverrideDirectory(String overrideDirectory);

    CloneMessageProcessorDefinition withOverrideDirectory(ExpressionEvaluatorBuilder overrideDirectoryExp);
}
