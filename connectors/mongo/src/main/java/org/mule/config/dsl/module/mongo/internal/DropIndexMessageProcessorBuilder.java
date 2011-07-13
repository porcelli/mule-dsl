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
import org.mule.config.dsl.module.mongo.DropIndexMessageProcessorDefinition;
import org.mule.module.mongo.MongoCloudConnector;
import org.mule.module.mongo.config.DropIndexMessageProcessor;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class DropIndexMessageProcessorBuilder implements DropIndexMessageProcessorDefinition, Builder<DropIndexMessageProcessor> {

    private final MongoCloudConnector object;

    //Required
    private String collection = null;
    private String index = null;

    private ExpressionEvaluatorDefinition collectionExp = null;
    private ExpressionEvaluatorDefinition indexExp = null;

    public DropIndexMessageProcessorBuilder(MongoCloudConnector object, String collection, String index) {
        this.object = object;
        this.collection = collection;
        this.index = index;
    }

    public DropIndexMessageProcessorBuilder(MongoCloudConnector object, ExpressionEvaluatorDefinition collectionExp, String index) {
        this.object = object;
        this.collectionExp = collectionExp;
        this.index = index;
    }

    public DropIndexMessageProcessorBuilder(MongoCloudConnector object, String collection, ExpressionEvaluatorDefinition indexExp) {
        this.object = object;
        this.collection = collection;
        this.indexExp = indexExp;
    }

    public DropIndexMessageProcessorBuilder(MongoCloudConnector object, ExpressionEvaluatorDefinition collectionExp, ExpressionEvaluatorDefinition indexExp) {
        this.object = object;
        this.collectionExp = collectionExp;
        this.indexExp = indexExp;
    }

    @Override
    public DropIndexMessageProcessor build(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        DropIndexMessageProcessor mp = new DropIndexMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(object);

        if (collectionExp != null) {
            mp.setCollection(collectionExp.toString(placeholder));
        } else {
            mp.setCollection(collection);
        }

        if (indexExp != null) {
            mp.setIndex(indexExp.toString(placeholder));
        } else {
            mp.setIndex(index);
        }

        return mp;

    }
}
