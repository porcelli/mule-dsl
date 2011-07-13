/*
 * ---------------------------------------------------------------------------
 *  Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 */

package org.mule.config.dsl.module.mongo;

import org.mule.config.dsl.ExpressionEvaluatorDefinition;
import org.mule.config.dsl.MessageProcessorDefinition;
import org.mule.module.mongo.api.IndexOrder;

public interface CreateIndexMessageProcessorDefinition extends MessageProcessorDefinition {

    CreateIndexMessageProcessorDefinition withOrder(IndexOrder order);

    CreateIndexMessageProcessorDefinition withOrder(ExpressionEvaluatorDefinition orderExp);

}
