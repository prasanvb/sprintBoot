package com.java.example.sprintBootStarter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping(path="/hello")
   public String helloWorld(){
        return "Hello World, Prasan's Spring boot Java API?";
    }

}
