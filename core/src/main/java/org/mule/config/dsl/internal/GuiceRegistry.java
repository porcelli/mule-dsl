/*
 * ---------------------------------------------------------------------------
 *  Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import com.google.inject.Injector;
import org.mule.api.MuleContext;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.registry.RegistrationException;
import org.mule.lifecycle.RegistryLifecycleManager;
import org.mule.registry.AbstractRegistry;

import java.util.Collection;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

/**
 * A very basic read-only guice based registry.
 *
 * @author porcelli
 */
public class GuiceRegistry extends AbstractRegistry implements Initialisable {

    public static final String GUICE_INJECTOR_REF = "_guice_injector";
    private final Injector injector;

    /**
     * @param muleContext the mule context
     * @param injector    the guice injector
     * @throws NullPointerException if {@code muleContext} or {@code injector} params are null
     */
    public GuiceRegistry(final MuleContext muleContext, final Injector injector) throws NullPointerException {
        super("guice_dsl", muleContext);
        checkNotNull(muleContext, "muleContext");
        this.injector = checkNotNull(injector, "injector");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected RegistryLifecycleManager createLifecycleManager() {
        return new GuiceRegistryLifecycleManager(getRegistryId(), this, muleContext);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doInitialise() throws InitialisationException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doDispose() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T lookupObject(final Class<T> type) throws RegistrationException {
        return injector.getInstance(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T lookupObject(final String key) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Collection<T> lookupObjects(final Class<T> type) {
        return emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Map<String, T> lookupByType(final Class<T> type) {
        return emptyMap();
    }

    /**
     * Operation not supported, generates a {@link UnsupportedOperationException} if executed.
     */
    @Override
    public void registerObject(final String key, final Object value) throws RegistrationException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Operation not supported");
    }

    /**
     * Operation not supported, generates a {@link UnsupportedOperationException} if executed.
     */
    @Override
    public void registerObject(final String key, final Object value, final Object metadata) throws RegistrationException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Operation not supported");
    }

    /**
     * Operation not supported, generates a {@link UnsupportedOperationException} if executed.
     */
    @Override
    public void registerObjects(final Map<String, Object> objects) throws RegistrationException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Operation not supported");
    }

    /**
     * Operation not supported, generates a {@link UnsupportedOperationException} if executed.
     */
    @Override
    public void unregisterObject(final String key) throws RegistrationException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Operation not supported");
    }

    /**
     * Operation not supported, generates a {@link UnsupportedOperationException} if executed.
     */
    @Override
    public void unregisterObject(final String key, final Object metadata) throws RegistrationException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Operation not supported");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isReadOnly() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRemote() {
        return false;
    }
}