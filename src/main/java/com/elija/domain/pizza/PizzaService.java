package com.elija.domain.pizza;

import io.vavr.collection.Set;

public interface PizzaService {

    int addPizza(Pizza pizza);

    Set<Pizza> getPizzas();

    Set<Pizza> getSamplePizzas();
}
