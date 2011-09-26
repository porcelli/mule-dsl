/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import com.google.inject.Injector;
import org.mule.api.MuleContext;
import org.mule.api.endpoint.EndpointBuilder;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.processor.MessageProcessor;
import org.mule.config.dsl.ConfigurationException;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.config.dsl.TimePeriod;
import org.mule.config.dsl.component.InvokerFlowComponent;
import org.mule.config.dsl.internal.util.InjectorUtil;
import org.mule.endpoint.EndpointURIEndpointBuilder;
import org.mule.endpoint.URIBuilder;
import org.mule.transport.AbstractConnector;
import org.mule.transport.polling.MessageProcessorPollingMessageReceiver;
import org.mule.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;

import static org.mule.config.dsl.internal.GuiceRegistry.GUICE_INJECTOR_REF;
import static org.mule.config.dsl.util.Preconditions.*;

/**
 * Internal class that builds an {@link InboundEndpoint} that polls a given message processor or flow.
 *
 * @author porcelli
 * @see org.mule.config.dsl.FlowBuilder#poll(String)
 * @see org.mule.config.dsl.FlowBuilder#poll(org.mule.config.dsl.FlowDefinition)
 * @see org.mule.config.dsl.FlowBuilder#poll(org.mule.api.processor.MessageProcessor)
 * @see org.mule.config.dsl.FlowBuilder#poll(Class)
 */
public class PollBuilderImpl implements DSLBuilder<InboundEndpoint> {

    private final MessageProcessor obj;
    private final Class<? extends MessageProcessor> clazz;
    private final String flowName;
    private long frequency = 1L;

    /**
     * @param obj the message processor to be polled
     * @throws NullPointerException if {@code obj} param is null
     */
    public <MP extends MessageProcessor> PollBuilderImpl(MP obj) throws NullPointerException {
        this.obj = checkNotNull(obj, "obj");
        this.flowName = null;
        this.clazz = null;
    }

    /**
     * @param clazz the type of message processot to be polled
     * @throws NullPointerException if {@code clazz} param is null
     */
    public <MP extends MessageProcessor> PollBuilderImpl(Class<MP> clazz) throws NullPointerException {
        this.clazz = checkNotNull(clazz, "clazz");
        this.obj = null;
        this.flowName = null;
    }

    /**
     * @param flowName the flow name to be polled
     * @throws IllegalArgumentException if {@code flowName} param is null or empty
     */
    public PollBuilderImpl(String flowName) throws IllegalArgumentException {
        this.flowName = checkNotEmpty(flowName, "flowName");
        this.obj = null;
        this.clazz = null;
    }

    /**
     * Sets the polling frequency based on given params.
     *
     * @param duration the duration
     * @param period   the time period unit
     * @throws IllegalArgumentException if {@code duration} param is equal or less than zero
     * @throws NullPointerException     if {@code perido} param is null
     */
    public synchronized void setFrequency(long duration, TimePeriod period) throws IllegalArgumentException, NullPointerException {
        checkState(duration > 0, "Duration should be higher than zero.");
        checkNotNull(period, "period");
        frequency = period.toMillis(duration);
    }

    /**
     * Builds a inbound endpoint that polls, based on it's internal config, a message processor or flow.
     *
     * @param muleContext the mule context
     * @param placeholder the property placeholder
     * @return an inbound endpoint configured to poll
     * @throws NullPointerException   if {@code muleContext} param is null
     * @throws ConfigurationException if can't configure the polling endpoint
     */
    @Override
    public InboundEndpoint build(MuleContext muleContext, PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException {
        checkNotNull(muleContext, "muleContext");
        final MessageProcessor messageProcessor;
        if (clazz != null) {
            try {
                if (InjectorUtil.hasProvider(muleContext.getRegistry().<Injector>get(GUICE_INJECTOR_REF), clazz)) {
                    messageProcessor = muleContext.getRegistry().lookupObject(clazz);
                } else {
                    messageProcessor = ClassUtils.instanciateClass(clazz);
                }
            } catch (final Exception e) {
                throw new ConfigurationException("Failed to configure Poll Endpoint.", e);
            }
        } else if (flowName != null) {
            messageProcessor = new InvokerFlowComponent(flowName);
        } else {
            messageProcessor = obj;
        }

        if (messageProcessor == null) {
            throw new ConfigurationException("Failed to configure Poll Endpoint.");
        }

        final EndpointBuilder internalEndpointBuilder = new EndpointURIEndpointBuilder(new URIBuilder("polling://" + hashCode(), muleContext));

        Map<Object, Object> properties = new HashMap<Object, Object>();
        properties.put(AbstractConnector.PROPERTY_POLLING_FREQUENCY, frequency);
        properties.put(MessageProcessorPollingMessageReceiver.SOURCE_MESSAGE_PROCESSOR_PROPERTY_NAME, messageProcessor);

        internalEndpointBuilder.setProperties(properties);

        try {
            return internalEndpointBuilder.buildInboundEndpoint();
        } catch (final Exception e) {
            throw new ConfigurationException("Failed to configure Poll Endpoint.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PollBuilderImpl that = (PollBuilderImpl) o;

        if (frequency != that.frequency) return false;
        if (clazz != null ? !clazz.equals(that.clazz) : that.clazz != null) return false;
        if (flowName != null ? !flowName.equals(that.flowName) : that.flowName != null) return false;
        if (obj != null ? !obj.equals(that.obj) : that.obj != null) return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = obj != null ? obj.hashCode() : 0;
        result = 31 * result + (clazz != null ? clazz.hashCode() : 0);
        result = 31 * result + (flowName != null ? flowName.hashCode() : 0);
        result = 31 * result + (int) (frequency ^ (frequency >>> 32));
        return result;
    }
}
