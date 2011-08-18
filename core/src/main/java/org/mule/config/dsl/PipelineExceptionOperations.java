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
 * Interface that defines all exception path related operations.
 *
 * @author porcelli
 */
public interface PipelineExceptionOperations {

    <E extends Exception> PipelineExceptionInvokeOperations onException(Class<E> exception) throws NullPointerException;

    public static interface PipelineExceptionInvokeOperations {
        /**
         * Invokes the most appropriate {@code obj}'s method.
         *
         * @param obj the object to be invoked
         * @return the executor builder
         * @throws NullPointerException     if {@code obj} param is null
         * @throws IllegalArgumentException if {@code obj} is an instance of {@link org.mule.api.processor.MessageProcessor}
         * @see org.mule.model.resolvers.ReflectionEntryPointResolver
         */
        <B> InvokeBuilder invoke(B obj) throws NullPointerException, IllegalArgumentException;

        <B> InvokeBuilder invoke(Class<B> clazz) throws NullPointerException, IllegalArgumentException;
    }

    public interface InvokeBuilder extends PipelineExceptionOperations {

        /**
         * Defines the method to be invoked that should be annotated by given annotation type.
         *
         * @param annotationType the annotation type that method should be annotated by
         * @return the inner invoker builder
         * @throws NullPointerException if {@code annotationType} param is null
         */
        InnerArgsInvokeBuilder methodAnnotatedWith(Class<? extends Annotation> annotationType) throws NullPointerException;

        /**
         * Defines the method to be invoked that should be annotated by given annotation.
         *
         * @param annotation the annotation that method should be annotated by
         * @return the inner invoker builder
         * @throws NullPointerException if {@code annotation} param is null
         */
        InnerArgsInvokeBuilder methodAnnotatedWith(Annotation annotation) throws NullPointerException;

        /**
         * Defines the method to be invoked.
         *
         * @param name the method name to be invoked
         * @return the inner invoker builder
         * @throws IllegalArgumentException if {@code name} param is empty or null
         */
        InnerArgsInvokeBuilder methodName(String name) throws IllegalArgumentException;

        /**
         * Points that the choosen method will get the default arg.
         * <p/>
         * <b>Important Note:</b> this method has no effect, but helps legibility and, most important,
         * in some occations it's necessary to invoke it to show the block terminator.
         *
         * @return the parameterized builder
         */
        PipelineExceptionOperations withDefaultArg();

        /**
         * Inner interface that extends {@link PipelineBuilder} and adds specific configuration related to method arguments.
         *
         * @author porcelli
         */
        public interface InnerArgsInvokeBuilder extends PipelineExceptionOperations {

            /**
             * Points that the choosen method will not get any args.
             * <p/>
             * <b>Important Note:</b> this method has no effect, but helps legibility and, most important,
             * in some occations it's necessary to invoke it to show the block terminator.
             *
             * @return the parameterized builder
             */
            PipelineExceptionOperations withoutArgs();

            /**
             * Defines the argument list to be used on method invoke.
             *
             * @param args the argument list
             * @return the parameterized builder
             * @throws NullPointerException if any element of {@code args} param is null
             */
            <E extends ExpressionEvaluatorDefinition> PipelineExceptionOperations args(E... args) throws NullPointerException;
        }
    }


}

