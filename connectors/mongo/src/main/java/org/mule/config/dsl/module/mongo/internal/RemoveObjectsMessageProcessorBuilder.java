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
import org.mule.config.dsl.module.mongo.RemoveObjectsMessageProcessorDefinition;
import org.mule.module.mongo.MongoCloudConnector;
import org.mule.module.mongo.api.WriteConcern;
import org.mule.module.mongo.config.RemoveObjectsMessageProcessor;

import java.util.Map;

public class RemoveObjectsMessageProcessorBuilder implements RemoveObjectsMessageProcessorDefinition, Builder<RemoveObjectsMessageProcessor> {

    private MongoCloudConnector object;

    private String collection = null;

    private ExpressionEvaluatorBuilder collectionExp = null;

    private Object query = null;
    private Map<String, Object> queryAttributes = null;
    private WriteConcern writeConcern = WriteConcern.DATABASE_DEFAULT;

    private ExpressionEvaluatorBuilder queryExp = null;
    private ExpressionEvaluatorBuilder queryAttributesExp = null;
    private ExpressionEvaluatorBuilder writeConcernExp = null;

    public RemoveObjectsMessageProcessorBuilder(MongoCloudConnector object, String collection) {
        this.object = object;
        this.collection = collection;
    }

    public RemoveObjectsMessageProcessorBuilder(MongoCloudConnector object, ExpressionEvaluatorBuilder collectionExp) {
        this.object = object;
        this.collectionExp = collectionExp;
    }

    @Override
    public RemoveObjectsMessageProcessorDefinition withQuery(Object query) {
        this.query = query;
        return this;
    }

    @Override
    public RemoveObjectsMessageProcessorDefinition withQuery(ExpressionEvaluatorBuilder queryExp) {
        this.queryExp = queryExp;
        return this;
    }

    @Override
    public RemoveObjectsMessageProcessorDefinition withQueryAttributes(Map<String, Object> queryAttributes) {
        this.queryAttributes = queryAttributes;
        return this;
    }

    @Override
    public RemoveObjectsMessageProcessorDefinition withQueryAttributes(ExpressionEvaluatorBuilder queryAttributesExp) {
        this.queryAttributesExp = queryAttributesExp;
        return this;
    }

    @Override
    public RemoveObjectsMessageProcessorDefinition withWriteConcern(WriteConcern writeConcern) {
        this.writeConcern = writeConcern;
        return this;
    }

    @Override
    public RemoveObjectsMessageProcessorDefinition withWriteConcern(ExpressionEvaluatorBuilder writeConcernExp) {
        this.writeConcernExp = writeConcernExp;
        return this;
    }

    @Override
    public RemoveObjectsMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        RemoveObjectsMessageProcessor mp = new RemoveObjectsMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(object);

        if (collectionExp != null) {
            mp.setCollection(collectionExp);
        } else {
            mp.setCollection(collection);
        }

        if (queryExp != null) {
            mp.setQuery(queryExp);
        } else {
            mp.setQuery(query);
        }

        if (queryAttributesExp != null) {
            mp.setQueryAttributes(queryAttributesExp);
        } else {
            mp.setQueryAttributes(queryAttributes);
        }

        if (writeConcernExp != null) {
            mp.setWriteConcern(writeConcernExp);
        } else {
            mp.setWriteConcern(writeConcern);
        }

        return mp;
    }
}
