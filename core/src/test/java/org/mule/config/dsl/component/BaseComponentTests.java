/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.component;

import static org.fest.assertions.Fail.fail;

import org.mule.DefaultMuleEvent;
import org.mule.DefaultMuleEventContext;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleContext;
import org.mule.api.MuleEventContext;
import org.mule.context.DefaultMuleContextFactory;
import org.mule.model.seda.SedaService;
import org.mule.session.DefaultMuleSession;

public abstract class BaseComponentTests {
    protected MuleContext muleContext;

    public BaseComponentTests() {
        try {
            this.muleContext = new DefaultMuleContextFactory().createMuleContext();
        } catch (final Exception e) {
            fail("Can't initialize muleContext.", e);
        }
    }

    protected MuleEventContext getEventContext() {
        return getEventContext(null);
    }


    protected MuleEventContext getEventContext(final Object messageContent) {
        return new DefaultMuleEventContext(new DefaultMuleEvent(new DefaultMuleMessage(messageContent, muleContext), null, new DefaultMuleSession(new SedaService(muleContext), muleContext)));
    }

}
