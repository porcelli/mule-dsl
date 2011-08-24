/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import com.google.inject.Injector;
import org.mule.api.DefaultMuleException;
import org.mule.api.MuleContext;
import org.mule.api.exception.MessagingExceptionHandler;
import org.mule.api.lifecycle.Callable;
import org.mule.api.processor.MessageProcessor;
import org.mule.component.DefaultJavaComponent;
import org.mule.component.SimpleCallableJavaComponent;
import org.mule.config.dsl.*;
import org.mule.config.dsl.component.InvokerMessageProcessorAdaptor;
import org.mule.config.dsl.internal.util.InjectorUtil;
import org.mule.object.PrototypeObjectFactory;
import org.mule.object.SingletonObjectFactory;
import org.mule.processor.InvokerMessageProcessor;
import org.mule.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import static org.mule.config.dsl.internal.GuiceRegistry.GUICE_INJECTOR_REF;
import static org.mule.config.dsl.internal.util.EntryPointResolverSetUtil.getDefaultResolverSet;
import static org.mule.config.dsl.internal.util.ExpressionArgsUtil.toListOfStrings;
import static org.mule.config.dsl.internal.util.Preconditions.checkContentsNotNull;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

/**
 * Internal implementation of {@link org.mule.config.dsl.InvokeBuilder} interface that, based on its internal state,
 * builds:
 * <ul>
 * <li>InvokerMessageProcessor - if a method name or an annotation is provided</li>
 * <li>SimpleCallableJavaComponent - if component type or instance is a {@link Callable}</li>
 * <li>DefaultJavaComponent - otherwise</li>
 * </ul>
 *
 * @author porcelli
 * @see org.mule.config.dsl.PipelineBuilder#invoke(Object)
 * @see org.mule.config.dsl.PipelineBuilder#invoke(Class)
 * @see org.mule.config.dsl.PipelineBuilder#invoke(Class, org.mule.config.dsl.Scope)
 */
public class InvokeBuilderImpl<P extends PipelineBuilder<P>> extends PipelineBuilderImpl<P> implements InvokeBuilder<P>, Builder<MessageProcessor> {

    private Object obj;
    private final Class<?> clazz;
    private final Builder<?> builder;
    private final Scope scope;

    private Annotation methodAnnotatedWith = null;
    private String methodName = null;
    private Class<? extends Annotation> methodAnnotatedWithCustomType = null;
    private ExpressionEvaluatorDefinition[] args = null;

    /**
     * @param parentScope the parent scope, null is allowed
     * @param clazz       the type to be invoked, Mule will instantiate it at runtime
     * @param scope       the type scope
     * @throws NullPointerException if {@code clazz} or {@code scope} params are null
     */
    public InvokeBuilderImpl(final P parentScope, final Class<?> clazz, final Scope scope) throws NullPointerException {
        super(parentScope);
        this.clazz = checkNotNull(clazz, "clazz");
        this.scope = checkNotNull(scope, "scope");
        this.obj = null;
        this.builder = null;
    }

    /**
     * @param parentScope the parent scope, null is allowed
     * @param clazz       the type to be invoked, Mule will instantiate it at runtime
     * @throws NullPointerException if {@code clazz} param is null
     */
    public InvokeBuilderImpl(final P parentScope, final Class<?> clazz) throws NullPointerException {
        super(parentScope);
        this.clazz = checkNotNull(clazz, "clazz");
        this.scope = Scope.PROTOTYPE;
        this.obj = null;
        this.builder = null;
    }

    /**
     * @param parentScope the parent scope, null is allowed
     * @param obj         the object to be invoked
     * @throws NullPointerException if {@code obj} param is null
     */
    public InvokeBuilderImpl(final P parentScope, final Object obj) throws NullPointerException {
        super(parentScope);
        this.obj = checkNotNull(obj, "obj");
        this.scope = Scope.PROTOTYPE;
        this.clazz = null;
        this.builder = null;
    }

    /**
     * @param parentScope the parent scope, null is allowed
     * @param builder     the object builder
     * @throws NullPointerException if {@code builder} param is null
     */
    public InvokeBuilderImpl(final P parentScope, final Builder<?> builder) throws NullPointerException {
        super(parentScope);
        this.builder = checkNotNull(builder, "builder");
        this.scope = Scope.PROTOTYPE;
        this.clazz = null;
        this.obj = null;
    }

    /**
     * Getter of internal component builder
     *
     * @return the component builder
     */
    public Builder<?> getBuilder() {
        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageProcessor build(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        if (methodAnnotatedWith != null || methodAnnotatedWithCustomType != null || methodName != null) {
            return buildInvokerMessage(muleContext, placeholder);
        }

        return buildComponent(muleContext, placeholder);
    }

    private InvokerMessageProcessor buildInvokerMessage(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws IllegalStateException {
        InvokerMessageProcessorAdaptor invoker = null;

        final Class<?> type;
        if (clazz != null) {
            type = clazz;
        } else if (obj != null) {
            type = obj.getClass();
        } else {
            throw new IllegalStateException("Type or object instance should be provided");
        }

        find_method:
        for (final Method method : type.getMethods()) {
            if (methodName != null && method.getName().equals(methodName)) {
                invoker = new InvokerMessageProcessorAdaptor();
                invoker.setMethodName(method.getName());
                break find_method;
            } else if (methodAnnotatedWithCustomType != null) {
                if (method.isAnnotationPresent(methodAnnotatedWithCustomType)) {
                    invoker = new InvokerMessageProcessorAdaptor();
                    invoker.setMethodName(method.getName());
                    break find_method;
                }
            } else {
                for (final Annotation annotation : method.getAnnotations()) {
                    if (annotation.equals(methodAnnotatedWith)) {
                        invoker = new InvokerMessageProcessorAdaptor();
                        invoker.setMethodName(method.getName());
                        break find_method;
                    }
                }
            }
        }

        if (invoker == null) {
            throw new IllegalStateException("Can't find method to be invoked.");
        }

        if (args != null) {
            invoker.setArguments(toListOfStrings(placeholder, args));
        }
        invoker.setMuleContext(muleContext);

        invoker.setObjectType(clazz);
        invoker.setObject(obj);

        return invoker;
    }

    private MessageProcessor buildComponent(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws ConfigurationException {
        if (clazz != null) {
            if (Callable.class.isAssignableFrom(clazz)) {
                try {
                    if (InjectorUtil.hasProvider(muleContext.getRegistry().<Injector>get(GUICE_INJECTOR_REF), clazz)) {
                        return new SimpleCallableJavaComponent(new MuleContextLookup(clazz, scope, muleContext));
                    }
                    return new SimpleCallableJavaComponent(clazz);
                } catch (final DefaultMuleException e) {
                    throw new ConfigurationException("Failed to configure a SimpleCallableJavaComponent.", e);
                }
            } else if (MessageProcessor.class.isAssignableFrom(clazz)) {
                try {
                    if (InjectorUtil.hasProvider(muleContext.getRegistry().<Injector>get(GUICE_INJECTOR_REF), clazz)) {
                        return (MessageProcessor) muleContext.getRegistry().lookupObject(clazz);
                    } else {
                        return (MessageProcessor) ClassUtils.instanciateClass(clazz);
                    }
                } catch (final Exception e) {
                    throw new ConfigurationException("Failed to configure a MessageProcessor.", e);
                }
            } else {
                if (InjectorUtil.hasProvider(muleContext.getRegistry().<Injector>get(GUICE_INJECTOR_REF), clazz)) {
                    return new DefaultJavaComponent(new MuleContextLookup(clazz, scope, muleContext), getDefaultResolverSet(), null);
                } else {
                    if (scope.equals(Scope.PROTOTYPE)) {
                        return new DefaultJavaComponent(new PrototypeObjectFactory(clazz), getDefaultResolverSet(), null);
                    } else if (scope.equals(Scope.SINGLETON)) {
                        return new DefaultJavaComponent(new SingletonObjectFactory(clazz), getDefaultResolverSet(), null);
                    }
                }
            }
        }

        if (builder != null) {
            obj = builder.build(muleContext, placeholder);
        }

        if (obj instanceof Callable) {
            return new SimpleCallableJavaComponent((Callable) obj);
        } else if (obj instanceof MessageProcessor) {
            return (MessageProcessor) obj;
        }

        return new DefaultJavaComponent(new SingletonObjectFactory(obj), getDefaultResolverSet(), null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    protected P getThis() {
        if (parentScope != null) {
            return parentScope;
        }
        return (P) this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InnerArgsInvokeBuilder<P> methodAnnotatedWith(final Class<? extends Annotation> annotationType) throws NullPointerException {
        this.methodAnnotatedWithCustomType = checkNotNull(annotationType, "annotationType");
        return new InnerArgsInvokeBuilderImpl<P>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InnerArgsInvokeBuilder<P> methodAnnotatedWith(final Annotation annotation) throws NullPointerException {
        this.methodAnnotatedWith = checkNotNull(annotation, "annotation");
        return new InnerArgsInvokeBuilderImpl<P>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InnerArgsInvokeBuilder<P> methodName(String methodName) throws IllegalArgumentException {
        this.methodName = checkNotNull(methodName, "methodName");
        return new InnerArgsInvokeBuilderImpl<P>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P withDefaultArg() {
        return parentScope;
    }

    private <E extends ExpressionEvaluatorDefinition> P args(final E... args) throws NullPointerException {
        this.args = checkContentsNotNull(args, "args");
        return parentScope;
    }

    public class InnerArgsInvokeBuilderImpl<P extends PipelineBuilder<P>> extends PipelineBuilderImpl<InnerArgsInvokeBuilder<P>> implements InnerArgsInvokeBuilder<P> {

        InnerArgsInvokeBuilderImpl() {
            super(InvokeBuilderImpl.this.getFlowName());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("unchecked")
        public P withoutArgs() {
            return (P) InvokeBuilderImpl.this.withDefaultArg();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("unchecked")
        public <E extends ExpressionEvaluatorDefinition> P args(final E... args) throws NullPointerException {
            return (P) InvokeBuilderImpl.this.args(args);
        }


        /**
         * {@inheritDoc}
         */
        @Override
        public void addExceptionBuilder(Builder<? extends MessagingExceptionHandler> builder) throws NullPointerException {
            checkNotNull(builder, "builder");
            InvokeBuilderImpl.this.addExceptionBuilder(builder);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<Builder<? extends MessagingExceptionHandler>> getExceptionBuilders() {
            return InvokeBuilderImpl.this.getExceptionBuilders();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isExceptionBuilderListEmpty() {
            return InvokeBuilderImpl.this.isExceptionBuilderListEmpty();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<MessagingExceptionHandler> buildExceptionHandlerList(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException {
            checkNotNull(muleContext, "muleContext");
            checkNotNull(placeholder, "placeholder");
            return InvokeBuilderImpl.this.buildExceptionHandlerList(muleContext, placeholder);
        }
    }

}
