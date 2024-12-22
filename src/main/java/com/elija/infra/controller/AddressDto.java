package com.elija.infra.controller;

import com.elija.domain.person.Address;
import lombok.NonNull;

/**
 * @param houseNumber is non-numeric since "2A" etc. is possible
 */
record AddressDto(
        @NonNull String streetName,
        @NonNull String houseNumber,
        int zipCode,
        @NonNull String city,
        long longitude,
        long latitude
) {
    public Address toAddress() {
        return new Address(
                streetName,
                houseNumber,
                zipCode,
                city,
                longitude,
                latitude
        );
    }
}
