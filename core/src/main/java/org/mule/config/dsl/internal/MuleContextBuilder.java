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
import org.mule.config.dsl.ConfigurationException;
import org.mule.context.DefaultMuleContextFactory;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

/**
 * Utility class that, based on given dsl {@link org.mule.config.dsl.Catalog}
 * builds an instance of {@link MuleContext}.
 *
 * @author porcelli
 */
public class MuleContextBuilder implements org.mule.config.dsl.Builder {

    private final DefaultCatalogImpl catalog;
    private final Injector injector;

    /**
     * @param catalog  the dsl catalog
     * @param injector the guice injector, null is allowed
     * @throws NullPointerException if {@code catalog} param is null
     */
    public MuleContextBuilder(final DefaultCatalogImpl catalog, final Injector injector) throws NullPointerException {
        this.catalog = checkNotNull(catalog, "catalog");
        this.injector = injector;
    }

    /**
     * Creates a {@link MuleContext} and configure it based on dsl {@link org.mule.config.dsl.Catalog}.
     *
     * @return the mule context
     * @throws ConfigurationException if can't configure the mule context
     */
    public MuleContext build() throws ConfigurationException {
        try {
            final MuleContext muleContext = new DefaultMuleContextFactory().createMuleContext();

            return new MuleContextConfig(catalog, injector, muleContext).config();
        } catch (final Exception e) {
            throw new ConfigurationException("Failed to configure mule context.", e);
        }
    }
}
