package com.elija.domain.pizza;

public record PizzaId(int id) {
    public static PizzaId fromPrimitive(int id) {
        return new PizzaId(id);
    }

    public int toPrimitive() {
        return id;
    }
}
