package com.elija.infra.controller;

import com.elija.domain.address.AddressDescription;
import lombok.NonNull;

/**
 * @param houseNumber is non-numeric since "2A" etc. is possible
 */
record AddressDto(
        @NonNull String streetName,
        @NonNull String houseNumber,
        int zipCode,
        @NonNull String city
) {
    public AddressDescription toCreateAddressCommand(){
        return new AddressDescription(
                streetName,
                houseNumber,
                zipCode,
                city
        );
    }
}
