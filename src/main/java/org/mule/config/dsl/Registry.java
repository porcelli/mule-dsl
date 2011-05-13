/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.construct.FlowConstruct;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

public class Registry {

    final Map<String, FlowBuilder> flows;
    final MuleContext muleContext;
    final Injector injector;

    public Registry(MuleContext muleContext) {
        this.muleContext = checkNotNull(muleContext, "muleContext");
        this.flows = new HashMap<String, FlowBuilder>();
        this.injector = Guice.createInjector();
    }

    public FlowBuilder flow(String flowName) {
        if (flows.containsKey(flowName)) {
            throw new IllegalArgumentException();
        }

        FlowBuilder fb = new FlowBuilder(flowName, muleContext);
        flows.put(flowName, fb);

        return fb;
    }

    public void registerPropertyResolver(InputStream inputStream) {
        //todo implment
    }

    public void build(Injector injector) {
        for (FlowBuilder activeFlowBuilder : flows.values()) {
            FlowConstruct flow = activeFlowBuilder.build(injector);
            if (flow != null) {
                try {
                    muleContext.getRegistry().registerFlowConstruct(flow);
                } catch (MuleException e) {
                    //TODO handle
                    throw new RuntimeException();
                }
            }
        }
    }

}
