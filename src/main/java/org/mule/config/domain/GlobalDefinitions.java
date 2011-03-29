/*
 * $Id: 20811 2011-03-29 12:19:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.domain;

public interface GlobalDefinitions {

    void define(Connector connector);

    void define(Endpoint endpoint);

    void define(InboundEndpoint inbound);

    void define(Logger logger);

    void define(Filter filter);

    void define(Splitter splitter);

    void define(Aggregator aggregator);

    void define(OutboundEndpoint outbound);

    void define(Component componet);

    void define(Transformer transformer);

    void define(Router router);
}
