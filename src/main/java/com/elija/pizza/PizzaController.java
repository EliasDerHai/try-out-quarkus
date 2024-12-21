package com.elija.pizza;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/pizza")
public class PizzaController {

    private final Set<Pizza> pizzas = HashSet.of(
            new Pizza("Margerita", Option.of("Tomatoes, Mozzarella - minimalistic")),
            new Pizza("Funghi", Option.of("basically just Cheese and Champignons")),
            new Pizza("Quattro-Formaggi", Option.of("Cheese with cheese on-top of cheese-covered cheese")),
            new Pizza("Quattro-Stagioni", Option.none())
    );

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<PizzaDto> get() {
        return pizzas.map(PizzaDto::fromPizza);
    }
}
