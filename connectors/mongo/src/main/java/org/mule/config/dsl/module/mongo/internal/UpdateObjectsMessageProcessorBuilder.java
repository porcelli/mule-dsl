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
import org.mule.config.dsl.module.mongo.UpdateObjectsMessageProcessorDefinition;
import org.mule.module.mongo.MongoCloudConnector;
import org.mule.module.mongo.api.WriteConcern;
import org.mule.module.mongo.config.UpdateObjectsMessageProcessor;

import java.util.Map;

public class UpdateObjectsMessageProcessorBuilder implements UpdateObjectsMessageProcessorDefinition, Builder<UpdateObjectsMessageProcessor> {

    private MongoCloudConnector object;

    private String collection = null;

    private ExpressionEvaluatorBuilder collectionExp = null;

    private Object query = null;
    private Map<String, Object> queryAttributes = null;
    private Object element = null;
    private Map<String, Object> elementAttributes = null;
    private boolean upsert = false;
    private boolean multi = true;
    private WriteConcern writeConcern = WriteConcern.DATABASE_DEFAULT;

    private ExpressionEvaluatorBuilder queryExp = null;
    private ExpressionEvaluatorBuilder queryAttributesExp = null;
    private ExpressionEvaluatorBuilder elementExp = null;
    private ExpressionEvaluatorBuilder elementAttributesExp = null;
    private ExpressionEvaluatorBuilder upsertExp = null;
    private ExpressionEvaluatorBuilder multiExp = null;
    private ExpressionEvaluatorBuilder writeConcernExp = null;

    public UpdateObjectsMessageProcessorBuilder(MongoCloudConnector object, String collection) {
        this.object = object;
        this.collection = collection;
    }

    public UpdateObjectsMessageProcessorBuilder(MongoCloudConnector object, ExpressionEvaluatorBuilder collectionExp) {
        this.object = object;
        this.collectionExp = collectionExp;
    }

    @Override
    public UpdateObjectsMessageProcessorDefinition withQuery(Object query) {
        this.query = query;
        return this;
    }

    @Override
    public UpdateObjectsMessageProcessorDefinition withQuery(ExpressionEvaluatorBuilder queryExp) {
        this.queryExp = queryExp;
        return this;
    }

    @Override
    public UpdateObjectsMessageProcessorDefinition withQueryAttributes(Map<String, Object> queryAttributes) {
        this.queryAttributes = queryAttributes;
        return this;
    }

    @Override
    public UpdateObjectsMessageProcessorDefinition withQueryAttributes(ExpressionEvaluatorBuilder queryAttributesExp) {
        this.queryAttributesExp = queryAttributesExp;
        return this;
    }

    @Override
    public UpdateObjectsMessageProcessorDefinition withElement(Object element) {
        this.element = element;
        return this;
    }

    @Override
    public UpdateObjectsMessageProcessorDefinition withElement(ExpressionEvaluatorBuilder elementExp) {
        this.elementExp = elementExp;
        return this;
    }

    @Override
    public UpdateObjectsMessageProcessorDefinition withElementAttributes(Map<String, Object> elementAttributes) {
        this.elementAttributes = elementAttributes;
        return this;
    }

    @Override
    public UpdateObjectsMessageProcessorDefinition withElementAttributes(ExpressionEvaluatorBuilder elementAttributesExp) {
        this.elementAttributesExp = elementAttributesExp;
        return this;
    }

    @Override
    public UpdateObjectsMessageProcessorDefinition withUpsert(boolean upsert) {
        this.upsert = upsert;
        return this;
    }

    @Override
    public UpdateObjectsMessageProcessorDefinition withUpsert(ExpressionEvaluatorBuilder upsertExp) {
        this.upsertExp = upsertExp;
        return this;
    }

    @Override
    public UpdateObjectsMessageProcessorDefinition withMulti(boolean multi) {
        this.multi = multi;
        return this;
    }

    @Override
    public UpdateObjectsMessageProcessorDefinition withMulti(ExpressionEvaluatorBuilder multiExp) {
        this.multiExp = multiExp;
        return this;
    }

    @Override
    public UpdateObjectsMessageProcessorDefinition withWriteConcern(WriteConcern writeConcern) {
        this.writeConcern = writeConcern;
        return this;
    }

    @Override
    public UpdateObjectsMessageProcessorDefinition withWriteConcern(ExpressionEvaluatorBuilder writeConcernExp) {
        this.writeConcernExp = writeConcernExp;
        return this;
    }

    @Override
    public UpdateObjectsMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        UpdateObjectsMessageProcessor mp = new UpdateObjectsMessageProcessor();

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

        if (elementExp != null) {
            mp.setElement(elementExp);
        } else {
            mp.setElement(element);
        }

        if (elementAttributesExp != null) {
            mp.setElementAttributes(elementAttributesExp);
        } else {
            mp.setElementAttributes(elementAttributes);
        }

        if (upsertExp != null) {
            mp.setUpsert(upsertExp);
        } else {
            mp.setUpsert(upsert);
        }

        if (multiExp != null) {
            mp.setMulti(multiExp);
        } else {
            mp.setMulti(multi);
        }

        if (writeConcernExp != null) {
            mp.setWriteConcern(writeConcernExp);
        } else {
            mp.setWriteConcern(writeConcern);
        }

        return mp;
    }
}
