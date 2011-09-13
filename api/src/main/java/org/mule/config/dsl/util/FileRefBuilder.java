/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.util;

import org.mule.config.dsl.IOException;
import org.mule.util.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.mule.config.dsl.util.Preconditions.checkNotEmpty;
import static org.mule.config.dsl.util.Preconditions.checkNotNull;

/**
 * Supporter class to expose files from filesystem as an {@link java.io.InputStream}.
 *
 * @author porcelli
 */
public class FileRefBuilder {
    private final File file;
    private InputStream content = null;
    private String stringContent = null;

    /**
     * Constructor
     *
     * @param path filesystem path
     * @throws IllegalArgumentException if {@code path} param is null or empty
     */
    public FileRefBuilder(final String path) throws IllegalArgumentException {
        checkNotEmpty(path, "path");
        this.file = new File(path);
    }

    /**
     * Constructor
     *
     * @param file file instance
     * @throws NullPointerException if {@code file} param is null
     */
    public FileRefBuilder(final File file) throws NullPointerException {
        this.file = checkNotNull(file, "file");
    }

    /**
     * Getter of the input stream.
     *
     * @return the input stream ready to use
     * @throws org.mule.config.dsl.IOException if file not found, can't be read or it's not a file
     * @see java.io.InputStream
     */
    public InputStream get() throws IOException {
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
