package com.elija.domain.pizza;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
 class PizzaServiceImpl implements PizzaService {
    private final PizzaRepository repository;

    @Override
    public int addPizza(Pizza pizza) {
        return repository.save(pizza);
    }

    @Override
    public Set<Pizza> getPizzas() {
        return repository.findAll();
    }

    @Override
    public Set<Pizza> getSamplePizzas() {
        return HashSet.of(
                new Pizza("Margerita", Option.of("Tomatoes, Mozzarella - minimalistic")),
                new Pizza("Funghi", Option.of("Basically just Cheese and Champignons")),
                new Pizza("Quattro-Formaggi", Option.of("Cheese with cheese on-top of cheese-covered cheese")),
                new Pizza("Quattro-Stagioni", Option.none())
        );
    }
}
