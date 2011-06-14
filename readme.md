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

# Scope and Boundaries

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

# Documentation

For a very quick start check this [two minutes tutorial](https://github.com/mulesoft/mule-dsl/wiki). You can also explore mule dsl features by reading the [Users Guide](https://github.com/mulesoft/mule-dsl/wiki/User-Guide) and check the complete list of available [Building Blocks](https://github.com/mulesoft/mule-dsl/wiki/Building-Blocks).