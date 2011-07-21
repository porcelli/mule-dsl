/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

/**
 * Interface that aggregates all the {@code Flow}'s common actions.
 *
 * @author porcelli
 */
public interface PipelineBuilder<P extends PipelineBuilder<P>>
        extends PipelineDebugOutputOperations<P>, PipelineRouterOperations<P>, PipelineTransformerOperations<P>,
        PipelineFilterOperations<P>, PipelineInvokerOperations<P>, PipelineOutboundEndpointOperations<P>,
        PipelineMessagePropertiesOperations<P>, Builder {

}