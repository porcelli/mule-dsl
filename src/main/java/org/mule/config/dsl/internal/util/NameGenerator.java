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

public class NameGenerator {

    private NameGenerator() {
    }

    public static String newName(String prefix) {
        if (isEmpty(prefix)) {
            return UUID.getUUID();
        }
        return prefix + ":" + UUID.getUUID();
    }
}
