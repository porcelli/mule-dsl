# Mule DSL Send Email examples

Both examples presented here shows how to configure a DSL Flow to send emails and invoke it directly from your code using `org.mule.config.dsl.Mule` utility class.

Note that Mule DSL supports most email related protocols including SMTP, POP, IMAP and their secure conterparts SMTPS, POPS and IMAPS.

## SendEmail.java

Example that shows how to configure a Mule DSL flow to send emails using the SMTP protocol.

In this example we first create a local (and fake) send mail server that will handle the smtp protocol for us and themwe start Mule passing, as an argument, the Module that defines the flow responsible to send emails.

The `SendEmail` flow expects the mail body as payload and also some properies, most are explicit defined by the URI:

```java
	"smtp://#[header:user]:#[header:password]@#[header:host]:#[header:port]?address=#[header:address]"
```

To hold all those properties, we'll create a `Map<String, Object>` and fill it, as follow:

```java
	final Map<String, Object> properties = new HashMap<String, Object>(); //map that holds message properties

	properties.put("host", "localhost"); //smtp host server
	properties.put("port", port); //smtp port
	properties.put("user", "user1"); //username
	properties.put("password", "secret"); //password

	properties.put("subject", "Look this new Mule cool feature!"); //email subject
	properties.put("address", "mule_users@company.com"); //email address to send to
```

And finally, to invoke the flow, all we need is call the `process` method of `Mule` class:

```java
    myMule.flow("SendEmail").process(mailContent, properties);
```

Note that you can change this example to use your own smtp server, all you need to do is remove the line that starts the fake server (line 30) and set your smtp server details (lines 35-38).

## SendEmailUsingGMail.java

Example that shows how to configure a Mule DSL flow to send emails using Gmail.

This example is quite similar to the previous, except that we use a custom connector that handles for us all the necessary config to setup a GMail connection, all you need is setup your credentials.

The folling code show how we create and register the GMail custom connector:

```java
	//Custom connector necessary to use gmail smtp server
	final GmailSmtpConnector gmail = new GmailSmtpConnector(null);
	gmail.setAuthenticator(new SMTPAuthenticator()); //defines the smtp authentication
	register("GmailConnector", gmail); //register the connector
```

Note that to run this example you'll have to set your own Gmail user and password.