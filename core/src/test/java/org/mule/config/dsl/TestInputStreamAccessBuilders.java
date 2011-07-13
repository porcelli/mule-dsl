/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;

import org.junit.Test;

public class TestInputStreamAccessBuilders {

    @Test
    public void testClasspathResource() {
        new AbstractModule() {
            @Override
            protected void configure() {
                final AbstractModule.ClasspathBuilder classpathBuilder = classpath("test-resource.properties");
                assertThat(classpathBuilder.get()).isNotNull();
            }
        }.configure();
    }

    @Test(expected = IOException.class)
    public void testClasspathResourceNotFound() {
        new AbstractModule() {
            @Override
            protected void configure() {
                final AbstractModule.ClasspathBuilder classpathBuilder = classpath("test-does-not-exists.properties");
                classpathBuilder.get();
            }
        }.configure();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testClasspathEmptyParam() {
        new AbstractModule() {
            @Override
            protected void configure() {
                classpath("");
            }
        }.configure();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testClasspathNullParam() {
        new AbstractModule() {
            @Override
            protected void configure() {
                classpath(null);
            }
        }.configure();
    }


    @Test
    public void testFileAsStringResource() {
        new AbstractModule() {
            @Override
            protected void configure() {
                final AbstractModule.FileRefBuilder fileRefBuilder = file("./src/test/resources/test-resource.properties");
                assertThat(fileRefBuilder.get()).isNotNull();
            }
        }.configure();
    }

    @Test
    public void testFileAsFileResource() {
        new AbstractModule() {
            @Override
            protected void configure() {
                final AbstractModule.FileRefBuilder fileRefBuilder = file(new File("./src/test/resources/test-resource.properties"));
                assertThat(fileRefBuilder.get()).isNotNull();
            }
        }.configure();
    }

    @Test(expected = IOException.class)
    public void testFileAsStringResourceNotFound() {
        new AbstractModule() {
            @Override
            protected void configure() {
                final AbstractModule.FileRefBuilder fileRefBuilder = file("./src/test/resources/test-does-not-exists.properties");
                fileRefBuilder.get();
            }
        }.configure();
    }

    @Test(expected = IOException.class)
    public void testFileAsFileResourceNotFound() {
        new AbstractModule() {
            @Override
            protected void configure() {
                final AbstractModule.FileRefBuilder fileRefBuilder = file(new File("./src/test/resources/test-does-not-exists.properties"));
                fileRefBuilder.get();
            }
        }.configure();
    }

    @Test(expected = IOException.class)
    public void testFileAsStringResourceButDirectory() {
        new AbstractModule() {
            @Override
            protected void configure() {
                final AbstractModule.FileRefBuilder fileRefBuilder = file("./src/test/resources/");
                fileRefBuilder.get();
            }
        }.configure();
    }

    @Test(expected = IOException.class)
    public void testFileAsFileResourceButDirectory() {
        new AbstractModule() {
            @Override
            protected void configure() {
                final AbstractModule.FileRefBuilder fileRefBuilder = file(new File("./src/test/resources/"));
                fileRefBuilder.get();
            }
        }.configure();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFileAsStringEmptyParam() {
        new AbstractModule() {
            @Override
            protected void configure() {
                file("");
            }
        }.configure();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFileAsStringNullParam() {
        new AbstractModule() {
            @Override
            protected void configure() {
                file((String) null);
            }
        }.configure();
    }

    @Test(expected = NullPointerException.class)
    public void testFileAsFileEmptyParam() {
        new AbstractModule() {
            @Override
            protected void configure() {
                file(new File((String) null));
            }
        }.configure();
    }

    @Test(expected = NullPointerException.class)
    public void testFileAsFileNullParam() {
        new AbstractModule() {
            @Override
            protected void configure() {
                file((File) null);
            }
        }.configure();
    }

}
