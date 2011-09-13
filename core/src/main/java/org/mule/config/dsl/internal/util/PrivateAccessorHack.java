/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal.util;

import org.mule.config.dsl.ConfigurationException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.mule.config.dsl.util.Preconditions.checkNotEmpty;
import static org.mule.config.dsl.util.Preconditions.checkNotNull;

/**
 * Provides access to private members in classes.
 *
 * @author porcelli
 */
public class PrivateAccessorHack {

    /**
     * Sets the value for a private field based on given params.
     *
     * @param type      the type to query the field
     * @param object    the object to run the type against
     * @param fieldName the field name
     * @param value     the value to be setted
     * @return the object with field already setted
     * @throws IllegalArgumentException if {@code fieldName} param is null or empty
     * @throws NullPointerException     if {@code type}, {@code object} or {@code value} params are null
     * @throws ConfigurationException   if field not found or unable to access it
     */
    public static Object setPrivateFieldValue(final Class<?> type, final Object object, final String fieldName, final Object value) throws IllegalArgumentException, NullPointerException, ConfigurationException {
        checkNotNull(type, "type");
        checkNotNull(object, "object");
        checkNotEmpty(fieldName, "fieldName");
        checkNotNull(value, "value");

        try {
            Field f = type.getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(object, value);

            return object;
        } catch (NoSuchFieldException e) {
            throw new ConfigurationException("Field '" + fieldName + "' not found.", e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationException("Can't set field '" + fieldName + "' value.", e);
        }
    }

    /**
     * Executes a private method based on given params.
     *
     * @param type       the type to query the method
     * @param object     the object to run the type against
     * @param methodName the method name to be executed
     * @throws IllegalArgumentException if {@code methodName} param is null or empty
     * @throws NullPointerException     if {@code type} or {@code object} params are null
     * @throws ConfigurationException   if method not found or unable to access it
     */
    public static void executeHiddenMethod(final Class<?> type, final Object object, final String methodName) throws IllegalArgumentException, NullPointerException, ConfigurationException {
        checkNotNull(type, "type");
        checkNotNull(object, "object");
        checkNotEmpty(methodName, "methodName");

        try {
            Method m = type.getDeclaredMethod(methodName);
            m.setAccessible(true);
            m.invoke(object);
        } catch (NoSuchMethodException e) {
            throw new ConfigurationException("Method '" + methodName + "' not found.", e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationException("Can't invoke method '" + methodName + "' value.", e);
        } catch (InvocationTargetException e) {
            throw new ConfigurationException("Can't invoke method '" + methodName + "' value.", e);
        }
    }


    /**
     * Gets the value from a private field  based on given params.
     *
     * @param object    the object to be scanned
     * @param fieldName the field name to get the value from
     * @return the field value
     * @throws NullPointerException     if {@code object} param is null
     * @throws IllegalArgumentException if {@code fieldName} param is null or empty
     * @throws ConfigurationException   if field not found or unable to access it
     */
    public static Object getPrivateFieldValue(final Object object, final String fieldName) throws NullPointerException, IllegalArgumentException, ConfigurationException {
        checkNotNull(object, "object");
        checkNotEmpty(fieldName, "fieldName");

        for (final Field activeField : getAllFields(object.getClass())) {
            if (fieldName.equals(activeField.getName())) {
                try {
                    activeField.setAccessible(true);
                    return activeField.get(object);
                } catch (final IllegalAccessException ex) {
                    throw new ConfigurationException("IllegalAccessException accessing " + fieldName, ex);
                }
            }
        }

        throw new ConfigurationException("Field '" + fieldName + "' not found");
    }

    /**
     * Return the set of fields declared at broadcast level of class hierachy
     */
    private static Set<Field> getAllFields(final Class clazz) {
        return getAllFieldsRec(clazz, new HashSet<Field>());
    }

    private static Set<Field> getAllFieldsRec(final Class clazz, final Set<Field> set) {
        final Class superClazz = clazz.getSuperclass();
        if (superClazz != null) {
            getAllFieldsRec(superClazz, set);
        }
        set.addAll(Arrays.asList(clazz.getDeclaredFields()));
        return set;
    }


}
