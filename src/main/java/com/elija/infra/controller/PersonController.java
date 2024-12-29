package com.elija.infra.controller;

import com.elija.domain.person.Person;
import io.vavr.control.Option;
import lombok.NonNull;

public class PersonController {
     record CreatePersonDto(
            @NonNull String firstName,
            @NonNull String lastName,
            @NonNull Option<String> phoneNumber,
            @NonNull String userGroup
            ) {
    }

    record GetPersonDto(
            @NonNull String id,
            @NonNull String firstName,
            @NonNull String lastName,
            @NonNull Option<String> phoneNumber,
            @NonNull String userGroup
    ) {
        public static GetPersonDto fromPerson(Person orderer) {
            return new GetPersonDto(
                    orderer.id().toString(),
                    orderer.firstName().toString(),
                    orderer.lastName().toString(),
                    orderer.phoneNumber().toOption(),
                    orderer.userGroup().toString()
            );
        }
    }
}
