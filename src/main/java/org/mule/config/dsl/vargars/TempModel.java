/*
 * $Id: 20811 2011-04-01 14:45:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.vargars;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface TempModel {

    public interface FlowBuilder {
        InboundEndpointProcessor from(String uri);

        FlowBuilderPipeline process(ProcessorBuilder processor, ProcessorBuilder... processors);
    }

    public interface FlowBuilderPipeline {
        FlowBuilderPipeline process(ProcessorBuilder processor, ProcessorBuilder... processors);
    }

    public interface ProcessorBuilder {
    }


    public interface CustomExecutorBuilder extends ProcessorBuilder {
        ProcessorBuilder asSingleton();

        ProcessorBuilder asPrototype();
    }

    public interface EndpointProcessor extends ProcessorBuilder {
    }

    public interface OutboundEndpointProcessor extends EndpointProcessor {
        ProcessorBuilder asOneWay();

        ProcessorBuilder asRequestResponse();
    }

    public interface ThenToInboundEndpointProcessor extends EndpointProcessor {

        FlowBuilderPipeline then();

    }

    public interface InboundEndpointProcessor extends FlowBuilderPipeline {
    }


    public interface RouterBuilder extends ProcessorBuilder {
    }

    public interface ChoiceRouterBuilder extends RouterBuilder {
        WhenChoiceBuilder when(String expr, Evaluator evaluator);

        WhenChoiceBuilder when(String expr, ExpressionEvaluatorBuilder evaluator);

        OtherwiseChoiceBuilder otherwise();

        public interface WhenChoiceBuilder {
            ChoiceRouterBuilder then(ProcessorBuilder... processorBuilders);
        }

        public interface OtherwiseChoiceBuilder {
            ProcessorBuilder then(ProcessorBuilder... processorBuilders);
        }
    }


    public interface ExpressionEvaluator {
    }

    public enum Evaluator {
        XPATH, BEAN, GROOVY
    }

    public interface ExpressionBuilder {
    }

    public interface ExpressionEvaluatorBuilder {
    }

    public interface ClasspathBuilder {
    }

    public interface FileRefBuilder {
    }

    public interface NameBuilder {
    }

    public interface URIBuilder {
    }

    public enum ErrorLevel {
        WARN, INFO, ERROR, FATAL
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ModuleInfo {
        String name();

        String description();
    }


}
