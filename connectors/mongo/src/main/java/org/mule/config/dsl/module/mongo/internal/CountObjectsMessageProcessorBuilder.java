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
import org.mule.config.dsl.module.mongo.CountObjectsMessageProcessorDefinition;
import org.mule.module.mongo.MongoCloudConnector;
import org.mule.module.mongo.config.CountObjectsMessageProcessor;

import java.util.Map;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class CountObjectsMessageProcessorBuilder implements CountObjectsMessageProcessorDefinition, Builder<CountObjectsMessageProcessor> {

    private MongoCloudConnector object;

    private String collection = null;

    private ExpressionEvaluatorDefinition collectionExp = null;

    private Object query = null;
    private Map<String, Object> queryAttributes = null;

    private ExpressionEvaluatorDefinition queryExp = null;
    private ExpressionEvaluatorDefinition queryAttributesExp = null;

    public CountObjectsMessageProcessorBuilder(MongoCloudConnector object, String collection) {
        this.object = object;
        this.collection = collection;
    }

    public CountObjectsMessageProcessorBuilder(MongoCloudConnector object, ExpressionEvaluatorDefinition collectionExp) {
        this.object = object;
        this.collectionExp = collectionExp;
    }

    @Override
    public CountObjectsMessageProcessorDefinition withQuery(Object query) {
        this.query = query;
        return this;
    }

    @Override
    public CountObjectsMessageProcessorDefinition withQuery(ExpressionEvaluatorDefinition queryExp) {
        this.queryExp = queryExp;
        return this;
    }

    @Override
    public CountObjectsMessageProcessorDefinition withQueryAttributes(Map<String, Object> queryAttributes) {
        this.queryAttributes = queryAttributes;
        return this;
    }

    @Override
    public CountObjectsMessageProcessorDefinition withQueryAttributes(ExpressionEvaluatorDefinition queryAttributesExp) {
        this.queryAttributesExp = queryAttributesExp;
        return this;
    }

    @Override
    public CountObjectsMessageProcessor build(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

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
