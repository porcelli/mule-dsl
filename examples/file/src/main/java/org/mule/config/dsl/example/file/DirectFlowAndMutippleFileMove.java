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
import org.mule.transport.file.transformers.FileToByteArray;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.mule.config.dsl.expression.ScriptingExpr.groovy;

/**
 * This example defines a flow that polls a source folder and move it to
 * three different destiny folders. The interesting part of this example
 * is to show how to invoke the flow passing the payload (string or a file).
 * <p/>
 * Note that the folder's path are defined using property placeholders that can
 * be configured the `path-resource.properties` properties file.
 *
 * @author porcelli
 */
public class DirectFlowAndMutippleFileMove {

    public static void main(String... args) throws MuleException, IOException {
        Mule myMule = new Mule(new AbstractModule() { //creates a new mule instance using an anonymous inner AbstractModule based class
            @Override
            protected void configure() {
                propertyResolver(classpath("path-resource.properties")); //set property resolver resource from classpath

                flow("multipleFileMove")
                        .from("file://${in.folder.path}")  // source folder
                        .transformWith(FileToByteArray.class)  //transform file to byte[]
                        .messageProperties() //set filename, based on original or set `newname.txt` if there isn't an original one
                            .put("filename", groovy("message.getOutboundProperty(\"originalFilename\", \"newname.txt\")"))
                        .broadcast() //broadcast the payload to
                            .send("file://${out.folder.path}-1") //destiny folder
                            .send("file://${out.folder.path}-2") //destiny folder
                            .send("file://${out.folder.path}-3") //destiny folder
                        .endBroadcast();

            }
        });

        myMule.start(); //start mule

        File tempFile = File.createTempFile("some", "temp.txt"); //creates a temp file
        FileOutputStream out = new FileOutputStream(tempFile); //use FileOutputStream to write into it
        out.write("MY SIMPLE AD-HOC FILE!".getBytes()); //set some content
        out.close(); //close

        myMule.flow("multipleFileMove").process(tempFile); //executes the flow using tempFile as payload
        myMule.flow("multipleFileMove").process("SOME DIRECT STRING CONTENT!"); //executes the flow using a string as payload
    }
}
