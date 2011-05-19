/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.junit.Test;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;

public class TestInputStreamAccessBuilders {

    @Test
    public void testClasspathResource() {
        AbstractModule.ClasspathBuilder classpathBuilder = new AbstractModule.ClasspathBuilder("test-resource.properties");
        assertThat(classpathBuilder.get()).isNotNull();
    }

    @Test(expected = RuntimeException.class)
    public void testClasspathResourceNotFound() {
        AbstractModule.ClasspathBuilder classpathBuilder = new AbstractModule.ClasspathBuilder("test-does-not-exists.properties");
        classpathBuilder.get();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testClasspathEmptyParam() {
        new AbstractModule.ClasspathBuilder("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testClasspathNullParam() {
        new AbstractModule.ClasspathBuilder(null);
    }


    @Test
    public void testFileAsStringResource() {
        AbstractModule.FileRefBuilder fileRefBuilder = new AbstractModule.FileRefBuilder("./src/test/resources/test-resource.properties");
        assertThat(fileRefBuilder.get()).isNotNull();
    }

    @Test
    public void testFileAsFileResource() {
        AbstractModule.FileRefBuilder fileRefBuilder = new AbstractModule.FileRefBuilder(new File("./src/test/resources/test-resource.properties"));
        assertThat(fileRefBuilder.get()).isNotNull();
    }

    @Test(expected = RuntimeException.class)
    public void testFileAsStringResourceNotFound() {
        AbstractModule.FileRefBuilder fileRefBuilder = new AbstractModule.FileRefBuilder("./src/test/resources/test-does-not-exists.properties");
        fileRefBuilder.get();
    }

    @Test(expected = RuntimeException.class)
    public void testFileAsFileResourceNotFound() {
        AbstractModule.FileRefBuilder fileRefBuilder = new AbstractModule.FileRefBuilder(new File("./src/test/resources/test-does-not-exists.properties"));
        fileRefBuilder.get();
    }

    @Test(expected = RuntimeException.class)
    public void testFileAsStringResourceButDirectory() {
        AbstractModule.FileRefBuilder fileRefBuilder = new AbstractModule.FileRefBuilder("./src/test/resources/");
        fileRefBuilder.get();
    }

    @Test(expected = RuntimeException.class)
    public void testFileAsFileResourceButDirectory() {
        AbstractModule.FileRefBuilder fileRefBuilder = new AbstractModule.FileRefBuilder(new File("./src/test/resources/"));
        fileRefBuilder.get();
    }

    @Test(expected = RuntimeException.class)
    public void testFileAsStringEmptyParam() {
        new AbstractModule.FileRefBuilder("");
    }

    @Test(expected = RuntimeException.class)
    public void testFileAsStringNullParam() {
        new AbstractModule.FileRefBuilder((String) null);
    }

    @Test(expected = RuntimeException.class)
    public void testFileAsFileEmptyParam() {
        new AbstractModule.FileRefBuilder(new File((String) null));
    }

    @Test(expected = RuntimeException.class)
    public void testFileAsFileNullParam() {
        new AbstractModule.FileRefBuilder((File) null);
    }

}
