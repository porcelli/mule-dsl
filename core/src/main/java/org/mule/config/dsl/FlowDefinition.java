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
 * Interaces that, at mininum, represents a flow. This interface should be extended to higher interfaces and types.
 * <p/>
 * Other important use of this interface is to force the end of flow method chain (for now used just by {@link org.mule.config.dsl.PipelineExceptionOperations.PipelineExceptionInvokeOperations#process}).
 */
public interface FlowDefinition {

}