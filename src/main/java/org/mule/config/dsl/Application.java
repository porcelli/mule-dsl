/*
 * $Id: 20811 2011-03-30 15:49:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl;

import org.mule.config.domain.Lifecycle;

public class Application implements Lifecycle {

    public void start() {
    }

    public void stop() {
    }

    public Flow getFlow(String dataWarehouse) {
        return null;
    }
}
