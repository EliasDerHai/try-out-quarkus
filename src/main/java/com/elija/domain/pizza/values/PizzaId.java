package com.elija.domain.pizza.values;

public record PizzaId(int value) {
    public static PizzaId fromInt(int value) {
        return new PizzaId(value);
    }

    public int toInt() {
        return value;
    }
}
