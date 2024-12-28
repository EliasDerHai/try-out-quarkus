package com.elija.domain.person.values;

import io.vavr.control.Option;
import lombok.NonNull;

public record PhoneNumber(@NonNull Option<String> value) {
    public static PhoneNumber fromNullableString(String value) {
        return new PhoneNumber(Option.of(value));
    }
    public static PhoneNumber fromOption(Option<String> value) {
        return new PhoneNumber(value);
    }

    public String toNullableString() {
        return value.getOrNull();
    }

    public Option<String> toOption() {
        return value;
    }
}
