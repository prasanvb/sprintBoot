package com.java.example.sprintBootStarter.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Data
public class PizzaConfig {
    private final String crust;
    private final String sauce;
    private final String toppings;

}
