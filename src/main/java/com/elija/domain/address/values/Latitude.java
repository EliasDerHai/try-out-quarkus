package com.elija.domain.address.values;

public record Latitude(double latitude) {
    public static Latitude fromDouble(double latitude) {
        return new Latitude(latitude);
    }

    public double toDouble() {
        return latitude;
    }
}
