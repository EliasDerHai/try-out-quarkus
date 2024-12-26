package com.elija.infra.controller;

import com.elija.domain.address.AddressDescription;
import com.elija.domain.order.PlaceOrderCommand;
import com.elija.domain.person.PersonDescription;
import com.elija.domain.pizza.PizzaId;
import io.vavr.collection.Map;
import lombok.NonNull;

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
 *     "city": "RGB",
 *     "longitude": 9592,
 *     "latitude": 2957
 *   },
 *   "orderer": {
 *     "id": "da1f6444-01e8-4d25-ab6d-7f2de267f3a5",
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
                pizzaIdWithQuantity.mapKeys(PizzaId::fromPrimitive),
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
