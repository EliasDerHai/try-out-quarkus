package com.elija.domain.pizza;

import com.elija.domain.shared.Price;
import io.vavr.control.Option;
import lombok.NonNull;

public record Pizza(
        @NonNull PizzaId id,
        @NonNull String name,
        @NonNull Option<String> description,
        @NonNull Price price
) {
}
