/*
 * ---------------------------------------------------------------------------
 *  Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 */

package org.mule.config.dsl;

/**
 * A module contributes configuration information, typically flow definitions,
 * which will be used to create a {@link org.mule.api.MuleContext}. A typical
 * Mule DSL configuration is composed of a set of {@code Module}s that aggregates
 * integration related configs.
 * <p/>
 * <p>Your Module classes can use a more streamlined syntax by extending
 * {@link AbstractModule} rather than implementing this interface directly.
 *
 * @author porcelli
 * @see <a href="https://github.com/mulesoft/mule-dsl/wiki/Module">More about Mule DSL Modules</a>
 * @see <a href="http://www.mulesoft.org/documentation/display/MULE3USER/Using+Flows+for+Service+Orchestration">More about flows</a>
 */
public interface Module {

    /**
     * Contributes flow definitions and other configurations for this module to {@code catalog}.
     *
     * @param catalog Catalog responsible to collect configuration defined by {@code Module}s.
     * @throws NullPointerException  if {@code catalog} is null
     * @throws IllegalStateException if method executed more than once
     */
    void configure(final Catalog catalog) throws NullPointerException, IllegalStateException;
}
