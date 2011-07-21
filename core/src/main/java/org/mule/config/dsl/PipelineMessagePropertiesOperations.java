/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

/**
 * Interface that defines all message properties related operations.
 *
 * @author porcelli
 */
public interface PipelineMessagePropertiesOperations<P extends PipelineBuilder<P>> {

    /* message properties */

    /**
     * Exposes message properties manipilation, enabling some operations
     * like {@code put}, {@code remove} and , {@code rename}.
     *
     * @return the message properties builder
     */
    MessagePropertiesBuilder<P> messageProperties();


}
