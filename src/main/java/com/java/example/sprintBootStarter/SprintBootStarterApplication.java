package com.java.example.sprintBootStarter;

import com.java.example.sprintBootStarter.config.PizzaConfig;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log
public class SprintBootStarterApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SprintBootStarterApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        final PizzaConfig pizza1 = new PizzaConfig(
                "pan", "regular", "mushroom and pineapple"
        );

        final PizzaConfig pizza2 = new PizzaConfig();

        // All args constructor
        logPizza(pizza1);

        // No name constructor
        logPizza(pizza2);


    }

    private static void logPizza(PizzaConfig pizza) {
        log.info(String.format("I want a %s crust, %s pizza with %s toppings",
                pizza.getCrust(),
                pizza.getSauce(),
                pizza.getToppings()
        ));
    }
}
