package com.elija.domain.order;

import com.elija.domain.address.AddressDescription;
import com.elija.domain.address.AddressService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderNotPlacedReason {
    /** vendor does not deliver to the specified destination */
    DESTINATION_OUT_OF_DELIVERY_ZONE("destination-out-of-delivery-zone"),

    /** destination cannot be found via {@link AddressService#findLatitudeAndLongitudeForAddress(AddressDescription)} */
    DESTINATION_UNKNOWN("destination-unknown");

    private final String value;
}
