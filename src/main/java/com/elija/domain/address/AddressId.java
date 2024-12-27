package com.elija.domain.address;

public record AddressId(int id) {
    public static AddressId fromInt(int id) {
        return new AddressId(id);
    }

    public int toInt() {
        return id;
    }
}
