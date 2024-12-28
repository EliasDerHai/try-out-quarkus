package com.elija.domain.pizza;

import lombok.NonNull;

public record Pizza(
        @NonNull PizzaId id,
        @NonNull PizzaName name,
        @NonNull PizzaDescription description,
        @NonNull Price price
) {
}
