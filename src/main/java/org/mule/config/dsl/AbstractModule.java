/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import java.io.File;
import java.io.InputStream;

import static org.mule.config.dsl.internal.util.NameGenerator.newName;
import static org.mule.config.dsl.internal.util.Preconditions.*;

public abstract class AbstractModule extends com.google.inject.AbstractModule {

    private Registry registry;

    void setRegistry(Registry registry) {
        checkState(this.registry == null, "Re-entry is not allowed.");
        this.registry = checkNotNull(registry, "registry");
    }

    public abstract void configure();

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

        public ClasspathBuilder(String value) {
            this.value = checkNotEmpty(value, "classpath");
        }

        public InputStream get() {
            return null;
        }
    }

    public static class FileRefBuilder {
        private final File file;

        public FileRefBuilder(String file) {
            checkNotEmpty(file, "file");
            this.file = new File(file);
        }

        public FileRefBuilder(File file) {
            this.file = checkNotNull(file, "file");
        }

        public InputStream get() {
            return null;
        }
    }

}
