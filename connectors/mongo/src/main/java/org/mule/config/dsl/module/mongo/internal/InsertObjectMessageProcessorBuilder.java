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
import org.mule.config.dsl.module.mongo.InsertObjectMessageProcessorDefinition;
import org.mule.module.mongo.MongoCloudConnector;
import org.mule.module.mongo.api.WriteConcern;
import org.mule.module.mongo.config.InsertObjectMessageProcessor;

import java.util.Map;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class InsertObjectMessageProcessorBuilder implements InsertObjectMessageProcessorDefinition, Builder<InsertObjectMessageProcessor> {

    private MongoCloudConnector object;

    private String collection = null;

    private ExpressionEvaluatorDefinition collectionExp = null;


    private Object element = null;
    private Map<String, Object> elementAttributes = null;
    private WriteConcern writeConcern = WriteConcern.DATABASE_DEFAULT;

    private ExpressionEvaluatorDefinition elementExp = null;
    private ExpressionEvaluatorDefinition elementAttributesExp = null;
    private ExpressionEvaluatorDefinition writeConcernExp = null;

    public InsertObjectMessageProcessorBuilder(MongoCloudConnector object, String collection) {
        this.object = object;
        this.collection = collection;
    }

    public InsertObjectMessageProcessorBuilder(MongoCloudConnector object, ExpressionEvaluatorDefinition collectionExp) {
        this.object = object;
        this.collectionExp = collectionExp;
    }

    @Override
    public InsertObjectMessageProcessorDefinition withElement(Object element) {
        this.element = element;
        return this;
    }

    @Override
    public InsertObjectMessageProcessorDefinition withElement(ExpressionEvaluatorDefinition elementExp) {
        this.elementExp = elementExp;
        return this;
    }

    @Override
    public InsertObjectMessageProcessorDefinition withElementAttributes(Map<String, Object> elementAttributes) {
        this.elementAttributes = elementAttributes;
        return this;
    }

    @Override
    public InsertObjectMessageProcessorDefinition withElementAttributes(ExpressionEvaluatorDefinition elementAttributesExp) {
        this.elementAttributesExp = elementAttributesExp;
        return this;
    }

    @Override
    public InsertObjectMessageProcessorDefinition withWriteConcern(WriteConcern writeConcern) {
        this.writeConcern = writeConcern;
        return this;
    }

    @Override
    public InsertObjectMessageProcessorDefinition withWriteConcern(ExpressionEvaluatorDefinition writeConcernExp) {
        this.writeConcernExp = writeConcernExp;
        return this;
    }

    @Override
    public InsertObjectMessageProcessor build(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        InsertObjectMessageProcessor mp = new InsertObjectMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(object);

        if (collectionExp != null) {
            mp.setCollection(collectionExp.toString(placeholder));
        } else {
            mp.setCollection(collection);
        }

        if (elementExp != null) {
            mp.setElement(elementExp.toString(placeholder));
        } else {
            mp.setElement(element);
        }

        if (elementAttributesExp != null) {
            mp.setElementAttributes(elementAttributesExp.toString(placeholder));
        } else {
            mp.setElementAttributes(elementAttributes);
        }

        if (writeConcernExp != null) {
            mp.setWriteConcern(writeConcernExp.toString(placeholder));
        } else {
            mp.setWriteConcern(writeConcern);
        }

        return mp;
    }
}
