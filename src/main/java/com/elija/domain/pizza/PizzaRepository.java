package com.elija.domain.pizza;

import io.vavr.collection.Set;
import io.vavr.control.Option;

public interface PizzaRepository {
    Option<PizzaId> save(PizzaName pizzaName, PizzaDescription pizzaDescription, Price price);

    Set<Pizza> findAll();

    Option<Pizza> find(int id);

    Option<Pizza> findByName(String name);
}
