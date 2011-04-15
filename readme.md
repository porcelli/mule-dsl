# Background

A subset of developers getting started with integration are looking to solve a specific problem and do so by integrating a little bit of integration code into their application. They don’t want to invest in an ESB server and a separate runtime and managing everything through external XML configuration, which can be very verbose. Open Source projects like [Spring Integration](http://www.springsource.org/spring-integration) and [Apache Camel](http://camel.apache.org/), already offer a more fluid way of doing this in code and as a result, these developers are migrating towards those solutions even though they may eventually need something more complete like Mule ESB. 

Today the only practical way of using Mule is via the Mule XML configuration mechanism which leverages spring custom namespace authoring.  This configuration mechanism is very powerful and with the introduction of flow is now much simpler and intuitive but it’s not necessarily the best choice for everyone or every use case.

By making an internal DSL available we cater for those that prefer programmatic configuration as well as ensure we are not left behind as other competing integration products push this their own DSL’s.  The primary reason though, is to open up the use of Mule to new usage scenarios and therefore increase adoption.

# Mule DSL Goals

To implement an internal Domain Specific Language (DSL) in Java that allows users to configure Mule ESB programmatically. The following characteristics are expected from the DSL:

 - Easy to write, read and learn
 - Hard to misuse
 - Extensible
 - Take advantage of IDE auto-complete
 - Avoid the use of Strings to reference domain objects
 - Impact as little as possible the actual codebase
 - Use the most modern techniques to build its structure

# DSL Scope and Boundaries

The expected DSL has its focus on Mule Community Edition (CE) and should cover:

 - Basic Mule Configuration (config attributes and default threading profiles)
 - Global Transformers, Filters, Processors and Processor Chains.
 - Connectors and Endpoints (both global generic endpoints and inbound/outbound specific endpoint).
 - Flows
 - Configuration Patterns (?)

Its also clear that its out of the this initial scope the following:

 - Services
 - Globally defined Interceptor-stacks
 - Component Interceptors, Bindings, and less common Entry-point resolvers
 - Enterprise Edition module, MuleForge Projects and Cloud Connectors

# Current Status

This is the first preview of the Mule DSL syntax ***and is still a working in progress***.

**Important:** Feedbacks are welcomed ;)

# Design Decisions

Although this is a preview version, it has some strong design decisions that influenced its construction. Here are the most prominent:

 - Hide complexity from users
 - Configuration based on modules
 - Extensive use of generics for type inference
 - Named args

## Hide complexity from users

Hide complexity from users is almost a common sense in API design, but in DSLs sometimes it's necessary to go extreme on this. To achieve readability sometimes it's necessary to cut (or hide) some options from users and consider convention over configuration. The real challenge here is identify what is possible to cut (or hide) and what is not.

An example of this simplification on this preview can be seen on custom components configuration, that is suppressing the explicit use of entry point resolvers (at least for now). Users can define a custom component execution using mule internal dsl thru execute method, like this:

	[...]// <-- flow definition supressed	
	execute(MyPojo.class)

If a custom component is declared like above, the default behavior will be: check if MyPojo is an instance of a Callable, if not use use reflection entry point resolver.
But what about users that whant to define a specific method to be executed? Simple... just annotate the method and point wich annotation mule should looking for, something like this:

	//using a specific annotation
	execute(MyPojo.class).methodAnnotatedWith(MyBusinessMethod2Execute.class)

	public class MyPojo {
	    public void method1() {
	    }
	    @MyBusinessMethod2Execute
	    public void method2() {
	    }
	}

or

	//using the javax.inject.Named annotation
	execute(My2ndPojo.class).methodAnnotatedWith(Names.named("myMethodToExecute"))

	public class My2ndPojo {
	    public void methodA() {
	    }
	    @Named("myMethodToExecute")
	    public void methodB() {
	    }
	}

*[this approach was inspired by [google-guice binding annotations](http://code.google.com/p/google-guice/wiki/BindingAnnotations)]*

#### *This topic helps to enforce the following goals:*

 - *Easy to write, read and learn*
 - *Hard to misuse*
 - *Use the most modern techniques to build its structure*

## Configuration based on modules

*[this funcionality is also inspired by [Google Guice](http://code.google.com/p/google-guice)]*

Besides the fact that mule internal dsl will use a well know idiom inspired from a popular java library, the module concept will bring to mule internal dsl portability on deployment scenarios.

A module is an aggragator that must be used to declare all necessary building blocks to define flows, endpoint, connector or any other configurable element. In practice a module is an abstract class that exposes a series of util methods and has an abstract method called **configure()** that must be overriden by users. This is a simple example:

	public class MyModule extends AbstractModule {
	    @Override
	    public void configure() {
			//connections, endpoints, flows are defined here
	    }
	}

As already mentioned modules will enable deployment portability of the user's application that can start using mule embedded and later can deploy it into mule server without touch any code at all. Of course its necessary to adapt the actual deployer to understand those modules.

#### *This topic helps to enforce the following goals:*

 - *Extensible*
 - *Avoid the use of Strings to reference domain objects*
 - *Use the most modern techniques to build its structure*

## Extensive use of generics for type inference

Although generics in java are erasure based, it's still possible to do impressive things with that. When building a fancy internal DSL it's important to drive users in a assertive path to avoid misuses - and extensice use of generics helps a lot. In this preview version, a good example of generics goodies is present on endpoint configuration. If you're configuring a generic endpoint like this:

	from(uri("salesforce://login(g1,g2);*query(g3,r1);"))

You will have just a few options to complement it like define a connector (connectWith method) or define a process request or response (processRequest or processResponse methods). 
But if you're configuring an endpoint using a specific protocol like HTTP, you'll have more options - all of them related to HTTP:

    //the listen associated with WS usage is specific to HTTP protocol
	from(HTTP.INBOUND).listen(host("0.0.0.0").port(8777).path("services/catalog")).using(WS.INBOUND).with(CatalogService.class)

#### *This topic helps to enforce the following goals:*

- *Easy to write, read and learn*
- *Hard to misuse*
- *Extensible*
- *Take advantage of IDE auto-complete*
- *Use the most modern techniques to build its structure*

## Named args (artificial)

Its well know that java syntax does not allow named parameters wich is an important resource to source code readability.

	//this is a definition of a transformer that references
	//an already defined transformer named transfRef
	transformeWith(ref: "transfRef")

But, using a bit of creativity and static methods, its possible to create something with almost the same effect:

	//now using the ref method that is a simple method
	transformeWith(ref("transfRef"))

Of course that there's always the risk that users aren't aware of this "ref" method and create something like this:

	//"wrong" usage of named arg
	transformeWith(new RefBuilder("transfRef"))

And note that, even using a this bad idiom to define a reference, it's much more clear than use a simple string.

Readability is not the unique advantage of this named args, it's also help on disambiguites on string parameters (ie. ref and uri).

	//defining an inbound endpoit based on uri
	from(uri("salesforce://someservice.here/xxx?"))

	//defining an inbound endpoit that references an already defined endpoint
	from(ref("mySalesForceEndpoint"))

#### *This topic helps to enforce the following goals:*

- *Easy to write, read and learn*
- *Hard to misuse*
- *Avoid the use of Strings to reference domain objects*
- *Use the most modern techniques to build its structure*

# Approaches

This first preview version comes in two different flavors: method chain and vargars. Each approach has its advantages and disagvantages that are briefly explored here.

## Method Chain

The method chain technique is kind of popular in java, some core java classes like StringBuilder uses this as we can see here:

	StringBuilder b = new StringBuilder();
	  b.append("Hello, ")
	   .append(" cruel ")
	   .append("World !");

Most internal DSLs use this approach that has the biggest advantage to give the user's, while typing (using a modern IDE that has an auto-complete feature) all the next options available.

Here is an example of the BookStore application written using the method chain approach.

	public static class BookStore extends AbstractModule {
	    @Override
	    public void configure() {
	        propertyPlaceholder("email.properties");

	        //Configure some properties to work with GMail's SMTP
	        connector(GMAIL.CONNECTOR, name("emailConnector"));

	        //Use this as a poor man's message queue, in the real world we would use JMS
	        connector(VM.CONNECTOR, name("vmQueues"));

	        //This queue contains a feed of the latest statistics generated by
	        // the Data Warehouse (it should really be a LIFO queue)
	        endpoint(VM.ENDPOINT, name("stats")).path("statistics");

	        TransformerBuilder setHtmlContentType = transformer(MessagePropertiesTransformer.class, name("setHtmlContentType"))
	                .addMessageProperty("Content-Type", "text/html")
	                        //Tomcat lowercases headers, need to drop this old one too
	                .deleteMessageProperty("content-type");

	        flow(name("CatalogService"))
	                //Public interface
	               .from(HTTP.INBOUND).listen(host("0.0.0.0").port(8777).path("services/catalog"))
	                        .using(WS.INBOUND).with(CatalogService.class)
	                //Administration interface
	               .from(uri("servlet://catalog"))
	                        .processRequest(
	                                //Convert request parameters to Book object
	                                pipeline().transformWith(HttpRequestToBook.class))
	                        .processResponse(
	                                //Format response to be a nice HTML page
	                                pipeline().transformWith(AddBookResponse.class)
	                                //Force text/html, otherwise it falls back to request
	                                // props, which have form-encoded one
	                                .transformWith(setHtmlContentType))
	        .execute(CatalogServiceImpl.class).asSingleton();

	        flow(name("OrderService"))
	                //Public interface
	                .from(HTTP.INBOUND).listen(host("0.0.0.0").port(8777).path("services/order"))
	                        .using(WS.INBOUND).with(OrderService.class)
	         .execute(OrderServiceImpl.class).asSingleton()
	         .send(VM.OUTBOUND).path("emailNotification")
	         .send(VM.OUTBOUND).path("dataWarehouse");

	        flow(name("EmailNotificationService"))
	               .from(VM.INBOUND).path("emailNotification")
	         .transformWith(OrderToEmailTransformer.class)
	         .transformWith(StringToEmailTransformer.class)
	         .send(SMTP.OUTBOUND)
	                        .secure()
	                        .user("${user}")
	                        .password("${password}")
	                        .host("${host}")
	                        .from("${from}")
	                        .subject("Your order has been placed!");

	        flow(name("DataWarehouse"))
	               .from(VM.INBOUND).path("dataWarehouse")
	         .execute(DataWarehouse.class).asSingleton()
	         .transformWith(setHtmlContentType)
	         .send(ref("stats"));
	    }
	}

**Important to note**: reading the code is just a small part of the API evaluation, you should try it using in your own IDE. To experiment this syntax all that you need is extend the org.mule.config.dsl.method\_chain.AbstractModule class and import all elements defined into  org.mule.config.dsl.method_chain.TempModel class. Just like this:

	import org.mule.config.dsl.method_chain.AbstractModule;
	import org.mule.config.dsl.method_chain.TempModel.*;

	public class MyModuleExample extends AbstractModule {
		@Override
        public void configure() {
            //insert your code here
        }
	}

### Advantages

 1. Well know idiom, most java developers are familiar with
 2. All the options that users can take are displayed on every method invocation (in IDE)
 3. Most commons internal DSL format

### Disadvantages

 1. A bad consequence of display to user all possible paths, is that you show a huge list of options that can confuse users.
 2. Hard to read
 3. Hard to implement
 4. Furure new features can impact the method chain

## Varargs

Vargars, or variable arguments, was introduced in Java 5 (back in 2004) and enables users declare that a method can take a variable number of parameters for a given argument.
In this preview version of mule internal DSL, we can take advantage of this variable number of parameters to create pipelines or define inbound endpoints of a flow. But to avoid the verbosity of java type declaration for each element defined that uses the varagars, it's necessary provide some util methods that internally creates those elements for us. Check this example:

	//convetional that uses varargs
	.process(new MyTransformer(), new FilterX(), ...)

	//vargars + util methods
	.process(transformWith(MyTransformer.class), filterBy(String.class), ...)

Here is the bookstore example that uses the varargs approach:

	public class BookStore extends AbstractModule {
	    @Override
	    public void configure() {
	        propertyPlaceholder("email.properties");

	        //Configure some properties to work with GMail's SMTP
	        connector(GMAIL.CONNECTOR, name("emailConnector"));

	        //Use this as a poor man's message queue, in the real world we would use JMS
	        connector(VM.CONNECTOR, name("vmQueues"));

	        //This queue contains a feed of the latest statistics generated by
	        // the Data Warehouse (it should really be a LIFO queue)
	        endpoint(VM.ENDPOINT, name("stats")).path("statistics");

	        TransformerBuilder setHtmlContentType = transformer(MessagePropertiesTransformer.class, name("setHtmlContentType"))
	                .addMessageProperty("Content-Type", "text/html")
	                        //Tomcat lowercases headers, need to drop this old one too
	                .deleteMessageProperty("content-type");

	        flow(name("CatalogService")).in(
	                //Public interface
	                from(HTTP.INBOUND).listen(host("0.0.0.0").port(8777).path("services/catalog"))
	                        .using(WS.INBOUND).with(CatalogService.class),

	                //Administration interface
	                from(uri("servlet://catalog"))
	                        .processRequest(
	                                //Convert request parameters to Book object
	                                transformWith(HttpRequestToBook.class))
	                        .processResponse(
	                                //Format response to be a nice HTML page
	                                transformWith(AddBookResponse.class),
	                                //Force text/html, otherwise it falls back to request
	                                // props, which have form-encoded one
	                                transformWith(setHtmlContentType))
	        ).process(
	                execute(CatalogServiceImpl.class).asSingleton()
	        );

	        flow(name("OrderService")).in(
	                //Public interface
	                from(HTTP.INBOUND).listen(host("0.0.0.0").port(8777).path("services/order"))
	                        .using(WS.INBOUND).with(OrderService.class)
	        ).process(
	                execute(OrderServiceImpl.class).asSingleton(),
	                send(VM.OUTBOUND).path("emailNotification"),
	                send(VM.OUTBOUND).path("dataWarehouse")
	        );


	        flow(name("EmailNotificationService")).in(
	                from(VM.INBOUND).path("emailNotification")
	        ).process(
	                transformWith(OrderToEmailTransformer.class),
	                transformWith(StringToEmailTransformer.class),
	                send(SMTP.OUTBOUND)
	                        .secure()
	                        .user("${user}")
	                        .password("${password}")
	                        .host("${host}")
	                        .from("${from}")
	                        .subject("Your order has been placed!")
	        );

	        flow(name("DataWarehouse")).in(
	                from(VM.INBOUND).path("dataWarehouse")
	        ).process(
	                execute(DataWarehouse.class).asSingleton(),
	                transformWith(setHtmlContentType),
	                send(ref("stats"))
	        );
	    }
	}

**Important to note**: as already mentioned on previous approach, it's important to try the api by yourself using in your preferred IDE. To experiment this syntax all that you need is extend the org.mule.config.dsl.vargars.AbstractModule class and import all elements defined into  org.mule.config.dsl.method_chain.TempModel class. Just like this:

		import org.mule.config.dsl.vargars.AbstractModule;
		import org.mule.config.dsl.vargars.TempModel.*;

		public class MyModuleExample extends AbstractModule {
			@Override
	        public void configure() {
	            //insert your code here
	        }
		}

### Advantages

 1. Better reading expirience
 2. Easier to implement and extend
 3. Easy to configure the building blocks, the options are focused on that configuration piece

### Disadvantages

 1. Static imports or internal methods aren't the most intuitive way to expose the building blocks

## Missing Features

 - <del>Flows</del>
 - <del>Protocols</del>
 - <del>Components</del>
 - <del>Connectors</del>
 - <del>Endpoints: Global, Inbound & Outbound</del>
 - <del>Transformers</del>
 - <del>Filters</del>
 - <del>Routers</del>
 - <del>Loggers</del>
 - <del>Request and Response Processing</del>
 - <del>Splitters</del>
 - <del>Aggregators</del>
 - *Expression* Filters & Transformers
 - Security Filter
 - Exception Handler
 - Transaction Behavior
 - Thread Profile
 - Agent
 - Application Definition/Configuration/Default Behavior

The following features are out of scope, but can be provided (for free!) by Guice:

 - [Interceptors](http://code.google.com/p/google-guice/wiki/AOP)
 - [Bindings](http://code.google.com/p/google-guice/wiki/Bindings)

**Note:** those features that are already implemented are just samples/mocks. Most of them still need improvements.

# References

## Articles & Blogs

 - [Video] [Introduction to Domain Specific Languages @ JAOO](http://www.infoq.com/presentations/domain-specific-languages) by Martin Fowler
 - [An Approach to Internal Domain-Specific Languages in Java](http://www.infoq.com/articles/internal-dsls-java) by Alex Ruiz and Jeff Bay
 - [Dsl Boundary](http://martinfowler.com/bliki/DslBoundary.html) by Martin Fowler
 - [Domain Specific Language](http://martinfowler.com/bliki/DomainSpecificLanguage.html) by Martin Fowler
 - [Fluent Interface](http://martinfowler.com/bliki/FluentInterface.html) by Martin Fowler
 - [Simulating named parameters in Java](http://www.jroller.com/alexRuiz/entry/simulating_named_parameters_in_java) by Alex Ruiz

## Libs

 - [mockito](http://mockito.org)
 - [fest-assert](http://docs.codehaus.org/display/FEST/Fluent+Assertions+Module)
 - [query dsl](http://www.querydsl.com)
 - <del>[camel](http://camel.apache.org)</del> - oops! i did it again ;)

## Books

 - [DSL in Action](http://www.manning.com/ghosh/)
 - [Domain Specific Languages](http://martinfowler.com/books.html#dsl)
