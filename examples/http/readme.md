# Mule DSL HTTP examples

All examples presented here start an embedded HTTP server that listen on a specific port (all examples are using 8080) and them use some additional resources to process input and generate different output data and format.

Besides HTTP and its secure conterpart HTTPS, Mule also supports several other network protocols like TCP, UDP, XMPP and many others.

## OutputInputParamsAsXML.java

Example that starts an embedded http server on 8080 port and outputs the input request content as XML.

In this example we capture all requests (except `favicon.ico`) input and transform it into a xml format. To ignore the `favicon.ico` request we use a expression based filter as presented here:

```java
	filter(groovy("message.payload != \"/favicon.ico\"")) //don't process the browser request for `favicon.ico`
```

To convert the input params to xml all we need is use two, out of the box, transformers, as follows:

```java
	.transformWith(HttpRequestBodyToParamMap.class) //transform http request body to a map
	.transformWith(ObjectToXml.class) //transform the payload (map) to xml
```

It's also important to note that, to provide a qualified return data, it's important to set properly the http `Content-Type` header as shows the folling code:

```java
 	messageProperties().put("Content-Type", "text/xml"); //sets the output content-type to xml
```

To test it just starts the java code and point your web browser and add some params in URL, something like:

```
	http://localhost:8080/?param1=value1&param2=123&param3=content%20here
```

## OutputRequestDataIdAsJSON.java

Example that starts an embedded http server on 8080 port and, based on request `id` parameter, loads a data and output it as json.

This example, as the previous one, process all http requests  (except `favicon.ico`) and, after the filter, transforms the http request body to a map, as presented here:

```java
	transformWith(HttpRequestBodyToParamMap.class) //transform http request body to a map
```

After that it invokes a java class defining the method and the payload as argument:

```java
	invoke(QueryData.class) //invokes a java class
	    .methodAnnotatedWith(QueryData.MethodGetPerson.class) //defining the method that should be executed
	        .args(payload()) //with payload (map) as argument
```

If the `id` matches with some data, it returns the related data otherwise it returns an error message. So, after the java type invoke, the payload is converted to JSON and the content type is properly setted as follows:

```java
	transformWith(ObjectToJson.class) //transform the payload to json
	.messageProperties()
	    .put("Content-Type", "application/json"); //sets the output content-type to json
```

To test this example you should start the java code and, on your web browser, open some of these URLs:

```
	http://localhost:8080/?id=0
	http://localhost:8080/?id=1
	http://localhost:8080/?id=5
	http://localhost:8080/?id=15
	http://localhost:8080
```

## FormatOutputBasedOnRequestParam.java

Example that starts an embedded http server on 8080 port, invokes a java class to load a random data and format its output based on request `format` parameter. 

Following the same pattern of the previous, this example process all requests (except `favicon.ico`). Just after the filter we execute a java class `QueryData` and we define wich method to execute as presented here:

```java
	invoke(QueryData.class) //invokes a java class
	    .methodAnnotatedWith(named("randomData")) //defining the method that should be executed
	        .withoutArgs() //without args
```

Note: as this java component will be used later, we are using guice to bind its type to a specific instance:

```java
	bind(QueryData.class).toInstance(new QueryData()); //guice bind
```

The next step on our flow is define wich output it needs to generate, and to help us on this task, we'll use the `choice` router, and define on each `when` clausule the restriction that should be applyed. In this case those conditions are based on `format` request parameter. We also define that, if we don't have this parameter xml is the default value:

```java
	.choice() //router based on flow content
	    .when(groovy("message.getInboundProperty(\"format\", \"xml\").toLowerCase() == \"xml\"")) //if format is xml or omitted (default)
	        .transformWith(ObjectToXml.class) //transform payload (class Person) to XML
	        .messageProperties()
	            .put("Content-Type", "text/xml") //sets the output content-type to xml
	        .endMessageProperties()
	    .when(groovy("message.getInboundProperty(\"format\", \"\").toLowerCase() == \"json\"")) //if format is json
	        .transformWith(ObjectToJson.class) //transform payload (class Person) to JSON
	        .messageProperties()
	            .put("Content-Type", "application/json") //sets the output content-type to json
	        .endMessageProperties()
	    .otherwise() //format param is not supported
	        .invoke(QueryData.class).methodName("error").withoutArgs() //invokes the "error" method
	        .messageProperties()
	            .put("Content-Type", "text/plain") //sets the output content-type to text
	        .endMessageProperties()
	.endChoice();
```

Look that the `choice` router also has an `otherwise` clausule, that in this case is executed if the format is different than `xml` or `json`.

To test this example you should start the java code and, on your web browser, open some of these URLs:

```
	http://localhost:8080/?format=json
	http://localhost:8080/?format=xml
	http://localhost:8080/?format=none
	http://localhost:8080
```
