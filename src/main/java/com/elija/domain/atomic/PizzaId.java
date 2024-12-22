package com.elija.domain.atomic;

public record PizzaId(int id) {
    public static PizzaId fromPrimitive(int id) {
        return new PizzaId(id);
    }

    public int toPrimitive() {
        return id;
    }
}
