package com.elija.infra.persistence;

import com.elija.domain.order.OrderDescription;
import com.elija.domain.order.OrderId;
import com.elija.domain.order.OrderRepository;
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
    public Option<OrderId> saveOrder(OrderDescription orderDescription) {
        var orderId = Option.of(dslContext.insertInto(CUSTOMER_ORDER)
                        .set(CUSTOMER_ORDER.ORDERER, orderDescription.orderer().toUUID())
                        .set(CUSTOMER_ORDER.CHEF, orderDescription.chef().toUUID())
                        .set(CUSTOMER_ORDER.DELIVERY_DRIVER, orderDescription.deliveryDriver().toUUID())
                        .set(CUSTOMER_ORDER.DESTINATION, orderDescription.destination().toInt())
                        .set(CUSTOMER_ORDER.ORDER_STATUS, orderDescription.orderState().getValue())
                        .returning()
                        .fetchOne()
                )
                .map(r -> r.get(CUSTOMER_ORDER.ID))
                .map(OrderId::fromInt);

        orderId.forEach(id -> orderDescription.pizzaIdWithQuantity().forEach(((pizzaId, quantity) -> {
            dslContext.insertInto(CUSTOMER_ORDER_PIZZA)
                    .set(CUSTOMER_ORDER_PIZZA.ORDER_ID, id.toInt())
                    .set(CUSTOMER_ORDER_PIZZA.PIZZA_ID, pizzaId.toInt())
                    .set(CUSTOMER_ORDER_PIZZA.QUANTITY, quantity);
        })));

        return orderId;
    }
}
