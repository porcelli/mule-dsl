/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org;

import org.fest.util.Collections;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import static org.mule.config.dsl.internal.util.Preconditions.checkNotNull;

/**
 * Provides access to private members in classes.
 */
public class PrivateAccessor {

    public static Object getPrivateFieldValue(Object object, String fieldName) {
        checkNotNull(object, "object");
        checkNotNull(fieldName, "fieldName");

        for (Field activeField : getAllFields(object.getClass())) {
            if (fieldName.equals(activeField.getName())) {
                try {
                    activeField.setAccessible(true);
                    return activeField.get(object);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException("IllegalAccessException accessing " + fieldName, ex);
                }
            }
        }

        throw new RuntimeException("Field '" + fieldName + "' not found");
    }

    /**
     * Return the set of fields declared at all level of class hierachy
     */
    private static Set<Field> getAllFields(Class clazz) {
        return getAllFieldsRec(clazz, new HashSet<Field>());
    }

    private static Set<Field> getAllFieldsRec(Class clazz, Set<Field> set) {
        Class superClazz = clazz.getSuperclass();
        if (superClazz != null) {
            getAllFieldsRec(superClazz, set);
        }
        set.addAll(Collections.set(clazz.getDeclaredFields()));
        return set;
    }


}
