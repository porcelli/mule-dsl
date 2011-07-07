/*
 * ---------------------------------------------------------------------------
 *  Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 */

package org.mule.config.dsl.module.git.internal;

import com.google.inject.Injector;
import org.mule.api.MuleContext;
import org.mule.config.dsl.ExpressionEvaluatorBuilder;
import org.mule.config.dsl.internal.Builder;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.config.dsl.module.git.CreateCollectionMessageProcessorDefinition;
import org.mule.module.mongo.MongoCloudConnector;
import org.mule.module.mongo.config.CreateCollectionMessageProcessor;

public class CreateCollectionMessageProcessorBuilder implements CreateCollectionMessageProcessorDefinition, Builder<CreateCollectionMessageProcessor> {

    private final MongoCloudConnector object;

    //Required
    private String collection = null;

    private ExpressionEvaluatorBuilder collectionExp = null;

    //Optional
    private boolean capped = false;
    private Integer maxObjects = null;
    private Integer size = null;

    private ExpressionEvaluatorBuilder cappedExp = null;
    private ExpressionEvaluatorBuilder maxObjectsExp = null;
    private ExpressionEvaluatorBuilder sizeExp = null;

    public CreateCollectionMessageProcessorBuilder(MongoCloudConnector object, String collection) {
        this.object = object;
        this.collection = collection;
    }

    public CreateCollectionMessageProcessorBuilder(MongoCloudConnector object, ExpressionEvaluatorBuilder collectionExp) {
        this.object = object;
        this.collectionExp = collectionExp;
    }

    @Override
    public CreateCollectionMessageProcessorDefinition withCapped(boolean capped) {
        this.capped = capped;
        return this;
    }

    @Override
    public CreateCollectionMessageProcessorDefinition withCapped(ExpressionEvaluatorBuilder cappedExp) {
        this.cappedExp = cappedExp;
        return this;
    }

    @Override
    public CreateCollectionMessageProcessorDefinition withMaxObjects(Integer maxObjects) {
        this.maxObjects = maxObjects;
        return this;
    }

    @Override
    public CreateCollectionMessageProcessorDefinition withMaxObjects(ExpressionEvaluatorBuilder maxObjectsExp) {
        this.maxObjectsExp = maxObjectsExp;
        return this;
    }

    @Override
    public CreateCollectionMessageProcessorDefinition withSize(Integer size) {
        this.size = size;
        return this;
    }

    @Override
    public CreateCollectionMessageProcessorDefinition withSize(ExpressionEvaluatorBuilder sizeExp) {
        this.sizeExp = sizeExp;
        return this;
    }

    @Override
    public CreateCollectionMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        CreateCollectionMessageProcessor mp = new CreateCollectionMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(object);

        if (collectionExp != null) {
            mp.setCollection(collectionExp.toString(placeholder));
        } else {
            mp.setCollection(collection);
        }

        if (cappedExp != null) {
            mp.setCapped(cappedExp.toString(placeholder));
        } else {
            mp.setCapped(capped);
        }

        if (maxObjectsExp != null) {
            mp.setMaxObjects(maxObjectsExp.toString(placeholder));
        } else {
            mp.setMaxObjects(maxObjects);
        }

        if (sizeExp != null) {
            mp.setSize(sizeExp.toString(placeholder));
        } else {
            mp.setSize(size);
        }

        return mp;
    }
}
