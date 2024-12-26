package com.elija.domain.order;

import com.elija.domain.address.AddressService;
import com.elija.domain.address.Latitude;
import com.elija.domain.address.Longitude;
import com.elija.domain.person.PersonService;
import com.elija.domain.person.UserGroup;
import io.vavr.control.Either;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final AddressService addressService;
    private final PersonService personService;

    @Override
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



        return Either.left(OrderNotPlacedReason.DESTINATION_OUT_OF_DELIVERY_ZONE);
    }
}
