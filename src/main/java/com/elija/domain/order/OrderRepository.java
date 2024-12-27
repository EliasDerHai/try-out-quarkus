package com.elija.domain.order;

import io.vavr.control.Option;

public interface OrderRepository {
    Option<OrderId> saveOrder(OrderDescription orderDescription);
}
