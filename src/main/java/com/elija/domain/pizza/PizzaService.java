package com.elija.domain.pizza;

import io.vavr.collection.Set;
import io.vavr.control.Option;

public interface PizzaService {

    Option<PizzaId> addPizza(CreatePizzaCommand pizza);

    Set<Pizza> getPizzas();

    Option<Pizza> getPizzaById(int id);

    Set<Pizza> getExamplePizzas();
}
