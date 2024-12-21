package com.elija.pizza;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class Pizza {
    private final String name;
    private final Optional<String> description;
}
