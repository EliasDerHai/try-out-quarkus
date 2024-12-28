package com.elija.infra.persistence;

import com.elija.domain.address.values.AddressId;
import com.elija.domain.order.OrderRepository;
import com.elija.domain.order.values.OrderId;
import com.elija.domain.order.values.OrderState;
import com.elija.domain.person.values.PersonId;
import com.elija.domain.pizza.values.PizzaId;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;

import static com.elija.generated.Tables.CUSTOMER_ORDER;
import static com.elija.generated.Tables.CUSTOMER_ORDER_PIZZA;

@Singleton
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final DSLContext dslContext;

    @Override
    @Transactional
    public Option<OrderId> saveOrder(
            Map<PizzaId, Integer> pizzaIdWithQuantity,
            AddressId destination,
            PersonId orderer,
            PersonId chef,
            PersonId deliveryDriver,
            OrderState orderState
    ) {
        var orderId = Option.of(dslContext.insertInto(CUSTOMER_ORDER)
                        .set(CUSTOMER_ORDER.DESTINATION, destination.toInt())
                        .set(CUSTOMER_ORDER.ORDERER, orderer.toUUID())
                        .set(CUSTOMER_ORDER.CHEF, chef.toUUID())
                        .set(CUSTOMER_ORDER.DELIVERY_DRIVER, deliveryDriver.toUUID())
                        .set(CUSTOMER_ORDER.ORDER_STATUS, orderState.getValue())
                        .returning()
                        .fetchOne()
                )
                .map(r -> r.get(CUSTOMER_ORDER.ID))
                .map(OrderId::fromInt);

        orderId.forEach(id -> pizzaIdWithQuantity.forEach(((pizzaId, quantity) -> {
            dslContext.insertInto(CUSTOMER_ORDER_PIZZA)
                    .set(CUSTOMER_ORDER_PIZZA.ORDER_ID, id.toInt())
                    .set(CUSTOMER_ORDER_PIZZA.PIZZA_ID, pizzaId.toInt())
                    .set(CUSTOMER_ORDER_PIZZA.QUANTITY, quantity);
        })));

        return orderId;
    }
}
