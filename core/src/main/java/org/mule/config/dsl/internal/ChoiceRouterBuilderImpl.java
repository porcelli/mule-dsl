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
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.api.transformer.Transformer;
import org.mule.config.dsl.*;
import org.mule.config.dsl.ChoiceRouterBuilder.InnerWhenChoiceBuilder;
import org.mule.routing.ChoiceRouter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mule.config.dsl.internal.util.MessageProcessorUtil.buildProcessorChain;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

/**
 * Internal implementation of {@link org.mule.config.dsl.ChoiceRouterBuilder} and {@link InnerWhenChoiceBuilder}
 * interfaces that, based on its internal state, builds a {@link ChoiceRouter}.
 *
 * @author porcelli
 * @see org.mule.config.dsl.PipelineBuilder#choice()
 */
public class ChoiceRouterBuilderImpl<P extends PipelineBuilder<P>> implements ChoiceRouterBuilder<P>, InnerWhenChoiceBuilder<P>, Builder<ChoiceRouter>, MessageProcessorBuilderList {

    private final P parentScope;
    private final PipelineBuilderImpl<P> pipeline;
    private final LinkedList<Route> choiceElements;

    private ExpressionEvaluatorDefinition lastWhenExpr = null;

    /**
     * @param parentScope the parent scope
     * @throws NullPointerException if {@code parentScope} param is null
     */
    ChoiceRouterBuilderImpl(final P parentScope) {
        this.parentScope = checkNotNull(parentScope, "parentScope");
        this.pipeline = new PipelineBuilderImpl<P>(null);
        this.choiceElements = new LinkedList<Route>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends ExpressionEvaluatorDefinition> InnerWhenChoiceBuilder<P> when(final E expr) throws NullPointerException {
        if (lastWhenExpr != null) {
            choiceElements.add(new Route(lastWhenExpr, pipeline.getBuilders()));
            pipeline.getBuilders().clear();
        }
        lastWhenExpr = checkNotNull(expr, "expr");
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OtherwiseChoiceBuilder<P> otherwise() {
        if (lastWhenExpr != null) {
            choiceElements.add(new Route(lastWhenExpr, pipeline.getBuilders()));
            pipeline.getBuilders().clear();
        }
        lastWhenExpr = null;

        return new OtherwiseChoiceBuilderImpl<P>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P endChoice() {
        return parentScope;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InnerWhenChoiceBuilder<P> log() {
        pipeline.log();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InnerWhenChoiceBuilder<P> log(final LogLevel level) throws NullPointerException {
        pipeline.log(level);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InnerWhenChoiceBuilder<P> log(final String message) throws IllegalArgumentException {
        pipeline.log(message);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InnerWhenChoiceBuilder<P> log(final String message, final LogLevel level) throws IllegalArgumentException, NullPointerException {
        pipeline.log(message, level);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends ExpressionEvaluatorDefinition> InnerWhenChoiceBuilder<P> log(final E expr) throws NullPointerException {
        pipeline.log(expr);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends ExpressionEvaluatorDefinition> InnerWhenChoiceBuilder<P> log(final E expr, final LogLevel level) throws NullPointerException {
        pipeline.log(expr, level);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InnerWhenChoiceBuilder<P> echo() {
        pipeline.echo();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <B> InvokeBuilder<InnerWhenChoiceBuilder<P>> invoke(final B obj) throws NullPointerException, IllegalArgumentException {
        checkNotNull(obj, "obj");
        if (obj instanceof MessageProcessor){
            throw new IllegalArgumentException("Use `process` to execute custom MessageProcessor.");
        }

        final InvokeBuilderImpl<InnerWhenChoiceBuilder<P>> builder = new InvokeBuilderImpl<InnerWhenChoiceBuilder<P>>(this, obj);
        pipeline.addBuilder(builder);

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <B> InvokeBuilder<InnerWhenChoiceBuilder<P>> invoke(final Class<B> clazz) throws NullPointerException, IllegalArgumentException {
        checkNotNull(clazz, "clazz");
        if (MessageProcessor.class.isAssignableFrom(clazz)){
            throw new IllegalArgumentException("Use `process` to execute custom MessageProcessor.");
        }

        final InvokeBuilderImpl<InnerWhenChoiceBuilder<P>> builder = new InvokeBuilderImpl<InnerWhenChoiceBuilder<P>>(this, clazz, Scope.PROTOTYPE);
        pipeline.addBuilder(builder);

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <B> InvokeBuilder<InnerWhenChoiceBuilder<P>> invoke(final Class<B> clazz, final Scope scope) throws NullPointerException, IllegalArgumentException {
        checkNotNull(clazz, "clazz");
        checkNotNull(scope, "scope");

        if (clazz.isAssignableFrom(MessageProcessor.class)){
            throw new IllegalArgumentException("Use `process` to execute custom MessageProcessor.");
        }

        final InvokeBuilderImpl<InnerWhenChoiceBuilder<P>> builder = new InvokeBuilderImpl<InnerWhenChoiceBuilder<P>>(this, clazz, scope);
        pipeline.addBuilder(builder);

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InnerWhenChoiceBuilder<P> executeFlow(String flowName) throws IllegalArgumentException {
        pipeline.executeFlow(flowName);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InnerWhenChoiceBuilder<P> executeScript(String lang, String script) throws IllegalArgumentException {
        pipeline.executeScript(lang, script);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InnerWhenChoiceBuilder<P> executeScript(String lang, AbstractModule.FileRefBuilder fileRef) throws IllegalArgumentException, NullPointerException {
        pipeline.executeScript(lang, fileRef);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InnerWhenChoiceBuilder<P> executeScript(String lang, AbstractModule.ClasspathBuilder classpathRef) throws IllegalArgumentException, NullPointerException {
        pipeline.executeScript(lang, classpathRef);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InnerWhenChoiceBuilder<P> executeScript(ScriptLanguage lang, String script) throws NullPointerException, IllegalArgumentException {
        pipeline.executeScript(lang, script);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InnerWhenChoiceBuilder<P> executeScript(ScriptLanguage lang, AbstractModule.FileRefBuilder fileRef) throws NullPointerException {
        pipeline.executeScript(lang, fileRef);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InnerWhenChoiceBuilder<P> executeScript(ScriptLanguage lang, AbstractModule.ClasspathBuilder classpathRef) throws NullPointerException {
        pipeline.executeScript(lang, classpathRef);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <MP extends MessageProcessor> InnerWhenChoiceBuilder<P> process(Class<MP> clazz) throws NullPointerException {
        pipeline.process(clazz);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <MP extends MessageProcessor> InnerWhenChoiceBuilder<P> process(MP obj) throws NullPointerException {
        pipeline.process(obj);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InnerWhenChoiceBuilder<P> process(final MessageProcessorDefinition messageProcessor) throws NullPointerException, IllegalArgumentException {
        pipeline.process(messageProcessor);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InnerWhenChoiceBuilder<P> send(final String uri) throws IllegalArgumentException {
        pipeline.send(uri);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InnerWhenChoiceBuilder<P> send(final String uri, final ExchangePattern pattern) throws IllegalArgumentException {
        pipeline.send(uri, pattern);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends ExpressionEvaluatorDefinition> InnerWhenChoiceBuilder<P> transform(final E expr) throws NullPointerException {
        pipeline.transform(expr);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> InnerWhenChoiceBuilder<P> transformTo(final Class<T> clazz) throws NullPointerException {
        pipeline.transformTo(clazz);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Transformer> InnerWhenChoiceBuilder<P> transformWith(final Class<T> clazz) throws NullPointerException {
        pipeline.transformWith(clazz);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Transformer> InnerWhenChoiceBuilder<P> transformWith(final T obj) throws NullPointerException {
        pipeline.transformWith(obj);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InnerWhenChoiceBuilder<P> transformWith(final TransformerDefinition obj) throws NullPointerException {
        pipeline.transformWith(obj);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InnerWhenChoiceBuilder<P> transformWith(final String ref) {
        pipeline.transformWith(ref);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends ExpressionEvaluatorDefinition> InnerWhenChoiceBuilder<P> filter(final E expr) throws NullPointerException {
        pipeline.filter(expr);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> InnerWhenChoiceBuilder<P> filterBy(final Class<T> clazz) throws NullPointerException {
        pipeline.filterBy(clazz);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <F extends Filter> InnerWhenChoiceBuilder<P> filterWith(final Class<F> clazz) throws NullPointerException {
        pipeline.filterWith(clazz);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <F extends Filter> InnerWhenChoiceBuilder<P> filterWith(final F obj) throws NullPointerException {
        pipeline.filterWith(obj);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InnerWhenChoiceBuilder<P> filterWith(final FilterDefinition obj) throws NullPointerException {
        pipeline.filterWith(obj);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InnerWhenChoiceBuilder<P> filterWith(final String ref) throws IllegalArgumentException {
        pipeline.filterWith(ref);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessagePropertiesBuilder<InnerWhenChoiceBuilder<P>> messageProperties() {
        final MessagePropertiesBuilderImpl<InnerWhenChoiceBuilder<P>> builder = new MessagePropertiesBuilderImpl<InnerWhenChoiceBuilder<P>>(this);
        pipeline.addBuilder(builder);

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BroadcastRouterBuilder<InnerWhenChoiceBuilder<P>> broadcast() {
        final BroadcastRouterBuilderImpl<InnerWhenChoiceBuilder<P>> builder = new BroadcastRouterBuilderImpl<InnerWhenChoiceBuilder<P>>(this);
        pipeline.addBuilder(builder);

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChoiceRouterBuilder<InnerWhenChoiceBuilder<P>> choice() {
        final ChoiceRouterBuilderImpl<InnerWhenChoiceBuilder<P>> builder = new ChoiceRouterBuilderImpl<InnerWhenChoiceBuilder<P>>(this);
        pipeline.addBuilder(builder);

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AsyncRouterBuilder<InnerWhenChoiceBuilder<P>> async() {
        final AsyncRouterBuilderImpl<InnerWhenChoiceBuilder<P>> builder = new AsyncRouterBuilderImpl<InnerWhenChoiceBuilder<P>>(this);
        pipeline.addBuilder(builder);

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FirstSuccessfulRouterBuilder<InnerWhenChoiceBuilder<P>> firstSuccessful() {
        final FirstSuccessfulRouterBuilderImpl<InnerWhenChoiceBuilder<P>> builder = new FirstSuccessfulRouterBuilderImpl<InnerWhenChoiceBuilder<P>>(this);
        pipeline.addBuilder(builder);

        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoundRobinRouterBuilder<InnerWhenChoiceBuilder<P>> roundRobin() {
        final RoundRobinRouterBuilderImpl<InnerWhenChoiceBuilder<P>> builder = new RoundRobinRouterBuilderImpl<InnerWhenChoiceBuilder<P>>(this);
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
    public ChoiceRouter build(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        if (lastWhenExpr != null) {
            choiceElements.add(new Route(lastWhenExpr, pipeline.getBuilders()));
            pipeline.getBuilders().clear();
        } else if (pipeline.getBuilders().size() > 0) {
            choiceElements.add(new Route(null, pipeline.getBuilders()));
        }

        final ChoiceRouter choiceRouter = new ChoiceRouter();
        for (final Route activeRoute : choiceElements) {
            if (activeRoute.getExpr() != null) {
                choiceRouter.addRoute(buildProcessorChain(activeRoute.getProcessorList(), muleContext, placeholder), activeRoute.getExpr().getFilter(muleContext, placeholder));
            } else {
                choiceRouter.setDefaultRoute(buildProcessorChain(activeRoute.getProcessorList(), muleContext, placeholder));
            }
        }

        return choiceRouter;
    }

    public class OtherwiseChoiceBuilderImpl<P extends PipelineBuilder<P>> implements OtherwiseChoiceBuilder<P> {
        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("unchecked")
        public P endChoice() {
            return (P) parentScope;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OtherwiseChoiceBuilder<P> log() {
            ChoiceRouterBuilderImpl.this.log();
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OtherwiseChoiceBuilder<P> log(final LogLevel level) throws NullPointerException {
            ChoiceRouterBuilderImpl.this.log(level);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OtherwiseChoiceBuilder<P> log(final String message) throws IllegalArgumentException {
            ChoiceRouterBuilderImpl.this.log(message);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OtherwiseChoiceBuilder<P> log(final String message, final LogLevel level) throws IllegalArgumentException, NullPointerException {
            ChoiceRouterBuilderImpl.this.log(message, level);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <E extends ExpressionEvaluatorDefinition> OtherwiseChoiceBuilder<P> log(final E expr) throws NullPointerException {
            ChoiceRouterBuilderImpl.this.log(expr);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <E extends ExpressionEvaluatorDefinition> OtherwiseChoiceBuilder<P> log(final E expr, final LogLevel level) throws NullPointerException {
            ChoiceRouterBuilderImpl.this.log(expr, level);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OtherwiseChoiceBuilder<P> echo() {
            ChoiceRouterBuilderImpl.this.echo();
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <B> InvokeBuilder<OtherwiseChoiceBuilder<P>> invoke(final B obj) throws NullPointerException, IllegalArgumentException {
            checkNotNull(obj, "obj");
            if (obj instanceof MessageProcessor){
                throw new IllegalArgumentException("Use `process` to execute custom MessageProcessor.");
            }

            final InvokeBuilderImpl<OtherwiseChoiceBuilder<P>> builder = new InvokeBuilderImpl<OtherwiseChoiceBuilder<P>>(this, obj);
            pipeline.addBuilder(builder);

            return builder;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <B> InvokeBuilder<OtherwiseChoiceBuilder<P>> invoke(final Class<B> clazz) throws NullPointerException, IllegalArgumentException {
            checkNotNull(clazz, "clazz");
            if (MessageProcessor.class.isAssignableFrom(clazz)){
                throw new IllegalArgumentException("Use `process` to execute custom MessageProcessor.");
            }

            final InvokeBuilderImpl<OtherwiseChoiceBuilder<P>> builder = new InvokeBuilderImpl<OtherwiseChoiceBuilder<P>>(this, clazz, Scope.PROTOTYPE);
            pipeline.addBuilder(builder);

            return builder;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <B> InvokeBuilder<OtherwiseChoiceBuilder<P>> invoke(final Class<B> clazz, final Scope scope) throws NullPointerException, IllegalArgumentException {
            checkNotNull(clazz, "clazz");
            checkNotNull(scope, "scope");

            if (clazz.isAssignableFrom(MessageProcessor.class)){
                throw new IllegalArgumentException("Use `process` to execute custom MessageProcessor.");
            }

            final InvokeBuilderImpl<OtherwiseChoiceBuilder<P>> builder = new InvokeBuilderImpl<OtherwiseChoiceBuilder<P>>(this, clazz, scope);
            pipeline.addBuilder(builder);

            return builder;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OtherwiseChoiceBuilder<P> executeFlow(String flowName) throws IllegalArgumentException {
            ChoiceRouterBuilderImpl.this.executeFlow(flowName);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OtherwiseChoiceBuilder<P> executeScript(String lang, String script) throws IllegalArgumentException {
            ChoiceRouterBuilderImpl.this.executeScript(lang, script);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OtherwiseChoiceBuilder<P> executeScript(String lang, AbstractModule.FileRefBuilder fileRef) throws IllegalArgumentException, NullPointerException {
            ChoiceRouterBuilderImpl.this.executeScript(lang, fileRef);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OtherwiseChoiceBuilder<P> executeScript(String lang, AbstractModule.ClasspathBuilder classpathRef) throws IllegalArgumentException, NullPointerException {
            ChoiceRouterBuilderImpl.this.executeScript(lang, classpathRef);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OtherwiseChoiceBuilder<P> executeScript(ScriptLanguage lang, String script) throws NullPointerException, IllegalArgumentException {
            ChoiceRouterBuilderImpl.this.executeScript(lang, script);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OtherwiseChoiceBuilder<P> executeScript(ScriptLanguage lang, AbstractModule.FileRefBuilder fileRef) throws NullPointerException {
            ChoiceRouterBuilderImpl.this.executeScript(lang, fileRef);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OtherwiseChoiceBuilder<P> executeScript(ScriptLanguage lang, AbstractModule.ClasspathBuilder classpathRef) throws NullPointerException {
            ChoiceRouterBuilderImpl.this.executeScript(lang, classpathRef);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <MP extends MessageProcessor> OtherwiseChoiceBuilder<P> process(Class<MP> clazz) throws NullPointerException {
            ChoiceRouterBuilderImpl.this.process(clazz);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <MP extends MessageProcessor> OtherwiseChoiceBuilder<P> process(MP obj) throws NullPointerException {
            ChoiceRouterBuilderImpl.this.process(obj);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OtherwiseChoiceBuilder<P> process(final MessageProcessorDefinition messageProcessor) throws NullPointerException, IllegalArgumentException {
            ChoiceRouterBuilderImpl.this.process(messageProcessor);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OtherwiseChoiceBuilder<P> send(final String uri) throws IllegalArgumentException {
            ChoiceRouterBuilderImpl.this.send(uri);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OtherwiseChoiceBuilder<P> send(final String uri, final ExchangePattern pattern) throws IllegalArgumentException {
            ChoiceRouterBuilderImpl.this.send(uri, pattern);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <E extends ExpressionEvaluatorDefinition> OtherwiseChoiceBuilder<P> transform(final E expr) throws NullPointerException {
            ChoiceRouterBuilderImpl.this.transform(expr);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> OtherwiseChoiceBuilder<P> transformTo(final Class<T> clazz) throws NullPointerException {
            ChoiceRouterBuilderImpl.this.transformTo(clazz);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T extends Transformer> OtherwiseChoiceBuilder<P> transformWith(final Class<T> clazz) throws NullPointerException {
            ChoiceRouterBuilderImpl.this.transformWith(clazz);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T extends Transformer> OtherwiseChoiceBuilder<P> transformWith(final T obj) throws NullPointerException {
            ChoiceRouterBuilderImpl.this.transformWith(obj);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OtherwiseChoiceBuilder<P> transformWith(final TransformerDefinition obj) throws NullPointerException {
            ChoiceRouterBuilderImpl.this.transformWith(obj);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OtherwiseChoiceBuilder<P> transformWith(final String ref) {
            ChoiceRouterBuilderImpl.this.transformWith(ref);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <E extends ExpressionEvaluatorDefinition> OtherwiseChoiceBuilder<P> filter(final E expr) throws NullPointerException {
            ChoiceRouterBuilderImpl.this.filter(expr);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> OtherwiseChoiceBuilder<P> filterBy(final Class<T> clazz) throws NullPointerException {
            ChoiceRouterBuilderImpl.this.filterBy(clazz);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <F extends Filter> OtherwiseChoiceBuilder<P> filterWith(final Class<F> clazz) throws NullPointerException {
            ChoiceRouterBuilderImpl.this.filterWith(clazz);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <F extends Filter> OtherwiseChoiceBuilder<P> filterWith(final F obj) throws NullPointerException {
            ChoiceRouterBuilderImpl.this.filterWith(obj);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OtherwiseChoiceBuilder<P> filterWith(final FilterDefinition obj) throws NullPointerException {
            ChoiceRouterBuilderImpl.this.filterWith(obj);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OtherwiseChoiceBuilder<P> filterWith(final String ref) throws IllegalArgumentException {
            ChoiceRouterBuilderImpl.this.filterWith(ref);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public MessagePropertiesBuilder<OtherwiseChoiceBuilder<P>> messageProperties() {
            final MessagePropertiesBuilderImpl<OtherwiseChoiceBuilder<P>> builder = new MessagePropertiesBuilderImpl<OtherwiseChoiceBuilder<P>>(this);
            pipeline.addBuilder(builder);

            return builder;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public BroadcastRouterBuilder<OtherwiseChoiceBuilder<P>> broadcast() {
            final BroadcastRouterBuilderImpl<OtherwiseChoiceBuilder<P>> builder = new BroadcastRouterBuilderImpl<OtherwiseChoiceBuilder<P>>(this);
            pipeline.addBuilder(builder);

            return builder;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ChoiceRouterBuilder<OtherwiseChoiceBuilder<P>> choice() {
            final ChoiceRouterBuilderImpl<OtherwiseChoiceBuilder<P>> builder = new ChoiceRouterBuilderImpl<OtherwiseChoiceBuilder<P>>(this);
            pipeline.addBuilder(builder);

            return builder;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public AsyncRouterBuilder<OtherwiseChoiceBuilder<P>> async() {
            final AsyncRouterBuilderImpl<OtherwiseChoiceBuilder<P>> builder = new AsyncRouterBuilderImpl<OtherwiseChoiceBuilder<P>>(this);
            pipeline.addBuilder(builder);

            return builder;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public FirstSuccessfulRouterBuilder<OtherwiseChoiceBuilder<P>> firstSuccessful() {
            final FirstSuccessfulRouterBuilderImpl<OtherwiseChoiceBuilder<P>> builder = new FirstSuccessfulRouterBuilderImpl<OtherwiseChoiceBuilder<P>>(this);
            pipeline.addBuilder(builder);

            return builder;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public RoundRobinRouterBuilder<OtherwiseChoiceBuilder<P>> roundRobin() {
            final RoundRobinRouterBuilderImpl<OtherwiseChoiceBuilder<P>> builder = new RoundRobinRouterBuilderImpl<OtherwiseChoiceBuilder<P>>(this);
            pipeline.addBuilder(builder);

            return builder;
        }
    }

    /**
     * Utility value class to store a list of message builders
     * associated to a choice route (when's or otherwise).
     *
     * @author porcelli
     */
    private static class Route {
        final ExpressionEvaluatorDefinition expr;

        final List<Builder<? extends MessageProcessor>> processorList;

        /**
         * @param expr          the expression evaluator definition, null is allowed
         * @param processorList the message processor builder list
         * @throws NullPointerException if {@code processorList} param is null
         */
        Route(final ExpressionEvaluatorDefinition expr, final List<Builder<? extends MessageProcessor>> processorList) throws NullPointerException {
            checkNotNull(processorList, "processorList");
            this.expr = expr;
            this.processorList = new ArrayList<Builder<? extends MessageProcessor>>(processorList);
        }

        /**
         * Getter of expression evaluator definition
         *
         * @return the expression evaluator definition
         */
        ExpressionEvaluatorDefinition getExpr() {
            return expr;
        }

        /**
         * Getter of message processor builder list
         *
         * @return the message processor builder list
         */
        List<Builder<? extends MessageProcessor>> getProcessorList() {
            return processorList;
        }
    }
}
