# Background

A subset of developers getting started with integration are looking to solve a specific problem and do so by integrating a little bit of integration code into their application. They don’t want to invest in an ESB server and a separate runtime and managing everything through external XML configuration, which can be very verbose. Open Source projects like [Spring Integration](http://www.springsource.org/spring-integration) and [Apache Camel](http://camel.apache.org/), already offer a more fluid way of doing this in code and as a result, these developers are migrating towards those solutions even though they may eventually need something more complete like Mule ESB. 

Today the only practical way of using Mule is via the Mule XML configuration mechanism which leverages spring custom namespace authoring.  This configuration mechanism is very powerful and with the introduction of flow is now much simpler and intuitive but it’s not necessarily the best choice for everyone or every use case.

By making an internal DSL available we cater for those that prefer programmatic configuration as well as ensure we are not left behind as other competing integration products push this their own DSL’s.  The primary reason though, is to open up the use of Mule to new usage scenarios and therefore increase adoption.

# Mule DSL Goals

To implement an internal Domain Specific Language (DSL) in Java (also know as a fluent API) that allows users to configure Mule ESB programmatically. The following characteristics are expected from the DSL:

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

This is the first preview of the Mule DSL syntax *and is still a working in progress*.

**Important:** Feedbacks are welcomed ;)

# Design Decisions

Although this is a preview version, it has some strong design decisions that influenced its construction. Here are the most prominent:

 - Hide complexity from users
 - Configuration based on modules
 - Extensive use of generics for type inference
 - Specializations publish it's available extensions
 - Named args

## Hide complexity from users

Hide complexity from user is almost a common sense in API design, but in DSLs sometimes it's necessary to go to extreme on this. To achieve readability sometimes it's necessary to cut (or hide) some options from users and consider convention over configuration.
The real chalange here is identify what is possible to cut (or hide) and what is not.

An example of this simplification on this preview version can be seen on custom components configuration, that for now is supressing the explicit definition of entry point resolvers. On mule internal DSL users can define a custom compoenent execution using the execute method, like this:

	[...]
	execute(MyPojo.class)
	[...]

If the user specify just it, the default behavior will be: check if MyPojo is an instance of a Callable, if not, it will use (behind the scenes) the reflection based entry point resolver. 
But what about if user whant define a specific method to be executed? Simple... just need to use an annotation to define wich method to execute like this:

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

[this approach was inspired by [google-guice binding annotations](http://code.google.com/p/google-guice/wiki/BindingAnnotations)]

*This topic helps to enforce the following elements:*

 - *Easy to write, read and learn*
 - *Hard to misuse*

**Note:** This is the most abstract concept descrived in this documentation, the next sections are more practical and hand-on based concepts.

## Configuration based on modules

This is another funcionality inspired by [Google Guice](http://code.google.com/p/google-guice). Besides the fact that mule internal dsl will use a well know idiom inspired from a popular java library, the module concept will bring to mule internal dsl portability.

The module is an aggragator that is used to declare all the building blocks necessary to build flows and other mule related condifugations. In practive a module is just an abstract class that exposes a series of util methods that has an abstract method called **condigure** that must be overriden. This is a simple example of it:

	public class MyModule extends AbstractModule {
	    @Override
	    public void configure() {
			//build blocks defined here
	    }
	}

*This topic helps to enforce the following elements:*

 - *Extensible*
 - *Avoid the use of Strings to reference domain objects*
 - *Use the most modern techniques to build its structure*


### How to use modules

This is still not defined, but here are some ideas of how to use it:

	//as parameter on mule context constructor
	MuleContext context = new DSLMuleContext(new ModuleA(), new ModuleB(), ...)


	The module concept  is 

	just simple abstract class that defines a set of util method and 

	it's abstract method

	to constuct flows and other configurations that has the configure method that 



	One of the biggest advantages of this approach is that it'll enable applications that started to use mule embedded to run it easily on Mule Server.


## Extensive use of generics for type inference

Although generics in java are erasure based, it's still possible to do impressive things with that. When building a nice to use internal DSL, one goal is to drive user's thru the API give to them just the options based on a context.

Using generics it's possible to give user's a set of possible actions based on his previous choices. An example of this is when you try to configure an endpoint. If you're configuring a generic endpoint, you have few choices as show here:

But if you're configuring a specific endpoint, based on HTTP for instance, you'll have several http specific options:


This topic helps to enforce the following elements:

- Easy to write, read and learn
- Hard to misuse
- Extensible
- Take advantage of IDE auto-complete
- Use the most modern techniques to build its structure


## Specializations publish it's available extensions


This topic helps to enforce the following elements:

- Extensible
- Take advantage of IDE auto-complete


## Named args (artificial)

Its well know that java syntax does not allow named parameters wich is an important resource to source code readability.

This is just a simple code convention used on this preview dsl. One goal of a 


This topic helps to enforce the following elements:

- Easy to write, read and learn
- Hard to misuse
- Avoid the use of Strings to reference domain objects
- Use the most modern techniques to build its structure

# Approaches

This first preview version comes in two different flavors: method chain and vargars. Each approach has its advantages and disagvantages that are briefly explored here.

## Method Chain

Method chain is one of the most popular

    StringBuilder b = new StringBuilder();
      b.append("Hello, ")
       .append(" cruel ")
       .append("World !");

### Advantages

### Disadvantages

### Known issues

## Varargs

### Advantages

### Disadvantages

### Known issues

## Features

 - Flows
 - Protocols
 - Components
 - Connectors
 - Endpoints: Global, Inbound & Outbound
 - Transformers
 - Filters
 - Routers
 - Loggers
 - Request and Response Processing
 - Interceptors
 - Splitters
 - Aggregators
 - *Expression* Filters & Transformers
 - Application Definition/Configuration
 - Security Filter
 - Exception Handler
 - Transaction Behavior
 - Thread Profile
 - Default Behavior
 - Agent

**Note:** those features that are already implemented are just samples/mocks. Most of them still need improvements.

# References

## Libs

 - [mockito](http://mockito.org)
 - [fest-assert](http://docs.codehaus.org/display/FEST/Fluent+Assertions+Module)
 - [query dsl](http://www.querydsl.com)
 - <del>[camel](http://camel.apache.org)</del> - oops! i did it again ;)

## Books

 - [DSL in Action](http://www.manning.com/ghosh/)
 - [Domain Specific Languages](http://martinfowler.com/books.html#dsl)

# Glossary
