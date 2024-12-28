package com.elija.domain.person;

import com.elija.domain.person.values.*;
import lombok.NonNull;

public record Person(
        @NonNull PersonId id,
        @NonNull FirstName firstName,
        @NonNull LastName lastName,
        @NonNull PhoneNumber phoneNumber,
        @NonNull UserGroup userGroup
) {
}
