/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.mule.config.dsl.util.ClasspathBuilder;
import org.mule.config.dsl.util.FileRefBuilder;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import static org.apache.commons.collections.MapUtils.toProperties;
import static org.mule.config.dsl.internal.util.NameGenerator.newName;
import static org.mule.config.dsl.util.Preconditions.*;

/**
 * A support class for {@link Module}s which reduces repetition and results in
 * a more readable configuration. Simply extend this class, implement {@link
 * #configure()}, and call the inherited methods. For example:
 * <p/>
 * <pre>
 * public class MyModule extends AbstractModule {
 *   protected void configure() {
 *     flow("MyFlow")
 *          .from("http://localhost:8081/input")
 *          .send("http://10.0.0.2/output");
 *   }
 * }
 * </pre>
 *
 * @author porcelli
 */
public abstract class AbstractModule extends com.google.inject.AbstractModule implements Module {

    private Catalog catalog;

    /**
     * Configures a {@link Catalog} via the exposed methods.
     */
    @Override
    protected abstract void configure();

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(final Catalog catalog) throws IllegalStateException, NullPointerException {
        checkState(this.catalog == null, "Re-entry is not allowed.");
        this.catalog = checkNotNull(catalog, "catalog");
    }

    /* elements definition/declaration */

    /* property placeholder */

    /**
     * Loads the content of {@code fileRef} into property placeholder resolver.
     *
     * @param fileRef reference to file to be loaded
     * @throws NullPointerException if {@code fileRef} param is null
     */
    public void propertyResolver(final FileRefBuilder fileRef) throws NullPointerException {
        checkNotNull(fileRef, "fileRef");
        propertyResolver(fileRef.get());
    }

    /**
     * Loads the content of {@code classpathRef} into property placeholder resolver.
     *
     * @param classpathRef reference to classpath to be loaded
     * @throws NullPointerException if {@code classpathRef} param is null
     */
    public void propertyResolver(final ClasspathBuilder classpathRef) throws NullPointerException {
        checkNotNull(classpathRef, "classpathRef");
        propertyResolver(classpathRef.get());
    }

    /**
     * Loads {@code inputStream} into property placeholder resolver.
     * <p/>
     * <b>Important Note:</b> It's expeced that {@code inputStream} is formatted as a property file.
     *
     * @param inputStream stream to be loaded
     * @throws NullPointerException if {@code inputStream} param is null
     * @throws IOException          if {@code inputStream} is not a properties stream
     */
    public void propertyResolver(final InputStream inputStream) throws NullPointerException, IOException {
        checkNotNull(inputStream, "inputStream");
        final Properties fileProperties = new Properties();
        try {
            fileProperties.load(inputStream);
            propertyResolver(fileProperties);
        } catch (final Exception e) {
            throw new IOException("The given input stream is does not follow the properties format.", e);
        }
    }

    /**
     * Loads {@code propertyMap} into property placeholder resolver.
     *
     * @param propertyMap map to be loaded
     * @throws NullPointerException if {@code propertyMap} param is null
     */
    public void propertyResolver(final Map<?, ?> propertyMap) throws NullPointerException {
        checkNotNull(propertyMap, "propertyMap");
        propertyResolver(toProperties(propertyMap));
    }

    /**
     * Loads {@code properties} into property placeholder resolver.
     *
     * @param properties properties to be loaded
     * @throws NullPointerException if {@code properties} param is null
     */
    public void propertyResolver(final Properties properties) throws NullPointerException {
        checkNotNull(properties, "properties");
        catalog.addToPropertyPlaceholder(properties);
    }

    /* global register */

    /**
     * Register the given object into dsl catalog. Registered objects are
     * later availbale on {@link org.mule.api.MuleContext}.
     *
     * @param name the name of the component to be registered
     * @param obj  the object to be registered
     * @throws IllegalArgumentException if {@code name} param is null or empty
     * @throws NullPointerException     if {@code obj} param is null
     */
    public <O> void register(final String name, O obj) throws IllegalArgumentException, NullPointerException {
        checkNotEmpty(name, "name");
        checkNotNull(name, "obj");
        catalog.newRegistry(name, obj);
    }

    /* global connector */

    /**
     * Declares an anonymous global connector, returning an instance of a
     * {@link ConnectorBuilder} that should be configured.
     *
     * @return the connector builder to be configured
     * @see ConnectorBuilder
     * @see Catalog#newConnector(String)
     * @see org.mule.api.transport.Connector
     */
    public ConnectorBuilder connector() {
        return connector(newName("Connector"));
    }

    /**
     * Declares a global connector using {@code name} as an unique
     * identifier, returning an instance of a {@link ConnectorBuilder} that
     * should be configured.
     *
     * @param name connector name that identifies it uniquely
     * @return the connector builder to be configured
     * @throws IllegalArgumentException if {@code name} param is null or empty
     * @see ConnectorBuilder
     * @see Catalog#newConnector(String)
     * @see org.mule.api.transport.Connector
     */
    public ConnectorBuilder connector(final String name) throws IllegalArgumentException {
        checkNotEmpty(name, "name");
        return catalog.newConnector(name);
    }

    /* global transformar and filters */

    /**
     * Declares an anonymous global transformer, returning an instance of a
     * {@link TransformerBuilder} that should be configured.
     *
     * @return the tranformer builder to be configured
     * @see TransformerBuilder
     * @see Catalog#newTransformer(String)
     * @see org.mule.api.transformer.Transformer
     */
    public TransformerBuilder transformer() {
        return transformer(newName("Transformer"));
    }

    /**
     * Declares a global transformer using {@code name} as an unique
     * identifier, returning an instance of a {@link TransformerBuilder} that
     * should be configured.
     *
     * @param name transformer name that identifies it uniquely
     * @return the tranformer builder to be configured
     * @throws IllegalArgumentException if {@code name} param is null or empty
     * @see TransformerBuilder
     * @see Catalog#newTransformer(String)
     * @see org.mule.api.transformer.Transformer
     */
    public TransformerBuilder transformer(final String name) throws IllegalArgumentException {
        checkNotEmpty(name, "name");
        return catalog.newTransformer(name);
    }

    /**
     * Declares an anonymous global filter, returning an instance of
     * a {@link FilterBuilder} that should be configured.
     *
     * @return the filter builder to be configured
     * @see FilterBuilder
     * @see Catalog#newFilter(String)
     * @see org.mule.routing.MessageFilter
     */
    public FilterBuilder filter() {
        return filter(newName("Filter"));
    }

    /**
     * Declares a global filter using {@code name} as an unique
     * identifier, returning an instance of a {@link FilterBuilder} that
     * should be configured.
     *
     * @param name filter name that identifies it uniquely
     * @return the filter builder to be configured
     * @throws IllegalArgumentException if {@code name} param is null or empty
     * @see FilterBuilder
     * @see Catalog#newFilter(String)
     * @see org.mule.routing.MessageFilter
     */
    public FilterBuilder filter(final String name) throws IllegalArgumentException {
        checkNotEmpty(name, "name");
        return catalog.newFilter(name);
    }

    /* flow */

    /**
     * Declares an anonymous flow, returning an instance of a {@link FlowBuilder} that
     * should be configured.
     *
     * @return the flow builder to be configured
     * @see FlowBuilder
     * @see Catalog#newFlow(String)
     * @see org.mule.api.construct.FlowConstruct
     */
    public FlowBuilder flow() {
        return flow(newName("Flows"));
    }

    /**
     * Declares a flow using {@code name} as an unique identifier,
     * returning an instance of a {@link FlowBuilder} that
     * should be configured.
     *
     * @param name flow name that identifies it uniquely
     * @return the flow builder to be configured
     * @throws IllegalArgumentException if {@code name} param is null or empty
     * @see FlowBuilder
     * @see Catalog#newFlow(String)
     * @see org.mule.api.construct.FlowConstruct
     */
    public FlowBuilder flow(final String name) throws IllegalArgumentException {
        checkNotEmpty(name, "name");
        return catalog.newFlow(name);
    }

    /* util methods: named params  */

    /**
     * Lookups classpath for a resource based on input param.
     *
     * @param classpath path to lookup
     * @return the class path builder
     * @throws IllegalArgumentException if {@code classpath} param is null or empty
     */
    public ClasspathBuilder classpath(final String classpath) throws IllegalArgumentException {
        checkNotEmpty(classpath, "classpath");
        return new ClasspathBuilder(classpath);
    }

    /**
     * Lookups filesystem for a resource based on input param.
     *
     * @param path filesystem path
     * @return the file reference builder
     * @throws IllegalArgumentException if {@code path} param is null or empty
     */
    public FileRefBuilder file(final String path) throws IllegalArgumentException {
        checkNotEmpty(path, "path");
        return new FileRefBuilder(path);
    }

    /**
     * Wraps the input param on a {@link FileRefBuilder}.
     *
     * @param file file reference
     * @return the file reference builder
     * @throws NullPointerException if {@code file} param is null
     */
    public FileRefBuilder file(final File file) throws NullPointerException {
        checkNotNull(file, "path");
        return new FileRefBuilder(file);
    }

}
