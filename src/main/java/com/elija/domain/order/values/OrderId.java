package com.elija.domain.order.values;

public record OrderId(int value) {
    public static OrderId fromInt(int value) {
        return new OrderId(value);
    }

    public int toInt() {
        return value;
    }
}
