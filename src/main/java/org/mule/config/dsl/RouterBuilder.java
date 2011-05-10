/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

public interface RouterBuilder {

    public interface ChoiceRouterBuilder extends RouterBuilder {

        <E extends ExpressionEvaluatorBuilder> PipelineBuilder when(E expr);

        OtherwiseChoiceBuilder otherwise();

        public interface WhenChoiceBuilder {
            ChoiceRouterBuilder then(PipelineBuilder pipeline);
        }

        public interface OtherwiseChoiceBuilder {
            PipelineBuilder then(PipelineBuilder pipeline);
        }
    }

}
