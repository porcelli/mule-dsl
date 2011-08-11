# Description

Mule is a lightweight java-based enterprise service bus (ESB) and integration platform that allows developers connect their applications together quickly and easily, enabling them to exchange data. Mule also provides an extensive out-of-the-box set of components that covers most common integration scenarios and, in complement, enables users create and use their own components (basically POJOs).

Until now, to setup and wire those components to build an integration solution, users had to create an external XML configuration file, which is a very powerful mechanism but not necessarily the best choice for everyone or every use case.

Mule DSL provides an alternative to XML, offering a simple and easy to use java based Domain Specific Language that helps users configure their integration flows. This project relies on Google Guice framework, given users all the power of a programmatic DI framework to configure their flow dependencies, a similar approach of Mule ESB with XML configuration file that relies on Spring Framework.

Here is a small Mule DSL code snippet that shows how easy is to build a flow:

```java
public static void main(String... args) throws MuleException {
    Mule myMule = Mule.newInstance(new AbstractModule() {
        @Override
        protected void configure() {
            flow("SimpleFilePollAndMove")
                    .from("file:///opt/my_app/in") // source folder
                    .send("file:///opt/other_app/in"); //destiny folder
        }
    });

    myMule.start(); //start mule
}
```

The above example just bridges files, polling the source folder and moving every file from there to the destiny.

# Documentation

For a very quick start check this [two minutes tutorial](https://github.com/mulesoft/mule-dsl/wiki). You can also explore mule dsl features by reading the [Users Guide](https://github.com/mulesoft/mule-dsl/wiki/User-Guide) and check the complete list of available [Building Blocks](https://github.com/mulesoft/mule-dsl/wiki/Building-Blocks).
