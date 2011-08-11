/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.example.http.support;

import com.google.inject.name.Named;
import org.mule.config.dsl.example.http.model.Person;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import java.util.Random;

/**
 * Support class that creates some data and make it available to be queried.
 *
 * @author porcelli
 */
public class QueryData {

    private final Person[] persons;
    private final Random random;

    public QueryData() {
        this.persons = new Person[]{new Person("John", 20),
                new Person("Helen of Troy", 28),
                new Person("Bilbo Baggins", 80),
                new Person("Princess Leia", 22),
                new Person("Chewbacca", 35),
                new Person("Alexsander, the Great", 30)};
        random = new Random();
    }

    @Named("randomData")
    public Object randomData() {
        return persons[random.nextInt(this.persons.length)];
    }

    @MethodGetPerson
    public Object getPerson(Map<String, Object> payload) {
        if (payload.containsKey("id")) {
            try {
                int id = Integer.valueOf(payload.get("id").toString());
                if (id >= persons.length) {
                    return "ID '" + id + "' not found!";
                }
                return persons[id];
            } catch (NumberFormatException ex) {
                return "ERR: ID param must be an integer!";
            }
        }
        return "ERR: Param ID must be provided!";
    }

    public String error() {
        return "ERROR!";
    }

    /**
     * Simple annotation that "marks" a method.
     *
     * @author porcelli
     */
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface MethodGetPerson {
    }
}

