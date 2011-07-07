/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.module.mongo.internal;

import com.google.inject.Injector;
import org.mule.api.MuleContext;
import org.mule.config.dsl.internal.Builder;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.config.dsl.module.mongo.ExistsCollectionMessageProcessorDefinition;
import org.mule.module.mongo.MongoCloudConnector;
import org.mule.module.mongo.config.ListCollectionsMessageProcessor;

public class ListCollectionsMessageProcessorBuilder implements ExistsCollectionMessageProcessorDefinition, Builder<ListCollectionsMessageProcessor> {

    private final MongoCloudConnector object;

    public ListCollectionsMessageProcessorBuilder(MongoCloudConnector object) {
        this.object = object;
    }

    @Override
    public ListCollectionsMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        ListCollectionsMessageProcessor mp = new ListCollectionsMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(object);

        return mp;
    }
}
