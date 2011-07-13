/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.hack;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.fest.util.Collections;

/**
 * Provides access to private members in classes.
 */
public class PrivateAccessor {

    public static Object getPrivateFieldValue(final Object object, final String fieldName) {
        checkNotNull(object, "object");
        checkNotNull(fieldName, "fieldName");

        for (final Field activeField : getAllFields(object.getClass())) {
            if (fieldName.equals(activeField.getName())) {
                try {
                    activeField.setAccessible(true);
                    return activeField.get(object);
                } catch (final IllegalAccessException ex) {
                    throw new RuntimeException("IllegalAccessException accessing " + fieldName, ex);
                }
            }
        }

        throw new RuntimeException("Field '" + fieldName + "' not found");
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
        set.addAll(Collections.set(clazz.getDeclaredFields()));
        return set;
    }


}
