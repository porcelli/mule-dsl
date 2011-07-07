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
import org.mule.config.dsl.MessageProcessorDefinition;

public interface DeleteBranchMessageProcessorDefinition extends MessageProcessorDefinition {

    DeleteBranchMessageProcessorDefinition withForce(boolean force);

    DeleteBranchMessageProcessorDefinition withForce(ExpressionEvaluatorBuilder forceExp);

    DeleteBranchMessageProcessorDefinition withOverrideDirectory(String overrideDirectory);

    DeleteBranchMessageProcessorDefinition withOverrideDirectory(ExpressionEvaluatorBuilder overrideDirectoryExp);
}
