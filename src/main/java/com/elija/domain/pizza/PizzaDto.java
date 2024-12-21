package com.elija.domain.pizza;

import io.vavr.control.Option;

public record PizzaDto(String name, Option<String> description) {
    public static PizzaDto fromPizza(Pizza pizza) {
        return new PizzaDto(pizza.getName(), pizza.getDescription());
    }
}