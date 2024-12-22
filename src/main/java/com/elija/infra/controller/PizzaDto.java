package com.elija.infra.controller;

import com.elija.domain.pizza.Pizza;
import io.vavr.control.Option;

import java.io.Serializable;

record PizzaDto(String name, Option<String> description) implements Serializable {
    public static PizzaDto fromPizza(Pizza pizza) {
        return new PizzaDto(pizza.getName(), pizza.getDescription());
    }

    Pizza toPizza(){
        return new Pizza(
                name,
                description
        );
    }
}