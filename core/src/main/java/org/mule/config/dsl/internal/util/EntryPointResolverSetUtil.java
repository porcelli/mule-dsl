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

/**
 * Utility class that provides a generic entrypoint resolver set and expose it thru a static method.
 *
 * @author porcelli
 * @see EntryPointResolverSet
 * @see org.mule.api.model.EntryPointResolver
 */
public final class EntryPointResolverSetUtil {

    static final EntryPointResolverSet resolverSet = new DefaultEntryPointResolverSet();

    static {
        resolverSet.addEntryPointResolver(new ReflectionEntryPointResolver());
    }

    private EntryPointResolverSetUtil() {
    }

    /**
     * Returns the default entrypoint resolver set, that is composed by {@link ReflectionEntryPointResolver}.
     *
     * @return the default entrypoint resolver set
     * @see EntryPointResolverSet
     * @see ReflectionEntryPointResolver
     * @see org.mule.api.model.EntryPointResolver
     */
    public static EntryPointResolverSet getDefaultResolverSet() {
        return resolverSet;
    }
}
