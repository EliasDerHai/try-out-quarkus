package com.elija.domain.pizza;

import com.elija.domain.pizza.values.Price;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceTest {

    @Test
    void shouldPrint() {
        var actual = Price.fromEuroCents(999).toString();
        assertEquals("9,99 €", actual);
    }

    @Test
    void shouldCompareByBoxedValue() {
        var a = Price.fromEuroCents(999);
        var b = Price.fromEuroCents(999);

        assertEquals(a, b);
    }
}