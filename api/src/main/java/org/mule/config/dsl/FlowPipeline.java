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
 * Interface that exposed a flow pipeline but hiddes some methods that are just enabled
 * as the first action of a flow (like poll and from).
 *
 * @author porcelli
 */
public interface FlowPipeline extends PipelineBuilder<FlowPipeline>, FlowDefinition {

}