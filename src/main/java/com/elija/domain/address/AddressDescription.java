package com.elija.domain.address;

import lombok.NonNull;

/**
 * same as {@link Address} but without {@link Address#id()}, {@link Address#latitude()}, {@link Address#longitude()}
 * <p> essentially matches the user input but is not sufficient to be persisted (which would be {@link AddressDescriptionWithLatLong})
 */
public record AddressDescription(
        @NonNull String streetName,
        @NonNull String houseNumber,
        int zipCode,
        @NonNull String city
) {
    public AddressDescriptionWithLatLong enhanceLatLong(
            Latitude latitude,
            Longitude longitude
    ) {
        return new AddressDescriptionWithLatLong(
                streetName,
                houseNumber,
                zipCode,
                city,
                latitude,
                longitude
        );
    }
}
