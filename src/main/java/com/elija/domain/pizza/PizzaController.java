package com.elija.domain.pizza;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;

@Path("/pizza")
@RequiredArgsConstructor
public class PizzaController {
    private final PizzaRepository pizzaRepository;

    private final Set<Pizza> pizzas = HashSet.of(
            new Pizza("Margerita", Option.of("Tomatoes, Mozzarella - minimalistic")),
            new Pizza("Funghi", Option.of("basically just Cheese and Champignons")),
            new Pizza("Quattro-Formaggi", Option.of("Cheese with cheese on-top of cheese-covered cheese")),
            new Pizza("Quattro-Stagioni", Option.none())
    );

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<PizzaDto> get() {
        pizzaRepository.save(pizzas.head());
        return pizzas.map(PizzaDto::fromPizza);
    }
}
