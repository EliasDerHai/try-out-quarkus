package com.elija.domain.order;

import io.vavr.control.Option;

public interface OrderService {

    Option<OrderId> saveOrder(OrderDescription orderDescription);

}
