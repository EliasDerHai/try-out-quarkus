package com.elija.domain.person;

import java.util.UUID;

public record PersonId(UUID id) {
    public static PersonId fromPrimitive(UUID id) {
        return new PersonId(id);
    }

    public UUID toPrimitive() {
        return id;
    }
}
