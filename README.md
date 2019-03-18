# GRPC_SPRING_BOOT
Integration of Grpc api with spring boot using rest service  as wrapper

Steps To run the above sample hello world project:
Step 1 : Do the maven build
          mvn clean install -DskipTests

Step 2 : Run the HelloWorldMain class

Step 3 : To test if service is running fine or not, make a GET request to 
        localhost:8080/hello
        You should get a response.

About GRPC

What Is gRPC?
gRPC is a high performance, open-source universal RPC framework. By default it uses Protocol Buffers to define exposed services.

The framework provides features such as bidirectional streaming and has support for many different programming languages.

gRPC was initially developed at Google and is now licensed under Apache 2.0.

2. Defining a Service Using Protocol Buffers
gRPC services are defined using protocol buffers. These are Google’s language-neutral, platform-neutral, extensible mechanism for serializing structured data.

You specify how you want the information you’re serializing to be structured by defining protocol buffer message types in .proto files. Each protocol buffer message is a small logical record of information, containing a series of name-value pairs.

For this example, we define a first message containing information about a Person and a second message containing a Greeting. Both are then used in a sayHello() RPC method that takes the person message from the client and returns a greeting from the server.

We also define the version of the protocol buffers language that is used (proto3) in addition to package name and an option that enables the generation of separate files for different classes.

For more information check the protocol buffers language guide.

The below protocol buffer file is stored in src/main/proto/HelloWorld.proto.

syntax = "proto3";

option java_multiple_files = true;
package com.sample.helloworld;

message Person {
  string first_name = 1;
  string last_name = 2;
}

message Greeting {
  string message = 1;
}

service HelloWorldService {
  rpc sayHello (Person) returns (Greeting);
}
Now that we have defined how the data is structured we need to generate source code that allows us to easily write and read protobuf messages using Java. We will do this in the next section using a Maven plugin.

3. General Project Setup
We will use the following tools/frameworks:

gRPC 1.16
Spring Boot 2.1
Maven 3.5

4. Maven Setup
We build and run our example using Maven. If not already the case make sure to download and install Apache Maven.

Shown below is the XML representation of our Maven project in a POM file. It contains the needed dependencies for compiling and running the example.

In order to configure and expose the Hello World gRPC service endpoint, we will use the Spring Boot project.


To facilitate the management of the different Spring dependencies, Spring Boot Starters are used. These are a set of convenient dependency descriptors that you can include in your application.

We include the spring-boot-starter-web dependency which automatically sets up an embedded Apache Tomcat that will host our gRPC service endpoint.

The spring-boot-starter-test includes the dependencies for testing Spring Boot applications with libraries that include JUnit, Hamcrest and Mockito.

The Spring boot starter for gRPC framework auto-configures and runs an embedded gRPC server with @GRpcService enabled Beans as part of a Spring Boot application. The starter supports both Spring Boot version 1.5.X and 2.X.X. We enable it by including the grpc-spring-boot-starter dependency.

Protocol buffers support generated code in a number of programming languages. This tutorial focuses on Java.


There are multiple ways to generate the protobuf-based code and in this example we will use the protobuf-maven-plugin as documented on the grpc-java GitHub page.

We also include the os-maven-plugin extension that generates various useful platform-dependent project properties. This information is needed as the Protocol Buffer compiler is native code. In other words, the protobuf-maven-plugin needs to fetch the correct compiler for the platform it is running on.

Finally, the plugins section includes the spring-boot-maven-plugin. This allows us to build a single, runnable uber-jar. This is a convenient way to execute and transport our code. Also, the plugin allows us to start the example via a Maven command.

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.hello</groupId>
  <artifactId>grpc-java-hello-world</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <packaging>jar</packaging>
  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.1.0.RELEASE</version>
    <relativePath /> <!-- lookup parent from repository -->
  </parent>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    <java.version>1.8</java.version>
    <grpc-spring-boot-starter.version>3.0.0</grpc-spring-boot-starter.version>
    <os-maven-plugin.version>1.6.1</os-maven-plugin.version>
    <protobuf-maven-plugin.version>0.6.1</protobuf-maven-plugin.version>
  </properties>

  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
      <groupId>io.github.lognet</groupId>
      <artifactId>grpc-spring-boot-starter</artifactId>
      <version>${grpc-spring-boot-starter.version}</version>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>
  </dependencies>

  <build>
    <extensions>
      <extension>
        <groupId>kr.motd.maven</groupId>
        <artifactId>os-maven-plugin</artifactId>
        <version>${os-maven-plugin.version}</version>
      </extension>
    </extensions>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
      </plugin>
      <plugin>
        <groupId>org.xolstice.maven.plugins</groupId>
        <artifactId>protobuf-maven-plugin</artifactId>
        <version>${protobuf-maven-plugin.version}</version>
        <configuration>
          <protocArtifact>com.google.protobuf:protoc:3.5.1-1:exe:${os.detected.classifier}</protocArtifact>
          <pluginId>grpc-java</pluginId>
          <pluginArtifact>io.grpc:protoc-gen-grpc-java:1.16.1:exe:${os.detected.classifier}</pluginArtifact>
        </configuration>
        <executions>
          <execution>
            <goals>
              <goal>compile</goal>
              <goal>compile-custom</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>

</project>
The protobuf-maven-plugin will generate Java artifacts for the HelloWorld.proto file located in src/main/proto/ (this is the default location the plugin uses).

Execute following Maven command, and the different message and service classes should be generated under target/generated-sources/protobuf/.

mvn compile

