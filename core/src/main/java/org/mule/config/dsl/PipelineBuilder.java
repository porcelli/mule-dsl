/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.mule.api.routing.filter.Filter;
import org.mule.api.transformer.Transformer;

/**
 * Interface that defines all the {@code Flow}'s common actions.
 *
 * @author porcelli
 */
public interface PipelineBuilder<P extends PipelineBuilder<P>> extends Builder {

    /* debug & output */

    /**
     * Logs the payload content at INFO level.
     *
     * @return the parameterized builder
     * @see org.mule.config.dsl.component.SimpleLogComponent
     */
    P log();

    /**
     * Logs payload content at given level.
     *
     * @param level the log level to use
     * @return the parameterized builder
     * @throws NullPointerException if {@code expr} param is null
     * @see org.mule.config.dsl.component.SimpleLogComponent
     */
    P log(LogLevel level) throws NullPointerException;

    /**
     * Logs {@code message} parameter at INFO level.
     *
     * @param message the content to be logged
     * @return the parameterized builder
     * @throws IllegalArgumentException if {@code message} param is null or empty
     * @see org.mule.config.dsl.internal.ExtendedLogComponentBuilder
     */
    P log(String message) throws IllegalArgumentException;

    /**
     * Logs the {@code message} parameter at given level.
     *
     * @param message the content to be logged
     * @param level   the log level to use
     * @return the parameterized builder
     * @throws IllegalArgumentException if {@code message} param is null or empty
     * @throws NullPointerException     if {@code level} param is null
     * @see org.mule.config.dsl.internal.ExtendedLogComponentBuilder
     */
    P log(String message, LogLevel level) throws IllegalArgumentException, NullPointerException;

    /**
     * Logs {@code expr} parameter at INFO level.
     *
     * @param expr the expression to be logged
     * @return the parameterized builder
     * @throws NullPointerException if {@code expr} param is null
     * @see org.mule.config.dsl.internal.ExpressionLogComponentBuilder
     */
    <E extends ExpressionEvaluatorDefinition> P log(E expr) throws NullPointerException;

    /**
     * Logs {@code expr} parameter at given level.
     *
     * @param expr  the expression to be logged
     * @param level the log level to use
     * @return the parameterized builder
     * @throws NullPointerException if {@code expr} or {@code level} params are null
     * @see org.mule.config.dsl.internal.ExpressionLogComponentBuilder
     */
    <E extends ExpressionEvaluatorDefinition> P log(E expr, LogLevel level) throws NullPointerException;

    /**
     * Logs message at INFO level and return the payload back as the result.
     *
     * @return the parameterized builder
     * @see org.mule.component.simple.EchoComponent
     */
    P echo();

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

    /* outbound */

    /**
     * Sends current {@link org.mule.api.MuleMessage} to given outbound endpoint.
     *
     * @param uri the outbound endpoint uri
     * @return the parameterized builder
     * @throws IllegalArgumentException if {@code uri} param is null or empty
     * @see org.mule.api.endpoint.OutboundEndpoint
     */
    P send(String uri) throws IllegalArgumentException;

    /**
     * Sends current {@link org.mule.api.MuleMessage} to given outbound endpoint
     * using given exchange pattern.
     *
     * @param uri     the outbound endpoint uri
     * @param pattern the exchange pattern, null is allowed
     * @return the parameterized builder
     * @throws IllegalArgumentException if {@code uri} param is null or empty
     * @see org.mule.api.endpoint.OutboundEndpoint
     */
    P send(String uri, ExchangePattern pattern) throws IllegalArgumentException;

    /* transform */

    /**
     * Transforms, if possible, the current payload to given type.
     *
     * @param clazz the type to be converted to
     * @return the parameterized builder
     * @throws NullPointerException if {@code clazz} param is null
     * @see org.mule.transformer.simple.AutoTransformer
     */
    <T> P transformTo(Class<T> clazz) throws NullPointerException;

    /**
     * Transforms current payload using the given expression.
     *
     * @param expr the transformer expression
     * @return the parameterized builder
     * @throws NullPointerException if {@code expr} param is null
     * @see org.mule.expression.transformers.ExpressionTransformer
     */
    <E extends ExpressionEvaluatorDefinition> P transform(E expr) throws NullPointerException;

    /**
     * Transforms the current payload using the given transformer object.
     *
     * @param obj the transformer object
     * @return the parameterized builder
     * @throws NullPointerException if {@code obj} param is null
     * @see Transformer
     */
    <T extends Transformer> P transformWith(T obj) throws NullPointerException;

    /**
     * Transforms current payload using the given transformer type.
     *
     * @param clazz the transformer type, Mule will instantiate it at runtime
     * @return the parameterized builder
     * @throws NullPointerException if {@code clazz} param is null
     * @see Transformer
     */
    <T extends Transformer> P transformWith(Class<T> clazz) throws NullPointerException;

    /**
     * Transforms current payload using the given global transformer reference.
     *
     * @param obj the global transformer object reference
     * @return the parameterized builder
     * @throws NullPointerException if {@code obj} param is null
     * @see Catalog#newTransformer(String)
     * @see org.mule.config.dsl.AbstractModule#transformer()
     * @see org.mule.config.dsl.AbstractModule#transformer(String)
     * @see TransformerDefinition
     */
    P transformWith(TransformerDefinition obj) throws NullPointerException;

    /**
     * Transforms the current payload using the given global transformer reference.
     *
     * @param ref the global transformer unique identifier
     * @return the parameterized builder
     * @throws IllegalArgumentException if {@code ref} param is null or empty
     * @see Catalog#newTransformer(String)
     * @see org.mule.config.dsl.AbstractModule#transformer(String)
     */
    P transformWith(String ref) throws IllegalArgumentException;

    /* filter */

    /**
     * Filters messages by the given type, allowing only matched to continue in the flow.
     *
     * @param clazz the type to be filtered by
     * @return the parameterized builder
     * @throws NullPointerException if {@code clazz} param is null
     * @see org.mule.routing.filters.PayloadTypeFilter
     */
    <T> P filterBy(Class<T> clazz) throws NullPointerException;

    /**
     * Filters messages by the given expression, allowing only matched to continue in the flow.
     *
     * @param expr the expression to be filtered by
     * @return the parameterized builder
     * @throws NullPointerException if {@code expr} param is null
     * @see org.mule.routing.filters.ExpressionFilter
     */
    <E extends ExpressionEvaluatorDefinition> P filter(E expr) throws NullPointerException;

    /**
     * Filters messages by given filter, allowing only matched to continue in the flow.
     *
     * @param obj the filter object
     * @return the parameterized builder
     * @throws NullPointerException if {@code obj} param is null
     * @see Filter
     */
    <F extends Filter> P filterWith(F obj) throws NullPointerException;

    /**
     * Filters messages by given filter, allowing only matched to continue in the flow.
     *
     * @param clazz the filter type, Mule will instantiate it at runtime
     * @return the parameterized builder
     * @throws NullPointerException if {@code clazz} param is null
     * @see Filter
     */
    <F extends Filter> P filterWith(Class<F> clazz) throws NullPointerException;

    /**
     * Filters messages by using the given global filter reference, allowing only
     * matched to continue in the flow.
     *
     * @param obj the global filter object reference
     * @return the parameterized builder
     * @throws NullPointerException if {@code obj} param is null
     * @see Catalog#newFilter(String)
     * @see AbstractModule#filter()
     * @see org.mule.config.dsl.AbstractModule#filter(String)
     * @see FilterDefinition
     */
    P filterWith(FilterDefinition obj) throws NullPointerException;

    /**
     * Filters messages by using the given global filter reference, allowing only
     * matched to continue in the flow.
     *
     * @param ref the global filter unique identifier
     * @return the parameterized builder
     * @throws IllegalArgumentException if {@code ref} param is null or empty
     * @see Catalog#newFilter(String)
     * @see org.mule.config.dsl.AbstractModule#filter(String)
     */
    P filterWith(String ref) throws IllegalArgumentException;

    /* message properties */

    /**
     * Exposes message properties manipilation, enabling some operations
     * like {@code put}, {@code remove} and , {@code rename}.
     *
     * @return the message properties builder
     */
    MessagePropertiesBuilder<P> messageProperties();

    /* routers */

    /**
     * Creates a broadcast router.
     * <p/>
     * <b>Important Note:</b> This is the DSL counterpart of {@code All} router of Mule XML configuration.
     *
     * @return the broadcast router builder
     * @see <a href="http://www.mulesoft.org/documentation/display/MULE3USER/Routing+Message+Processors#RoutingMessageProcessors-All">More about All router</a>
     */
    BroadcastRouterBuilder<P> broadcast();

    /**
     * Creates a choice router that can be composed by one or more predicates and an otherwise clause.
     *
     * @return the choice router builder
     * @see <a href="http://www.mulesoft.org/documentation/display/MULE3USER/Routing+Message+Processors#RoutingMessageProcessors-Choice">More about Choice Router</a>
     */
    ChoiceRouterBuilder<P> choice();

    /**
     * Creates an async router.
     *
     * @return the async router builder
     * @see <a href="http://www.mulesoft.org/documentation/display/MULE3USER/Routing+Message+Processors#RoutingMessageProcessors-Async">More about Async Router</a>
     */
    AsyncRouterBuilder<P> async();

    /**
     * Creates a first successful router.
     *
     * @return the first successful router builder
     * @see <a href="http://www.mulesoft.org/documentation/display/MULE3USER/Routing+Message+Processors#RoutingMessageProcessors-FirstSuccessful">More about First Successful Router</a>
     */
    FirstSuccessfulRouterBuilder<P> firstSuccessful();

    /**
     * Creates a round robin router.
     *
     * @return the round robin router builder
     * @see <a href="http://www.mulesoft.org/documentation/display/MULE3USER/Routing+Message+Processors#RoutingMessageProcessors-RoundRobin">More about Round Robin Router</a>
     */
    RoundRobinRouterBuilder<P> roundRobin();
}