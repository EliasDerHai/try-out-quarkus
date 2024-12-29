package com.elija.domain.person.values;

import java.util.UUID;

public record PersonId(UUID value) {
    public static PersonId fromUUID(UUID value) {
        return new PersonId(value);
    }

    public static PersonId fromString(String value) {
        return new PersonId(UUID.fromString(value));
    }

    public UUID toUUID() {
        return value;
    }

    public String toString() {
        return value.toString();
    }
}
