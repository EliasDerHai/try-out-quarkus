package com.elija.domain.person;

import lombok.NonNull;

/**
 * @param houseNumber is non-numeric since "2A" etc. is possible
 */
public record Address(
        @NonNull String streetName,
        @NonNull String houseNumber,
        int zipCode,
        @NonNull String city,
        long longitude,
        long latitude
) {
}
