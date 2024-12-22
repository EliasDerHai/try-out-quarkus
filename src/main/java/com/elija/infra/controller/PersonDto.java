package com.elija.infra.controller;

import com.elija.domain.atomic.PersonId;
import com.elija.domain.person.Person;
import com.elija.domain.person.UserGroup;
import io.vavr.control.Option;
import lombok.NonNull;

import java.util.UUID;

record PersonDto(
        @NonNull String id,
        @NonNull String firstName,
        @NonNull String lastName,
        @NonNull Option<String> phoneNumber,
        @NonNull String userGroup
        ) {
        public Person toPerson(){
                return new Person(
                        PersonId.fromPrimitive(UUID.fromString(id)),
                        firstName,
                        lastName,
                        phoneNumber,
                        UserGroup.fromValue(userGroup)
                );
        }
}
