package com.elija.domain.order.values;

import com.elija.domain.address.AddressService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderNotPlacedReason {
    /** vendor does not deliver to the specified destination */
    DESTINATION_OUT_OF_DELIVERY_ZONE("destination-out-of-delivery-zone"),

    /** destination cannot be found via {@link AddressService#findLatitudeAndLongitudeForAddress} */
    DESTINATION_UNKNOWN("destination-unknown"),

    /** something went wrong while persisting */
    PERSISTENCE_ERROR("persistence-error");

    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
