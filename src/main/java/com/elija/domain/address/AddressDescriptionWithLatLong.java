package com.elija.domain.address;

import lombok.NonNull;

/**
 * same as {@link Address} but without {@link Address#id()}
 * <p>can be persisted
 */
public record AddressDescriptionWithLatLong(
        @NonNull String streetName,
        @NonNull String houseNumber,
        int zipCode,
        @NonNull String city,
        @NonNull Latitude latitude,
        @NonNull Longitude longitude
) {
}
