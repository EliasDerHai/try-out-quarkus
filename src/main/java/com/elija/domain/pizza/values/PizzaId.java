package com.elija.domain.pizza.values;

public record PizzaId(int id) {
    public static PizzaId fromInt(int id) {
        return new PizzaId(id);
    }

    public int toInt() {
        return id;
    }
}
