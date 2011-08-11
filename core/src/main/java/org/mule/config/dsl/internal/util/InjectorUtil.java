/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal.util;

import com.google.inject.Injector;
import com.google.inject.Scopes;

/**
 * Utility class that helps extract information from guice's {@link Injector}.
 *
 * @author porcelli
 */
public final class InjectorUtil {

    private InjectorUtil() {
    }

    /**
     * Checks if the given type has a registered provider.
     *
     * @param injector the guice injector
     * @param clazz    the clazz type to look for provider
     * @return true if has a registered provider, otherwise false
     */
    public static boolean hasProvider(final Injector injector, final Class<?> clazz) {
        if (injector == null || clazz == null) {
            return false;
        }
        try {
            injector.getProvider(clazz);
            return true;
        } catch (final Exception ex) {
        }
        return false;
    }

    /**
     * Checks if the given type is binded as singleton.
     *
     * @param injector the guice injector
     * @param clazz    the clazz type to look for
     * @return true if its is registered as singleton, otherwise false
     */
    public static boolean isSingleton(final Injector injector, final Class<?> clazz) {
        if (injector == null || clazz == null && injector.getBinding(clazz) != null) {
            return false;
        }
        try {
            return Scopes.isSingleton(injector.getBinding(clazz));
        } catch (final Exception ex) {
        }
        return false;
    }

}
