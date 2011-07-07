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
import org.mule.config.dsl.module.mongo.CountObjectsMessageProcessorDefinition;
import org.mule.module.mongo.MongoCloudConnector;
import org.mule.module.mongo.config.CountObjectsMessageProcessor;

import java.util.Map;

public class CountObjectsMessageProcessorBuilder implements CountObjectsMessageProcessorDefinition, Builder<CountObjectsMessageProcessor> {

    private MongoCloudConnector object;

    private String collection = null;

    private ExpressionEvaluatorBuilder collectionExp = null;

    private Object query = null;
    private Map<String, Object> queryAttributes = null;

    private ExpressionEvaluatorBuilder queryExp = null;
    private ExpressionEvaluatorBuilder queryAttributesExp = null;

    public CountObjectsMessageProcessorBuilder(MongoCloudConnector object, String collection) {
        this.object = object;
        this.collection = collection;
    }

    public CountObjectsMessageProcessorBuilder(MongoCloudConnector object, ExpressionEvaluatorBuilder collectionExp) {
        this.object = object;
        this.collectionExp = collectionExp;
    }

    @Override
    public CountObjectsMessageProcessorDefinition withQuery(Object query) {
        this.query = query;
        return this;
    }

    @Override
    public CountObjectsMessageProcessorDefinition withQuery(ExpressionEvaluatorBuilder queryExp) {
        this.queryExp = queryExp;
        return this;
    }

    @Override
    public CountObjectsMessageProcessorDefinition withQueryAttributes(Map<String, Object> queryAttributes) {
        this.queryAttributes = queryAttributes;
        return this;
    }

    @Override
    public CountObjectsMessageProcessorDefinition withQueryAttributes(ExpressionEvaluatorBuilder queryAttributesExp) {
        this.queryAttributesExp = queryAttributesExp;
        return this;
    }

    @Override
    public CountObjectsMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        CountObjectsMessageProcessor mp = new CountObjectsMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(object);

        if (collectionExp != null) {
            mp.setCollection(collectionExp.toString(placeholder));
        } else {
            mp.setCollection(collection);
        }

        if (queryExp != null) {
            mp.setQuery(queryExp.toString(placeholder));
        } else {
            mp.setQuery(query);
        }

        if (queryAttributesExp != null) {
            mp.setQueryAttributes(queryAttributesExp.toString(placeholder));
        } else {
            mp.setQueryAttributes(queryAttributes);
        }

        return mp;
    }
}
