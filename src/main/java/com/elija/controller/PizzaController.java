package com.elija.controller;

import com.elija.domain.pizza.Pizza;
import com.elija.domain.pizza.PizzaRepository;
import com.elija.domain.pizza.values.PizzaDescription;
import com.elija.domain.pizza.values.PizzaId;
import com.elija.domain.pizza.values.PizzaName;
import com.elija.domain.pizza.values.Price;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.net.URI;

@Path(PizzaController.PIZZA_URI)
@RequiredArgsConstructor
class PizzaController {
    public static final String PIZZA_URI = "/pizza";

    private final PizzaRepository pizzaRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<GetPizzaDto> getAll() {
        return pizzaRepository.findAll()
                .map(GetPizzaDto::fromPizza);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id") int id) {
        return pizzaRepository.find(PizzaId.fromInt(id))
                .map(GetPizzaDto::fromPizza)
                .map(p -> Response.ok(p).build())
                .getOrElse(Response.status(404).build());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(CreatePizzaDto pizzaDto) {
        return pizzaRepository.save(
                        pizzaDto.getPizzaName(),
                        pizzaDto.getPizzaDescription(),
                        pizzaDto.getPrice()
                )
                .map(id -> URI.create("%s/%d".formatted(PIZZA_URI, id.toInt())))
                .map(uri -> Response.created(uri).build())
                .getOrElse(Response.serverError().build());
    }

    private record GetPizzaDto(
            int id,
            String name,
            Option<String> description,
            String price
    ) implements Serializable {
        public static GetPizzaDto fromPizza(Pizza pizza) {
            return new GetPizzaDto(
                    pizza.id().toInt(),
                    pizza.name().value(),
                    pizza.description().toOption(),
                    pizza.price().toString()
            );
        }
    }

    private record CreatePizzaDto(
            String name,
            Option<String> description,
            float price
    ) implements Serializable {
        PizzaName getPizzaName() {
            return PizzaName.fromString(name);
        }

        PizzaDescription getPizzaDescription() {
            return PizzaDescription.fromOption(description);
        }

        Price getPrice() {
            return Price.fromEuros(price);
        }
    }
}
