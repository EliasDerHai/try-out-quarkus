package com.elija.infra.controller;

import io.vavr.control.Option;
import lombok.NonNull;

record PersonDto(
        @NonNull String firstName,
        @NonNull String lastName,
        @NonNull Option<String> phoneNumber,
        @NonNull String userGroup
        ) {
}
