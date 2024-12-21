package com.elija.pizza;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/pizza")
public class PizzaController {

    private final Set<Pizza> pizzas = new HashSet<>() {{
        add(new Pizza("Margerita", Optional.of("Tomatoes, Mozzarella - minimalistic")));
        add(new Pizza("Funghi", Optional.of("basically just Cheese and Champignons")));
    }};

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<PizzaDto> get() {
        System.out.println(pizzas.size());
        return pizzas.stream().map(PizzaDto::fromPizza).collect(Collectors.toSet());
    }
}
