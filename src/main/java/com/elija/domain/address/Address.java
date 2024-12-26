package com.elija.domain.address;

import lombok.NonNull;

/**
 * @param houseNumber is non-numeric since "2A" etc. is possible
 */
public record Address(
        @NonNull AddressId id,
        @NonNull String streetName,
        @NonNull String houseNumber,
        int zipCode,
        @NonNull String city,
        @NonNull Latitude latitude,
        @NonNull Longitude longitude
) {
}
