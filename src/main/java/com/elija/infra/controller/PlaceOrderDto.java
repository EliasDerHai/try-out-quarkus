package com.elija.infra.controller;

import com.elija.domain.address.AddressDescription;
import com.elija.domain.order.PlaceOrderCommand;
import com.elija.domain.person.PersonDescription;
import com.elija.domain.pizza.PizzaId;
import io.vavr.collection.HashMap;
import lombok.NonNull;

import java.util.Map;

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
record PlaceOrderDto(
        @NonNull Map<Integer, Integer> pizzaIdWithQuantity,
        @NonNull AddressDto destination,
        @NonNull PersonDto orderer
) {
    public PlaceOrderCommand toPlaceOrderCommand() {
        return new PlaceOrderCommand(
                HashMap.ofAll(pizzaIdWithQuantity).mapKeys(PizzaId::fromInt),
                new AddressDescription(
                        destination.streetName(),
                        destination.houseNumber(),
                        destination.zipCode(),
                        destination.city()
                ),
                new PersonDescription(
                        orderer.firstName(),
                        orderer.lastName(),
                        orderer.phoneNumber()
                )
        );
    }
}
