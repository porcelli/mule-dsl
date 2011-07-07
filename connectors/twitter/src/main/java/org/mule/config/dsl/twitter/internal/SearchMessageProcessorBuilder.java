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
import org.mule.config.dsl.twitter.SearchMessageProcessorDefinition;
import org.mule.ibeans.twitter.config.SearchMessageProcessor;

public class SearchMessageProcessorBuilder implements SearchMessageProcessorDefinition, Builder<SearchMessageProcessor> {

    private final IBeansTwitterReference reference;

    private String query = null;
    private ExpressionEvaluatorBuilder queryExp = null;

    private String lang = null;
    private String locale = null;
    private String rpp = null;
    private String page = null;
    private String sinceId = null;
    private String geocode = null;
    private String showUser = null;
    private String accessToken = null;
    private String accessSecret = null;

    private ExpressionEvaluatorBuilder langExp = null;
    private ExpressionEvaluatorBuilder localeExp = null;
    private ExpressionEvaluatorBuilder rppExp = null;
    private ExpressionEvaluatorBuilder pageExp = null;
    private ExpressionEvaluatorBuilder sinceIdExp = null;
    private ExpressionEvaluatorBuilder geocodeExp = null;
    private ExpressionEvaluatorBuilder showUserExp = null;
    private ExpressionEvaluatorBuilder accessTokenExp = null;
    private ExpressionEvaluatorBuilder accessSecretExp = null;

    public SearchMessageProcessorBuilder(IBeansTwitterReference reference, String query) {
        this.reference = reference;
        this.query = query;
    }

    public SearchMessageProcessorBuilder(IBeansTwitterReference reference, ExpressionEvaluatorBuilder queryExp) {
        this.reference = reference;
        this.queryExp = queryExp;
    }

    @Override
    public SearchMessageProcessorDefinition withLang(String lang) {
        this.lang = lang;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withLang(ExpressionEvaluatorBuilder langExp) {
        this.langExp = langExp;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withLocale(String locale) {
        this.locale = locale;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withLocale(ExpressionEvaluatorBuilder localeExp) {
        this.localeExp = localeExp;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withRpp(String rpp) {
        this.rpp = rpp;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withRpp(ExpressionEvaluatorBuilder rppExp) {
        this.rppExp = rppExp;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withPage(String page) {
        this.page = page;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withPage(ExpressionEvaluatorBuilder pageExp) {
        this.pageExp = pageExp;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withSinceId(String sinceId) {
        this.sinceId = sinceId;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withSinceId(ExpressionEvaluatorBuilder sinceIdExp) {
        this.sinceIdExp = sinceIdExp;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withGeocode(String geocode) {
        this.geocode = geocode;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withGeocode(ExpressionEvaluatorBuilder geocodeExp) {
        this.geocodeExp = geocodeExp;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withShowUser(String showUser) {
        this.showUser = showUser;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withShowUser(ExpressionEvaluatorBuilder showUserExp) {
        this.showUserExp = showUserExp;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withAccessToken(ExpressionEvaluatorBuilder accessTokenExp) {
        this.accessTokenExp = accessTokenExp;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withAccessSecret(ExpressionEvaluatorBuilder accessSecretExp) {
        this.accessSecretExp = accessSecretExp;
        return this;
    }

    @Override
    public SearchMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        SearchMessageProcessor mp = new SearchMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(reference.getObject(muleContext));

        if (queryExp != null) {
            mp.setQuery(queryExp.toString(placeholder));
        } else {
            mp.setQuery(query);
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

        if (accessTokenExp != null) {
            mp.setAccessToken(accessTokenExp.toString(placeholder));
        } else {
            mp.setAccessToken(accessToken);
        }

        if (accessSecretExp != null) {
            mp.setAccessSecret(accessSecretExp.toString(placeholder));
        } else {
            mp.setAccessSecret(accessSecret);
        }

        return mp;
    }
}
