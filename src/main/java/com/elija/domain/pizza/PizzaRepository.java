package com.elija.domain.pizza;

import io.vavr.collection.Set;
import io.vavr.control.Option;

public interface PizzaRepository {
    Option<Integer> save(Pizza pizza);

    Set<Pizza> findAll();

    Option<Pizza> find(int id);
}
