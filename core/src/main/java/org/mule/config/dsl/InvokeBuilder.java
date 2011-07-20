/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import java.lang.annotation.Annotation;

/**
 * Interface that extends {@link PipelineBuilder} and adds some additional invoke specific configuration.
 *
 * @author porcelli
 */
public interface InvokeBuilder<P extends PipelineBuilder<P>> extends PipelineBuilder<P> {

    /**
     * Defines the method to be invoked that should be annotated by given annotation type.
     *
     * @param annotationType the annotation type that method should be annotated by
     * @return the inner invoker builder
     * @throws NullPointerException if {@code annotationType} param is null
     */
    InnerArgsInvokeBuilder<P> methodAnnotatedWith(Class<? extends Annotation> annotationType) throws NullPointerException;

    /**
     * Defines the method to be invoked that should be annotated by given annotation.
     *
     * @param annotation the annotation that method should be annotated by
     * @return the inner invoker builder
     * @throws NullPointerException if {@code annotation} param is null
     */
    InnerArgsInvokeBuilder<P> methodAnnotatedWith(Annotation annotation) throws NullPointerException;

    /**
     * Defines the method to be invoked.
     *
     * @param name the method name to be invoked
     * @return the inner invoker builder
     * @throws IllegalArgumentException if {@code name} param is empty or null
     */
    InnerArgsInvokeBuilder<P> methodName(String name) throws IllegalArgumentException;

    /**
     * Points that the choosen method will get the default arg.
     * <p/>
     * <b>Important Note:</b> this method has no effect, but helps legibility and, most important,
     * in some occations it's necessary to invoke it to show the block terminator.
     *
     * @return the parameterized builder
     */
    P withDefaultArg();

    /**
     * Inner interface that extends {@link PipelineBuilder} and adds specific configuration related to method arguments.
     *
     * @author porcelli
     */
    public interface InnerArgsInvokeBuilder<P extends PipelineBuilder<P>> extends PipelineBuilder<InnerArgsInvokeBuilder<P>> {

        /**
         * Points that the choosen method will not get any args.
         * <p/>
         * <b>Important Note:</b> this method has no effect, but helps legibility and, most important,
         * in some occations it's necessary to invoke it to show the block terminator.
         *
         * @return the parameterized builder
         */
        P withoutArgs();

        /**
         * Defines the argument list to be used on method invoke.
         *
         * @param args the argument list
         * @return the parameterized builder
         * @throws NullPointerException if any element of {@code args} param is null
         */
        <E extends ExpressionEvaluatorDefinition> P args(E... args) throws NullPointerException;
    }
}

