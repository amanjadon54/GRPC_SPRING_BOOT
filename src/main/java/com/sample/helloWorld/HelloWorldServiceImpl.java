package com.sample.helloWorld;

import com.sample.helloworld.Greeting;
import com.sample.helloworld.HelloWorldServiceGrpc;
import com.sample.helloworld.Person;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class HelloWorldServiceImpl extends HelloWorldServiceGrpc.HelloWorldServiceImplBase {

    @Override
    public void sayHello(Person request, StreamObserver<Greeting> responseObserver) {
        System.out.println("server received {} "+ request);

        String message = "Hello " + request.getFirstName() + " "
                + request.getLastName() + "!";
        Greeting greeting =
                Greeting.newBuilder().setMessage(message).build();
        System.out.println("server responded {} "+ greeting);

        responseObserver.onNext(greeting);
        responseObserver.onCompleted();
    }
}
