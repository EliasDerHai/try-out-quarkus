package com.elija.domain.order;

import com.elija.domain.address.Address;
import com.elija.domain.address.AddressService;
import com.elija.domain.address.values.Latitude;
import com.elija.domain.address.values.Longitude;
import com.elija.domain.order.values.OrderNotPlacedReason;
import com.elija.domain.order.values.OrderState;
import com.elija.domain.person.Person;
import com.elija.domain.person.PersonService;
import com.elija.domain.person.values.UserGroup;
import io.vavr.control.Either;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class PlaceOrderFacadeImpl implements PlaceOrderFacade {

    private final AddressService addressService;
    private final PersonService personService;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public Either<OrderNotPlacedReason, Order> placeOrder(PlaceOrderCommand placeOrderCommand) {
        var latLong = addressService.findLatitudeAndLongitudeForAddress(
                placeOrderCommand.destination()._1,
                placeOrderCommand.destination()._2,
                placeOrderCommand.destination()._3,
                placeOrderCommand.destination()._4
        );

        if (latLong.isEmpty()) {
            return Either.left(OrderNotPlacedReason.DESTINATION_UNKNOWN);
        }

        var destinationLatitude = latLong.get()._1;
        var destinationLongitude = latLong.get()._2;
        var vendorLatitude = Latitude.fromDouble(Math.random());
        var vendorLongitude = Longitude.fromDouble(Math.random());

        var distance = addressService.getDistanceBetween(vendorLatitude, vendorLongitude, destinationLatitude, destinationLongitude);
        if (distance > 42) {
            return Either.left(OrderNotPlacedReason.DESTINATION_OUT_OF_DELIVERY_ZONE);
        }

        var addressId = addressService.createAddress(
                placeOrderCommand.destination()._1,
                placeOrderCommand.destination()._2,
                placeOrderCommand.destination()._3,
                placeOrderCommand.destination()._4,
                destinationLatitude,
                destinationLongitude
        );
        var customerId = personService.addPerson(
                placeOrderCommand.orderer()._1,
                placeOrderCommand.orderer()._2,
                placeOrderCommand.orderer()._3,
                UserGroup.CUSTOMER
        );
        var chef = personService.getLeastBusyChef();
        var deliveryDriver = personService.getLeastBusyDeliveryDriver();

        var orderId = orderRepository.saveOrder(
                placeOrderCommand.pizzaIdWithQuantity(),
                addressId.getOrElseThrow(() -> new IllegalStateException("Address not available")),
                customerId.getOrElseThrow(() -> new IllegalStateException("Customer not available")),
                chef.map(Person::id).getOrElseThrow(() -> new IllegalStateException("No chef available")),
                deliveryDriver.map(Person::id).getOrElseThrow(() -> new IllegalStateException("No driver available")),
                OrderState.PLACED
        );

        return orderId
                .map(id -> new Order(
                        id,
                        placeOrderCommand.pizzaIdWithQuantity(),
                        new Address(
                                addressId.get(),
                                placeOrderCommand.destination()._1,
                                placeOrderCommand.destination()._2,
                                placeOrderCommand.destination()._3,
                                placeOrderCommand.destination()._4,
                                destinationLatitude,
                                destinationLongitude
                        ),
                        new Person(
                                customerId.get(),
                                placeOrderCommand.orderer()._1,
                                placeOrderCommand.orderer()._2,
                                placeOrderCommand.orderer()._3,
                                UserGroup.CUSTOMER
                        ),
                        chef.get(),
                        deliveryDriver.get(),
                        OrderState.PLACED
                ))
                .toEither(OrderNotPlacedReason.PERSISTENCE_ERROR);
    }
}
