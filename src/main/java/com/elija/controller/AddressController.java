package com.elija.controller;

import com.elija.domain.address.Address;
import lombok.NonNull;

public class AddressController {

    /**
     * @param houseNumber is non-numeric since "2A" etc. is possible
     */
    record CreateAddressDto(
            @NonNull String streetName,
            @NonNull String houseNumber,
            int zipCode,
            @NonNull String city
    ) {
    }

    record GetAddressDto(
            int id,
            @NonNull String street,
            @NonNull String house,
            int zipCode,
            @NonNull String city,
            double latitude,
            double longitude
    ) {

        public static GetAddressDto fromAddress(Address address) {
            return new GetAddressDto(
                    address.id().toInt(),
                    address.street().toString(),
                    address.house().toString(),
                    address.zipCode().toInt(),
                    address.city().toString(),
                    address.latitude().toDouble(),
                    address.longitude().toDouble()
            );
        }
    }
}
