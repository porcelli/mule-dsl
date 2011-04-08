/*
 * $Id: 20811 2011-04-01 15:19:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.approach2.example.features.business;

import javax.inject.Named;

public class MyPojo {

    public void someMethod(String value) {
    }

    @Named("myMethodToExecute")
    public void otherMethod(String value) {
    }


    @MyBusinessMethod2Execute
    public void myBuzzMethod(String value) {
    }
}
