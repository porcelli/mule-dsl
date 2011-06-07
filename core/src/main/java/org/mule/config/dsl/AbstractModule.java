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
import org.mule.config.dsl.internal.Registry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import static org.mule.config.dsl.internal.util.NameGenerator.newName;
import static org.mule.config.dsl.internal.util.Preconditions.*;

public abstract class AbstractModule extends com.google.inject.AbstractModule {

    private Registry registry;

    void setRegistry(Registry registry) {
        checkState(this.registry == null, "Re-entry is not allowed.");
        this.registry = checkNotNull(registry, "registry");
    }

    /* elements definition/declaration */

    /* property placeholder */

    public void propertyResolver(FileRefBuilder fileRef) {
        registry.registerPropertyResolver(fileRef.get());
    }

    public void propertyResolver(ClasspathBuilder classpathRef) {
        registry.registerPropertyResolver(classpathRef.get());
    }

    public void propertyResolver(InputStream inputStream) {
        registry.registerPropertyResolver(inputStream);
    }

    public void propertyResolver(Map<String, String> properties) {
        registry.registerPropertyResolver(properties);
    }

    public void propertyResolver(Properties properties) {
        registry.registerPropertyResolver(properties);
    }

    /* global transformar and filters */

    //<T extends Transformer>

    public TransformerBuilder transformer() {
        return registry.transformer(newName("Transformer"));
    }

    public TransformerBuilder transformer(String name) {
        return registry.transformer(name);
    }

    public FilterBuilder filter() {
        return registry.filter(newName("Filter"));
    }

    public FilterBuilder filter(String name) {
        return registry.filter(name);
    }

    /* flow */

    public FlowBuilder flow() {
        return registry.flow(newName("Flow"));
    }

    public FlowBuilder flow(String name) {
        return registry.flow(name);
    }

    /* util methods: named params  */

    public ClasspathBuilder classpath(String classpath) {
        return new ClasspathBuilder(classpath);
    }

    public FileRefBuilder file(File path) {
        return new FileRefBuilder(path);
    }

    public FileRefBuilder file(String path) {
        return new FileRefBuilder(path);
    }

    public static class ClasspathBuilder {
        private final String value;
        private InputStream content = null;

        public ClasspathBuilder(String value) {
            this.value = checkNotEmpty(value, "classpath");
        }

        public InputStream get() {
            if (content == null) {
                Resource res = ResourceUtils.getResource(this.getClass(), value, null);
                if (res == null) {
                    //TODO improve
                    throw new RuntimeException("Resource not found.");
                }
                this.content = res.getResourceAsStream();
            }
            return content;
        }
    }

    public static class FileRefBuilder {
        private final File file;
        private InputStream content = null;

        public FileRefBuilder(String file) {
            checkNotEmpty(file, "file");
            this.file = new File(file);
        }

        public FileRefBuilder(File file) {
            this.file = checkNotNull(file, "file");
        }

        public InputStream get() {
            if (content == null) {
                if (!file.exists()) {
                    //TODO improve
                    throw new RuntimeException("File not found.");
                }
                if (!file.isFile()) {
                    throw new RuntimeException("It's not a file.");
                }
                if (!file.canRead()) {
                    throw new RuntimeException("Can't read file.");
                }
                try {
                    this.content = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    //TODO improve
                    throw new RuntimeException("File not found.", e);
                }
            }

            return content;
        }
    }

}
