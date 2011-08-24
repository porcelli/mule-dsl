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
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.api.transformer.Transformer;
import org.mule.api.transport.Connector;
import org.mule.config.dsl.*;

import java.util.List;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

/**
 * Abstract class that provised basic implementation to routers that provides nesting scope and
 * needs handle multiple message processors.
 *
 * @author porcelli
 */
public abstract class BasePipelinedRouterImpl<P extends PipelineBuilder<P>, X extends PipelineBuilder<X>> implements FlowNameAware, PipelineBuilder<P>, MessageProcessorBuilderList, MessagingExceptionHandlerBuilderList {

    protected final X parentScope;
    protected final PipelineBuilderImpl<P> pipeline;

    public BasePipelinedRouterImpl(final X parentScope) {
        this.parentScope = checkNotNull(parentScope, "parentScope");
        this.pipeline = new PipelineBuilderImpl<P>((P) null);
    }

    /**
     * Defines the getThis trick.
     *
     * @return the this parameterized type
     * @see <a href="http://www.angelikalanger.com/GenericsFAQ/FAQSections/ProgrammingIdioms.html#What is the getThis trick?">More about getThis trick</a>
     */
    protected abstract P getThis();

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFlowName() {
        if (parentScope instanceof FlowNameAware) {
            return ((FlowNameAware) parentScope).getFlowName();
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P log() {
        pipeline.log();
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P log(final LogLevel level) throws NullPointerException {
        pipeline.log(level);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P log(final String message) throws IllegalArgumentException {
        pipeline.log(message);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P log(final String message, final LogLevel level) throws IllegalArgumentException, NullPointerException {
        pipeline.log(message, level);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends ExpressionEvaluatorDefinition> P log(final E expr) throws NullPointerException {
        pipeline.log(expr);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends ExpressionEvaluatorDefinition> P log(final E expr, final LogLevel level) throws NullPointerException {
        pipeline.log(expr, level);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P echo() {
        pipeline.echo();
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <B> InvokeBuilder<P> invoke(final B obj) throws NullPointerException, IllegalArgumentException {
        checkNotNull(obj, "obj");

        if (obj instanceof MessageProcessor) {
            throw new IllegalArgumentException("Use `process` to execute custom MessageProcessor.");
        }

        final InvokeBuilderImpl<P> builder = new InvokeBuilderImpl<P>(getThis(), obj);
        pipeline.addBuilder(builder);

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <B> InvokeBuilder<P> invoke(final Class<B> clazz) throws NullPointerException, IllegalArgumentException {
        checkNotNull(clazz, "clazz");

        if (MessageProcessor.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("Use `process` to execute custom MessageProcessor.");
        }

        final InvokeBuilderImpl<P> builder = new InvokeBuilderImpl<P>(getThis(), clazz, Scope.PROTOTYPE);
        pipeline.addBuilder(builder);

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <B> InvokeBuilder<P> invoke(final Class<B> clazz, final Scope scope) throws NullPointerException, IllegalArgumentException {
        checkNotNull(clazz, "clazz");
        checkNotNull(scope, "scope");

        if (clazz.isAssignableFrom(MessageProcessor.class)) {
            throw new IllegalArgumentException("Use `process` to execute custom MessageProcessor.");
        }

        final InvokeBuilderImpl<P> builder = new InvokeBuilderImpl<P>(getThis(), clazz, scope);
        pipeline.addBuilder(builder);

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P process(String flowName) throws IllegalArgumentException {
        pipeline.process(flowName);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P process(FlowDefinition flow) throws NullPointerException {
        pipeline.process(flow);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P executeScript(String lang, String script) throws IllegalArgumentException {
        pipeline.executeScript(lang, script);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P executeScript(String lang, AbstractModule.FileRefBuilder fileRef) throws IllegalArgumentException, NullPointerException {
        pipeline.executeScript(lang, fileRef);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P executeScript(String lang, AbstractModule.ClasspathBuilder classpathRef) throws IllegalArgumentException, NullPointerException {
        pipeline.executeScript(lang, classpathRef);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P executeScript(ScriptLanguage lang, String script) throws IllegalArgumentException, NullPointerException {
        pipeline.executeScript(lang, script);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P executeScript(ScriptLanguage lang, AbstractModule.FileRefBuilder fileRef) throws NullPointerException {
        pipeline.executeScript(lang, fileRef);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P executeScript(ScriptLanguage lang, AbstractModule.ClasspathBuilder classpathRef) throws NullPointerException {
        pipeline.executeScript(lang, classpathRef);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <MP extends MessageProcessor> P process(Class<MP> clazz) throws NullPointerException {
        pipeline.process(clazz);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <MP extends MessageProcessor> P process(MP obj) throws NullPointerException {
        pipeline.process(obj);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P process(final MessageProcessorDefinition messageProcessor) throws NullPointerException, IllegalArgumentException {
        pipeline.process(messageProcessor);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P send(final String uri) throws IllegalArgumentException {
        pipeline.send(uri);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <C extends Connector> P send(final String uri, C connector) throws IllegalArgumentException, NullPointerException {
        pipeline.send(uri, connector);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P send(final String uri, final String connectorName) throws IllegalArgumentException {
        pipeline.send(uri, connectorName);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P send(final String uri, final ExchangePattern pattern) throws IllegalArgumentException, NullPointerException {
        pipeline.send(uri, pattern);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <C extends Connector> P send(String uri, ExchangePattern pattern, C connector) throws IllegalArgumentException, NullPointerException {
        pipeline.send(uri, pattern, connector);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P send(String uri, ExchangePattern pattern, String connectorName) throws IllegalArgumentException, NullPointerException {
        pipeline.send(uri, pattern, connectorName);
        return getThis();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends ExpressionEvaluatorDefinition> P transform(final E expr) throws NullPointerException {
        pipeline.transform(expr);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> P transformTo(final Class<T> clazz) throws NullPointerException {
        pipeline.transformTo(clazz);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Transformer> P transformWith(final Class<T> clazz) throws NullPointerException {
        pipeline.transformWith(clazz);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Transformer> P transformWith(final T obj) throws NullPointerException {
        pipeline.transformWith(obj);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P transformWith(final TransformerDefinition obj) throws NullPointerException {
        pipeline.transformWith(obj);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P transformWith(final String ref) {
        pipeline.transformWith(ref);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends ExpressionEvaluatorDefinition> P filter(final E expr) throws NullPointerException {
        pipeline.filter(expr);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> P filterBy(final Class<T> clazz) throws NullPointerException {
        pipeline.filterBy(clazz);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <F extends Filter> P filterWith(final Class<F> clazz) throws NullPointerException {
        pipeline.filterWith(clazz);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <F extends Filter> P filterWith(final F obj) throws NullPointerException {
        pipeline.filterWith(obj);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P filterWith(final FilterDefinition obj) throws NullPointerException {
        pipeline.filterWith(obj);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P filterWith(final String ref) throws IllegalArgumentException {
        pipeline.filterWith(ref);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessagePropertiesBuilder<P> messageProperties() {
        final MessagePropertiesBuilderImpl<P> builder = new MessagePropertiesBuilderImpl<P>(getThis());
        pipeline.addBuilder(builder);

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PipelineExceptionInvokeOperations onException() {
        if (parentScope != null) {
            return parentScope.onException();
        }
        return pipeline.onException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BroadcastRouterBuilder<P> broadcast() {
        final BroadcastRouterBuilderImpl<P> builder = new BroadcastRouterBuilderImpl<P>(getThis());
        pipeline.addBuilder(builder);

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChoiceRouterBuilder<P> choice() {
        final ChoiceRouterBuilderImpl<P> builder = new ChoiceRouterBuilderImpl<P>(getThis());
        pipeline.addBuilder(builder);

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AsyncRouterBuilder<P> async() {
        final AsyncRouterBuilderImpl<P> builder = new AsyncRouterBuilderImpl<P>(getThis());
        pipeline.addBuilder(builder);

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FirstSuccessfulRouterBuilder<P> firstSuccessful() {
        final FirstSuccessfulRouterBuilderImpl<P> builder = new FirstSuccessfulRouterBuilderImpl<P>(getThis());
        pipeline.addBuilder(builder);

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoundRobinRouterBuilder<P> roundRobin() {
        final RoundRobinRouterBuilderImpl<P> builder = new RoundRobinRouterBuilderImpl<P>(getThis());
        pipeline.addBuilder(builder);

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBuilder(final Builder<? extends MessageProcessor> builder) throws NullPointerException {
        checkNotNull(builder, "builder");
        pipeline.addBuilder(builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MessageProcessor> buildMessageProcessorList(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");
        return pipeline.buildMessageProcessorList(muleContext, placeholder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBuilderListEmpty() {
        return pipeline.isBuilderListEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Builder<? extends MessageProcessor>> getBuilders() {
        return pipeline.getBuilders();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addExceptionBuilder(Builder<? extends MessagingExceptionHandler> builder) throws NullPointerException {
        checkNotNull(builder, "builder");
        if (parentScope instanceof MessagingExceptionHandlerBuilderList) {
            ((MessagingExceptionHandlerBuilderList) parentScope).addExceptionBuilder(builder);
        } else {
            pipeline.addExceptionBuilder(builder);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Builder<? extends MessagingExceptionHandler>> getExceptionBuilders() {
        if (parentScope instanceof MessagingExceptionHandlerBuilderList) {
            return ((MessagingExceptionHandlerBuilderList) parentScope).getExceptionBuilders();
        } else {
            return pipeline.getExceptionBuilders();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExceptionBuilderListEmpty() {
        if (parentScope instanceof MessagingExceptionHandlerBuilderList) {
            return ((MessagingExceptionHandlerBuilderList) parentScope).isExceptionBuilderListEmpty();
        } else {
            return pipeline.isExceptionBuilderListEmpty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MessagingExceptionHandler> buildExceptionHandlerList(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");
        if (parentScope instanceof MessagingExceptionHandlerBuilderList) {
            return ((MessagingExceptionHandlerBuilderList) parentScope).buildExceptionHandlerList(muleContext, placeholder);
        } else {
            return pipeline.buildExceptionHandlerList(muleContext, placeholder);
        }
    }
}
