package com.elija.domain.address.values;

import lombok.NonNull;

public record House(@NonNull String value) {
    public static House fromString(@NonNull String value) {
        return new House(value);
    }

    public String toString() {
        return value();
    }
}
