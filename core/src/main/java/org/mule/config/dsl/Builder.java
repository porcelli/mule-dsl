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
 * Marker interface that denotes its extenders as implementers of builder design
 * pattern to configure themselves.
 * <p/>
 * The difference between {@link Definition} and {@code Builder} is that Definition holds an already
 * set up component, in the other hand {@code Builder} has the goal to configure (using the builder pattern),
 * a component to be used. Occationally some builder may generates a {@link Definition} model.
 *
 * @author porcelli
 */
public interface Builder {
}
