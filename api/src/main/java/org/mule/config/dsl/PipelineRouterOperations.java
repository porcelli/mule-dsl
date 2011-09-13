/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

/**
 * Interface that defines all router's related operations.
 *
 * @author porcelli
 */
public interface PipelineRouterOperations<P extends PipelineBuilder<P>> {

    /* routers */

    /**
     * Creates a broadcast router.
     * <p/>
     * <b>Important Note:</b> This is the DSL counterpart of {@code All} router of Mule XML configuration.
     *
     * @return the broadcast router builder
     * @see <a href="http://www.mulesoft.org/documentation/display/MULE3USER/Routing+Message+Processors#RoutingMessageProcessors-All">More about All router</a>
     */
    BroadcastRouterBuilder<P> broadcast();

    /**
     * Creates a choice router that can be composed by one or more predicates and an otherwise clause.
     *
     * @return the choice router builder
     * @see <a href="http://www.mulesoft.org/documentation/display/MULE3USER/Routing+Message+Processors#RoutingMessageProcessors-Choice">More about Choice Router</a>
     */
    ChoiceRouterBuilder<P> choice();

    /**
     * Creates an async router.
     *
     * @return the async router builder
     * @see <a href="http://www.mulesoft.org/documentation/display/MULE3USER/Routing+Message+Processors#RoutingMessageProcessors-Async">More about Async Router</a>
     */
    AsyncRouterBuilder<P> async();

    /**
     * Creates a first successful router.
     *
     * @return the first successful router builder
     * @see <a href="http://www.mulesoft.org/documentation/display/MULE3USER/Routing+Message+Processors#RoutingMessageProcessors-FirstSuccessful">More about First Successful Router</a>
     */
    FirstSuccessfulRouterBuilder<P> firstSuccessful();

    /**
     * Creates a round robin router.
     *
     * @return the round robin router builder
     * @see <a href="http://www.mulesoft.org/documentation/display/MULE3USER/Routing+Message+Processors#RoutingMessageProcessors-RoundRobin">More about Round Robin Router</a>
     */
    RoundRobinRouterBuilder<P> roundRobin();
}
