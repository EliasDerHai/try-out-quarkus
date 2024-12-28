package com.elija.domain.person.values;

import java.util.UUID;

public record PersonId(UUID id) {
    public static PersonId fromUUID(UUID id) {
        return new PersonId(id);
    }

    public static PersonId fromString(String id) {
        return new PersonId(UUID.fromString(id));
    }

    public UUID toUUID() {
        return id;
    }

    public String toString() {
        return id.toString();
    }
}
