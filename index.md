## Recreate Spring MVC Hello World example from TutorialsPoint

#### Why to study this example

To better understand Spring Boot it helps a lot to take a step back in history and study the Spring framework as it has been used before the introduction of Spring Boot in 2013. Very helpful for me was the [Spring Core Basic tutorial](https://www.tutorialspoint.com/spring/index.htm) on [TutorialsPoint][https://www.tutorialspoint.com]. Although the tutorial is somewhat outdated the core principles are well explained and illustrated with clear examples. I recommend trying to reproduce these examples and have them run correctly. To help you with this I give here an example of how I got the examples running. Also I summarize in simple terms how it helped me to understand better how the Spring framework works.

The examples in the tutorial do not use any dependency manager as Maven or Gradle. Actually I found it was a great exercise to try to reproduce the examples by adding all JAR dependencies by hand. It very quickly brings home to you why dependency managers exist in the first place. However recreating the examples without Maven is beyond the scope of this example.

So we do use Maven. In order to still get a firm idea of how it felt to develop a Spring application before Spring Boot existed I deliberately avoided all use of spring boot libraries as spring boot starters, Initializr, spring.io etc.

#### Target audience

You only need to have some basic understanding of Java, how to create a simple Java application and how to run it with the IntelliJ IDE. I avoided using theoretical concepts such as dependency injection, inversion-of-control, etc. and tried to explain everything as simple as possible, just enough to give you a basic idea of what is going on. You can find excellent in-depth information on Tutorialspoint.

Creating the Spring MVC Hello World example.

#### What do we want to create?

We want to create an application which allows us to show a web page, in other words we create a web application. The web page will show some text and time and date.

How do we create our application with Spring?

We are using the Java Spring framework. A core principle of the Spring framework is to contain our business logic as much as possible in simple Java classes. The instantiating of these classes and all needed functionality is taken care of by Spring behind the scene. These instantiated classes (components) are called Spring beans. The configuration of the beans can be done in an XML Spring configuration file. A characteristic of this approach is that we have a lot of functionality available from these Spring Beans without having to instantiate them ourselves. This can be confusing at first. We will see in our example how this is done in practice. 

Spring allows us to develop a web application using the MVC architecture. MVC stand for Model-View-Controller. This allows us to clearly separate the different tasks. One task is to show a web page (the view), another to create the content (the model). In this case the controller waits for a request from a web client. In other words, by entering a link in a browser that targets our application we trigger the controller which takes care of sending the data offered by the model to the view. The result is that a web page is shown in our browser with the data we send.

The functionality of this example is largely identical to [spring\_mvc\_hello\_world\_example](https://www.tutorialspoint.com/spring/spring_mvc_hello_world_example.htm) on Tutorialspoint.

There are some important differences:

\- We use as IDE IntelliJ instead of Eclipse

\- We use Maven dependency management, so we have to create a Maven project in IntelliJ. Keep in mind that Maven also creates a somewhat different project structure than is used in the original example on Tutorialspoint.

\- We run the application by using the imbedded tomcat server offered by the Apache tomcat maven plugin. This avoids having to export a WAR to a separately installed web server to be able to see the web page created by the web application.

#### Summary of steps

1. Create an IntelliJ Maven project with the name spring01-mvc-helloworld and create a package _nl.bvsit_ under the _src/main/java_ folder in the created project.

2. Adapt the content of the Maven pom.xml to have the necessary dependencies available.

3. Create under the folder main the folder structure /webapp/WEB-INF

4. Create Spring configuration files _web.xml_ and _HelloWeb-servlet.xml_ under the _webapp/WEB-INF_ folder.

5. Create a Java class _HelloController_ under the _nl.bvsit_ package.

6. Create a sub-folder with a name _jsp_ under the _Webapp/WEB-INF_ folder. Create a view file _hello.jsp_ under this sub-folder.

7. Create the content of all the source and configuration files as mentioned below.

8. Start the application by running in the terminal >mvn tomcat7:run

9. Browse to [http://localhost/HelloWeb/hello](http://localhost/HelloWeb/hello) to see the result.

#### All files to be created

Let’s have a look at the various files to create in some more detail.

First of all we have to adapt the Maven configuration file pom.xml In this file we add the dependencies our application needs. A dependency is one or combination of several Java libraries (JARs) our application needs to have some functionality available. In this case we need a.o. the Spring framework libraries to be able to run a web application, so in the pom.xml you will find the dependency called _spring-webmvc_ which consist in itself of several JARs.

It also describes several plugins. The _tomcat7-maven-plugin_ allows us to run our application in a temporary tomcat web server, i.e. it allows us to run the application by entering in the IntelliJ Terminal window the command “mvn tomcat7:run”.

**pom.xml**

````xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>nl.bvsit</groupId>
    <artifactId>HelloWeb</artifactId>
    <packaging>war</packaging>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <java.version>11</java.version>
        <maven.compiler.source>$JAVA.VERSION$</maven.compiler.source>
        <maven.compiler.target>$JAVA.VERSION$</maven.compiler.target>
    </properties>

    <dependencies>
        <!-- Spring MVC support -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.3.9</version>
        </dependency>

        <!-- Tag libs support for view layer -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
            <scope>runtime</scope>
        </dependency>
    </dependencies>
    <build>
        <!--BvS Do not include version in WAR name-->
        <finalName>${project.artifactId}</finalName>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.0</version>
                <configuration>
                    <release>11</release>  <!--or <release>10</release>-->
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.tomcat.maven</groupId>
                <artifactId>tomcat7-maven-plugin</artifactId>
                <version>2.2</version>
            </plugin>
        </plugins>
    </build>
</project>
````

Spring will look for the existence of XML files that can serve as configuration files. If it finds web.xml it will try to create a Spring bean of type DispatcherServlet This servlet is responsible for receiving requests and sending them to the controller. This servlet will run in a web server, in our case a Tomcat server.

**src/main/webapp/WEB-INF/web.xml**

````xml
<web-app id = "WebApp_ID" version = "2.4"
         xmlns = "http://java.sun.com/xml/ns/j2ee"
         xmlns:xsi = "http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation = "http://java.sun.com/xml/ns/j2ee
   http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd">

    <display-name>Spring MVC Application</display-name>

    <servlet>
        <servlet-name>HelloWeb</servlet-name>
        <servlet-class>
            org.springframework.web.servlet.DispatcherServlet
        </servlet-class>
        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>HelloWeb</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

</web-app>
````
Another Spring configuration file with typically a name ending on -servlet.xml  will create a Spring bean of type InternalResourceViewResolver. It allows to create a web page from a JSP file to be send on a request. Note that the JSP files have to exist under “/WEB-INF/jsp/” since this path is set as a property.

**src/main/webapp/WEB-INF/HelloWeb-servlet.xml**

````xml
<beans xmlns = "http://www.springframework.org/schema/beans"
       xmlns:context = "http://www.springframework.org/schema/context"
       xmlns:xsi = "http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation = "http://www.springframework.org/schema/beans
   http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
   http://www.springframework.org/schema/context
   http://www.springframework.org/schema/context/spring-context-3.0.xsd">

    <context:component-scan base-package = "nl.bvsit" />

    <bean class = "org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name = "prefix" value = "/WEB-INF/jsp/" />
        <property name = "suffix" value = ".jsp" />
    </bean>

</beans>
````
Just the same as with the original example on Tutorialspoint we use a Java Server Page file to create the view. JSP has some limitations which is why nowadays Spring recommends other HTML template engines and will use by default ThymeLeaf to create a web page view. In this example the variables _message_ and _time_ are passed by the HelloController.

 **src/main/webapp/WEB-INF/jsp/hello.jsp**

````jsp 
<%@ page contentType = "text/html; charset = UTF-8" %>
<html>
<head>
    <title>Hello World</title>
</head>

<body>
<h2>${message}</h2>
<h3>${time}</h3>
</body>
</html>
````

In HelloController.java we see some other typical characteristics of Spring. First of all there is the extensive use of annotations. Annotations are part of Java since a long time, i.e. the very common @Override annotation. Nowadays frameworks use annotations use extensively to signal to the compiler to compile the class in a specific way. To be able to do this it uses a process called reflection. By using reflection the compiler can for example create code which instantiates our classes with a certain name. The Spring framework contains its own specific annotations. The use of Spring annotations is an alternative to using XML files as a way to configure Spring Beans. In this case annotating the class with @Controller will have Spring create a Spring MVC Controller bean with the name helloController from the class (and all other necessary beans if needed).

As you can see the method printHello() returns a simple String. This string has to match the name of a view file, in this case hello.jsp .

**src/main/java/nl/bvsit/HelloController.java**

````java
package nl.bvsit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.ModelMap;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/hello")
public class HelloController {
    @RequestMapping(method = RequestMethod.GET)public String printHello(ModelMap model) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentTime = "Time : "+formatter.format(new Date());
        String message = "Spring MVC basic example. Refresh your browser to show the current time.";
        model.addAttribute("message", message);
        model.addAttribute("time",currentTime);
        return "hello";
    }
}

````

#### Running the application

Start the application by running in the IntelliJ Terminal window the command _mvn tomcat7:run_ Then browse to [http://localhost/HelloWeb/hello](http://localhost/HelloWeb/hello) to see the result.

In the original TutorialsPoint example the application is run by first compiling and exporting it as a WAR which then is run on a separately installed Tomcat server. This is still also possible with Maven as follows:

1. running in the IntelliJ Terminal window the command _mvn package_ This will create spring-01-mvc-helloworld\\target\\HelloWeb.war

2. Install a Tomcat server. In the installation directory you will find a directory webapps. Copy HelloWeb.war to this directory

3. Browse to [http://localhost/HelloWeb/hello](http://localhost/HelloWeb/hello) to see the result.

#### Links

[https://www.tutorialspoint.com/springmvc/index](https://www.tutorialspoint.com/springmvc/index)

[https://www.tutorialspoint.com/springmvc/springmvc/overview](https://www.tutorialspoint.com/springmvc/springmvc/overview)

