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
import org.mule.api.client.MuleClient;

/**
 * Exposes advanced configurations from {@link org.mule.config.dsl.Mule}.
 *
 * @author porcelli
 */
public class MuleAdvancedConfig {
    private final MuleContext muleContext;

    public MuleAdvancedConfig(final MuleContext muleContext) {
        this.muleContext = muleContext;
    }

    /**
     * Returns the mule context
     *
     * @return the mule context
     * @see MuleContext
     */
    public MuleContext muleContext() {
        return muleContext;
    }

    /**
     * Returns a mule client
     *
     * @return the mule client
     * @see MuleClient
     */
    public MuleClient muleClient() {
        return muleContext.getClient();
    }

}
