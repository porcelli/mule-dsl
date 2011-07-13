/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.module.mongo.internal;

import org.mule.api.MuleContext;
import org.mule.config.dsl.ConfigurationException;
import org.mule.config.dsl.ExpressionEvaluatorDefinition;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.config.dsl.internal.Builder;
import org.mule.config.dsl.module.mongo.ExistsCollectionMessageProcessorDefinition;
import org.mule.module.mongo.MongoCloudConnector;
import org.mule.module.mongo.config.ExistsCollectionMessageProcessor;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class ExistsCollectionMessageProcessorBuilder implements ExistsCollectionMessageProcessorDefinition, Builder<ExistsCollectionMessageProcessor> {

    private final MongoCloudConnector object;

    private String collection = null;

    private ExpressionEvaluatorDefinition collectionExp = null;

    public ExistsCollectionMessageProcessorBuilder(MongoCloudConnector object, String collection) {
        this.object = object;
        this.collection = collection;
    }

    public ExistsCollectionMessageProcessorBuilder(MongoCloudConnector object, ExpressionEvaluatorDefinition collectionExp) {
        this.object = object;
        this.collectionExp = collectionExp;
    }

    @Override
    public ExistsCollectionMessageProcessor build(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        ExistsCollectionMessageProcessor mp = new ExistsCollectionMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(object);

        if (collectionExp != null) {
            mp.setCollection(collectionExp.toString(placeholder));
        } else {
            mp.setCollection(collection);
        }

        return mp;
    }
}
