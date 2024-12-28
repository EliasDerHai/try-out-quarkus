package com.elija.domain.address;

import com.elija.domain.address.values.*;
import io.vavr.Tuple2;
import io.vavr.control.Option;

public interface AddressService {
    Option<Tuple2<Latitude, Longitude>> findLatitudeAndLongitudeForAddress(
            Street street,
            House house,
            ZipCode zipCode,
            City city
    );

    Option<AddressId> createAddress(
            Street street,
            House house,
            ZipCode zipCode,
            City city,
            Latitude latitude,
            Longitude longitude
    );

    double getDistanceBetween(Latitude aLat, Longitude aLong, Latitude bLat, Longitude bLong);
}
