package com.java.example.sprintBootStarter;

import com.java.example.sprintBootStarter.config.PizzaConfig;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log
public class SprintBootStarterApplication implements CommandLineRunner {

//    NOTE: Key Takeaway
//    Without Dependency Injection → pizzaConfig throws NullPointerException on run.
//    With Dependency Injection (@Autowired or constructor) → Spring injects the bean automatically.

//    METHOD 1: Field Injection with @Autowired
//    Spring sees the @Autowired annotation and directly injects the dependency PizzaConfig bean into the field.

    @Autowired
    private PizzaConfig pizzaConfig;

//     METHOD 2: Constructor Injection (Dependency Injection)
//     When Spring creates SprintBootStarterApplication, it notices the constructor requires a PizzaConfig.
//     Spring finds the matching (PizzaConfig) bean and passes it automatically.

//    private final PizzaConfig pizzaConfig;
//
//    public SprintBootStarterApplication (PizzaConfig pizzaConfig ){
//        this.pizzaConfig = pizzaConfig;
//    }


    public static void main(String[] args) {
        SpringApplication.run(SprintBootStarterApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        log.info(String.format("I want a %s crust, %s pizza with %s toppings",
                pizzaConfig.getCrust(),
                pizzaConfig.getSauce(),
                pizzaConfig.getToppings()
        ));
    }
}
