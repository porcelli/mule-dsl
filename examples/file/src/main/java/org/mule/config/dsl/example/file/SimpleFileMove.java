/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.example.file;

import org.mule.api.MuleException;
import org.mule.config.dsl.AbstractModule;
import org.mule.config.dsl.Mule;

/**
 * Example that polls a source folder and move every file to a destiny folder.
 * <p/>
 * Note that the folder's path are defined using property placeholders that can
 * be configured the `path-resource.properties` properties file.
 *
 * @author porcelli
 */
public class SimpleFileMove {

    public static void main(String... args) throws MuleException {
        Mule myMule = Mule.newInstance(new AbstractModule() { //creates a new mule instance using an anonymous inner AbstractModule based class
            @Override
            protected void configure() {
                propertyResolver(classpath("path-resource.properties")); //set property resolver resource from classpath

                flow("mySimpleFileMove")
                        .from("file://${in.folder.path}") // source folder
                        .send("file://${out.folder.path}"); //destiny folder
            }
        });

        myMule.start(); //start mule
    }

}
