package com.elija.domain.address;

import io.vavr.Tuple2;
import io.vavr.control.Option;

public interface AddressService {
     Option<Tuple2<Latitude, Longitude>> findLatitudeAndLongitudeForAddress(AddressDescription addressDescription);
     Option<AddressId> createAddress(AddressDescriptionWithLatLong addressDescription);
     double getDistanceBetween(Latitude aLat, Longitude aLong, Latitude bLat, Longitude bLong);
}
