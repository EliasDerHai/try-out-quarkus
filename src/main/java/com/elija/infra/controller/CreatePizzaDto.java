package com.elija.infra.controller;

import com.elija.domain.pizza.values.PizzaDescription;
import com.elija.domain.pizza.values.PizzaName;
import com.elija.domain.pizza.values.Price;
import io.vavr.control.Option;

import java.io.Serializable;

record CreatePizzaDto(
        String name,
        Option<String> description,
        float price
) implements Serializable {
    PizzaName getPizzaName() {
        return PizzaName.fromString(name);
    }

    PizzaDescription getPizzaDescription() {
        return PizzaDescription.fromOption(description);
    }

    Price getPrice() {
        return Price.fromEuros(price);
    }
}