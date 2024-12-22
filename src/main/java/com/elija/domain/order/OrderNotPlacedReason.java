package com.elija.domain.order;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderNotPlacedReason {
    DESTINATION_OUT_OF_DELIVERY_ZONE("destination-out-of-delivery-zone");

    private final String value;
}
