package com.elija.domain.person;

import com.elija.domain.atomic.PersonId;
import io.vavr.control.Option;
import lombok.NonNull;

public record Person(
        @NonNull PersonId id,
        @NonNull String firstName,
        @NonNull String lastName,
        @NonNull Option<String> phoneNumber,
        @NonNull UserGroup userGroup
        ) {
}
