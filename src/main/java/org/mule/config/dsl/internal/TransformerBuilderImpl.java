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
import org.mule.api.transformer.Transformer;
import org.mule.transformer.simple.AutoTransformer;
import org.mule.transformer.types.SimpleDataType;

class TransformerBuilderImpl<T> implements Builder<Transformer> {

    private final Class<T> clazz;

    public TransformerBuilderImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Transformer build(Injector injector) {
        Transformer transformer = new AutoTransformer();
        transformer.setReturnDataType(new SimpleDataType<T>(clazz));
        return transformer;
    }
}
