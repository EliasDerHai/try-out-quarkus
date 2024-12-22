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
    public Option<Integer> addPizza(CreatePizzaCommand createPizzaCommand) {
        return repository.save(createPizzaCommand);
    }

    @Override
    public Set<Pizza> getPizzas() {
        return repository.findAll();
    }

    @Override
    public Option<Pizza> getPizzaById(int id) {
        return repository.find(id);
    }

    @Override
    public Set<Pizza> getSamplePizzas() {
        return HashSet.of(
                new Pizza(1, "Margerita", Option.of("Tomatoes, Mozzarella - minimalistic"), Price.fromEuroCents(999)),
                new Pizza(2, "Funghi", Option.of("Basically just Cheese and Champignons"), Price.fromEuroCents(999)),
                new Pizza(3, "Quattro-Formaggi", Option.of("Cheese with cheese on-top of cheese-covered cheese"), Price.fromEuroCents(999)),
                new Pizza(4, "Quattro-Stagioni", Option.none(), Price.fromEuroCents(999))
        );
    }
}
