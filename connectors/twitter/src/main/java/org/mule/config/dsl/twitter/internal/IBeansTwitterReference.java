/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.twitter.internal;

import org.mule.api.MuleContext;
import org.mule.ibeans.twitter.TwitterBase;
import org.mule.ibeans.twitter.TwitterIBean;
import org.mule.ibeans.twitter.TwitterIBeanFactoryBean;

public class IBeansTwitterReference {

    private TwitterIBean object = null;

    private TwitterBase.FORMAT format;
    private String consumerKey;
    private String consumerSecret;
    private String oathToken;
    private String oathTokenSecret;

    public TwitterIBean getObject(MuleContext muleContext) {
        if (object == null) {
            TwitterIBeanFactoryBean ibeanFactoryBean = new TwitterIBeanFactoryBean();
            ibeanFactoryBean.setMuleContext(muleContext);
            ibeanFactoryBean.setFormat(format.toString());
            ibeanFactoryBean.setConsumerKey(consumerKey);
            ibeanFactoryBean.setConsumerSecret(consumerSecret);
            ibeanFactoryBean.setOauthToken(oathToken);
            ibeanFactoryBean.setOauthTokenSecret(oathTokenSecret);
            try {
                object = ibeanFactoryBean.getObject();
            } catch (Exception e) {
                //TODO improve here
                throw new RuntimeException(e);
            }
        }
        return object;
    }

    public TwitterBase.FORMAT getFormat() {
        return format;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public String getOathToken() {
        return oathToken;
    }

    public String getOathTokenSecret() {
        return oathTokenSecret;
    }

    public void setFormat(TwitterBase.FORMAT format) {
        this.format = format;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public void setOathToken(String oathToken) {
        this.oathToken = oathToken;
    }

    public void setOathTokenSecret(String oathTokenSecret) {
        this.oathTokenSecret = oathTokenSecret;
    }
}
