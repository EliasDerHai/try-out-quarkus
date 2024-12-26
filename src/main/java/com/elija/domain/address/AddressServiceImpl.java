package com.elija.domain.address;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    @Override
    public Option<Tuple2<Latitude, Longitude>> findLatitudeAndLongitudeForAddress(AddressDescription addressDescription) {
        // TODO use https://geocode.maps.co
        var latitudeLongitude = Tuple.of(Math.random(), Math.random())
                .map(Latitude::fromPrimitive, Longitude::fromPrimitive);

        return Option.of(latitudeLongitude);
    }

    @Override
    public Option<AddressId> createAddress(AddressDescriptionWithLatLong addressDescription) {
        return addressRepository.saveAddress(addressDescription);
    }

    /** pythagoras (may also try manhattan) */
    @Override
    public double getDistanceBetween(Latitude aLat, Longitude aLong, Latitude bLat, Longitude bLong) {
        var aSquare = Math.pow(aLat.toPrimitive() - bLat.toPrimitive(), 2);
        var bSquare = Math.pow(aLong.toPrimitive() - bLong.toPrimitive(), 2);
        return Math.sqrt(aSquare * bSquare);
    }
}
