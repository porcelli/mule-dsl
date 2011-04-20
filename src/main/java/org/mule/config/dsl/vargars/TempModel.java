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

import java.lang.annotation.*;

public interface TempModel {

    public interface FlowBuilder {
        FlowBuilderPipeline from(URIBuilder uri);

        void process(ProcessorBuilder processor, ProcessorBuilder... processors);

        public interface FlowBuilderPipeline {
            FlowBuilder process(ProcessorBuilder processor, ProcessorBuilder... processors);
        }
    }

    public interface ProcessorBuilder {
    }


    public interface CustomExecutorBuilder extends ProcessorBuilder {
        CustomExecutorBuilder methodAnnotatedWith(Class<? extends Annotation> annotationType);

        CustomExecutorBuilder methodAnnotatedWith(Annotation annotation);

        CustomExecutorBuilder asSingleton();
    }

    public interface EndpointProcessor extends ProcessorBuilder {
    }

    public interface OutboundEndpointProcessor extends EndpointProcessor {
    }

    public interface ThenToInboundEndpointProcessor extends EndpointProcessor {

        InboundEndpointProcessor then();

    }

    public interface InboundEndpointProcessor extends EndpointProcessor {
        InboundEndpointProcessor processRequest(ProcessorBuilder... processors);

        InboundEndpointProcessor processResponse(ProcessorBuilder... processors);
    }


    public interface RouterBuilder extends ProcessorBuilder {
    }

    public interface ChoiceRouterBuilder extends RouterBuilder {
        WhenChoiceBuilder when(ExpressionBuilder x);

        OtherwiseChoiceBuilder otherwise();

        public interface WhenChoiceBuilder {
            ChoiceRouterBuilder then(ProcessorBuilder... processorBuilders);
        }

        public interface OtherwiseChoiceBuilder {
            ProcessorBuilder then(ProcessorBuilder... processorBuilders);
        }
    }


    public interface ExpressionBuilder {
    }

    public interface ExpressionEvaluator {
    }

    public interface ExpressionEvaluatorBuilder {
    }

    public interface NameBuilder {
    }

    public interface URIBuilder {
    }

    public interface ClasspathBuilder{
    }

    public interface FileRefBuilder{
    }

    public enum ErrorLevel {
        WARN, INFO, ERROR, FATAL
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ModuleName {
        String value();
    }


}
