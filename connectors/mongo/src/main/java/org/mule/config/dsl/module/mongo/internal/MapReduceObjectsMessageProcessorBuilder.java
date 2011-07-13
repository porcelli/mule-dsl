/*
 * ---------------------------------------------------------------------------
 *  Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 */

package org.mule.config.dsl.module.mongo.internal;

import org.mule.api.MuleContext;
import org.mule.config.dsl.ConfigurationException;
import org.mule.config.dsl.ExpressionEvaluatorDefinition;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.config.dsl.internal.Builder;
import org.mule.config.dsl.module.mongo.MapReduceObjectsMessageProcessorDefinition;
import org.mule.module.mongo.MongoCloudConnector;
import org.mule.module.mongo.config.MapReduceObjectsMessageProcessor;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class MapReduceObjectsMessageProcessorBuilder implements MapReduceObjectsMessageProcessorDefinition, Builder<MapReduceObjectsMessageProcessor> {

    private MongoCloudConnector object;

    private String collection = null;
    private String mapFunction = null;
    private String reduceFunction = null;

    private ExpressionEvaluatorDefinition collectionExp = null;
    private ExpressionEvaluatorDefinition mapFunctionExp = null;
    private ExpressionEvaluatorDefinition reduceFunctionExp = null;

    private String outputCollection = null;
    private ExpressionEvaluatorDefinition outputCollectionExp = null;

    public MapReduceObjectsMessageProcessorBuilder(MongoCloudConnector object, String collection, String mapFunction, String reduceFunction) {
        this.object = object;
        this.collection = collection;
        this.mapFunction = mapFunction;
        this.reduceFunction = reduceFunction;
    }

    public MapReduceObjectsMessageProcessorBuilder(MongoCloudConnector object, ExpressionEvaluatorDefinition collectionExp, String mapFunction, String reduceFunction) {
        this.object = object;
        this.collectionExp = collectionExp;
        this.mapFunction = mapFunction;
        this.reduceFunction = reduceFunction;
    }

    public MapReduceObjectsMessageProcessorBuilder(MongoCloudConnector object, String collection, ExpressionEvaluatorDefinition mapFunctionExp, String reduceFunction) {
        this.object = object;
        this.collection = collection;
        this.mapFunctionExp = mapFunctionExp;
        this.reduceFunction = reduceFunction;
    }

    public MapReduceObjectsMessageProcessorBuilder(MongoCloudConnector object, String collection, String mapFunction, ExpressionEvaluatorDefinition reduceFunctionExp) {
        this.object = object;
        this.collection = collection;
        this.mapFunction = mapFunction;
        this.reduceFunctionExp = reduceFunctionExp;
    }

    public MapReduceObjectsMessageProcessorBuilder(MongoCloudConnector object, ExpressionEvaluatorDefinition collectionExp, ExpressionEvaluatorDefinition mapFunctionExp, String reduceFunction) {
        this.object = object;
        this.collectionExp = collectionExp;
        this.mapFunctionExp = mapFunctionExp;
        this.reduceFunction = reduceFunction;
    }

    public MapReduceObjectsMessageProcessorBuilder(MongoCloudConnector object, ExpressionEvaluatorDefinition collectionExp, String mapFunction, ExpressionEvaluatorDefinition reduceFunctionExp) {
        this.object = object;
        this.collectionExp = collectionExp;
        this.mapFunction = mapFunction;
        this.reduceFunctionExp = reduceFunctionExp;
    }

    public MapReduceObjectsMessageProcessorBuilder(MongoCloudConnector object, String collection, ExpressionEvaluatorDefinition mapFunctionExp, ExpressionEvaluatorDefinition reduceFunctionExp) {
        this.object = object;
        this.collection = collection;
        this.mapFunctionExp = mapFunctionExp;
        this.reduceFunctionExp = reduceFunctionExp;
    }

    public MapReduceObjectsMessageProcessorBuilder(MongoCloudConnector object, ExpressionEvaluatorDefinition collectionExp, ExpressionEvaluatorDefinition mapFunctionExp, ExpressionEvaluatorDefinition reduceFunctionExp) {
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
    public MapReduceObjectsMessageProcessorDefinition withOutputCollection(ExpressionEvaluatorDefinition outputCollectionExp) {
        this.outputCollectionExp = outputCollectionExp;
        return this;
    }

    @Override
    public MapReduceObjectsMessageProcessor build(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        MapReduceObjectsMessageProcessor mp = new MapReduceObjectsMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(object);

        if (collectionExp != null) {
            mp.setCollection(collectionExp.toString(placeholder));
        } else {
            mp.setCollection(collection);
        }

        if (mapFunctionExp != null) {
            mp.setMapFunction(mapFunctionExp.toString(placeholder));
        } else {
            mp.setMapFunction(mapFunction);
        }

        if (reduceFunctionExp != null) {
            mp.setReduceFunction(reduceFunctionExp.toString(placeholder));
        } else {
            mp.setReduceFunction(reduceFunction);
        }

        if (outputCollectionExp != null) {
            mp.setOutputCollection(outputCollectionExp.toString(placeholder));
        } else {
            mp.setOutputCollection(outputCollection);
        }

        return mp;
    }
}
