package com.elija.domain.person.values;


public record LastName(String value) {

    public static LastName fromString(String value) {
        return new LastName(value);
    }

    public String toString() {
        return value;
    }
}
