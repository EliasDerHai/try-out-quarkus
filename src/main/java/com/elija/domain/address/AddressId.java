package com.elija.domain.address;

public record AddressId(int id) {
    public static AddressId fromPrimitive(int id) {
        return new AddressId(id);
    }

    public int toPrimitive() {
        return id;
    }
}
