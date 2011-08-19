/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.mule.api.transport.Connector;

/**
 * Builder interface to configure Global Connectors.
 *
 * @author porcelli
 */
public interface ConnectorBuilder extends Builder {

    /**
     * Defines and creates the the global connector based on given type
     *
     * @param clazz the connector type
     * @return connector instance to be configured
     * @throws NullPointerException   if {@code clazz} param is null
     * @throws ConfigurationException if {@code clazz} param is an abstract class or can't be created
     * @see AbstractModule#connector()
     * @see org.mule.config.dsl.AbstractModule#connector(String)
     * @see org.mule.config.dsl.Catalog#newConnector(String)
     * @see org.mule.api.transport.Connector
     * @see Builder
     */
    <C extends Connector> C with(Class<C> clazz) throws NullPointerException, ConfigurationException;
}
