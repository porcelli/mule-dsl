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
 * Interface that defines all component invokes related operations.
 *
 * @author porcelli
 */
public interface PipelineInvokerOperations<P extends PipelineBuilder<P>> {

    /* component */

    //TODO improve docs here!

    /**
     * Invokes the most appropriate {@code obj}'s method.
     *
     * @param obj the object to be invoked
     * @return the executor builder
     * @throws NullPointerException if {@code obj} param is null
     * @see org.mule.model.resolvers.ReflectionEntryPointResolver
     */
    <B> InvokeBuilder<P> invoke(B obj) throws NullPointerException;

    /**
     * Invokes the most appropriate {@code clazz}'s method.
     *
     * @param clazz the class type to be invoked, Mule will instantiate an object at runtime
     * @return the executor builder
     * @throws NullPointerException if {@code clazz} param is null
     * @see org.mule.model.resolvers.ReflectionEntryPointResolver
     */
    <B> InvokeBuilder<P> invoke(Class<B> clazz) throws NullPointerException;

    /**
     * Invokes the most appropriate {@code clazz}'s method.
     *
     * @param clazz the class type to be invoked, Mule will instantiate it at runtime
     * @param scope the scope that type should be lifeclycled by Mule
     * @return the executor builder
     * @throws NullPointerException if {@code clazz} or {@code scope} params are null
     * @see org.mule.model.resolvers.ReflectionEntryPointResolver
     */
    <B> InvokeBuilder<P> invoke(Class<B> clazz, Scope scope) throws NullPointerException;


    /**
     * Invokes a flow.
     *
     * @param flowName the flow name to be invoked
     * @return the parameterized builder
     * @throws IllegalArgumentException if {@code flowName} param is null or empty
     * @see org.mule.model.resolvers.ReflectionEntryPointResolver
     */
    P invokeFlow(String flowName) throws IllegalArgumentException;

    /* typed MP builder */

    /**
     * Executes the message processor.
     *
     * @param messageProcessor the message processor definition instance
     * @return the parameterized builder
     * @throws NullPointerException     if {@code process} param is null
     * @throws IllegalArgumentException if can't build the given {@code process}
     * @see MessageProcessorDefinition
     * @see org.mule.api.processor.MessageProcessor
     * @see Definition
     */
    P process(MessageProcessorDefinition messageProcessor) throws NullPointerException, IllegalArgumentException;

}
