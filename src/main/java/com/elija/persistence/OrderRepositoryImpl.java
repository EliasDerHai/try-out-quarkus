package com.elija.persistence;

import com.elija.domain.address.Address;
import com.elija.domain.address.values.AddressId;
import com.elija.domain.order.Order;
import com.elija.domain.order.OrderRepository;
import com.elija.domain.order.values.OrderId;
import com.elija.domain.order.values.OrderState;
import com.elija.domain.person.Person;
import com.elija.domain.person.values.PersonId;
import com.elija.domain.pizza.values.PizzaId;
import com.elija.generated.tables.records.CustomerOrderRecord;
import io.vavr.Tuple;
import io.vavr.collection.HashSet;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.TableField;

import java.util.UUID;

import static com.elija.generated.Tables.*;

@Singleton
@RequiredArgsConstructor
class OrderRepositoryImpl implements OrderRepository {
    private final DSLContext dslContext;

    @Override
    @Transactional
    public Option<OrderId> save(
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
                        .fetchOne())
                .map(r -> r.get(CUSTOMER_ORDER.ID))
                .map(OrderId::fromInt);

        orderId.forEach(id -> pizzaIdWithQuantity.forEach(((pizzaId, quantity) ->
                dslContext.insertInto(CUSTOMER_ORDER_PIZZA)
                        .set(CUSTOMER_ORDER_PIZZA.ORDER_ID, id.toInt())
                        .set(CUSTOMER_ORDER_PIZZA.PIZZA_ID, pizzaId.toInt())
                        .set(CUSTOMER_ORDER_PIZZA.QUANTITY, quantity)
                        .execute()
        )));

        return orderId;
    }

    public Set<Order> findAll() {
        return HashSet.ofAll(dslContext
                        .selectFrom(CUSTOMER_ORDER)
                        .fetch())
                .map(this::getOrder);
    }

    public Option<Order> find(OrderId orderId) {
        return Option.of(dslContext
                        .selectFrom(CUSTOMER_ORDER)
                        .where(CUSTOMER_ORDER.ID.eq(orderId.toInt()))
                        .fetchOne())
                .map(this::getOrder);
    }

    /**
     * builds the domain object in a more 'programmatic way' - it should be possible to go real fancy with the
     * sql/joins etc. but I haven't fully figured out jooq as of yet
     */
    private Order getOrder(CustomerOrderRecord record) {
        var pizzaIdToQuantity = getPizzaIdToQuantity(record.get(CUSTOMER_ORDER.ID));
        var destination = getDestination(record);
        var orderer = getPerson(record, CUSTOMER_ORDER.ORDERER);
        var chef = getPerson(record, CUSTOMER_ORDER.CHEF);
        var deliverDriver = getPerson(record, CUSTOMER_ORDER.DELIVERY_DRIVER);

        return new Order(
                OrderId.fromInt(record.get(CUSTOMER_ORDER.ID)),
                pizzaIdToQuantity,
                destination,
                orderer,
                chef,
                deliverDriver,
                OrderState.fromValue(record.get(CUSTOMER_ORDER.ORDER_STATUS))
        );
    }

    private Address getDestination(CustomerOrderRecord record) {
        return Option.of(dslContext
                        .selectFrom(ADDRESS)
                        .where(ADDRESS.ID.eq(record.getDestination()))
                        .fetchOne())
                .map(AddressRepositoryImpl::getAddressFromRecord)
                .get();// safe bc of fk-constraint
    }

    private Person getPerson(CustomerOrderRecord record, TableField<CustomerOrderRecord, UUID> CUSTOMER_ORDER) {
        return Option.of(dslContext
                        .selectFrom(PERSON)
                        .where(PERSON.ID.eq(record.get(CUSTOMER_ORDER)))
                        .fetchOne())
                .map(PersonRepositoryImpl::getPersonFromRecord)
                .get();// safe bc of fk-constraint
    }

    private Map<PizzaId, Integer> getPizzaIdToQuantity(int orderId) {
        return HashSet.ofAll(
                        dslContext
                                .selectFrom(CUSTOMER_ORDER_PIZZA)
                                .where(CUSTOMER_ORDER_PIZZA.ORDER_ID.eq(orderId))
                                .fetch())
                .toMap(orderDetail -> Tuple.of(
                        PizzaId.fromInt(orderDetail.getPizzaId()),
                        orderDetail.getQuantity()
                ));
    }
}
