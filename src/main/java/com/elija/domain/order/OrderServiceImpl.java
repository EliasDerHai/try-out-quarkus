package com.elija.domain.order;

import io.vavr.control.Either;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Override
    public Either<OrderNotPlacedReason, Order> placeOrder(PlaceOrderCommand placeOrderCommand) {
        // TODO impl
        return Either.left(OrderNotPlacedReason.DESTINATION_OUT_OF_DELIVERY_ZONE);
    }
}
