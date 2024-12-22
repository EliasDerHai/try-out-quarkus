package com.elija.infra.controller;

import com.elija.domain.atomic.Price;
import com.elija.domain.pizza.CreatePizzaCommand;
import io.vavr.control.Option;

import java.io.Serializable;

record CreatePizzaDto(String name, Option<String> description, float price) implements Serializable {
    CreatePizzaCommand toCreatePizzaCommand() {
        return new CreatePizzaCommand(
                name,
                description,
                Price.fromEuroCents(Math.round(price * 100))
        );
    }
}