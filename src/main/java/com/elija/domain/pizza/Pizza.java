package com.elija.domain.pizza;

import io.vavr.control.Option;
import lombok.NonNull;

public record Pizza(
        int id,
        @NonNull String name,
        @NonNull Option<String> description,
        @NonNull Price price
) {
}
