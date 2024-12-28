package com.elija.infra.controller;

import com.elija.domain.pizza.Pizza;
import io.vavr.control.Option;

import java.io.Serializable;

record GetPizzaDto(int id, String name, Option<String> description, String price) implements Serializable {
    public static GetPizzaDto fromPizza(Pizza pizza) {
        return new GetPizzaDto(
                pizza.id().toInt(),
                pizza.name().value(),
                pizza.description().toOption(),
                pizza.price().toString()
        );
    }
}