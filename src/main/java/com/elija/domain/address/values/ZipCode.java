package com.elija.domain.address.values;

import lombok.NonNull;

public record ZipCode(int value) {
    public static ZipCode fromInteger(@NonNull Integer value) {
        return new ZipCode(value);
    }

    public int toInt() {
        return value();
    }
}
