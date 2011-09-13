/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import org.mule.api.MuleContext;
import org.mule.config.dsl.*;
import org.mule.transformer.simple.MessagePropertiesTransformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mule.config.dsl.util.Preconditions.checkNotEmpty;
import static org.mule.config.dsl.util.Preconditions.checkNotNull;

/**
 * Internal implementation of {@link org.mule.config.dsl.MessagePropertiesBuilder} interface that, based on its internal state,
 * builds a {@link MessagePropertiesTransformer}.
 *
 * @author porcelli
 * @see org.mule.config.dsl.PipelineBuilder#messageProperties()
 */
public class MessagePropertiesBuilderImpl<P extends PipelineBuilder<P>> extends PipelineBuilderImpl<P> implements MessagePropertiesBuilder<P>, Builder<MessagePropertiesTransformer> {

    private final List<String> removeProperties = new ArrayList<String>();
    private final Map<String, Object> putProperties = new HashMap<String, Object>();
    private final Map<String, Object> renameProperties = new HashMap<String, Object>();

    /**
     * @param parentScope the parent scope, null is allowed
     */
    public MessagePropertiesBuilderImpl(final P parentScope) {
        super(parentScope);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessagePropertiesBuilder<P> put(String key, ExpressionEvaluatorDefinition value) throws IllegalArgumentException, NullPointerException {
        checkNotEmpty(key, "key");
        checkNotNull(value, "value");

        putProperties.put(key, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessagePropertiesBuilder<P> put(String key, Object value) throws IllegalArgumentException, NullPointerException {
        checkNotEmpty(key, "key");
        checkNotNull(value, "value");

        putProperties.put(key, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessagePropertiesBuilder<P> rename(String key, String newKey) throws IllegalArgumentException {
        checkNotEmpty(key, "key");
        checkNotEmpty(newKey, "newKey");

        renameProperties.put(key, newKey);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessagePropertiesBuilder<P> rename(String key, ExpressionEvaluatorDefinition newKey) throws IllegalArgumentException {
        checkNotEmpty(key, "key");
        checkNotNull(newKey, "newKey");

        renameProperties.put(key, newKey);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessagePropertiesBuilder<P> remove(String key) throws IllegalArgumentException {
        checkNotEmpty(key, "key");

        removeProperties.add(key);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P endMessageProperties() {
        return parentScope;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessagePropertiesTransformer build(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {

        MessagePropertiesTransformer mp = new MessagePropertiesTransformer();
        mp.setMuleContext(muleContext);
        mp.setOverwrite(true);
        mp.setDeleteProperties(removeProperties);

        final Map<String, Object> finalPutProperties = new HashMap<String, Object>();

        for (Map.Entry<String, Object> entry : putProperties.entrySet()) {
            final Object newValue;
            if (entry.getValue() instanceof ExpressionEvaluatorDefinition) {
                newValue = ((ExpressionEvaluatorDefinition) entry.getValue()).toString(placeholder);
            } else if (entry.getValue() instanceof String) {
                newValue = placeholder.replace((String) entry.getValue());
            } else {
                newValue = entry.getValue();
            }
            finalPutProperties.put(entry.getKey(), newValue);
        }
        mp.setAddProperties(finalPutProperties);

        final Map<String, String> finalRenameProperties = new HashMap<String, String>();

        for (Map.Entry<String, Object> entry : renameProperties.entrySet()) {
            final String newValue;
            if (entry.getValue() instanceof ExpressionEvaluatorDefinition) {
                newValue = ((ExpressionEvaluatorDefinition) entry.getValue()).toString(placeholder);
            } else if (entry.getValue() instanceof String) {
                newValue = placeholder.replace((String) entry.getValue());
            } else {
                newValue = null;
            }
            if (newValue != null) {
                finalRenameProperties.put(entry.getKey(), newValue);
            }
        }
        mp.setRenameProperties(finalRenameProperties);
        mp.setMuleContext(muleContext);

        return mp;
    }

}
