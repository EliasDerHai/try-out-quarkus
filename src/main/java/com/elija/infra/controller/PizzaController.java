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
    public Set<GetPizzaDto> getAll() {
        return pizzaService.getPizzas()
                .map(GetPizzaDto::fromPizza);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id") int id) {
        return pizzaService.getPizzaById(id)
                .map(GetPizzaDto::fromPizza)
                .map(p -> Response.ok(p).build())
                .getOrElse(Response.status(404).build());
    }

    @GET()
    @Path("/examples")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<GetPizzaDto> getSamples() {
        return pizzaService.getExamplePizzas()
                .map(GetPizzaDto::fromPizza);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(CreatePizzaDto pizzaDto) {
        return pizzaService.addPizza(pizzaDto.toCreatePizzaCommand())
                .map(id -> URI.create("%s/%d".formatted(PIZZA_URI, id.toInt())))
                .map(uri -> Response.created(uri).build())
                .getOrElse(Response.serverError().build());
    }
}
