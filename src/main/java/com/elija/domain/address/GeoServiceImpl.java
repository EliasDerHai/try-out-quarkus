package com.elija.domain.address;

import com.elija.domain.address.values.*;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class GeoServiceImpl implements GeoService {

    @Override
    public Option<Tuple2<Latitude, Longitude>> findLatitudeAndLongitudeForAddress(
            Street street,
            House house,
            ZipCode zipCode,
            City city
    ) {
        // TODO use https://geocode.maps.co
        var latitudeLongitude = Tuple.of(Math.random(), Math.random())
                .map(Latitude::fromDouble, Longitude::fromDouble);

        return Option.of(latitudeLongitude);
    }

    /**
     * pythagoras (may also try manhattan)
     */
    @Override
    public double getDistanceBetween(Latitude aLat, Longitude aLong, Latitude bLat, Longitude bLong) {
        var aSquare = Math.pow(aLat.toDouble() - bLat.toDouble(), 2);
        var bSquare = Math.pow(aLong.toDouble() - bLong.toDouble(), 2);
        return Math.sqrt(aSquare * bSquare);
    }
}
