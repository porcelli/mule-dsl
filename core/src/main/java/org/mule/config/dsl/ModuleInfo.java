/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates {@link Module}s assigning to them a human readable name and optionally a description as well.
 *
 * @author porcelli
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
    /**
     * Human readable {@link Module}'s name
     *
     * @return the module name
     */
    String name();

    /**
     * An extended {@link Module}'s description
     *
     * @return the module description
     */
    String description() default "";
}
