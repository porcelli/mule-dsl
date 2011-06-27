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
import org.mule.config.dsl.module.mongo.InsertObjectMessageProcessorDefinition;
import org.mule.module.mongo.MongoCloudConnector;
import org.mule.module.mongo.api.WriteConcern;
import org.mule.module.mongo.config.InsertObjectMessageProcessor;

import java.util.Map;

public class InsertObjectMessageProcessorBuilder implements InsertObjectMessageProcessorDefinition, Builder<InsertObjectMessageProcessor> {

    private MongoCloudConnector object;

    private String collection = null;

    private ExpressionEvaluatorBuilder collectionExp = null;


    private Object element = null;
    private Map<String, Object> elementAttributes = null;
    private WriteConcern writeConcern = WriteConcern.DATABASE_DEFAULT;

    private ExpressionEvaluatorBuilder elementExp = null;
    private ExpressionEvaluatorBuilder elementAttributesExp = null;
    private ExpressionEvaluatorBuilder writeConcernExp = null;

    public InsertObjectMessageProcessorBuilder(MongoCloudConnector object, String collection) {
        this.object = object;
        this.collection = collection;
    }

    public InsertObjectMessageProcessorBuilder(MongoCloudConnector object, ExpressionEvaluatorBuilder collectionExp) {
        this.object = object;
        this.collectionExp = collectionExp;
    }

    @Override
    public InsertObjectMessageProcessorDefinition withElement(Object element) {
        this.element = element;
        return this;
    }

    @Override
    public InsertObjectMessageProcessorDefinition withElement(ExpressionEvaluatorBuilder elementExp) {
        this.elementExp = elementExp;
        return this;
    }

    @Override
    public InsertObjectMessageProcessorDefinition withElementAttributes(Map<String, Object> elementAttributes) {
        this.elementAttributes = elementAttributes;
        return this;
    }

    @Override
    public InsertObjectMessageProcessorDefinition withElementAttributes(ExpressionEvaluatorBuilder elementAttributesExp) {
        this.elementAttributesExp = elementAttributesExp;
        return this;
    }

    @Override
    public InsertObjectMessageProcessorDefinition withWriteConcern(WriteConcern writeConcern) {
        this.writeConcern = writeConcern;
        return this;
    }

    @Override
    public InsertObjectMessageProcessorDefinition withWriteConcern(ExpressionEvaluatorBuilder writeConcernExp) {
        this.writeConcernExp = writeConcernExp;
        return this;
    }

    @Override
    public InsertObjectMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        InsertObjectMessageProcessor mp = new InsertObjectMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(object);

        if (collectionExp != null) {
            mp.setCollection(collectionExp);
        } else {
            mp.setCollection(collection);
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

        if (writeConcernExp != null) {
            mp.setWriteConcern(writeConcernExp);
        } else {
            mp.setWriteConcern(writeConcern);
        }

        return mp;
    }
}
