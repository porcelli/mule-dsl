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
import org.mule.api.component.Component;
import org.mule.api.lifecycle.Callable;
import org.mule.component.DefaultJavaComponent;
import org.mule.component.SimpleCallableJavaComponent;
import org.mule.config.dsl.ExecutorBuilder;
import org.mule.config.dsl.ExpressionEvaluatorBuilder;
import org.mule.config.dsl.PipelineBuilder;
import org.mule.config.dsl.Scope;
import org.mule.config.dsl.component.InvokerMessageProcessorGuiceAdaptor;
import org.mule.config.dsl.internal.util.InjectorUtil;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.object.PrototypeObjectFactory;
import org.mule.object.SingletonObjectFactory;
import org.mule.processor.InvokerMessageProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import static org.mule.config.dsl.internal.util.EntryPointResolverSetUtil.createDefaultResolverSet;
import static org.mule.config.dsl.internal.util.ExpressionArgsUtil.toListOfStrings;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

class ExecutorBuilderImpl<P extends PipelineBuilder<P>> extends PipelineBuilderImpl<P> implements ExecutorBuilder<P>, Builder<Object> {

    private Object obj;
    private final Class<?> clazz;
    private final Builder<?> builder;
    private final Scope scope;

    private Annotation methodAnnotatedWith = null;
    private Class<? extends Annotation> methodAnnotatedWithCustomType = null;
    private ExpressionEvaluatorBuilder[] args = null;

    public ExecutorBuilderImpl(final P parentScope, Class<?> clazz, Scope scope) {
        super(parentScope);
        this.clazz = checkNotNull(clazz, "clazz");
        this.scope = checkNotNull(scope, "scope");
        this.obj = null;
        this.builder = null;
    }

    public ExecutorBuilderImpl(final P parentScope, Object obj) {
        super(parentScope);
        this.obj = checkNotNull(obj, "obj");
        this.scope = Scope.PROTOTYPE;
        this.clazz = null;
        this.builder = null;
    }

    public ExecutorBuilderImpl(final P parentScope, Builder<?> builder) {
        super(parentScope);
        this.builder = checkNotNull(builder, "builder");
        this.scope = Scope.PROTOTYPE;
        this.clazz = null;
        this.obj = null;
    }

    @Override
    public Object build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {

        if (methodAnnotatedWith != null || methodAnnotatedWithCustomType != null) {
            return buildInvokerMessage(muleContext, injector, placeholder);
        }

        return buildComponent(muleContext, injector, placeholder);
    }

    private InvokerMessageProcessor buildInvokerMessage(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        InvokerMessageProcessorGuiceAdaptor invoker = null;

        final Class<?> type;
        if (clazz != null) {
            type = clazz;
        } else if (obj != null) {
            type = obj.getClass();
        } else {
            throw new RuntimeException();
        }

        find_method:
        for (Method method : type.getMethods()) {
            if (methodAnnotatedWithCustomType != null) {
                if (method.isAnnotationPresent(methodAnnotatedWithCustomType)) {
                    invoker = new InvokerMessageProcessorGuiceAdaptor(injector);
                    invoker.setMethodName(method.getName());
                    break find_method;
                }
            } else {
                for (Annotation annotation : method.getAnnotations()) {
                    if (annotation.equals(methodAnnotatedWith)) {
                        invoker = new InvokerMessageProcessorGuiceAdaptor(injector);
                        invoker.setMethodName(method.getName());
                        break find_method;
                    }
                }
            }
        }

        if (invoker == null) {
            throw new RuntimeException();
        }

        if (args != null) {
            invoker.setArguments(toListOfStrings(args));
        }
        invoker.setMuleContext(muleContext);

        invoker.setObjectType(clazz);
        invoker.setObject(obj);

        return invoker;
    }

    private Component buildComponent(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        if (clazz != null) {
            if (Callable.class.isAssignableFrom(clazz)) {
                try {
                    if (InjectorUtil.hasProvider(injector, clazz)) {
                        return new SimpleCallableJavaComponent(new GuiceLookup(injector, clazz));
                    }
                    return new SimpleCallableJavaComponent(clazz);
                } catch (DefaultMuleException e) {
                    //todo improve
                    throw new RuntimeException(e);
                }
            } else {
                if (InjectorUtil.hasProvider(injector, clazz)) {
                    return new DefaultJavaComponent(new GuiceLookup(injector, clazz), createDefaultResolverSet(), null);
                } else {
                    if (scope.equals(Scope.PROTOTYPE)) {
                        return new DefaultJavaComponent(new PrototypeObjectFactory(clazz), createDefaultResolverSet(), null);
                    } else if (scope.equals(Scope.SINGLETON)) {
                        return new DefaultJavaComponent(new SingletonObjectFactory(clazz), createDefaultResolverSet(), null);
                    }
                }
            }
        }

        if (builder != null) {
            obj = builder.build(muleContext, injector, placeholder);
        }

        if (obj instanceof Callable) {
            return new SimpleCallableJavaComponent((Callable) obj);
        }

        return new DefaultJavaComponent(new SingletonObjectFactory(obj), createDefaultResolverSet(), null);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected P getThis() {
        if (parentScope != null) {
            return (P) parentScope;
        }
        return (P) this;
    }

    @Override
    public InnerArgsExecutorBuilder<P> methodAnnotatedWith(Class<? extends Annotation> annotationType) {
        this.methodAnnotatedWithCustomType = checkNotNull(annotationType, "annotationType");
        return new InnerArgsExecutorBuilderImpl<P>();
    }

    @Override
    public InnerArgsExecutorBuilder<P> methodAnnotatedWith(Annotation annotation) {
        this.methodAnnotatedWith = checkNotNull(annotation, "annotation");
        return new InnerArgsExecutorBuilderImpl<P>();
    }

    @Override
    public P withoutArgs() {
        return parentScope;
    }

    private <E extends ExpressionEvaluatorBuilder> P args(E... args) {
        this.args = checkNotNull(args, "args");
        return parentScope;
    }

    public class InnerArgsExecutorBuilderImpl<P extends PipelineBuilder<P>> extends PipelineBuilderImpl<InnerArgsExecutorBuilder<P>> implements InnerArgsExecutorBuilder<P> {

        InnerArgsExecutorBuilderImpl() {
            super(null);
        }

        @Override
        @SuppressWarnings("unchecked")
        public P withoutArgs() {
            return (P) ExecutorBuilderImpl.this.withoutArgs();
        }

        @Override
        @SuppressWarnings("unchecked")
        public <E extends ExpressionEvaluatorBuilder> P args(E... args) {
            return (P) ExecutorBuilderImpl.this.args(args);
        }
    }

}
