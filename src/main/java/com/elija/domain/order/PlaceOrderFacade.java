package com.elija.domain.order;

import io.vavr.control.Either;

public interface PlaceOrderFacade {

    Either<OrderNotPlacedReason, Order> placeOrder(PlaceOrderCommand placeOrderCommand);

}
