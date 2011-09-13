/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.util;

import org.apache.commons.discovery.Resource;
import org.apache.commons.discovery.tools.ResourceUtils;
import org.mule.config.dsl.IOException;
import org.mule.util.IOUtils;

import java.io.InputStream;

import static org.mule.config.dsl.util.Preconditions.checkNotEmpty;

/**
 * Supporter class to expose classpath resource as an {@link java.io.InputStream}.
 *
 * @author porcelli
 */
public class ClasspathBuilder {
    private final String path;
    private InputStream content = null;
    private String stringContent = null;

    /**
     * Constructor
     *
     * @param path path to classpath resource
     * @throws IllegalArgumentException if {@code path} param is null or empty
     */
    public ClasspathBuilder(final String path) throws IllegalArgumentException {
        this.path = checkNotEmpty(path, "path");
    }

    /**
     * Getter of the input stream.
     *
     * @return the input stream ready to use
     * @throws org.mule.config.dsl.IOException if resource not found
     * @see java.io.InputStream
     */
    public InputStream get() throws IOException {
        if (content == null) {
            final Resource res = ResourceUtils.getResource(this.getClass(), path, null);
            if (res == null) {
                throw new IOException("Resource not found.");
            }
            this.content = res.getResourceAsStream();
        }
        return content;
    }

    /**
     * Getter of resource content as string
     *
     * @return the resource content as string
     * @throws org.mule.config.dsl.IOException if file not found, can't be read or it's not a file
     * @see java.io.InputStream
     */
    public String getAsString() throws IOException {
        if (stringContent == null) {
            stringContent = IOUtils.toString(get());
        }

        return stringContent;
    }
}
