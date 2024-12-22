package com.elija.domain.pizza;

import io.vavr.collection.Set;

public interface PizzaRepository {
    int save(Pizza pizza);

    Set<Pizza> findAll();
}
