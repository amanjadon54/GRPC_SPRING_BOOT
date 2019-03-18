package com.sample.rest;

import com.sample.helloWorld.HelloWorldClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @Autowired
    HelloWorldClient client;

    @GetMapping("/hello")
    public Object sayHello(){
        return client.sayHello("SomeFirstName ","lastNameUser");
    }

}
