/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal.util;

import org.mule.util.UUID;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Utility class that provides unique names and expose it thru a static method.
 *
 * @author porcelli
 */
public class NameGenerator {

    private NameGenerator() {
    }

    /**
     * Returns a unique string based name. If {@code prefix} param provided, prefix it.
     * <p/>
     * <b>Note:</b> the generated string is an UUID.
     *
     * @param prefix the prefix string, null is allowed or empty
     * @return a unique string based name
     * @see UUID
     */
    public static String newName(final String prefix) {
        if (isEmpty(prefix)) {
            return UUID.getUUID();
        }
        return prefix + "-" + UUID.getUUID();
    }
}
