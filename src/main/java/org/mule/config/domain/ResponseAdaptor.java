/*
 * $Id: 20811 2011-03-30 14:53:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.domain;

public interface ResponseAdaptor extends Interceptor {

    void reponseAdaptor(Filter filter);

    void reponseAdaptor(Transformer transformer);

    void reponseAdaptor(Logger logger);

    void reponseAdaptor(Component processor);

    void reponseAdaptor(SecurityFilter filter);

    void reponseAdaptor(Splitter splitter);

    void reponseAdaptor(Aggregator aggregator);

    Pipeline pipeline();

    void addOutboundEndpoint(OutboundEndpoint outbound);

    void addRouter(Router router);


}
