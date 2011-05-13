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

public final class InjectorUtil {

    private InjectorUtil() {
    }

    public static boolean hasProvider(Injector injector, Class<?> clazz) {
        try {
            injector.getProvider(clazz);
            return true;
        } catch (Exception ex) {
        }
        return false;
    }
}
