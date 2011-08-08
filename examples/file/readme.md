# Mule DSL File examples

All examples presented here solves, in different ways, a common integration problem: move files from a source to one or more destiny folders.

Those examples rely on Mule `file` protocol supported by Mule.


## SimpleFileMove.java

Example that polls a source folder and move every file from it to a destiny folder.

In this example we use some property placeholders, enabling users to configure the `path-resource.properties` file to define the source and destiny folder. Here is how we define the properties placeholder resolver:

```java
	propertyResolver(classpath("path-resource.properties"));
```

The flow itself is straightforward, as you can see:

```java
	flow("mySimpleFileMove")
        	.from("file://${in.folder.path}")
        	.send("file://${out.folder.path}");
```

## MultipleFileMove.java

Example that polls a source folder and move every file from it to three different destiny folders.

This example is a bit more sofisticate than the previous `SimpleFileMove`, due the use of two additional componenents: transformer and router.

We use the transformer to convert the payload (that is an InputStream) to a byte array, it's necessary 'cos it's not possible to move the same input file to more than one place, to resolve it we just need to get the input file content, transformed to a byte array.

Them we use a `broadcast` router that, based on payload (that now is a `byte[]`), copy the payload content to a bunch of different folders. 

But now you maybe asking yourself: "where is defined the output file name?". Easy! When we got the file from the inbound endpoint (`from(...)`) it sets, on message properties, some information and one of them is "filename", and this is the property used by `file` protocol to serialize the payload to a file.

## AdvancedFileMove.java

Example that polls a source folder and, after change the file content, move it to three different destiny folders.

Here in this example we added an additional complexity: before route the input file, we modify it.

To modify the input file content, first we tranform it to String and them apply another transformer, this time a expression based transformer, as shows here:

```java
	transform(string("**changed**\n#[payload]\n**changed**"))
```

Other important difference from previous example is that we set the output file name, based on original, here is how:

```java
	.messageProperties()
    	.put("filename", string("changed-#[header:originalFilename]")) //set filename, based on original
```

## DirectFlowAndMutippleFileMove.java

Example that defines a flow that polls a source folder and move it to three different destiny folders. The interesting part of this example is to show how to invoke the flow passing the payload (string or a file).

In this last example we explore how we can execute, directly from code, a flow that already has an inbound endpoint. In fact this is pretty simple, the flow is almost the same from previous examples.

To execute the flow passing a file, we just create it and use it as payload, as presented here:

```java
	final File tempFile = File.createTempFile("some", "temp.txt"); //creates a temp file
	final FileOutputStream out = new FileOutputStream(tempFile); //use FileOutputStream to write into it
	out.write("MY SIMPLE AD-HOC FILE!".getBytes()); //set some content
	out.close(); //close

	myMule.flow("multipleFileMove").process(tempFile); //executes the flow using tempFile as payload
```

You can also set a string as payload as follows:

```java
	myMule.flow("multipleFileMove").process("SOME DIRECT STRING CONTENT!"); //executes the flow using a string as payload
```
