package com.elija.domain.pizza;

import io.vavr.control.Option;
import lombok.NonNull;

public record PizzaDescription(@NonNull Option<String> value) {
    public static PizzaDescription fromNullableString(String value) {
        return new PizzaDescription(Option.of(value));
    }
    public static PizzaDescription fromOption(Option<String> value) {
        return new PizzaDescription(value);
    }

    public String toNullableString() {
        return value.getOrNull();
    }

    public Option<String> toOption() {
        return value;
    }
}
