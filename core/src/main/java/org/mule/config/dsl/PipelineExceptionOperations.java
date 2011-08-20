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
 * Interface that defines all exception path related operations.
 *
 * @author porcelli
 */
public interface PipelineExceptionOperations {

    PipelineExceptionInvokeOperations onException();

    public static interface PipelineExceptionInvokeOperations {

        /**
         * Executes a flow.
         *
         * @param flowName the flow name to be executed
         * @return the parameterized builder
         * @throws IllegalArgumentException if {@code flowName} param is null or empty
         * @see org.mule.model.resolvers.ReflectionEntryPointResolver
         */
        FlowDefinition process(String flowName) throws IllegalArgumentException;

        /**
         * Executes a flow.
         *
         * @param flow the flow to be executed
         * @return the parameterized builder
         * @throws IllegalArgumentException if {@code flow} param is null
         * @see org.mule.model.resolvers.ReflectionEntryPointResolver
         */
        FlowDefinition process(FlowDefinition flow) throws NullPointerException;
    }
}

