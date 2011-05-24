/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal.util;

import org.mule.api.model.EntryPointResolverSet;
import org.mule.model.resolvers.DefaultEntryPointResolverSet;
import org.mule.model.resolvers.ReflectionEntryPointResolver;

public final class EntryPointResolverSetUtil {
    private EntryPointResolverSetUtil() {
    }

    public static EntryPointResolverSet createDefaultResolverSet() {
        final EntryPointResolverSet resolverSet = new DefaultEntryPointResolverSet();
        resolverSet.addEntryPointResolver(new ReflectionEntryPointResolver());
        return resolverSet;
    }
}
