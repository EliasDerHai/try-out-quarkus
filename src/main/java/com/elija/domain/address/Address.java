package com.elija.domain.address;

import com.elija.domain.address.values.*;
import lombok.NonNull;

/**
 * An Address fully describes a physical location in the context of pizza-delivery.
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
