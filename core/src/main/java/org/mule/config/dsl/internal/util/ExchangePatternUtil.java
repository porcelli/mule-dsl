/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal.util;

import org.mule.MessageExchangePattern;
import org.mule.config.dsl.ExchangePattern;

public final class ExchangePatternUtil {

    private ExchangePatternUtil() {
    }

    public static MessageExchangePattern convert(ExchangePattern exchangePattern) {
        if (exchangePattern.equals(ExchangePattern.ONE_WAY)) {
            return MessageExchangePattern.ONE_WAY;
        } else if (exchangePattern.equals(ExchangePattern.REQUEST_RESPONSE)) {
            return MessageExchangePattern.REQUEST_RESPONSE;
        }
        throw new IllegalArgumentException();
    }
}
