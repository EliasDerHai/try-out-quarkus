package com.elija.domain.address;

public record Latitude(double latitude) {
    public static Latitude fromPrimitive(double latitude) {
        return new Latitude(latitude);
    }

    public double toPrimitive() {
        return latitude;
    }
}
