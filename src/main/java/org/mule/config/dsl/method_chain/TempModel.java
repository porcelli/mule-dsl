/*
 * $Id: 20811 2011-04-01 14:45:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.method_chain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface TempModel {

    public interface FlowProcessBuilder {
        /* component */

        FlowProcessBuilder log();

        FlowProcessBuilder log(String message);

        FlowProcessBuilder log(String message, ErrorLevel level);

        <E extends ExpressionEvaluatorBuilder> FlowProcessBuilder log(E expr);

        <E extends ExpressionEvaluatorBuilder> FlowProcessBuilder log(E expr, ErrorLevel level);

        FlowProcessBuilder echo();

        FlowProcessBuilder nil();

        CustomExecutorBuilder execute(Object obj);

        CustomExecutorBuilder execute(Class<?> clazz);

        /* outbound */

        OutboundEndpointProcessor send(String uri);

        /* transform */

        <E extends ExpressionEvaluatorBuilder> FlowProcessBuilder transform(E expr);

        FlowProcessBuilder transformTo(Class<?> clazz);

        /* filter */
        <E extends ExpressionEvaluatorBuilder> FlowProcessBuilder filter(E expr);

        /* routers */

        FlowProcessBuilder multicast(FlowProcessBuilder pipeline);

        ChoiceRouterBuilder choice();
    }

    public interface FlowBuilder {
        InboundEndpointProcessor from(String uri);
    }

    public interface ProcessorBuilder {
    }

    public interface CustomExecutorBuilder extends FlowProcessBuilder {
        FlowProcessBuilder asSingleton();

        FlowProcessBuilder asPrototype();
    }

    public interface EndpointProcessor extends FlowProcessBuilder {
    }

    public interface OutboundEndpointProcessor extends EndpointProcessor {
        FlowProcessBuilder asOneWay();

        FlowProcessBuilder asRequestResponse();
    }

    public interface InboundEndpointProcessor extends EndpointProcessor {
    }


    public interface RouterBuilder extends ProcessorBuilder {
    }

    public interface ChoiceRouterBuilder extends RouterBuilder, FlowProcessBuilder {

        <E extends ExpressionEvaluatorBuilder> FlowProcessBuilder when(E expr);

        OtherwiseChoiceBuilder otherwise();

        public interface WhenChoiceBuilder {
            ChoiceRouterBuilder then(FlowProcessBuilder pipeline);
        }

        public interface OtherwiseChoiceBuilder {
            FlowProcessBuilder then(FlowProcessBuilder pipeline);
        }
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

        String description() default "";
    }

}
