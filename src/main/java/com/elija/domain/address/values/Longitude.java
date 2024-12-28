package com.elija.domain.address.values;

public record Longitude(double latitude) {
    public static Longitude fromDouble(double latitude) {
        return new Longitude(latitude);
    }

    public double toDouble() {
        return latitude;
    }
}
