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
import org.mule.config.dsl.module.mongo.FindOneObjectMessageProcessorDefinition;
import org.mule.module.mongo.MongoCloudConnector;
import org.mule.module.mongo.config.FindOneObjectMessageProcessor;

import java.util.List;
import java.util.Map;

public class FindOneObjectMessageProcessorBuilder implements FindOneObjectMessageProcessorDefinition, Builder<FindOneObjectMessageProcessor> {

    private MongoCloudConnector object;

    private String collection = null;

    private ExpressionEvaluatorBuilder collectionExp = null;

    private Object query = null;
    private Map<String, Object> queryAttributes = null;
    private List<String> fields = null;
    private Object fieldsRef = null;

    private ExpressionEvaluatorBuilder queryExp = null;
    private ExpressionEvaluatorBuilder queryAttributesExp = null;
    private ExpressionEvaluatorBuilder fieldsRefExp = null;
    private ExpressionEvaluatorBuilder fieldsExp = null;

    public FindOneObjectMessageProcessorBuilder(MongoCloudConnector object, String collection) {
        this.object = object;
        this.collection = collection;
    }

    public FindOneObjectMessageProcessorBuilder(MongoCloudConnector object, ExpressionEvaluatorBuilder collectionExp) {
        this.object = object;
        this.collectionExp = collectionExp;
    }

    @Override
    public FindOneObjectMessageProcessorDefinition withQuery(Object query) {
        this.query = query;
        return this;
    }

    @Override
    public FindOneObjectMessageProcessorDefinition withQuery(ExpressionEvaluatorBuilder queryExp) {
        this.queryExp = queryExp;
        return this;
    }

    @Override
    public FindOneObjectMessageProcessorDefinition withQueryAttributes(Map<String, Object> queryAttributes) {
        this.queryAttributes = queryAttributes;
        return this;
    }

    @Override
    public FindOneObjectMessageProcessorDefinition withQueryAttributes(ExpressionEvaluatorBuilder queryAttributesExp) {
        this.queryAttributesExp = queryAttributesExp;
        return this;
    }

    @Override
    public FindOneObjectMessageProcessorDefinition withFieldsRef(Object fieldsRef) {
        this.fieldsRef = fieldsRef;
        return this;
    }

    @Override
    public FindOneObjectMessageProcessorDefinition withFieldsRef(ExpressionEvaluatorBuilder fieldsRefExp) {
        this.fieldsRefExp = fieldsRefExp;
        return this;
    }

    @Override
    public FindOneObjectMessageProcessorDefinition withFields(List<String> fields) {
        this.fields = fields;
        return this;
    }

    @Override
    public FindOneObjectMessageProcessorDefinition withFields(ExpressionEvaluatorBuilder fieldsExp) {
        this.fieldsExp = fieldsExp;
        return this;
    }

    @Override
    public FindOneObjectMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        FindOneObjectMessageProcessor mp = new FindOneObjectMessageProcessor();

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
