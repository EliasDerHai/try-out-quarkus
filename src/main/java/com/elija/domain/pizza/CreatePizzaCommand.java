package com.elija.domain.pizza;

import com.elija.domain.atomic.Price;
import io.vavr.control.Option;
import lombok.NonNull;

public record CreatePizzaCommand(
        @NonNull String name,
        @NonNull Option<String> description,
        @NonNull Price price
) {
}
