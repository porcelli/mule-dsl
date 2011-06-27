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

public interface CreateBranchMessageProcessorDefinition extends MessageProcessorDefinition {

    CreateBranchMessageProcessorDefinition withForce(boolean force);

    CreateBranchMessageProcessorDefinition withForce(ExpressionEvaluatorBuilder forceExp);

    CreateBranchMessageProcessorDefinition withStartPoint(String startPoint);

    CreateBranchMessageProcessorDefinition withStartPoint(ExpressionEvaluatorBuilder startPointExp);

    CreateBranchMessageProcessorDefinition withOverrideDirectory(String overrideDirectory);

    CreateBranchMessageProcessorDefinition withOverrideDirectory(ExpressionEvaluatorBuilder overrideDirectoryExp);
}
