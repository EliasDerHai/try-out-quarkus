package com.elija.domain.order;

import com.elija.domain.order.values.OrderNotPlacedReason;
import io.vavr.control.Either;

public interface PlaceOrderFacade {

    Either<OrderNotPlacedReason, Order> placeOrder(PlaceOrderCommand placeOrderCommand);

}
