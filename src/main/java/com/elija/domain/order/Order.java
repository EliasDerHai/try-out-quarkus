package com.elija.domain.order;

import com.elija.domain.atomic.OrderId;
import com.elija.domain.atomic.PizzaId;
import com.elija.domain.person.Address;
import com.elija.domain.person.Person;
import io.vavr.collection.Map;
import lombok.NonNull;


public record Order(
        OrderId id,
        @NonNull Map<PizzaId, Integer> pizzaIdWithQuantity,
        @NonNull Address destination,
        @NonNull Person orderer,
        @NonNull Person chef,
        @NonNull Person deliveryDriver,
        @NonNull OrderState orderState
        ) {
}
