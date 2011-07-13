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
 * Interface that defines the choice router structure that is composed by
 * one or more when clausules an optional otherwise.
 *
 * @author porcelli
 */
public interface ChoiceRouterBuilder<P extends PipelineBuilder<P>> {

    /**
     * Creates an inner block that is executed if given expression is matched.
     *
     * @param expr the predicate that defines the block to be execute
     * @return the inner when builder
     * @throws NullPointerException if {@code expr} param is null
     */
    <E extends ExpressionEvaluatorDefinition> InnerWhenChoiceBuilder<P> when(E expr) throws NullPointerException;

    /**
     * Delimits the current block and returns back to outer block.
     *
     * @return the parameterized builder
     */
    P endChoice();

    /**
     * Inner interface that extends {@link PipelineBuilder} and just adds a way to add an otherwise clause to outer router.
     *
     * @author porcelli
     */
    public interface InnerWhenChoiceBuilder<P extends PipelineBuilder<P>> extends PipelineBuilder<InnerWhenChoiceBuilder<P>>, ChoiceRouterBuilder<P> {

        /**
         * Creates an inner block that is executed if non previous condition were match.
         *
         * @return the inner otherwise builder
         */
        OtherwiseChoiceBuilder<P> otherwise();
    }

    /**
     * Inner interface that extends {@link PipelineBuilder} and just adds a way to delimit the end of the choice router.
     *
     * @author porcelli
     */
    public interface OtherwiseChoiceBuilder<P extends PipelineBuilder<P>> extends PipelineBuilder<OtherwiseChoiceBuilder<P>> {
        /**
         * Delimits the current block and returns back to outer block.
         *
         * @return the parameterized builder
         */
        P endChoice();
    }

}
