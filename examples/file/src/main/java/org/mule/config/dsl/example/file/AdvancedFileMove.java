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

import static org.mule.config.dsl.expression.CoreExpr.string;

/**
 * This example polls a source folder and, after change the file content, move it
 * to three different destiny folders.
 * <p/>
 * Note that the folder's path are defined using property placeholders that can
 * be configured the `path-resource.properties` properties file.
 *
 * @author porcelli
 */
public class AdvancedFileMove {

    public static void main(String... args) throws MuleException {
        Mule myMule = Mule.newInstance(new AbstractModule() { //creates a new mule instance using an anonymous inner AbstractModule based class
            @Override
            protected void configure() {
                propertyResolver(classpath("path-resource.properties")); //set property resolver resource from classpath

                flow("myAdvancedFileMove")
                        .from("file://${in.folder.path}") // source folder
                            .transformTo(String.class) //transform file to String
                            .transform(string("**changed**\n#[payload]\n**changed**")) //modify the payload
                            .transformTo(byte[].class) // convert payload to byte[]
                        .messageProperties()
                            .put("filename", string("changed-#[header:originalFilename]")) //set filename, based on original
                        .broadcast() //broadcast the payload to
                            .send("file://${out.folder.path}-1") //destiny folder
                            .send("file://${out.folder.path}-2") //destiny folder
                            .send("file://${out.folder.path}-3") //destiny folder
                        .endBroadcast();
            }
        });

        myMule.start(); //start mule
    }
}
