package com.elija.domain.order;

import com.elija.domain.address.values.AddressId;
import com.elija.domain.order.values.OrderId;
import com.elija.domain.order.values.OrderState;
import com.elija.domain.person.values.PersonId;
import com.elija.domain.pizza.values.PizzaId;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import io.vavr.control.Option;

public interface OrderRepository {
    Option<OrderId> save(
            Map<PizzaId, Integer> pizzaIdWithQuantity,
            AddressId destination,
            PersonId orderer,
            PersonId chef,
            PersonId deliveryDriver,
            OrderState orderState
    );

    Option<Order> find(OrderId orderId);

    Set<Order> findAll();
}
