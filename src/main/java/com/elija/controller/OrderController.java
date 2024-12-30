package com.elija.controller;

import com.elija.domain.address.values.City;
import com.elija.domain.address.values.House;
import com.elija.domain.address.values.Street;
import com.elija.domain.address.values.ZipCode;
import com.elija.domain.order.Order;
import com.elija.domain.order.OrderRepository;
import com.elija.domain.order.PlaceOrderCommand;
import com.elija.domain.order.PlaceOrderFacade;
import com.elija.domain.order.values.OrderId;
import com.elija.domain.person.values.FirstName;
import com.elija.domain.person.values.LastName;
import com.elija.domain.person.values.PhoneNumber;
import com.elija.domain.pizza.values.PizzaId;
import io.vavr.Tuple;
import io.vavr.collection.HashMap;
import io.vavr.collection.Set;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.Map;

@Path(OrderController.ORDER_URI)
@RequiredArgsConstructor
class OrderController {
    public static final String ORDER_URI = "/order";

    private final PlaceOrderFacade placeOrderFacade;
    private final OrderRepository orderRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<OrderController.GetOrderDto> getAll() {
        return orderRepository.findAll()
                .map(OrderController.GetOrderDto::fromOrder);
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id") int id) {
        return orderRepository.find(OrderId.fromInt(id))
                .map(OrderController.GetOrderDto::fromOrder)
                .map(p -> Response.ok(p).build())
                .getOrElse(Response.status(404).build());
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(PlaceOrderDto placeOrderDto) {
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
                                .entity(GetOrderDto.fromOrder(order))
                                .build()
                );
    }

    /**
     * example:
     * <pre>
     * {
     *   "pizzaIdWithQuantity": {
     *     "1": 1,
     *     "2": 3
     *   },
     *   "destination": {
     *     "streetName": "street",
     *     "houseNumber": "2",
     *     "zipCode": 19232,
     *     "city": "RGB"
     *   },
     *   "orderer": {
     *     "firstName": "John",
     *     "lastName": "Doe",
     *     "userGroup": "customer"
     *   }
     * }
     * </pre>
     */
    private record PlaceOrderDto(
            @NonNull Map<Integer, Integer> pizzaIdWithQuantity,
            @NonNull AddressController.CreateAddressDto destination,
            @NonNull PersonController.CreatePersonDto orderer
    ) {
        public PlaceOrderCommand toPlaceOrderCommand() {
            return new PlaceOrderCommand(
                    HashMap.ofAll(pizzaIdWithQuantity).mapKeys(PizzaId::fromInt),
                    Tuple.of(
                            Street.fromString(destination.streetName()),
                            House.fromString(destination.houseNumber()),
                            ZipCode.fromInteger(destination.zipCode()),
                            City.fromString(destination.city())
                    ),
                    Tuple.of(
                            FirstName.fromString(orderer.firstName()),
                            LastName.fromString(orderer.lastName()),
                            PhoneNumber.fromOption(orderer.phoneNumber())
                    )
            );
        }
    }

    private record GetOrderDto(
            int id,
            @NonNull Map<Integer, Integer> pizzaIdWithQuantity,
            @NonNull AddressController.GetAddressDto destination,
            @NonNull PersonController.GetPersonDto orderer,
            @NonNull PersonController.GetPersonDto chef,
            @NonNull PersonController.GetPersonDto deliveryDriver,
            @NonNull String orderState
            ){

        static GetOrderDto fromOrder(Order order){
            return new GetOrderDto(
                    order.id().toInt(),
                    order.pizzaIdWithQuantity().mapKeys(PizzaId::toInt).toJavaMap(),
                    AddressController.GetAddressDto.fromAddress(order.destination()),
                    PersonController.GetPersonDto.fromPerson(order.orderer()),
                    PersonController.GetPersonDto.fromPerson(order.chef()),
                    PersonController.GetPersonDto.fromPerson(order.deliveryDriver()),
                    order.orderState().toString()
            );
        }
    }
}
