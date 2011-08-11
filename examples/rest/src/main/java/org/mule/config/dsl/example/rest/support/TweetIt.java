/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.example.rest.support;

import org.mule.config.dsl.example.rest.model.StockQuote;
import org.mule.util.StringUtils;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import javax.inject.Inject;

/**
 * Support class that tweets a stock quote.
 *
 * @author porcelli
 */
public class TweetIt {

    final Twitter twitter;

    @Inject
    public TweetIt(final Twitter twitter) {
        this.twitter = twitter;
    }

    public Object tweet(StockQuote quote) throws TwitterException {
        StringBuilder sb = new StringBuilder();
        sb.append(quote.getName()).append(" (").append(quote.getSymbol()).append("): US$ ").append(quote.getLast());
        twitter.updateStatus(StringUtils.abbreviate(sb.toString(), 140));
        return quote;
    }
}
