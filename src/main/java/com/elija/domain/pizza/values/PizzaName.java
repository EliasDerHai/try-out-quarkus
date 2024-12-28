package com.elija.domain.pizza.values;

import lombok.NonNull;

public record PizzaName(@NonNull String value) {
    public static PizzaName fromString(@NonNull String value) {
        return new PizzaName(value);
    }

    public String toString() {
        return value;
    }
}
