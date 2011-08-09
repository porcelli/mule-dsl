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
import org.mule.api.construct.FlowConstruct;
import org.mule.config.dsl.internal.DefaultCatalogImpl;
import org.mule.config.dsl.internal.FlowInterfaceHandler;
import org.mule.config.dsl.internal.MuleAdvancedConfig;
import org.mule.config.dsl.internal.MuleContextBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mule.config.dsl.internal.util.Preconditions.checkContentsNotNull;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotEmpty;

/**
 * {@code Mule} is the entry point to Mule DSL. Creates {@link MuleContext} from
 * {@link Module}s and also allows user's start or stop a global defined context.
 *
 * @author porcelli
 */
public final class Mule {

    private final MuleContext muleContext;
    private final MuleAdvancedConfig advancedConfig;
    private final Map<String, FlowInterfaceHandler> flowCache;

    public static Mule newInstance(final Module... modules){
        return new Mule(modules);
    }

    /**
     * @param modules array of non-null {@link Module}s
     * @throws NullPointerException     if any of given {@code modules} is null
     * @throws IllegalArgumentException if {@code modules} is empty
     */
    private Mule(final Module... modules) throws NullPointerException, IllegalArgumentException {
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

        this.muleContext = muleContextBuilder.build();
        this.advancedConfig = new MuleAdvancedConfig(muleContext);
        this.flowCache = new HashMap<String, FlowInterfaceHandler>();
    }

    /**
     * Starts mule.
     *
     * @throws NullPointerException     if any of given {@code modules} is null
     * @throws IllegalArgumentException if {@code modules} is empty
     * @throws FailedToStartException   if can't start mule context
     */
    public synchronized Mule start() throws FailedToStartException {
        if (muleContext != null && muleContext.isStarted()) {
            return this;
        }
        try {
            muleContext.start();
        } catch (final MuleException e) {
            throw new FailedToStartException("Can't start mule context.", e);
        }
        return this;
    }

    /**
     * Stops mule context.
     *
     * @throws FailedToStopException if can't stop mule context
     */
    public synchronized Mule stop() throws FailedToStopException {
        if (muleContext != null && muleContext.isStarted()) {
            try {
                muleContext.stop();
            } catch (final MuleException e) {
                throw new FailedToStopException("Can't stop mule context.", e);
            }
        }
        return this;
    }

    /**
     * Checks if mule is started.
     *
     * @return true if started, otherwise false
     */
    public synchronized boolean isStarted() {
        if (muleContext == null) {
            return false;
        }
        return muleContext.isStarted();
    }

    public MuleAdvancedConfig advanced() {
        return advancedConfig;
    }

    public FlowInterfaceHandler flow(String name) {
        checkNotEmpty(name, "name");
        if (!flowCache.containsKey(name)) {
            final FlowConstruct flow = muleContext.getRegistry().lookupFlowConstruct(name);
            if (flow == null) {
                flowCache.put(name, null);
            } else {
                flowCache.put(name, new FlowInterfaceHandler(flow, muleContext));
            }
        }
        final FlowInterfaceHandler flowProcess = flowCache.get(name);
        if (flowProcess == null) {
            throw new FlowNotFoundException("Flow not found");
        }
        return flowProcess;
    }
}
