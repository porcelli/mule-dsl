/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import org.mule.api.MuleContext;
import org.mule.api.lifecycle.*;
import org.mule.api.registry.Registry;
import org.mule.lifecycle.EmptyLifecycleCallback;
import org.mule.lifecycle.RegistryLifecycleManager;
import org.mule.lifecycle.phases.ContainerManagedLifecyclePhase;
import org.mule.lifecycle.phases.MuleContextStartPhase;
import org.mule.lifecycle.phases.MuleContextStopPhase;
import org.mule.lifecycle.phases.NotInLifecyclePhase;
import org.mule.registry.AbstractRegistryBroker;

import java.util.Map;

public class GuiceRegistryLifecycleManager extends RegistryLifecycleManager {

    public GuiceRegistryLifecycleManager(String id, Registry object, MuleContext muleContext) {
        super(id, object, muleContext);
    }

    public GuiceRegistryLifecycleManager(String id, Registry object, Map<String, LifecyclePhase> phases) {
        super(id, object, phases);
    }

    protected void registerPhases() {
        registerPhase(NotInLifecyclePhase.PHASE_NAME, NOT_IN_LIFECYCLE_PHASE,
                new EmptyLifecycleCallback<AbstractRegistryBroker>());
        registerPhase(Initialisable.PHASE_NAME, new GuiceContextInitialisePhase());
        registerPhase(Startable.PHASE_NAME, new MuleContextStartPhase(),
                new EmptyLifecycleCallback<AbstractRegistryBroker>());
        registerPhase(Stoppable.PHASE_NAME, new MuleContextStopPhase(),
                new EmptyLifecycleCallback<AbstractRegistryBroker>());
        registerPhase(Disposable.PHASE_NAME, new GuiceContextDisposePhase());

    }

    @Override
    protected void registerTransitions() {
        super.registerTransitions();
        addDirectTransition(NotInLifecyclePhase.PHASE_NAME, Startable.PHASE_NAME);
    }

    class GuiceContextInitialisePhase extends ContainerManagedLifecyclePhase {
        public GuiceContextInitialisePhase() {
            super(Initialisable.PHASE_NAME, Initialisable.class, Disposable.PHASE_NAME);
            registerSupportedPhase(NotInLifecyclePhase.PHASE_NAME);
        }

        @Override
        public void applyLifecycle(Object o) throws LifecycleException {
        }
    }

    class GuiceContextDisposePhase extends ContainerManagedLifecyclePhase {
        public GuiceContextDisposePhase() {
            super(Disposable.PHASE_NAME, Disposable.class, Initialisable.PHASE_NAME);
            registerSupportedPhase(NotInLifecyclePhase.PHASE_NAME);
            // You can dispose from all phases
            registerSupportedPhase(LifecyclePhase.ALL_PHASES);
        }

        @Override
        public void applyLifecycle(Object o) throws LifecycleException {
            ((GuiceRegistry) o).doDispose();
        }
    }
}
