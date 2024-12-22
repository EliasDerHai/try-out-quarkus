package com.elija.infra.controller;

import com.elija.domain.pizza.PizzaService;
import io.vavr.collection.Set;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.net.URI;

@Path(PizzaController.PIZZA_URI)
@RequiredArgsConstructor
class PizzaController {
    public static final String PIZZA_URI = "/pizza";

    private final PizzaService pizzaService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<PizzaDto> get() {
        return pizzaService.getPizzas().map(PizzaDto::fromPizza);
    }

    @GET()
    @Path("/samples")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<PizzaDto> getSamples() {
        return pizzaService.getSamplePizzas().map(PizzaDto::fromPizza);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(PizzaDto pizzaDto) {
        var id = pizzaService.addPizza(pizzaDto.toPizza());
        URI uriCreated = URI.create("%s/%d".formatted(PIZZA_URI, id));
        return Response.created(uriCreated).build();
    }
}
