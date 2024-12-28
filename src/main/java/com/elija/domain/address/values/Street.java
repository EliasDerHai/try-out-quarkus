package com.elija.domain.address.values;

import lombok.NonNull;

public record Street(@NonNull String value) {
    public static Street fromString(@NonNull String value) {
        return new Street(value);
    }

    public String toString() {
        return value();
    }
}
