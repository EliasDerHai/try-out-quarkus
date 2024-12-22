package com.elija.domain.pizza;

import com.elija.domain.atomic.PizzaId;
import com.elija.domain.atomic.Price;
import io.vavr.control.Option;
import lombok.NonNull;

public record Pizza(
        PizzaId id,
        @NonNull String name,
        @NonNull Option<String> description,
        @NonNull Price price
) {
}
