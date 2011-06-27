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
import org.mule.config.dsl.module.mongo.MapReduceObjectsMessageProcessorDefinition;
import org.mule.module.mongo.MongoCloudConnector;
import org.mule.module.mongo.config.MapReduceObjectsMessageProcessor;

public class MapReduceObjectsMessageProcessorBuilder implements MapReduceObjectsMessageProcessorDefinition, Builder<MapReduceObjectsMessageProcessor> {

    private MongoCloudConnector object;

    private String collection = null;
    private String mapFunction = null;
    private String reduceFunction = null;

    private ExpressionEvaluatorBuilder collectionExp = null;
    private ExpressionEvaluatorBuilder mapFunctionExp = null;
    private ExpressionEvaluatorBuilder reduceFunctionExp = null;

    private String outputCollection = null;
    private ExpressionEvaluatorBuilder outputCollectionExp = null;

    public MapReduceObjectsMessageProcessorBuilder(MongoCloudConnector object, String collection, String mapFunction, String reduceFunction) {
        this.object = object;
        this.collection = collection;
        this.mapFunction = mapFunction;
        this.reduceFunction = reduceFunction;
    }

    public MapReduceObjectsMessageProcessorBuilder(MongoCloudConnector object, ExpressionEvaluatorBuilder collectionExp, String mapFunction, String reduceFunction) {
        this.object = object;
        this.collectionExp = collectionExp;
        this.mapFunction = mapFunction;
        this.reduceFunction = reduceFunction;
    }

    public MapReduceObjectsMessageProcessorBuilder(MongoCloudConnector object, String collection, ExpressionEvaluatorBuilder mapFunctionExp, String reduceFunction) {
        this.object = object;
        this.collection = collection;
        this.mapFunctionExp = mapFunctionExp;
        this.reduceFunction = reduceFunction;
    }

    public MapReduceObjectsMessageProcessorBuilder(MongoCloudConnector object, String collection, String mapFunction, ExpressionEvaluatorBuilder reduceFunctionExp) {
        this.object = object;
        this.collection = collection;
        this.mapFunction = mapFunction;
        this.reduceFunctionExp = reduceFunctionExp;
    }

    public MapReduceObjectsMessageProcessorBuilder(MongoCloudConnector object, ExpressionEvaluatorBuilder collectionExp, ExpressionEvaluatorBuilder mapFunctionExp, String reduceFunction) {
        this.object = object;
        this.collectionExp = collectionExp;
        this.mapFunctionExp = mapFunctionExp;
        this.reduceFunction = reduceFunction;
    }

    public MapReduceObjectsMessageProcessorBuilder(MongoCloudConnector object, ExpressionEvaluatorBuilder collectionExp, String mapFunction, ExpressionEvaluatorBuilder reduceFunctionExp) {
        this.object = object;
        this.collectionExp = collectionExp;
        this.mapFunction = mapFunction;
        this.reduceFunctionExp = reduceFunctionExp;
    }

    public MapReduceObjectsMessageProcessorBuilder(MongoCloudConnector object, String collection, ExpressionEvaluatorBuilder mapFunctionExp, ExpressionEvaluatorBuilder reduceFunctionExp) {
        this.object = object;
        this.collection = collection;
        this.mapFunctionExp = mapFunctionExp;
        this.reduceFunctionExp = reduceFunctionExp;
    }

    public MapReduceObjectsMessageProcessorBuilder(MongoCloudConnector object, ExpressionEvaluatorBuilder collectionExp, ExpressionEvaluatorBuilder mapFunctionExp, ExpressionEvaluatorBuilder reduceFunctionExp) {
        this.object = object;
        this.collectionExp = collectionExp;
        this.mapFunctionExp = mapFunctionExp;
        this.reduceFunctionExp = reduceFunctionExp;
    }

    @Override
    public MapReduceObjectsMessageProcessorDefinition withOutputCollection(String outputCollection) {
        this.outputCollection = outputCollection;
        return this;
    }

    @Override
    public MapReduceObjectsMessageProcessorDefinition withOutputCollection(ExpressionEvaluatorBuilder outputCollectionExp) {
        this.outputCollectionExp = outputCollectionExp;
        return this;
    }

    @Override
    public MapReduceObjectsMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        MapReduceObjectsMessageProcessor mp = new MapReduceObjectsMessageProcessor();

        mp.setObject(object);

        if (collectionExp != null) {
            mp.setCollection(collectionExp);
        } else {
            mp.setCollection(collection);
        }

        if (mapFunctionExp != null) {
            mp.setMapFunction(mapFunctionExp);
        } else {
            mp.setMapFunction(mapFunction);
        }

        if (reduceFunctionExp != null) {
            mp.setReduceFunction(reduceFunctionExp);
        } else {
            mp.setReduceFunction(reduceFunction);
        }

        if (outputCollectionExp != null) {
            mp.setOutputCollection(outputCollectionExp);
        } else {
            mp.setOutputCollection(outputCollection);
        }

        return mp;
    }
}
