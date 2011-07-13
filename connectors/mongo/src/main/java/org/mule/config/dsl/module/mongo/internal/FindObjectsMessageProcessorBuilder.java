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
import org.mule.config.dsl.module.mongo.FindObjectsMessageProcessorDefinition;
import org.mule.module.mongo.MongoCloudConnector;
import org.mule.module.mongo.config.FindObjectsMessageProcessor;

import java.util.List;
import java.util.Map;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class FindObjectsMessageProcessorBuilder implements FindObjectsMessageProcessorDefinition, Builder<FindObjectsMessageProcessor> {

    private MongoCloudConnector object;

    private String collection = null;

    private ExpressionEvaluatorDefinition collectionExp = null;

    private Object query = null;
    private Map<String, Object> queryAttributes = null;
    private List<String> fields = null;
    private Object fieldsRef = null;

    private ExpressionEvaluatorDefinition queryExp = null;
    private ExpressionEvaluatorDefinition queryAttributesExp = null;
    private ExpressionEvaluatorDefinition fieldsRefExp = null;
    private ExpressionEvaluatorDefinition fieldsExp = null;

    public FindObjectsMessageProcessorBuilder(MongoCloudConnector object, String collection) {
        this.object = object;
        this.collection = collection;
    }

    public FindObjectsMessageProcessorBuilder(MongoCloudConnector object, ExpressionEvaluatorDefinition collectionExp) {
        this.object = object;
        this.collectionExp = collectionExp;
    }

    @Override
    public FindObjectsMessageProcessorDefinition withQuery(Object query) {
        this.query = query;
        return this;
    }

    @Override
    public FindObjectsMessageProcessorDefinition withQuery(ExpressionEvaluatorDefinition queryExp) {
        this.queryExp = queryExp;
        return this;
    }

    @Override
    public FindObjectsMessageProcessorDefinition withQueryAttributes(Map<String, Object> queryAttributes) {
        this.queryAttributes = queryAttributes;
        return this;
    }

    @Override
    public FindObjectsMessageProcessorDefinition withQueryAttributes(ExpressionEvaluatorDefinition queryAttributesExp) {
        this.queryAttributesExp = queryAttributesExp;
        return this;
    }

    @Override
    public FindObjectsMessageProcessorDefinition withFieldsRef(Object fieldsRef) {
        this.fieldsRef = fieldsRef;
        return this;
    }

    @Override
    public FindObjectsMessageProcessorDefinition withFieldsRef(ExpressionEvaluatorDefinition fieldsRefExp) {
        this.fieldsRefExp = fieldsRefExp;
        return this;
    }

    @Override
    public FindObjectsMessageProcessorDefinition withFields(List<String> fields) {
        this.fields = fields;
        return this;
    }

    @Override
    public FindObjectsMessageProcessorDefinition withFields(ExpressionEvaluatorDefinition fieldsExp) {
        this.fieldsExp = fieldsExp;
        return this;
    }

    @Override
    public FindObjectsMessageProcessor build(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        FindObjectsMessageProcessor mp = new FindObjectsMessageProcessor();

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

        if (fieldsRefExp != null) {
            mp.setFieldsRef(fieldsRefExp.toString(placeholder));
        } else {
            mp.setFieldsRef(fieldsRef);
        }

        if (fieldsExp != null) {
            mp.setFields(fieldsExp.toString(placeholder));
        } else {
            mp.setFields(fields);
        }

        return mp;
    }
}
