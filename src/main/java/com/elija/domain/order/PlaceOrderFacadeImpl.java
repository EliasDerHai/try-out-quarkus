package com.elija.domain.order;

import com.elija.domain.address.Address;
import com.elija.domain.address.AddressService;
import com.elija.domain.address.Latitude;
import com.elija.domain.address.Longitude;
import com.elija.domain.person.Person;
import com.elija.domain.person.PersonService;
import com.elija.domain.person.UserGroup;
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
                placeOrderCommand.destination()
        );

        if (latLong.isEmpty()) {
            return Either.left(OrderNotPlacedReason.DESTINATION_UNKNOWN);
        }

        var destinationLatitude = latLong.get()._1;
        var destinationLongitude = latLong.get()._2;
        var vendorLatitude = Latitude.fromPrimitive(Math.random());
        var vendorLongitude = Longitude.fromPrimitive(Math.random());

        var distance = addressService.getDistanceBetween(vendorLatitude, vendorLongitude, destinationLatitude, destinationLongitude);
        if (distance > 42) {
            return Either.left(OrderNotPlacedReason.DESTINATION_OUT_OF_DELIVERY_ZONE);
        }

        var addressDescription = placeOrderCommand
                .destination()
                .enhanceLatLong(destinationLatitude, destinationLongitude);

        var addressId = addressService.createAddress(addressDescription);
        var customerId = personService.addPerson(placeOrderCommand.orderer(), UserGroup.CUSTOMER);
        var chef = personService.getLeastBusyChef();
        var deliveryDriver = personService.getLeastBusyDeliveryDriver();

        var orderDescription = new OrderDescription(
                placeOrderCommand.pizzaIdWithQuantity(),
                addressId.getOrElseThrow(() -> new IllegalStateException("Address not available")),
                customerId.getOrElseThrow(() -> new IllegalStateException("Customer not available")),
                chef.map(Person::id).getOrElseThrow(() -> new IllegalStateException("No chef available")),
                deliveryDriver.map(Person::id).getOrElseThrow(() -> new IllegalStateException("No driver available")),
                OrderState.PLACED
        );

        var orderId = orderRepository.saveOrder(orderDescription);

        return orderId
                .map(id -> new Order(
                        id,
                        placeOrderCommand.pizzaIdWithQuantity(),
                        new Address(
                                addressId.get(),
                                addressDescription.streetName(),
                                addressDescription.houseNumber(),
                                addressDescription.zipCode(),
                                addressDescription.city(),
                                addressDescription.latitude(),
                                addressDescription.longitude()
                        ),
                        new Person(
                                customerId.get(),
                                placeOrderCommand.orderer().firstName(),
                                placeOrderCommand.orderer().lastName(),
                                placeOrderCommand.orderer().phoneNumber(),
                                UserGroup.CUSTOMER
                        ),
                        chef.get(),
                        deliveryDriver.get(),
                        OrderState.PLACED
                ))
                .toEither(OrderNotPlacedReason.PERSISTENCE_ERROR);
    }
}
