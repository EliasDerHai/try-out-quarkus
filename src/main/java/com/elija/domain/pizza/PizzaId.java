package com.elija.domain.pizza;

public record PizzaId(int id) {
    public static PizzaId fromInt(int id) {
        return new PizzaId(id);
    }

    public int toInt() {
        return id;
    }
}
