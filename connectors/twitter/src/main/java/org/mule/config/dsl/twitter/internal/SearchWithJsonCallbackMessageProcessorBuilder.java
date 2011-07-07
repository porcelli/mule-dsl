/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.twitter.internal;

import com.google.inject.Injector;
import org.mule.api.MuleContext;
import org.mule.config.dsl.ExpressionEvaluatorBuilder;
import org.mule.config.dsl.internal.Builder;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.config.dsl.twitter.SearchWithJsonCallbackMessageProcessorDefinition;
import org.mule.ibeans.twitter.config.SearchWithJsonCallbackMessageProcessor;

public class SearchWithJsonCallbackMessageProcessorBuilder implements SearchWithJsonCallbackMessageProcessorDefinition, Builder<SearchWithJsonCallbackMessageProcessor> {

    private final IBeansTwitterReference reference;

    private String query = null;
    private String callback = null;

    private ExpressionEvaluatorBuilder queryExp = null;
    private ExpressionEvaluatorBuilder callbackExp = null;

    private String lang = null;
    private String locale = null;
    private String rpp = null;
    private String page = null;
    private String sinceId = null;
    private String geocode = null;
    private String showUser = null;

    private ExpressionEvaluatorBuilder langExp = null;
    private ExpressionEvaluatorBuilder localeExp = null;
    private ExpressionEvaluatorBuilder rppExp = null;
    private ExpressionEvaluatorBuilder pageExp = null;
    private ExpressionEvaluatorBuilder sinceIdExp = null;
    private ExpressionEvaluatorBuilder geocodeExp = null;
    private ExpressionEvaluatorBuilder showUserExp = null;

    public SearchWithJsonCallbackMessageProcessorBuilder(IBeansTwitterReference reference, String query, String callback) {
        this.reference = reference;
        this.query = query;
        this.callback = callback;
    }

    public SearchWithJsonCallbackMessageProcessorBuilder(IBeansTwitterReference reference, String query, ExpressionEvaluatorBuilder callbackExp) {
        this.reference = reference;
        this.query = query;
        this.callbackExp = callbackExp;
    }

    public SearchWithJsonCallbackMessageProcessorBuilder(IBeansTwitterReference reference, ExpressionEvaluatorBuilder queryExp, String callback) {
        this.reference = reference;
        this.queryExp = queryExp;
        this.callback = callback;
    }

    public SearchWithJsonCallbackMessageProcessorBuilder(IBeansTwitterReference reference, ExpressionEvaluatorBuilder queryExp, ExpressionEvaluatorBuilder callbackExp) {
        this.reference = reference;
        this.queryExp = queryExp;
        this.callbackExp = callbackExp;
    }

    @Override
    public SearchWithJsonCallbackMessageProcessorDefinition withLang(String lang) {
        this.lang = lang;
        return this;
    }

    @Override
    public SearchWithJsonCallbackMessageProcessorDefinition withLang(ExpressionEvaluatorBuilder langExp) {
        this.langExp = langExp;
        return this;
    }

    @Override
    public SearchWithJsonCallbackMessageProcessorDefinition withLocale(String locale) {
        this.locale = locale;
        return this;
    }

    @Override
    public SearchWithJsonCallbackMessageProcessorDefinition withLocale(ExpressionEvaluatorBuilder localeExp) {
        this.localeExp = localeExp;
        return this;
    }

    @Override
    public SearchWithJsonCallbackMessageProcessorDefinition withRpp(String rpp) {
        this.rpp = rpp;
        return this;
    }

    @Override
    public SearchWithJsonCallbackMessageProcessorDefinition withRpp(ExpressionEvaluatorBuilder rppExp) {
        this.rppExp = rppExp;
        return this;
    }

    @Override
    public SearchWithJsonCallbackMessageProcessorDefinition withPage(String page) {
        this.page = page;
        return this;
    }

    @Override
    public SearchWithJsonCallbackMessageProcessorDefinition withPage(ExpressionEvaluatorBuilder pageExp) {
        this.pageExp = pageExp;
        return this;
    }

    @Override
    public SearchWithJsonCallbackMessageProcessorDefinition withSinceId(String sinceId) {
        this.sinceId = sinceId;
        return this;
    }

    @Override
    public SearchWithJsonCallbackMessageProcessorDefinition withSinceId(ExpressionEvaluatorBuilder sinceIdExp) {
        this.sinceIdExp = sinceIdExp;
        return this;
    }

    @Override
    public SearchWithJsonCallbackMessageProcessorDefinition withGeocode(String geocode) {
        this.geocode = geocode;
        return this;
    }

    @Override
    public SearchWithJsonCallbackMessageProcessorDefinition withGeocode(ExpressionEvaluatorBuilder geocodeExp) {
        this.geocodeExp = geocodeExp;
        return this;
    }

    @Override
    public SearchWithJsonCallbackMessageProcessorDefinition withShowUser(String showUser) {
        this.showUser = showUser;
        return this;
    }

    @Override
    public SearchWithJsonCallbackMessageProcessorDefinition withShowUser(ExpressionEvaluatorBuilder showUserExp) {
        this.showUserExp = showUserExp;
        return this;
    }

    @Override
    public SearchWithJsonCallbackMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        SearchWithJsonCallbackMessageProcessor mp = new SearchWithJsonCallbackMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(reference.getObject(muleContext));

        if (queryExp != null) {
            mp.setQuery(queryExp.toString(placeholder));
        } else {
            mp.setQuery(query);
        }

        if (callbackExp != null) {
            mp.setCallback(callbackExp.toString(placeholder));
        } else {
            mp.setCallback(callback);
        }

        if (langExp != null) {
            mp.setLang(langExp.toString(placeholder));
        } else {
            mp.setQuery(lang);
        }

        if (localeExp != null) {
            mp.setLocale(localeExp.toString(placeholder));
        } else {
            mp.setLocale(locale);
        }

        if (rppExp != null) {
            mp.setRpp(rppExp.toString(placeholder));
        } else {
            mp.setRpp(rpp);
        }

        if (pageExp != null) {
            mp.setPage(pageExp.toString(placeholder));
        } else {
            mp.setPage(page);
        }

        if (sinceIdExp != null) {
            mp.setSinceId(sinceIdExp.toString(placeholder));
        } else {
            mp.setSinceId(sinceId);
        }

        if (geocodeExp != null) {
            mp.setGeocode(geocodeExp.toString(placeholder));
        } else {
            mp.setGeocode(geocode);
        }

        if (showUserExp != null) {
            mp.setShowUser(showUserExp.toString(placeholder));
        } else {
            mp.setShowUser(showUser);
        }

        return mp;
    }
}
