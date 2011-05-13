/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.mule.api.MuleContext;
import org.mule.context.DefaultMuleContextFactory;

public final class Mule {

    private Mule() {
    }

    public static MuleContext newMuleContext(AbstractModule... modules) {

        MuleContext muleContext = null;
        try {
            muleContext = new DefaultMuleContextFactory().createMuleContext();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Registry myRegistry = new Registry(muleContext);

        for (AbstractModule module : modules) {
            module.setRegistry(myRegistry);
        }

        Injector injector = Guice.createInjector(modules);

        myRegistry.build(injector);

        return muleContext;
    }

}
