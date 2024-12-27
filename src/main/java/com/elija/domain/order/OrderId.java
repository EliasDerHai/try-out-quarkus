package com.elija.domain.order;

public record OrderId(int id) {
    public static OrderId fromInt(int id) {
        return new OrderId(id);
    }

    public int toInt() {
        return id;
    }
}
