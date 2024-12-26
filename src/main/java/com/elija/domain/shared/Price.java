package com.elija.domain.shared;

import java.text.NumberFormat;
import java.util.Locale;

public record Price(int inEuroCent) {
    @Override
    public String toString() {
        double inEuro = inEuroCent / 100.0;
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        return formatter.format(inEuro);
    }

    public static Price fromEuroCents(int euroCents){
        return new Price(euroCents);
    }
}
