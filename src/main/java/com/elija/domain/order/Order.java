package com.elija.domain.order;

import com.elija.domain.address.Address;
import com.elija.domain.person.Person;
import com.elija.domain.pizza.PizzaId;
import io.vavr.collection.Map;
import lombok.NonNull;


public record Order(
        @NonNull OrderId id,
        @NonNull Map<PizzaId, Integer> pizzaIdWithQuantity,
        @NonNull Address destination,
        @NonNull Person orderer,
        @NonNull Person chef,
        @NonNull Person deliveryDriver,
        @NonNull OrderState orderState
        ) {
}
