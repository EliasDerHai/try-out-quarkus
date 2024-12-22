package com.elija.infra.controller;

import com.elija.domain.pizza.PizzaService;
import io.vavr.collection.Set;
import jakarta.ws.rs.*;
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
    public Set<PizzaDto> getAll() {
        return pizzaService.getPizzas().map(PizzaDto::fromPizza);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id") int id) {
        return pizzaService.getPizzaById(id)
                .map(PizzaDto::fromPizza)
                .map(p -> Response.ok(p).build())
                .getOrElse(Response.status(404).build());
    }

    @GET()
    @Path("/examples")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<PizzaDto> getSamples() {
        return pizzaService.getSamplePizzas()
                .map(PizzaDto::fromPizza);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(PizzaDto pizzaDto) {
        return pizzaService.addPizza(pizzaDto.toPizza())
                .map(id -> URI.create("%s/%d".formatted(PIZZA_URI, id)))
                .map(uri -> Response.created(uri).build())
                .getOrElse(Response.serverError().build());
    }
}
