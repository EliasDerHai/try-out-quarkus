package com.elija.domain.person.values;


public record FirstName(String value) {

    public static FirstName fromString(String value) {
        return new FirstName(value);
    }

    public String toString() {
        return value;
    }
}
