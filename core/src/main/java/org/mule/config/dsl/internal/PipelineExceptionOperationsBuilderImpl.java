/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import org.mule.api.MuleContext;
import org.mule.api.exception.MessagingExceptionHandler;
import org.mule.config.dsl.FlowDefinition;
import org.mule.config.dsl.PipelineBuilder;
import org.mule.config.dsl.PipelineExceptionOperations;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.config.dsl.component.SubFlowMessagingExceptionHandler;

import static org.mule.config.dsl.util.Preconditions.checkNotEmpty;
import static org.mule.config.dsl.util.Preconditions.checkNotNull;

public class PipelineExceptionOperationsBuilderImpl implements FlowNameAware, PipelineExceptionOperations.PipelineExceptionInvokeOperations, FlowDefinition, DSLBuilder<MessagingExceptionHandler> {

    private final String currentFlowName;
    private String flowToBeExecute;

    public PipelineExceptionOperationsBuilderImpl(PipelineBuilder parent) {
        checkNotNull(parent, "parent");
        if (parent instanceof FlowNameAware) {
            this.currentFlowName = checkNotEmpty(((FlowNameAware) parent).getFlowName(), "flow.flowName");
        } else {
            throw new RuntimeException("XXX");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFlowName() {
        return currentFlowName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FlowDefinition process(String flowName) throws IllegalArgumentException {
        this.flowToBeExecute = checkNotEmpty(flowName, "flowName");

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FlowDefinition process(FlowDefinition flow) throws NullPointerException {
        checkNotNull(flow, "flow");
        if (flow instanceof FlowNameAware) {
            this.flowToBeExecute = checkNotEmpty(((FlowNameAware) flow).getFlowName(), "flow.flowName");
        } else {
            throw new RuntimeException("XXX");
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessagingExceptionHandler build(MuleContext muleContext, PropertyPlaceholder placeholder) {
        return new SubFlowMessagingExceptionHandler(flowToBeExecute);
    }

}
