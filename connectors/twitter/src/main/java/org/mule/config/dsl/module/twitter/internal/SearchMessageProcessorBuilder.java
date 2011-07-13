/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.module.twitter.internal;

import org.mule.api.MuleContext;
import org.mule.config.dsl.ConfigurationException;
import org.mule.config.dsl.ExpressionEvaluatorDefinition;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.config.dsl.internal.Builder;
import org.mule.config.dsl.module.twitter.SearchMessageProcessorDefinition;
import org.mule.ibeans.twitter.config.SearchMessageProcessor;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class SearchMessageProcessorBuilder implements SearchMessageProcessorDefinition, Builder<SearchMessageProcessor> {

    private final IBeanTwitterReference reference;

    private String query = null;
    private ExpressionEvaluatorDefinition queryExp = null;

    private String lang = null;
    private String locale = null;
    private String rpp = null;
    private String page = null;
    private String sinceId = null;
    private String geocode = null;
    private String showUser = null;
    private String accessToken = null;
    private String accessSecret = null;

    private ExpressionEvaluatorDefinition langExp = null;
    private ExpressionEvaluatorDefinition localeExp = null;
    private ExpressionEvaluatorDefinition rppExp = null;
    private ExpressionEvaluatorDefinition pageExp = null;
    private ExpressionEvaluatorDefinition sinceIdExp = null;
    private ExpressionEvaluatorDefinition geocodeExp = null;
    private ExpressionEvaluatorDefinition showUserExp = null;
    private ExpressionEvaluatorDefinition accessTokenExp = null;
    private ExpressionEvaluatorDefinition accessSecretExp = null;

    public SearchMessageProcessorBuilder(IBeanTwitterReference reference, String query) {
        this.reference = reference;
        this.query = query;
    }

    public SearchMessageProcessorBuilder(IBeanTwitterReference reference, ExpressionEvaluatorDefinition queryExp) {
        this.reference = reference;
        this.queryExp = queryExp;
    }

    @Override
    public SearchMessageProcessorDefinition withLang(String lang) {
        this.lang = lang;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withLang(ExpressionEvaluatorDefinition langExp) {
        this.langExp = langExp;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withLocale(String locale) {
        this.locale = locale;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withLocale(ExpressionEvaluatorDefinition localeExp) {
        this.localeExp = localeExp;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withRpp(String rpp) {
        this.rpp = rpp;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withRpp(ExpressionEvaluatorDefinition rppExp) {
        this.rppExp = rppExp;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withPage(String page) {
        this.page = page;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withPage(ExpressionEvaluatorDefinition pageExp) {
        this.pageExp = pageExp;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withSinceId(String sinceId) {
        this.sinceId = sinceId;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withSinceId(ExpressionEvaluatorDefinition sinceIdExp) {
        this.sinceIdExp = sinceIdExp;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withGeocode(String geocode) {
        this.geocode = geocode;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withGeocode(ExpressionEvaluatorDefinition geocodeExp) {
        this.geocodeExp = geocodeExp;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withShowUser(String showUser) {
        this.showUser = showUser;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withShowUser(ExpressionEvaluatorDefinition showUserExp) {
        this.showUserExp = showUserExp;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withAccessToken(ExpressionEvaluatorDefinition accessTokenExp) {
        this.accessTokenExp = accessTokenExp;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
        return this;
    }

    @Override
    public SearchMessageProcessorDefinition withAccessSecret(ExpressionEvaluatorDefinition accessSecretExp) {
        this.accessSecretExp = accessSecretExp;
        return this;
    }

    @Override
    public SearchMessageProcessor build(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

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
