/*
 * $Id: 20811 2011-04-01 15:19:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.vargars.example.features.business;

import javax.inject.Named;

public class MyPojo {

    @Named("myMethodToExecute")
    public void method1() {
    }

    @MyBusinessMethod2Execute
    public void method2() {
    }

}
