/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

public interface ChoiceRouterBuilder<P extends PipelineBuilder<P>> {

    <E extends ExpressionEvaluatorBuilder> InnerWhenChoiceBuilder<P> when(E expr);

    P endChoice();

    public static interface InnerWhenChoiceBuilder<P extends PipelineBuilder<P>> extends PipelineBuilder<InnerWhenChoiceBuilder<P>>, ChoiceRouterBuilder<P> {
        OtherwiseChoiceBuilder<P> otherwise();
    }

    public static interface OtherwiseChoiceBuilder<P extends PipelineBuilder<P>> extends PipelineBuilder<OtherwiseChoiceBuilder<P>> {
        P endChoice();
    }

}