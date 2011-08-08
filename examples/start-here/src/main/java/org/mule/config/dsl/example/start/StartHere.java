/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.example.start;

import org.mule.api.MuleException;
import org.mule.config.dsl.AbstractModule;
import org.mule.config.dsl.Mule;

public class StartHere {

    public static void main(String... args) throws MuleException {
        new Mule(new MyModule()).start();
    }

    public static class MyModule extends AbstractModule {

        @Override
        protected void configure() {
            //Define your flows here.
        }
    }

}
