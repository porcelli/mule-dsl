/*
 * $Id: 20811 2011-03-30 16:05:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.aproach2.example.features;

import com.google.inject.name.Names;
import org.mule.config.dsl.aproach2.AbstractMethodModule;
import org.mule.config.dsl.aproach2.example.features.business.MyBusinessMethod2Execute;
import org.mule.config.dsl.aproach2.example.features.business.MyPojo;

public class ComponentExamples {

    public static class BookStore extends AbstractMethodModule {
        @Override
        public void configure() {

            Object myObj = null;

            newFlow("MyFlow").in(
                    from(JMS.queue("queueName"))
                            .processResponse(transformTo(String.class))
            ).process(
                    //will check if its Callable, otherwise will use reflection for entry point resolver
                    //if method not found, will throw an exception
                    execute(MyPojo.class),
                    //will execute the method annotated with @javax.inject.Named with "myMethodToExecute" as parameter
                    //if method not found, will throw an exception
                    execute(MyPojo.class).methodAnnotatedWith(Names.named("myMethodToExecute")),
                    //will execute the method annotated with an application specific named MyBusinessMethod2Execute
                    //if method not found, will throw an exception
                    execute(MyPojo.class).methodAnnotatedWith(MyBusinessMethod2Execute.class),
                    //components can be singleton
                    execute(MyPojo.class).asSingleton(),
                    //components can be singleton
                    execute(MyPojo.class).methodAnnotatedWith(Names.named("myMethodToExecute")).asSingleton(),
                    //components can be singleton
                    execute(MyPojo.class).methodAnnotatedWith(MyBusinessMethod2Execute.class).asSingleton(),
                    //its possible to use an already instantiated object
                    execute(myObj),
                    //its possible to use an already instantiated object + explicit method definition
                    execute(myObj).methodAnnotatedWith(Names.named("myMethodToExecute")),
                    //its possible to use an already instantiated object + explicit method definition
                    execute(myObj).methodAnnotatedWith(MyBusinessMethod2Execute.class),
                    //in this case the asSingleton() is ignored
                    execute(myObj).asSingleton()
            );
        }
    }
}