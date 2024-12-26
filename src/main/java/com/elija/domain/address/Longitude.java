package com.elija.domain.address;

public record Longitude(double latitude) {
    public static Longitude fromPrimitive(double latitude) {
        return new Longitude(latitude);
    }

    public double toPrimitive() {
        return latitude;
    }
}
