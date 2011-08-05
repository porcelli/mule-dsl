/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.mule.api.processor.MessageProcessor;

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
     * @throws NullPointerException     if {@code obj} param is null
     * @throws IllegalArgumentException if {@code obj} is an instance of {@link MessageProcessor}
     * @see org.mule.model.resolvers.ReflectionEntryPointResolver
     */
    <B> InvokeBuilder<P> invoke(B obj) throws NullPointerException, IllegalArgumentException;

    /**
     * Invokes the most appropriate {@code clazz}'s method.
     *
     * @param clazz the class type to be invoked, Mule will instantiate an object at runtime
     * @return the executor builder
     * @throws NullPointerException     if {@code clazz} param is null
     * @throws IllegalArgumentException if {@code clazz} is assignable from a {@link MessageProcessor}
     * @see org.mule.model.resolvers.ReflectionEntryPointResolver
     */
    <B> InvokeBuilder<P> invoke(Class<B> clazz) throws NullPointerException, IllegalArgumentException;

    /**
     * Invokes the most appropriate {@code clazz}'s method.
     *
     * @param clazz the class type to be invoked, Mule will instantiate it at runtime
     * @param scope the scope that type should be lifeclycled by Mule
     * @return the executor builder
     * @throws NullPointerException     if {@code clazz} or {@code scope} params are null
     * @throws IllegalArgumentException if {@code clazz} is assignable from a {@link MessageProcessor}
     * @see org.mule.model.resolvers.ReflectionEntryPointResolver
     */
    <B> InvokeBuilder<P> invoke(Class<B> clazz, Scope scope) throws NullPointerException, IllegalArgumentException;

    /**
     * Executes a flow.
     *
     * @param flowName the flow name to be executed
     * @return the parameterized builder
     * @throws IllegalArgumentException if {@code flowName} param is null or empty
     * @see org.mule.model.resolvers.ReflectionEntryPointResolver
     */
    P executeFlow(String flowName) throws IllegalArgumentException;

    /**
     * Executes the given script and language.
     *
     * @param lang   the script language
     * @param script the script to be executed
     * @return the parameterized builder
     * @throws IllegalArgumentException if {@code flowName} or {@code script} params are null or empty
     */
    P executeScript(String lang, String script) throws IllegalArgumentException;

    /**
     * Executes the given script and language.
     *
     * @param lang    the script language
     * @param fileRef reference to file to be loaded
     * @return the parameterized builder
     * @throws IllegalArgumentException if {@code lang} param is null or empty
     * @throws NullPointerException     if {@code fileRef} param is null
     */
    P executeScript(String lang, AbstractModule.FileRefBuilder fileRef) throws IllegalArgumentException, NullPointerException;

    /**
     * Executes the given script and language.
     *
     * @param lang         the script language
     * @param classpathRef reference to classpath to be loaded
     * @return the parameterized builder
     * @throws IllegalArgumentException if {@code lang} param is null or empty
     * @throws NullPointerException     if {@code classpathRef} param is null
     */
    P executeScript(String lang, AbstractModule.ClasspathBuilder classpathRef) throws IllegalArgumentException, NullPointerException;

    /**
     * Executes the given script and language.
     *
     * @param lang   the script language
     * @param script the script to be executed
     * @return the parameterized builder
     * @throws NullPointerException     if {@code lang} param is null or empty
     * @throws IllegalArgumentException if {@code script} param is null or empty
     */
    P executeScript(ScriptLanguage lang, String script) throws NullPointerException, IllegalArgumentException;

    /**
     * Executes the given script and language.
     *
     * @param lang    the script language
     * @param fileRef reference to file to be loaded
     * @return the parameterized builder
     * @throws NullPointerException if {@code lang} or {@code fileRef} params are null or empty
     */
    P executeScript(ScriptLanguage lang, AbstractModule.FileRefBuilder fileRef) throws NullPointerException;

    /**
     * Executes the given script and language.
     *
     * @param lang         the script language
     * @param classpathRef reference to classpath to be loaded
     * @return the parameterized builder
     * @throws NullPointerException if {@code lang} or {@code classpathRef} params are null or empty
     */
    P executeScript(ScriptLanguage lang, AbstractModule.ClasspathBuilder classpathRef) throws NullPointerException;

    /* custom MP */

    /**
     * Executes the message processor.
     *
     * @param clazz the message processor type to be processed, Mule will instantiate an object at runtime
     * @return the parameterized builder
     * @throws NullPointerException if {@code clazz} param is null
     * @see org.mule.api.processor.MessageProcessor
     */
    <MP extends MessageProcessor> P process(Class<MP> clazz) throws NullPointerException;

    /**
     * Executes the message processor.
     *
     * @param obj the message processor instance
     * @return the parameterized builder
     * @throws NullPointerException if {@code obj} param is null
     * @see org.mule.api.processor.MessageProcessor
     */
    <MP extends MessageProcessor> P process(MP obj) throws NullPointerException;

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
