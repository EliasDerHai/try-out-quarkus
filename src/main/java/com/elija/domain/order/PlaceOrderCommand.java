package com.elija.domain.order;

import com.elija.domain.atomic.PizzaId;
import com.elija.domain.person.Address;
import com.elija.domain.person.Person;
import io.vavr.collection.Map;
import lombok.NonNull;

public record PlaceOrderCommand(
        @NonNull Map<PizzaId, Integer> pizzaIdWithQuantity,
        @NonNull Address destination,
        @NonNull Person orderer
) {
}
