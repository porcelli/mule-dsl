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

import static org.mule.config.dsl.util.Preconditions.checkNotNull;

/**
 * Utility class that converts {@link ExchangePattern} to mule's core counterpart {@link MessageExchangePattern}.
 *
 * @author porcelli
 */
public final class ExchangePatternUtil {

    private ExchangePatternUtil() {
    }

    /**
     * Converts the given param to mule's core {@link MessageExchangePattern}.
     *
     * @param exchangePattern the input exchange pattern
     * @return the converterd version of input exchange pattern
     * @throws NullPointerException     if {@code reference} is null
     * @throws IllegalArgumentException if cannot convert {@code reference}
     * @see ExchangePattern
     * @see MessageExchangePattern
     */
    public static MessageExchangePattern convert(final ExchangePattern exchangePattern) throws NullPointerException, IllegalArgumentException {
        checkNotNull(exchangePattern, "exchangePattern");
        if (exchangePattern.equals(ExchangePattern.ONE_WAY)) {
            return MessageExchangePattern.ONE_WAY;
        } else if (exchangePattern.equals(ExchangePattern.REQUEST_RESPONSE)) {
            return MessageExchangePattern.REQUEST_RESPONSE;
        }
        throw new IllegalArgumentException("Can't convert the given exchange pattern.");
    }
}
