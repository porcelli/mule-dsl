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
import org.mule.config.dsl.module.mongo.CreateIndexMessageProcessorDefinition;
import org.mule.module.mongo.MongoCloudConnector;
import org.mule.module.mongo.api.IndexOrder;
import org.mule.module.mongo.config.CreateIndexMessageProcessor;

public class CreateIndexMessageProcessorBuilder implements CreateIndexMessageProcessorDefinition, Builder<CreateIndexMessageProcessor> {

    private final MongoCloudConnector object;

    //Required
    private String collection = null;
    private String field = null;

    private ExpressionEvaluatorBuilder collectionExp = null;
    private ExpressionEvaluatorBuilder fieldExp = null;


    private IndexOrder order = IndexOrder.ASC;

    private ExpressionEvaluatorBuilder orderExp = null;

    public CreateIndexMessageProcessorBuilder(MongoCloudConnector object, String collection, String field) {
        this.object = object;
        this.collection = collection;
        this.field = field;
    }

    public CreateIndexMessageProcessorBuilder(MongoCloudConnector object, ExpressionEvaluatorBuilder collectionExp, String field) {
        this.object = object;
        this.collectionExp = collectionExp;
        this.field = field;
    }

    public CreateIndexMessageProcessorBuilder(MongoCloudConnector object, String collection, ExpressionEvaluatorBuilder fieldExp) {
        this.object = object;
        this.collection = collection;
        this.fieldExp = fieldExp;
    }

    public CreateIndexMessageProcessorBuilder(MongoCloudConnector object, ExpressionEvaluatorBuilder collectionExp, ExpressionEvaluatorBuilder fieldExp) {
        this.object = object;
        this.collectionExp = collectionExp;
        this.fieldExp = fieldExp;
    }

    @Override
    public CreateIndexMessageProcessorDefinition withOrder(IndexOrder order) {
        this.order = order;
        return this;
    }

    @Override
    public CreateIndexMessageProcessorDefinition withOrder(ExpressionEvaluatorBuilder orderExp) {
        this.orderExp = orderExp;
        return this;
    }

    @Override
    public CreateIndexMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        CreateIndexMessageProcessor mp = new CreateIndexMessageProcessor();

        mp.setObject(object);

        if (collectionExp != null) {
            mp.setCollection(collectionExp);
        } else {
            mp.setCollection(collection);
        }

        if (fieldExp != null) {
            mp.setField(fieldExp);
        } else {
            mp.setField(field);
        }

        if (orderExp != null) {
            mp.setOrder(orderExp);
        } else {
            mp.setOrder(order);
        }

        return mp;

    }
}
