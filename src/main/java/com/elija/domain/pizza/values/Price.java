package com.elija.domain.pizza.values;

import java.text.NumberFormat;
import java.util.Locale;

public record Price(int inEuroCent) {
    @Override
    public String toString() {
        double inEuro = inEuroCent / 100.0;
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        return formatter.format(inEuro);
    }

    /** eg. 125 -> 12,50€ */
    public static Price fromEuroCents(int euroCents){
        return new Price(euroCents);
    }

    /** eg. 12.5 -> 12,50€ */
    public static Price fromEuros(float euros) {
        return Price.fromEuroCents(Math.round(euros * 100));
    }
}
