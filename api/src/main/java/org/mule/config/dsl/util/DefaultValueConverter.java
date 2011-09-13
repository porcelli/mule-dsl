/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.util;

public final class DefaultValueConverter {

    private DefaultValueConverter() {
    }

    public static <T> T valueOf(Class<T> type, String s) {

        if (s == null) {
            throw new NullPointerException();
        }

        if (Byte.class.isAssignableFrom(type)) {
            return (T) Byte.valueOf(s);
        }

        if (Short.class.isAssignableFrom(type)) {
            return (T) Short.valueOf(s);
        }

        if (Integer.class.isAssignableFrom(type)) {
            return (T) Integer.valueOf(s);
        }

        if (Long.class.isAssignableFrom(type)) {
            return (T) Long.valueOf(s);
        }

        if (Float.class.isAssignableFrom(type)) {
            return (T) Float.valueOf(s);
        }

        if (Double.class.isAssignableFrom(type)) {
            return (T) Double.valueOf(s);
        }

        if (Character.class.isAssignableFrom(type)) {
            if (s.length() > 0) {
                return (T) new Character(s.charAt(0));
            }
            return (T) new Character('\u0000');
        }

        if (Boolean.class.isAssignableFrom(type)) {
            return (T) Boolean.valueOf(s);
        }

        if (String.class.isAssignableFrom(type)) {
            return (T) s;
        }

        if (type.isEnum()) {
            return (T) Enum.valueOf((Class<java.lang.Enum>) type, s);
        }

        return null;
    }

}
