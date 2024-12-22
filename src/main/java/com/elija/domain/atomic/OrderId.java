package com.elija.domain.atomic;

public record OrderId(int id) {
    public static OrderId fromPrimitive(int id) {
        return new OrderId(id);
    }

    public int toPrimitive() {
        return id;
    }
}
