package com.elija.domain.order;

import io.vavr.control.Option;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public Option<OrderId> saveOrder(OrderDescription orderDescription) {
        return orderRepository.saveOrder(orderDescription);
    }

}
