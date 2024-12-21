package com.elija.pizza;


import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
public class PizzaDto {
    private String name;
    private Optional<String> description;

    public static PizzaDto fromPizza(Pizza pizza){
        return new PizzaDto(pizza.getName(), pizza.getDescription());
    }
}
