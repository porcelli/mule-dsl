/*
 * ---------------------------------------------------------------------------
 *  Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 */

package org.mule.config.dsl.module.mongo.internal;

import com.google.inject.Injector;
import org.mule.api.MuleContext;
import org.mule.config.dsl.ExpressionEvaluatorBuilder;
import org.mule.config.dsl.internal.Builder;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.config.dsl.module.mongo.DropCollectionMessageProcessorDefinition;
import org.mule.module.mongo.MongoCloudConnector;
import org.mule.module.mongo.config.DropCollectionMessageProcessor;

public class DropCollectionMessageProcessorBuilder implements DropCollectionMessageProcessorDefinition, Builder<DropCollectionMessageProcessor> {

    private final MongoCloudConnector object;

    private String collection = null;

    private ExpressionEvaluatorBuilder collectionExp = null;

    public DropCollectionMessageProcessorBuilder(MongoCloudConnector object, String collection) {
        this.object = object;
        this.collection = collection;
    }

    public DropCollectionMessageProcessorBuilder(MongoCloudConnector object, ExpressionEvaluatorBuilder collectionExp) {
        this.object = object;
        this.collectionExp = collectionExp;
    }

    @Override
    public DropCollectionMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        DropCollectionMessageProcessor mp = new DropCollectionMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(object);

        if (collectionExp != null) {
            mp.setCollection(collectionExp.toString(placeholder));
        } else {
            mp.setCollection(collection);
        }

        return mp;
    }
}
