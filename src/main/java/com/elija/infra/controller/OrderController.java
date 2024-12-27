package com.elija.infra.controller;

import com.elija.domain.order.PlaceOrderFacade;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.net.URI;

@Path(OrderController.ORDER_URI)
@RequiredArgsConstructor
class OrderController {
    public static final String ORDER_URI = "/order";

    private final PlaceOrderFacade placeOrderFacade;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(PlaceOrderDto placeOrderDto) {

        System.out.println(placeOrderDto.destination().city());

        return placeOrderFacade
                .placeOrder(placeOrderDto.toPlaceOrderCommand())
                .fold(
                        // unhappy path
                        reason -> Response
                                .status(Response.Status.CONFLICT)
                                .entity(reason.toString())
                                .build(),
                        // happy path
                        order -> Response
                                .created(URI.create("%s/%d".formatted(
                                        ORDER_URI,
                                        order.id().toInt()
                                )))
                                .entity(order) // TODO convert to dto
                                .build()
                );
    }
}
