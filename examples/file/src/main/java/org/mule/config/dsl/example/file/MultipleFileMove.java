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
 * This examples polls a source folder and move that input file to three
 * different destiny folders.
 * <p/>
 * Note that the folder's path are defined using property placeholders that can
 * be configured the `path-resource.properties` properties file.
 *
 * @author porcelli
 */
public class MultipleFileMove {

    public static void main(String... args) throws MuleException {
        Mule.startMuleContext(new AbstractModule() { //start mule using an anonymous inner AbstractModule based class
            @Override
            protected void configure() {
                propertyResolver(classpath("path-resource.properties")); //set property resolver resource from classpath

                flow("multipleFileMove")
                        .from("file://${in.folder.path}") // source folder
                        .transformTo(byte[].class) //transform file to byte[]
                        .broadcast() //broadcast the payload to
                            .send("file://${out.folder.path}-1") //destiny folder
                            .send("file://${out.folder.path}-2") //destiny folder
                            .send("file://${out.folder.path}-3") //destiny folder
                        .endBroadcast();
            }
        });
    }
}
