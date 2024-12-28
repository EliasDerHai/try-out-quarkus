package com.elija.domain.address.values;

import lombok.NonNull;

public record City(@NonNull String value) {
    public static City fromString(@NonNull String value) {
        return new City(value);
    }

    public String toString() {
        return value();
    }
}
