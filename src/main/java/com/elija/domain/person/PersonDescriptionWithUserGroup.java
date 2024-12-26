package com.elija.domain.person;

import io.vavr.control.Option;
import lombok.NonNull;

/**
 * same as {@link Person} but without {@link Person#id()}
 * <p>can be persisted
 */
public record PersonDescriptionWithUserGroup(
        @NonNull String firstName,
        @NonNull String lastName,
        @NonNull Option<String> phoneNumber,
        @NonNull UserGroup userGroup
        ) {
        public static PersonDescriptionWithUserGroup fromPersonDescription(
                PersonDescription personDescription,
                UserGroup userGroup
        ) {
                return new PersonDescriptionWithUserGroup(
                        personDescription.firstName(),
                        personDescription.lastName(),
                        personDescription.phoneNumber(),
                        userGroup
                );
        }
}
