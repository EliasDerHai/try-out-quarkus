package com.elija.domain.pizza;

import io.vavr.control.Option;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Pizza {
    private final String name;
    private final Option<String> description;
}
