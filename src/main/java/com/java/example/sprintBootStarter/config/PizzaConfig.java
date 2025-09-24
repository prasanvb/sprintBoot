package com.java.example.sprintBootStarter.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration // tells Spring this class can be managed as a bean.
@ConfigurationProperties(prefix = "pizza") // binds values from properties or environment var
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PizzaConfig {
    private  String crust;
    private  String sauce;
    private  String toppings;

}
