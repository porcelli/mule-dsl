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
import org.mule.api.MuleException;
import org.mule.config.dsl.internal.DefaultCatalogImpl;
import org.mule.config.dsl.internal.MuleContextBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.mule.config.dsl.internal.util.Preconditions.checkContentsNotNull;

public final class Mule {

    private Mule() {
    }

    private static MuleContext muleContext = null;

    public static MuleContext newMuleContext(final Module... modules) throws NullPointerException, IllegalArgumentException {

        checkContentsNotNull(modules, "modules");

        if (modules.length < 1) {
            throw new IllegalArgumentException("At least one module should be provided.");
        }

        final DefaultCatalogImpl myCatalog = new DefaultCatalogImpl();
        final List<com.google.inject.Module> guiceModules = new ArrayList<com.google.inject.Module>();

        for (final Module module : modules) {
            module.configure(myCatalog);
            if (module instanceof com.google.inject.Module) {
                guiceModules.add((com.google.inject.Module) module);
            }
        }

        final MuleContextBuilder muleContextBuilder;
        if (guiceModules.size() > 0) {
            final Injector injector = Guice.createInjector(guiceModules);
            muleContextBuilder = new MuleContextBuilder(myCatalog, injector);
        } else {
            muleContextBuilder = new MuleContextBuilder(myCatalog, null);
        }

        return muleContextBuilder.build();
    }

    public static synchronized void startMuleContext(final Module... modules) throws FailedToStartException {
        Mule.muleContext = newMuleContext(modules);
        try {
            muleContext.start();
        } catch (final MuleException e) {
            throw new FailedToStartException("Can't start mule context.", e);
        }
    }

    public static synchronized void stopMuleContext() throws FailedToStopException {
        if (muleContext != null && muleContext.isStarted()) {
            try {
                muleContext.stop();
            } catch (final MuleException e) {
                throw new FailedToStopException("Can't stop mule context.", e);
            }
        }
    }
}
