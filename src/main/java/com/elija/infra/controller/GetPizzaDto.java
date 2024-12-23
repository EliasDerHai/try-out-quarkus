package com.elija.infra.controller;

import com.elija.domain.pizza.Pizza;
import io.vavr.control.Option;

import java.io.Serializable;

record GetPizzaDto(int id, String name, Option<String> description, String price) implements Serializable {
    public static GetPizzaDto fromPizza(Pizza pizza) {
        return new GetPizzaDto(
                pizza.id().toPrimitive(),
                pizza.name(),
                pizza.description(),
                pizza.price().toString()
        );
    }
}