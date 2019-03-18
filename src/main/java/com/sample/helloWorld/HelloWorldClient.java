package com.sample.helloWorld;

import com.sample.helloworld.Greeting;
import com.sample.helloworld.HelloWorldServiceGrpc;
import com.sample.helloworld.Person;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HelloWorldClient extends HelloWorldServiceImpl{

    private HelloWorldServiceGrpc.HelloWorldServiceBlockingStub helloWorldServiceBlockingStub;

    @PostConstruct
    private void init() {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost", 6565).usePlaintext().build();

        helloWorldServiceBlockingStub =
                HelloWorldServiceGrpc.newBlockingStub(managedChannel);
    }

    public String sayHello(String firstName, String lastName) {
        Person person = Person.newBuilder().setFirstName(firstName)
                .setLastName(lastName).build();
        System.out.println("client sending {} "+ person);

        Greeting greeting =
                helloWorldServiceBlockingStub.sayHello(person);
        System.out.println("client received {} "+ greeting);

        return greeting.getMessage();
    }
}
