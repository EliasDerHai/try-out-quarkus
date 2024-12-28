package com.elija.domain.address;

import com.elija.domain.address.values.*;
import lombok.NonNull;

/**
 * @param house is non-numeric since "2A" etc. is possible
 */
public record Address(
        @NonNull AddressId id,
        @NonNull Street street,
        @NonNull House house,
        @NonNull ZipCode zipCode,
        @NonNull City city,
        @NonNull Latitude latitude,
        @NonNull Longitude longitude
) {
}
