package com.elija.domain.person;

import io.vavr.control.Option;
import lombok.NonNull;

/**
 * same as {@link Person} but without {@link Person#id()}, {@link Person#userGroup()}
 * <p>can be persisted
 */
public record PersonDescription(
        @NonNull String firstName,
        @NonNull String lastName,
        @NonNull Option<String> phoneNumber
        ) {
}
