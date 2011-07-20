/*
 * ---------------------------------------------------------------------------
 *  Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import com.google.inject.Injector;
import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.NamedObject;
import org.mule.api.agent.Agent;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.transformer.Transformer;
import org.mule.api.transport.Connector;
import org.mule.config.dsl.ConfigurationException;
import org.mule.context.DefaultMuleContextFactory;
import org.mule.routing.MessageFilter;

import java.util.Map;

import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.mule.config.dsl.internal.GuiceRegistry.GUICE_INJECTOR_REF;
import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

/**
 * Utility class that, based on given dsl {@link org.mule.config.dsl.Catalog}
 * builds an instance of {@link MuleContext}.
 *
 * @author porcelli
 */
public class MuleContextBuilder implements org.mule.config.dsl.Builder {

    private final DefaultCatalogImpl catalog;
    private final Injector injector;

    /**
     * @param catalog  the dsl catalog
     * @param injector the guice injector, null is allowed
     * @throws NullPointerException if {@code catalog} param is null
     */
    public MuleContextBuilder(final DefaultCatalogImpl catalog, final Injector injector) throws NullPointerException {
        this.catalog = checkNotNull(catalog, "catalog");
        this.injector = injector;
    }

    /**
     * Creates a {@link MuleContext} and configure it based on dsl {@link org.mule.config.dsl.Catalog}.
     *
     * @return the mule context
     * @throws ConfigurationException if can't configure the mule context
     */
    public MuleContext build() throws ConfigurationException {
        try {
            final MuleContext muleContext = new DefaultMuleContextFactory().createMuleContext();

            if (injector != null) {
                muleContext.getRegistry().registerObject(GUICE_INJECTOR_REF, injector);
                final GuiceRegistry registry = new GuiceRegistry(muleContext, injector);
                muleContext.addRegistry(registry);
            }

            return config(muleContext);
        } catch (final Exception e) {
            throw new ConfigurationException("Failed to configure mule context.", e);
        }
    }

    /**
     * Configures the  {@link MuleContext} with dsl {@link org.mule.config.dsl.Catalog} information.
     *
     * @param muleContext the mule context
     * @return the mule context configured
     * @throws ConfigurationException if fails to register any configuration from catalog
     */
    private MuleContext config(final MuleContext muleContext) throws ConfigurationException {

        for (final Map.Entry<String, Object> entry : catalog.getComponents().entrySet()) {
            try {
                if (entry.getValue() instanceof NamedObject && isEmpty(((NamedObject) entry.getValue()).getName())) {
                    ((NamedObject) entry.getValue()).setName(entry.getKey());
                }
                if (entry.getValue() instanceof Connector) {
                    muleContext.getRegistry().registerConnector((Connector) entry.getValue());
                } else if (entry.getValue() instanceof Agent) {
                    muleContext.getRegistry().registerAgent((Agent) entry.getValue());
                } else {
                    muleContext.getRegistry().registerObject(entry.getKey(), entry.getValue());
                }
            } catch (MuleException e) {
                throw new ConfigurationException("Failed to configure a Component.", e);
            }
        }

        for (final TransformerBuilderImpl transformerBuilder : catalog.getGlobalTransformers().values()) {
            final Transformer transformer = transformerBuilder.build(muleContext, catalog.getPropertyPlaceholder());
            if (transformer != null) {
                try {
                    transformer.setName(transformerBuilder.getName());
                    muleContext.getRegistry().registerTransformer(transformer);
                } catch (final MuleException e) {
                    throw new ConfigurationException("Failed to configure a Global Transformer.", e);
                }
            }
        }

        for (final FilterBuilderImpl filterBuilder : catalog.getGlobalFilters().values()) {
            final MessageFilter filter = filterBuilder.build(muleContext, catalog.getPropertyPlaceholder());
            if (filter != null) {
                try {
                    muleContext.getRegistry().registerObject(filterBuilder.getName(), filter);
                } catch (final MuleException e) {
                    throw new ConfigurationException("Failed to configure a Global Filter.", e);
                }
            }
        }

        for (final FlowBuilderImpl activeFlowBuilder : catalog.getFlows().values()) {
            final FlowConstruct flow = activeFlowBuilder.build(muleContext, catalog.getPropertyPlaceholder());
            if (flow != null) {
                try {
                    muleContext.getRegistry().registerFlowConstruct(flow);
                } catch (final MuleException e) {
                    throw new ConfigurationException("Failed to configure a Flow.", e);
                }
            }
        }

        return muleContext;
    }

}
