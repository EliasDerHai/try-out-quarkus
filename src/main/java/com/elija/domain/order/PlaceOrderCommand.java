package com.elija.domain.order;

import com.elija.domain.address.AddressDescription;
import com.elija.domain.person.PersonDescription;
import com.elija.domain.pizza.PizzaId;
import io.vavr.collection.Map;
import lombok.NonNull;

public record PlaceOrderCommand(
        @NonNull Map<PizzaId, Integer> pizzaIdWithQuantity,
        @NonNull AddressDescription destination,
        @NonNull PersonDescription orderer
) {
}
