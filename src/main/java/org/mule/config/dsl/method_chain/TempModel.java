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

import java.lang.annotation.*;

public interface TempModel {

    public interface FlowProcessBuilder {
        /* component */

        FlowProcessBuilder log();

        FlowProcessBuilder log(String message);

        FlowProcessBuilder log(ExpressionBuilder message);

        FlowProcessBuilder log(String message, ErrorLevel level);

        FlowProcessBuilder log(ExpressionBuilder expression, ErrorLevel level);

        FlowProcessBuilder echo();

        FlowProcessBuilder nil();

        CustomExecutorBuilder execute(Object obj);

        CustomExecutorBuilder execute(Class<?> clazz);

        /* outbound */

        EndpointProcessor send(URIBuilder uri);

        EndpointProcessor sendAndWait(URIBuilder uri);

        /* transform */

        FlowProcessBuilder transform(ExpressionBuilder expression);

        FlowProcessBuilder transformTo(Class<?> clazz);

        /* filter */

        FlowProcessBuilder filter(ExpressionBuilder expression);

        /* routers */

        FlowProcessBuilder multicast(FlowProcessBuilder pipeline);

        ChoiceRouterBuilder choice();
    }

    public interface FlowBuilder {
        InboundEndpointProcessor from(URIBuilder uri);
    }

    public interface ProcessorBuilder {
    }

    public interface CustomExecutorBuilder extends FlowProcessBuilder {
        CustomExecutorBuilder methodAnnotatedWith(Class<? extends Annotation> annotationType);

        CustomExecutorBuilder methodAnnotatedWith(Annotation annotation);

        CustomExecutorBuilder asSingleton();
    }

    public interface EndpointProcessor extends FlowProcessBuilder {
    }

    public interface OutboundEndpointProcessor extends EndpointProcessor {
    }

    public interface InboundEndpointProcessor extends EndpointProcessor {
        InboundEndpointProcessor processRequest(FlowProcessBuilder pipeline);

        InboundEndpointProcessor processResponse(FlowProcessBuilder pipeline);
    }


    public interface RouterBuilder extends ProcessorBuilder {
    }

    public interface ChoiceRouterBuilder extends RouterBuilder, FlowProcessBuilder {
        WhenChoiceBuilder when(ExpressionBuilder x);

        OtherwiseChoiceBuilder otherwise();

        public interface WhenChoiceBuilder {
            ChoiceRouterBuilder then(FlowProcessBuilder pipeline);
        }

        public interface OtherwiseChoiceBuilder {
            ProcessorBuilder then(FlowProcessBuilder pipeline);
        }
    }

    public interface ExpressionBuilder {
    }

    public interface ExpressionEvaluator {
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
    public @interface ModuleName {
        String value();
    }
}
