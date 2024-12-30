package com.elija.domain.order.values;

import com.elija.domain.address.GeoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderNotPlacedReason {
    /** vendor does not deliver to the specified destination */
    DESTINATION_OUT_OF_DELIVERY_ZONE("destination-out-of-delivery-zone"),

    /** destination cannot be found via {@link GeoService#findLatitudeAndLongitudeForAddress} */
    DESTINATION_UNKNOWN("destination-unknown"),

    /** something went wrong while persisting - most likely the pizza's id doesn't exist */
    PERSISTENCE_ERROR("persistence-error"),

    /** the ordered quantity is zero/empty */
    EMPTY_ORDER("empty-order");

    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
