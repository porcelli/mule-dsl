/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.util;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Support class adapted from com.google.inject.internal.Preconditions
 *
 * @author porcelli
 */
public class Preconditions {

    private Preconditions() {
    }

    /**
     * Ensures the truth of an expression involving the state of the calling
     * instance, but not involving any parameters to the calling method.
     *
     * @param expression   a boolean expression
     * @param errorMessage the exception message to use if the check fails; will
     *                     be converted to a string using {@link String#valueOf(Object)}
     * @throws IllegalStateException if {@code expression} is false
     */
    public static void checkState(final boolean expression, final Object errorMessage) throws IllegalStateException {
        if (!expression) {
            throw new IllegalStateException(String.valueOf(errorMessage));
        }
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling
     * method is not null.
     *
     * @param reference    an object reference
     * @param errorMessage the exception message to use if the check fails; will
     *                     be converted to a string using {@link String#valueOf(Object)}
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T checkNotNull(final T reference, final Object errorMessage) throws NullPointerException {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }


    /**
     * Ensures that a string reference passed as a parameter to the calling
     * method is not null or empty.
     *
     * @param reference    an string reference
     * @param errorMessage the exception message to use if the check fails; will
     *                     be converted to a string using {@link String#valueOf(Object)}
     * @return the non-null reference that was validated
     * @throws IllegalArgumentException if {@code reference} is null or empty
     */
    public static String checkNotEmpty(final String reference,
                                       final Object errorMessage) throws IllegalArgumentException {
        if (isEmpty(reference)) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
        return reference;

    }

    /**
     * Ensures that the array reference passed as a parameter to the calling
     * method is not null as well it's content.
     *
     * @param reference    an array reference
     * @param errorMessage the exception message to use if the check fails; will
     *                     be converted to a string using {@link String#valueOf(Object)}
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null or any of its elements
     */
    public static <T> T[] checkContentsNotNull(final T[] reference, final Object errorMessage) throws NullPointerException {
        checkNotNull(reference, errorMessage);
        for (final Object element : reference) {
            checkNotNull(element, errorMessage);
        }
        return reference;
    }

}
