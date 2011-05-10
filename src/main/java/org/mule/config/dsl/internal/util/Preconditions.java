/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal.util;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Wrapper class for com.google.inject.internal.Preconditions
 */
public class Preconditions {

    private Preconditions() {
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the
     * calling method.
     *
     * @param expression a boolean expression
     * @throws IllegalArgumentException if {@code expression} is false
     */
    public static void checkArgument(boolean expression) {
        com.google.inject.internal.Preconditions.checkArgument(expression);
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the
     * calling method.
     *
     * @param expression   a boolean expression
     * @param errorMessage the exception message to use if the check fails; will
     *                     be converted to a string using {@link String#valueOf(Object)}
     * @throws IllegalArgumentException if {@code expression} is false
     */
    public static void checkArgument(boolean expression, Object errorMessage) {
        com.google.inject.internal.Preconditions.checkArgument(expression, errorMessage);
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the
     * calling method.
     *
     * @param expression           a boolean expression
     * @param errorMessageTemplate a template for the exception message should the
     *                             check fail. The message is formed by replacing each {@code %s}
     *                             placeholder in the template with an argument. These are matched by
     *                             position - the first {@code %s} gets {@code errorMessageArgs[0]}, etc.
     *                             Unmatched arguments will be appended to the formatted message in square
     *                             braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs     the arguments to be substituted into the message
     *                             template. Arguments are converted to strings using
     *                             {@link String#valueOf(Object)}.
     * @throws IllegalArgumentException if {@code expression} is false
     * @throws NullPointerException     if the check fails and either {@code
     *                                  errorMessageTemplate} or {@code errorMessageArgs} is null (don't let
     *                                  this happen)
     */
    public static void checkArgument(boolean expression,
                                     String errorMessageTemplate, Object... errorMessageArgs) {
        com.google.inject.internal.Preconditions.checkArgument(expression, errorMessageTemplate, errorMessageArgs);
    }

    /**
     * Ensures the truth of an expression involving the state of the calling
     * instance, but not involving any parameters to the calling method.
     *
     * @param expression a boolean expression
     * @throws IllegalStateException if {@code expression} is false
     */
    public static void checkState(boolean expression) {
        com.google.inject.internal.Preconditions.checkState(expression);
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
    public static void checkState(boolean expression, Object errorMessage) {
        com.google.inject.internal.Preconditions.checkState(expression, errorMessage);
    }

    /**
     * Ensures the truth of an expression involving the state of the calling
     * instance, but not involving any parameters to the calling method.
     *
     * @param expression           a boolean expression
     * @param errorMessageTemplate a template for the exception message should the
     *                             check fail. The message is formed by replacing each {@code %s}
     *                             placeholder in the template with an argument. These are matched by
     *                             position - the first {@code %s} gets {@code errorMessageArgs[0]}, etc.
     *                             Unmatched arguments will be appended to the formatted message in square
     *                             braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs     the arguments to be substituted into the message
     *                             template. Arguments are converted to strings using
     *                             {@link String#valueOf(Object)}.
     * @throws IllegalStateException if {@code expression} is false
     * @throws NullPointerException  if the check fails and either {@code
     *                               errorMessageTemplate} or {@code errorMessageArgs} is null (don't let
     *                               this happen)
     */
    public static void checkState(boolean expression,
                                  String errorMessageTemplate, Object... errorMessageArgs) {
        com.google.inject.internal.Preconditions.checkState(expression, errorMessageTemplate, errorMessageArgs);
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling
     * method is not null.
     *
     * @param reference an object reference
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T checkNotNull(T reference) {
        return com.google.inject.internal.Preconditions.checkNotNull(reference);
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
    public static <T> T checkNotNull(T reference, Object errorMessage) {
        return com.google.inject.internal.Preconditions.checkNotNull(reference, errorMessage);
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling
     * method is not null.
     *
     * @param reference            an object reference
     * @param errorMessageTemplate a template for the exception message should the
     *                             check fail. The message is formed by replacing each {@code %s}
     *                             placeholder in the template with an argument. These are matched by
     *                             position - the first {@code %s} gets {@code errorMessageArgs[0]}, etc.
     *                             Unmatched arguments will be appended to the formatted message in square
     *                             braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs     the arguments to be substituted into the message
     *                             template. Arguments are converted to strings using
     *                             {@link String#valueOf(Object)}.
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T checkNotNull(T reference, String errorMessageTemplate,
                                     Object... errorMessageArgs) {
        return com.google.inject.internal.Preconditions.checkNotNull(reference, errorMessageTemplate, errorMessageArgs);
    }

    /**
     * Ensures that an {@code Iterable} object passed as a parameter to the
     * calling method is not null and contains no null elements.
     *
     * @param iterable the iterable to check the contents of
     * @return the non-null {@code iterable} reference just validated
     * @throws NullPointerException if {@code iterable} is null or contains at
     *                              least one null element
     */
    public static <T extends Iterable<?>> T checkContentsNotNull(T iterable) {
        return com.google.inject.internal.Preconditions.checkContentsNotNull(iterable);
    }

    /**
     * Ensures that an {@code Iterable} object passed as a parameter to the
     * calling method is not null and contains no null elements.
     *
     * @param iterable     the iterable to check the contents of
     * @param errorMessage the exception message to use if the check fails; will
     *                     be converted to a string using {@link String#valueOf(Object)}
     * @return the non-null {@code iterable} reference just validated
     * @throws NullPointerException if {@code iterable} is null or contains at
     *                              least one null element
     */
    public static <T extends Iterable<?>> T checkContentsNotNull(
            T iterable, Object errorMessage) {
        return com.google.inject.internal.Preconditions.checkContentsNotNull(iterable, errorMessage);
    }

    /**
     * Ensures that an {@code Iterable} object passed as a parameter to the
     * calling method is not null and contains no null elements.
     *
     * @param iterable             the iterable to check the contents of
     * @param errorMessageTemplate a template for the exception message should the
     *                             check fail. The message is formed by replacing each {@code %s}
     *                             placeholder in the template with an argument. These are matched by
     *                             position - the first {@code %s} gets {@code errorMessageArgs[0]}, etc.
     *                             Unmatched arguments will be appended to the formatted message in square
     *                             braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs     the arguments to be substituted into the message
     *                             template. Arguments are converted to strings using
     *                             {@link String#valueOf(Object)}.
     * @return the non-null {@code iterable} reference just validated
     * @throws NullPointerException if {@code iterable} is null or contains at
     *                              least one null element
     */
    public static <T extends Iterable<?>> T checkContentsNotNull(T iterable,
                                                                 String errorMessageTemplate, Object... errorMessageArgs) {
        return com.google.inject.internal.Preconditions.checkContentsNotNull(iterable, errorMessageTemplate, errorMessageArgs);
    }

    /**
     * Ensures that {@code index} specifies a valid <i>element</i> in an array,
     * list or string of size {@code size}. An element index may range from zero,
     * inclusive, to {@code size}, exclusive.
     *
     * @param index a user-supplied index identifying an element of an array, list
     *              or string
     * @param size  the size of that array, list or string
     * @throws IndexOutOfBoundsException if {@code index} is negative or is not
     *                                   less than {@code size}
     * @throws IllegalArgumentException  if {@code size} is negative
     */
    public static void checkElementIndex(int index, int size) {
        com.google.inject.internal.Preconditions.checkElementIndex(index, size);
    }

    /**
     * Ensures that {@code index} specifies a valid <i>element</i> in an array,
     * list or string of size {@code size}. An element index may range from zero,
     * inclusive, to {@code size}, exclusive.
     *
     * @param index a user-supplied index identifying an element of an array, list
     *              or string
     * @param size  the size of that array, list or string
     * @param desc  the text to use to describe this index in an error message
     * @throws IndexOutOfBoundsException if {@code index} is negative or is not
     *                                   less than {@code size}
     * @throws IllegalArgumentException  if {@code size} is negative
     */
    public static void checkElementIndex(int index, int size, String desc) {
        com.google.inject.internal.Preconditions.checkElementIndex(index, size, desc);
    }

    /**
     * Ensures that {@code index} specifies a valid <i>position</i> in an array,
     * list or string of size {@code size}. A position index may range from zero
     * to {@code size}, inclusive.
     *
     * @param index a user-supplied index identifying a position in an array, list
     *              or string
     * @param size  the size of that array, list or string
     * @throws IndexOutOfBoundsException if {@code index} is negative or is
     *                                   greater than {@code size}
     * @throws IllegalArgumentException  if {@code size} is negative
     */
    public static void checkPositionIndex(int index, int size) {
        com.google.inject.internal.Preconditions.checkPositionIndex(index, size);
    }

    /**
     * Ensures that {@code index} specifies a valid <i>position</i> in an array,
     * list or string of size {@code size}. A position index may range from zero
     * to {@code size}, inclusive.
     *
     * @param index a user-supplied index identifying a position in an array, list
     *              or string
     * @param size  the size of that array, list or string
     * @param desc  the text to use to describe this index in an error message
     * @throws IndexOutOfBoundsException if {@code index} is negative or is
     *                                   greater than {@code size}
     * @throws IllegalArgumentException  if {@code size} is negative
     */
    public static void checkPositionIndex(int index, int size, String desc) {
        com.google.inject.internal.Preconditions.checkPositionIndex(index, size, desc);
    }

    /**
     * Ensures that {@code start} and {@code end} specify a valid <i>positions</i>
     * in an array, list or string of size {@code size}, and are in order. A
     * position index may range from zero to {@code size}, inclusive.
     *
     * @param start a user-supplied index identifying a starting position in an
     *              array, list or string
     * @param end   a user-supplied index identifying a ending position in an array,
     *              list or string
     * @param size  the size of that array, list or string
     * @throws IndexOutOfBoundsException if either index is negative or is
     *                                   greater than {@code size}, or if {@code end} is less than {@code start}
     * @throws IllegalArgumentException  if {@code size} is negative
     */
    public static void checkPositionIndexes(int start, int end, int size) {
        com.google.inject.internal.Preconditions.checkPositionIndexes(start, end, size);
    }


    /**
     * Assert that this parameter is not empty. It trims the parameter to see if have any valid data on that.
     * <p/>
     * TODO improve docs
     */
    public static String checkNotEmpty(final String reference,
                                       final Object errorMessage) {
        if (isEmpty(reference)) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
        return reference;

    }

}
