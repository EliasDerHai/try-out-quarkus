package com.elija.domain.order;

import io.vavr.control.Either;

public interface OrderService {
    Either<OrderNotPlacedReason, Order> placeOrder(PlaceOrderCommand placeOrderCommand);
}
