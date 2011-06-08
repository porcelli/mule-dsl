/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import com.google.inject.Injector;
import org.mule.api.MuleContext;
import org.mule.api.transformer.Transformer;
import org.mule.config.dsl.TransformerBuilder;
import org.mule.config.dsl.TransformerDefinition;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotEmpty;

public class TransformerBuilderImpl implements TransformerBuilder {

    private final String name;
    private TransformerDefinition<? extends Transformer> definition;

    public TransformerBuilderImpl(String name) {
        this.name = checkNotEmpty(name, "name");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public <T extends Transformer> TransformerDefinition<T> with(T obj) {
        this.definition = new TransformerDefinitionImpl<T>(name, obj);
        return (TransformerDefinition<T>) definition;
    }

    @Override
    public <T extends Transformer> TransformerDefinition<T> with(Class<T> clazz) {
        this.definition = new TransformerDefinitionImpl<T>(name, clazz);
        return (TransformerDefinition<T>) definition;
    }

    public Transformer build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        return ((TransformerDefinitionImpl) definition).build(muleContext, injector, placeholder);
    }
}
