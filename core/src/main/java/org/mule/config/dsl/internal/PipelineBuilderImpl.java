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
import org.mule.component.simple.EchoComponent;
import org.mule.config.dsl.*;
import org.mule.config.dsl.component.InvokerFlowComponent;
import org.mule.config.dsl.component.SimpleLogComponent;
import org.mule.config.dsl.internal.util.MessageProcessorUtil;

import java.util.ArrayList;
import java.util.List;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotEmpty;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

/**
 * Internal and base implementation of {@link PipelineBuilder} interface, also
 * handles multiple message processors by implementing {@link MessageProcessorBuilderList} interface.
 *
 * @author porcelli
 */
class PipelineBuilderImpl<P extends PipelineBuilder<P>> implements PipelineBuilder<P>, FlowNameAware, MessageProcessorBuilderList {

    protected final String flowName;
    protected final List<Builder<? extends MessageProcessor>> processorList;
    protected final List<Builder<? extends MessagingExceptionHandler>> exceptionHandlerList;
    protected final P parentScope;

    /**
     * @param parentScope the parent scope, null is allowed
     */
    public PipelineBuilderImpl(final P parentScope) {
        this.processorList = new ArrayList<Builder<? extends MessageProcessor>>();
        this.exceptionHandlerList = new ArrayList<Builder<? extends MessagingExceptionHandler>>();
        this.parentScope = parentScope;
        if (parentScope != null && parentScope instanceof FlowNameAware) {
            this.flowName = ((FlowNameAware) parentScope).getFlowName();
        } else {
            this.flowName = null;
        }
    }

    /**
     * @param parentScope the parent scope, null is allowed
     * @param flowName    the flow name
     */
    public PipelineBuilderImpl(final P parentScope, String flowName) {
        this.flowName = checkNotEmpty(flowName, "flowName");
        this.processorList = new ArrayList<Builder<? extends MessageProcessor>>();
        this.exceptionHandlerList = new ArrayList<Builder<? extends MessagingExceptionHandler>>();
        this.parentScope = parentScope;
    }

    /**
     * Returns the flow name
     *
     * @return the flow name
     */
    @Override
    public String getFlowName() {
        return flowName;
    }

    /**
     * Defines the getThis trick.
     *
     * @return the this parameterized type
     * @see <a href="http://www.angelikalanger.com/GenericsFAQ/FAQSections/ProgrammingIdioms.html#What is the getThis trick?">More about getThis trick</a>
     */
    @SuppressWarnings("unchecked")
    protected P getThis() {
        return (P) this;
    }

    /* component */

    /**
     * {@inheritDoc}
     */
    @Override
    public <B> InvokeBuilder<P> invoke(final B obj) throws NullPointerException, IllegalArgumentException {
        checkNotNull(obj, "obj");
        if (obj instanceof MessageProcessor) {
            throw new IllegalArgumentException("Use `process` to execute custom MessageProcessor.");
        }

        if (parentScope != null) {
            return parentScope.invoke(obj);
        }
        final InvokeBuilderImpl<P> builder = new InvokeBuilderImpl<P>(getThis(), obj);
        processorList.add(builder);

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <B> InvokeBuilder<P> invoke(final Class<B> clazz) throws NullPointerException, IllegalArgumentException {
        return invoke(clazz, Scope.PROTOTYPE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <B> InvokeBuilder<P> invoke(final Class<B> clazz, final Scope scope) throws NullPointerException, IllegalArgumentException {
        checkNotNull(clazz, "clazz");
        checkNotNull(scope, "scope");

        if (MessageProcessor.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("Use `process` to execute custom MessageProcessor.");
        }

        if (parentScope != null) {
            return parentScope.invoke(clazz, scope);
        }

        final InvokeBuilderImpl<P> builder = new InvokeBuilderImpl<P>(getThis(), clazz, scope);
        processorList.add(builder);

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P process(String flowName) throws IllegalArgumentException {
        checkNotEmpty(flowName, "flowName");
        if (parentScope != null) {
            return parentScope.process(flowName);
        }
        processorList.add(new InvokeBuilderImpl<P>(getThis(), new InvokerFlowComponent(flowName)));

        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P process(FlowDefinition flow) throws NullPointerException {
        checkNotNull(flow, "flow");
        if (parentScope != null) {
            return parentScope.process(flow);
        }
        processorList.add(new InvokeBuilderImpl<P>(getThis(), new InvokerFlowComponent(((FlowBuilderImpl) flow).getFlowName())));

        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P executeScript(String lang, AbstractModule.FileRefBuilder fileRef) throws IllegalArgumentException, NullPointerException {
        checkNotEmpty(lang, "lang");
        checkNotNull(fileRef, "fileRef");

        return executeScript(lang, fileRef.getAsString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P executeScript(String lang, AbstractModule.ClasspathBuilder classpathRef) throws IllegalArgumentException, NullPointerException {
        checkNotEmpty(lang, "lang");
        checkNotNull(classpathRef, "classpathRef");

        return executeScript(lang, classpathRef.getAsString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P executeScript(ScriptLanguage lang, String script) throws IllegalArgumentException, NullPointerException {
        checkNotNull(lang, "lang");
        checkNotNull(script, "script");

        return executeScript(lang.toString(), script);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P executeScript(ScriptLanguage lang, AbstractModule.FileRefBuilder fileRef) throws IllegalArgumentException, NullPointerException {
        checkNotNull(lang, "lang");
        checkNotNull(fileRef, "fileRef");

        return executeScript(lang.toString(), fileRef.getAsString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P executeScript(ScriptLanguage lang, AbstractModule.ClasspathBuilder classpathRef) throws NullPointerException {
        checkNotNull(lang, "lang");
        checkNotNull(classpathRef, "classpathRef");

        return executeScript(lang.toString(), classpathRef);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <MP extends MessageProcessor> P process(Class<MP> clazz) throws NullPointerException {
        checkNotNull(clazz, "clazz");

        if (parentScope != null) {
            return parentScope.process(clazz);
        }

        processorList.add(new InvokeBuilderImpl<P>(getThis(), clazz));
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <MP extends MessageProcessor> P process(MP obj) throws NullPointerException {
        checkNotNull(obj, "obj");

        if (parentScope != null) {
            return parentScope.process(obj);
        }

        processorList.add(new InvokeBuilderImpl<P>(getThis(), obj));
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P executeScript(String lang, String script) throws IllegalArgumentException {
        checkNotEmpty(lang, "lang");
        checkNotEmpty(script, "script");

        if (parentScope != null) {
            return parentScope.executeScript(lang, script);
        }

        processorList.add(new ScriptComponentBuilder(lang, script));

        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P log() {
        return log(LogLevel.INFO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P log(final LogLevel level) throws NullPointerException {
        checkNotNull(level, "level");
        if (parentScope != null) {
            return parentScope.log(level);
        }

        processorList.add(new InvokeBuilderImpl<P>(getThis(), new SimpleLogComponent(level)));
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P log(final String message) throws IllegalArgumentException {
        checkNotEmpty(message, "message");
        return log(message, LogLevel.INFO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P log(final String message, final LogLevel level) throws IllegalArgumentException, NullPointerException {
        checkNotEmpty(message, "message");
        checkNotNull(level, "level");

        if (parentScope != null) {
            return parentScope.log(message, level);
        }

        processorList.add(new InvokeBuilderImpl<P>(getThis(), new ExtendedLogComponentBuilder(message, level)));
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends ExpressionEvaluatorDefinition> P log(final E expr) throws NullPointerException {
        checkNotNull(expr, "expr");
        return log(expr, LogLevel.INFO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends ExpressionEvaluatorDefinition> P log(final E expr, final LogLevel level) throws NullPointerException {
        checkNotNull(expr, "expr");
        checkNotNull(level, "level");
        if (parentScope != null) {
            return parentScope.log(expr, level);
        }

        processorList.add(new InvokeBuilderImpl<P>(getThis(), new ExpressionLogComponentBuilder(expr, level)));

        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P echo() {
        if (parentScope != null) {
            return parentScope.echo();
        }

        processorList.add(new InvokeBuilderImpl<P>(getThis(), new EchoComponent()));
        return getThis();
    }

    /* outbound */

    /**
     * {@inheritDoc}
     */
    @Override
    public P send(final String uri) throws IllegalArgumentException {
        return send(uri, null, (String) null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <C extends Connector> P send(String uri, C connector) throws IllegalArgumentException, NullPointerException {
        return send(uri, null, connector);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P send(String uri, String connectorName) throws IllegalArgumentException {
        return send(uri, null, connectorName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P send(final String uri, final ExchangePattern pattern) throws IllegalArgumentException, NullPointerException {
        return send(uri, pattern, (String) null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P send(String uri, ExchangePattern pattern, String connectorName) throws IllegalArgumentException, NullPointerException {
        checkNotEmpty(uri, "uri");
        if (parentScope != null) {
            return parentScope.send(uri);
        }

        processorList.add(new OutboundEndpointBuilderImpl(uri, pattern, connectorName));

        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <C extends Connector> P send(String uri, ExchangePattern pattern, C connector) throws IllegalArgumentException, NullPointerException {
        checkNotEmpty(uri, "uri");
        if (parentScope != null) {
            return parentScope.send(uri);
        }

        processorList.add(new OutboundEndpointBuilderImpl(uri, pattern, connector));

        return getThis();
    }

    /* transform */

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends ExpressionEvaluatorDefinition> P transform(final E expr) throws NullPointerException {
        checkNotNull(expr, "expr");
        if (parentScope != null) {
            return parentScope.transform(expr);
        }

        processorList.add(new ExpressionTransformerBuilderImpl<E>(expr));
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> P transformTo(final Class<T> clazz) throws NullPointerException {
        checkNotNull(clazz, "clazz");
        if (parentScope != null) {
            return parentScope.transformTo(clazz);
        }

        processorList.add(new TypeBasedTransformerBuilderImpl<T>(clazz));
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Transformer> P transformWith(final Class<T> clazz) throws NullPointerException {
        checkNotNull(clazz, "clazz");
        if (parentScope != null) {
            return parentScope.transformWith(clazz);
        }

        processorList.add(new CustomTransformerBuilderImpl<T>(clazz));
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Transformer> P transformWith(final T obj) throws NullPointerException {
        checkNotNull(obj, "obj");
        if (parentScope != null) {
            return parentScope.transformWith(obj);
        }

        processorList.add(new CustomTransformerBuilderImpl<T>(obj));
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P transformWith(final TransformerDefinition obj) throws NullPointerException {
        checkNotNull(obj, "obj");
        if (parentScope != null) {
            return parentScope.transformWith(obj);
        }

        processorList.add(new CustomTransformerBuilderImpl(obj));
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P transformWith(final String ref) throws IllegalArgumentException {
        checkNotEmpty(ref, "ref");
        if (parentScope != null) {
            return parentScope.transformWith(ref);
        }

        processorList.add(new CustomTransformerBuilderImpl(ref));
        return getThis();
    }

    /* filter */

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends ExpressionEvaluatorDefinition> P filter(final E expr) throws NullPointerException {
        checkNotNull(expr, "expr");
        if (parentScope != null) {
            return parentScope.filter(expr);
        }

        processorList.add(new ExpressionFilterBuilderImpl(expr));
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> P filterBy(final Class<T> clazz) throws NullPointerException {
        checkNotNull(clazz, "clazz");
        if (parentScope != null) {
            return parentScope.filterBy(clazz);
        }

        processorList.add(new TypeBasedFilterBuilderImpl(clazz));
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <F extends Filter> P filterWith(final Class<F> clazz) throws NullPointerException {
        checkNotNull(clazz, "clazz");
        if (parentScope != null) {
            return parentScope.filterWith(clazz);
        }

        processorList.add(new CustomFilterBuilderImpl<F>(clazz));
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <F extends Filter> P filterWith(final F obj) throws NullPointerException {
        checkNotNull(obj, "obj");
        if (parentScope != null) {
            return parentScope.filterWith(obj);
        }

        processorList.add(new CustomFilterBuilderImpl<F>(obj));
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P filterWith(final FilterDefinition obj) throws NullPointerException {
        checkNotNull(obj, "obj");
        if (parentScope != null) {
            return parentScope.filterWith(obj);
        }

        processorList.add(new CustomFilterBuilderImpl(obj));
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P filterWith(final String ref) throws IllegalArgumentException {
        checkNotEmpty(ref, "ref");
        if (parentScope != null) {
            return parentScope.filterWith(ref);
        }

        processorList.add(new CustomFilterBuilderImpl(ref));
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessagePropertiesBuilder<P> messageProperties() {
        if (parentScope != null) {
            return parentScope.messageProperties();
        }
        final MessagePropertiesBuilderImpl<P> builder = new MessagePropertiesBuilderImpl<P>(getThis());
        processorList.add(builder);

        return builder;
    }

    /* error handling */

    @Override
    public PipelineExceptionInvokeOperations onException() {
        if (parentScope != null) {
            return parentScope.onException();
        }
        final PipelineExceptionOperationsBuilderImpl builder = new PipelineExceptionOperationsBuilderImpl(getThis());
        exceptionHandlerList.add(builder);

        return builder;
    }

    /* routers */

    /**
     * {@inheritDoc}
     */
    @Override
    public BroadcastRouterBuilder<P> broadcast() {
        if (parentScope != null) {
            return parentScope.broadcast();
        }
        final BroadcastRouterBuilderImpl<P> builder = new BroadcastRouterBuilderImpl<P>(getThis());
        processorList.add(builder);

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChoiceRouterBuilder<P> choice() {
        if (parentScope != null) {
            return parentScope.choice();
        }
        final ChoiceRouterBuilderImpl<P> builder = new ChoiceRouterBuilderImpl<P>(getThis());
        processorList.add(builder);

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AsyncRouterBuilder<P> async() {
        if (parentScope != null) {
            return parentScope.async();
        }
        final AsyncRouterBuilderImpl<P> builder = new AsyncRouterBuilderImpl<P>(getThis());
        processorList.add(builder);

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FirstSuccessfulRouterBuilder<P> firstSuccessful() {
        if (parentScope != null) {
            return parentScope.firstSuccessful();
        }
        final FirstSuccessfulRouterBuilderImpl<P> builder = new FirstSuccessfulRouterBuilderImpl<P>(getThis());
        processorList.add(builder);

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoundRobinRouterBuilder<P> roundRobin() {
        if (parentScope != null) {
            return parentScope.roundRobin();
        }
        final RoundRobinRouterBuilderImpl<P> builder = new RoundRobinRouterBuilderImpl<P>(getThis());
        processorList.add(builder);

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBuilder(final Builder<? extends MessageProcessor> builder) throws NullPointerException {
        checkNotNull(builder, "builder");
        processorList.add(builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MessageProcessor> buildMessageProcessorList(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");
        if (parentScope != null && parentScope instanceof MessageProcessorBuilderList) {
            return ((MessageProcessorBuilderList) parentScope).buildMessageProcessorList(muleContext, placeholder);
        }

        return MessageProcessorUtil.buildProcessorList(processorList, muleContext, placeholder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBuilderListEmpty() {
        if (parentScope != null && parentScope instanceof MessageProcessorBuilderList) {
            return ((MessageProcessorBuilderList) parentScope).isBuilderListEmpty();
        }

        if (processorList != null && processorList.size() > 0) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Builder<? extends MessageProcessor>> getBuilders() {
        return processorList;
    }
}
