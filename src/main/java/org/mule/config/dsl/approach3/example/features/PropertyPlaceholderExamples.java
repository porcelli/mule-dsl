/*
 * $Id: 20811 2011-03-30 16:05:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.approach3.example.features;

import org.mule.config.dsl.approach3.AbstractModule;
import org.mule.config.dsl.approach3.example.features.business.MyPojo;

import java.io.File;

public class PropertyPlaceholderExamples {

    public static class PropertyPlaceholders extends AbstractModule {
        @Override
        public void configure() {
            //Defines a property placeholder with a string to point a config file
            usePropertyPlaceholder("config.properties");
            //Defines a property placeholder with a file
            usePropertyPlaceholder(new File("config2.properties"));
            //Defines a property placeholder with an input stream
            usePropertyPlaceholder(PropertyPlaceholders.class.getResourceAsStream("config3.properties"));

            newFlow("MyFlow").in(
                    //with a variable that paceholer will update
                    from(JMS_In.class).queue("${queueName}")
                            .processResponse(transformTo(String.class))
            ).process(
                    execute(MyPojo.class)
            );
        }
    }
}