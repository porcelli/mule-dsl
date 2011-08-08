# Mule DSL REST examples

The examples presented here shows how to configure a Mule DSL flow to invoke a rest based service, that can be invoked directly from your code using `org.mule.config.dsl.Mule` utility class or exposed via HTTP. Those examples also present how you can re-use and compose your configuration modules.

Note that, differently from most Mule DSL, all examples presented here defines those modules in a different java file and group them into `module` package.

Those examples rely on Mule `http` protocol that is supported by Mule.

## StockQuotesModule.java (+ InvokeRestService.java)

Example that executes a stock quote rest service and them transform its return into a plain old java object (pojo).

In this example we define a flow that is expected to be executed directly from source code using the `Mule` class, and expect as its parameter (payload) the stock symbol, as follows:

```java
	StockQuote quote = myMule.flow("GetQuote").process("IBM").getPayloadAs(StockQuote.class);
```

Now let's understand how we build the flow to execute it. First we had to transform the payload to the format expected by the service, to execute this transformation we use a simple expression:

```java
	transform(string("symbol=#[payload]")) //transform payload to expected service format
```

Them we execute the rest service (note that before execute the rest service, we adjust the http `Content-Type` header to a proper format):

```java
	messageProperties()
	    .put("Content-Type", "application/x-www-form-urlencoded") //define the content type to rest app
	.send("http://www.webservicex.net/stockquote.asmx/GetQuote?method=POST") //uri of rest service + http verb to be used (post)
```

After the rest invocation we get a simple text string, that should be "cast" to xml, for this we use the `XmlEntityDecoder` class. Now with a xml content in the payload we'll use a XSLT transformer to adjust it's format to a format that we can later convert to a java object.

```java
	.transformWith(XsltTransformer.class) //transform, using xslt (guice bind), the payload to a xml format of a StockQuote class
```

In fact the `XsltTransformer` is binded by guice to an already configured instance, defined as follows:

```java
	XsltTransformer xslt = new XsltTransformer(); //defines the xslt transformer configuration
	xslt.setXslt(classpath("rest-stock.xsl").getAsString()); //load xslt content from classpath file
	bind(XsltTransformer.class).toInstance(xslt); //binds XsltTransformer to a specific intance
```

Last, but not least, we convert the payload (well formatted xml) to an object:

```java
	transformWith(XmlToObject.class); //transform payload to an instance of StockQuote class
```

## StockQuotesModule.java (+ InvokeAndTweetStockQuotes.java)

Example that executes a stock quote rest service, transform its return into a plain old java object (pojo) and them tweets it.

This example is quite similar to the previous, except that we, re-use the previous flow and tweets its result using a simple java component. Here is how we can execute other flows in Mule DSL:

```java
	executeFlow("GetQuote") //executes the `GetQuote` flow
```

And, as mentioned, all we need after this execution is invoke a java type to tweet the content for us:

```java
	invoke(TweetIt.class); //tweets the payload (StockQuote instance)
```

Look that we don't have to define wich method the flow should execute, in this case Mule DSL will "magically" find the correct method and will execute it.

Note that we're re-using flows that, in this case, both are defined in the same module.

## StockQuotesRestServiceModule.java (+ RestServerThatTweetsStockQuotes.java)

Example that starts an embedded http server on 8080 port that expose a rest service that once invoked query another stock quote rest service, transform its return into a plain old java object (pojo), tweets it and finally returns the payloas (pojo) in json format.

In this example we re-use the previous defined flow, but expose it thru a local rest services defined by an embedded http server on 8080. As we already presented by `http examples`, here is how we start an embedded http server that listen on 8080 port:

```java
	from("http://localhost:8080") //embedded http server that listen on 8080 port
```

To expose and process a REST services, we'll use a filter that helps us on this, here it's definition:

```java
	UriTemplateFilter restFilter = new UriTemplateFilter(); //defines a rest filter
	restFilter.setPattern("/stock/{set-payload.id}"); //defines the rest pattern - note that it sets the id to payload
	restFilter.setVerbs("GET"); //define the supported http verb
```

Note that the pattern defined also sets the expected `id` parameter as payload. Here is how we use it on our flow:

```java
	filterWith(restFilter) //filter the request using the rest filter
```

Next step all we need is execute the previous example flow ("TweetStockQuote") and transform the result payload to json format, as presented here:

```java
	transformWith(ObjectToJson.class) //transform tha payload (StockQuote instance) to json
	.messageProperties()
	    .put("Content-Type", "application/json"); //sets the output content-type to json
```

Note that we're re-using a flow that, in this case, isn't defined in the same module. And to enable it we must start Mule passing both modules as arguments, as show here:

```java
	new Mule(new StockQuotesRestServiceModule(), new StockQuotesModule()).start();
```

To test this example you should start the java code and, on your web browser, open some of these URLs:

```
	http://localhost:8080/stock/GOOG
	http://localhost:8080/stock/IBM
	http://localhost:8080/stock/WMT
```
