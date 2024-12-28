package com.elija.domain.address.values;

public record AddressId(int value) {
    public static AddressId fromInt(int value) {
        return new AddressId(value);
    }

    public int toInt() {
        return value;
    }
}
