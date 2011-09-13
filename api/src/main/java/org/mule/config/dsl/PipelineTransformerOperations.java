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
 * Interface that defines all transformer's related operations.
 *
 * @author porcelli
 */
public interface PipelineTransformerOperations<P extends PipelineBuilder<P>> {

    /* transform */

    /**
     * Transforms, if possible, the current payload to given type.
     *
     * @param clazz the type to be converted to
     * @return the parameterized builder
     * @throws NullPointerException if {@code clazz} param is null
     * @see org.mule.transformer.simple.AutoTransformer
     */
    <T> P transformTo(Class<T> clazz) throws NullPointerException;

    /**
     * Transforms current payload using the given expression.
     *
     * @param expr the transformer expression
     * @return the parameterized builder
     * @throws NullPointerException if {@code expr} param is null
     * @see org.mule.expression.transformers.ExpressionTransformer
     */
    <E extends ExpressionEvaluatorDefinition> P transform(E expr) throws NullPointerException;

    /**
     * Transforms the current payload using the given transformer object.
     *
     * @param obj the transformer object
     * @return the parameterized builder
     * @throws NullPointerException if {@code obj} param is null
     * @see org.mule.api.transformer.Transformer
     */
    <T extends Transformer> P transformWith(T obj) throws NullPointerException;

    /**
     * Transforms current payload using the given transformer type.
     *
     * @param clazz the transformer type, Mule will instantiate it at runtime
     * @return the parameterized builder
     * @throws NullPointerException if {@code clazz} param is null
     * @see Transformer
     */
    <T extends Transformer> P transformWith(Class<T> clazz) throws NullPointerException;

    /**
     * Transforms current payload using the given global transformer reference.
     *
     * @param obj the global transformer object reference
     * @return the parameterized builder
     * @throws NullPointerException if {@code obj} param is null
     * @see Catalog#newTransformer(String)
     * @see TransformerDefinition
     */
    P transformWith(TransformerDefinition obj) throws NullPointerException;

    /**
     * Transforms the current payload using the given global transformer reference.
     *
     * @param ref the global transformer unique identifier
     * @return the parameterized builder
     * @throws IllegalArgumentException if {@code ref} param is null or empty
     * @see Catalog#newTransformer(String)
     */
    P transformWith(String ref) throws IllegalArgumentException;

    /* message properties */

    /**
     * Exposes message properties manipilation, enabling some operations
     * like {@code put}, {@code remove} and , {@code rename}.
     *
     * @return the message properties builder
     */
    MessagePropertiesBuilder<P> messageProperties();

}
