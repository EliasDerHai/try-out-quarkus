package com.elija.domain.order;

import com.elija.domain.address.AddressId;
import com.elija.domain.person.PersonId;
import com.elija.domain.pizza.PizzaId;
import io.vavr.collection.Map;
import lombok.NonNull;

public record OrderDescription(
        @NonNull Map<PizzaId, Integer> pizzaIdWithQuantity,
        @NonNull AddressId destination,
        @NonNull PersonId orderer,
        @NonNull PersonId chef,
        @NonNull PersonId deliveryDriver,
        @NonNull OrderState orderState
) {
}
