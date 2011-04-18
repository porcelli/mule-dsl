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

        FlowProcessBuilder echo();

        FlowProcessBuilder nil();

        FlowProcessBuilder passThrough();

        CustomExecutorBuilder execute(Object obj);

        CustomExecutorBuilder execute(Class<?> clazz);

        /* outbound */

        EndpointProcessor send(URIBuilder uri);

        EndpointProcessor sendAndWait(URIBuilder uri);

        /* transform */

        PayloadTypeTransformToBuilder transform(Class<?> clazz);

        FlowProcessBuilder transform(ExpressionBuilder expression);

        /* filter */

        FlowProcessBuilder filter(ExpressionBuilder expression);

        /* routers */

        MulticastRouterBuilder multicast(FlowProcessBuilder pipeline);

        ChoiceRouterBuilder choice();
    }

    public interface FlowBuilder {
        InboundEndpointProcessor from(URIBuilder uri);
    }

    public interface ProcessorBuilder {
    }

    public interface PayloadTypeTransformToBuilder {
        FlowProcessBuilder to(Class<?> clzz);
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

    public interface MulticastRouterBuilder extends RouterBuilder {
        RouterBuilder onFirstSuccessful();

        RouterBuilder onFirstSuccessful(ExpressionBuilder ex);
    }

    public interface ChoiceRouterBuilder extends RouterBuilder {
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

    public interface NameBuilder {
    }

    public interface URIBuilder {
    }

    public interface HostBuilder {
        HostBuilder port(int port);

        HostBuilder path(String path);
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ModuleName {
        String value();
    }
}
