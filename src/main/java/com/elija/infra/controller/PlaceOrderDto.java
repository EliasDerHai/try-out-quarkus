package com.elija.infra.controller;

import com.elija.domain.address.values.City;
import com.elija.domain.address.values.House;
import com.elija.domain.address.values.Street;
import com.elija.domain.address.values.ZipCode;
import com.elija.domain.order.PlaceOrderCommand;
import com.elija.domain.person.values.FirstName;
import com.elija.domain.person.values.LastName;
import com.elija.domain.person.values.PhoneNumber;
import com.elija.domain.pizza.values.PizzaId;
import io.vavr.Tuple;
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
