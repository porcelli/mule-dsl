/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.mule.api.transformer.Transformer;

/**
 * Builder interface to configure Global Transformers.
 *
 * @author porcelli
 */
public interface TransformerBuilder extends Builder {

    /**
     * Sets global transformer to use the given transformer object.
     *
     * @param obj the transformer instance
     * @return the already configured transformer definition
     * @throws NullPointerException if {@code obj} param is null
     * @see org.mule.config.dsl.AbstractModule#transformer()
     * @see org.mule.config.dsl.AbstractModule#transformer(String)
     * @see org.mule.config.dsl.Catalog#newTransformer(String)
     * @see Transformer
     * @see Builder
     * @see Definition
     */
    <T extends Transformer> TransformerDefinition with(T obj) throws NullPointerException;

    /**
     * Sets global transformer to use the given transformer type.
     *
     * @param clazz the transformer type, Mule will instantiate an object at runtime
     * @return the already configured transformer definition
     * @throws NullPointerException if {@code clazz} param is null
     * @see org.mule.config.dsl.AbstractModule#transformer()
     * @see org.mule.config.dsl.AbstractModule#transformer(String)
     * @see org.mule.config.dsl.Catalog#newTransformer(String)
     * @see Transformer
     * @see Builder
     * @see Definition
     */
    <T extends Transformer> TransformerDefinition with(Class<T> clazz) throws NullPointerException;
}
