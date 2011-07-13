/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.apache.commons.discovery.Resource;
import org.apache.commons.discovery.tools.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import static org.apache.commons.collections.MapUtils.toProperties;
import static org.mule.config.dsl.internal.util.NameGenerator.newName;
import static org.mule.config.dsl.internal.util.Preconditions.*;

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
        return flow(newName("Flow"));
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

    /**
     * Supporter class to expose classpath resource as an {@link InputStream}.
     *
     * @author porcelli
     */
    class ClasspathBuilder {
        private final String path;
        private InputStream content = null;

        /**
         * Constructor
         *
         * @param path path to classpath resource
         * @throws IllegalArgumentException if {@code path} param is null or empty
         */
        private ClasspathBuilder(final String path) throws IllegalArgumentException {
            this.path = checkNotEmpty(path, "path");
        }

        /**
         * Getter of the input stream.
         *
         * @return the input stream ready to use
         * @throws IOException if resource not found
         * @see InputStream
         */
        InputStream get() throws IOException {
            if (content == null) {
                final Resource res = ResourceUtils.getResource(this.getClass(), path, null);
                if (res == null) {
                    throw new IOException("Resource not found.");
                }
                this.content = res.getResourceAsStream();
            }
            return content;
        }
    }

    /**
     * Supporter class to expose files from filesystem as an {@link InputStream}.
     *
     * @author porcelli
     */
    class FileRefBuilder {
        private final File file;
        private InputStream content = null;

        /**
         * Constructor
         *
         * @param path filesystem path
         * @throws IllegalArgumentException if {@code path} param is null or empty
         */
        private FileRefBuilder(final String path) throws IllegalArgumentException {
            checkNotEmpty(path, "path");
            this.file = new File(path);
        }

        /**
         * Constructor
         *
         * @param file file instance
         * @throws NullPointerException if {@code file} param is null
         */
        private FileRefBuilder(final File file) throws NullPointerException {
            this.file = checkNotNull(file, "file");
        }

        /**
         * Getter of the input stream.
         *
         * @return the input stream ready to use
         * @throws IOException if file not found, can't be read or it's not a file
         * @see InputStream
         */
        InputStream get() throws IOException {
            if (content == null) {
                if (!file.exists()) {
                    throw new IOException("File not found.");
                }
                if (!file.isFile()) {
                    throw new IOException("It's not a file.");
                }
                if (!file.canRead()) {
                    throw new IOException("Can't read file.");
                }
                try {
                    this.content = new FileInputStream(file);
                } catch (final FileNotFoundException e) {
                    throw new IOException("File not found.", e);
                }
            }

            return content;
        }
    }

}
