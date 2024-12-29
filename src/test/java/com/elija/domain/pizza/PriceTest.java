package com.elija.domain.pizza;

import com.elija.domain.pizza.values.Price;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PriceTest {

    @Test
    void shouldPrint() {
        var actual = Price.fromEuroCents(999).toString();
        assertEquals("9,99 €", actual);
    }

    @Test
    void shouldCompareByBoxedValue() {
        var a = Price.fromEuroCents(999);
        var b = Price.fromEuros(9.99f);

        assertEquals(a, b);
    }

    @Test
    void shouldHashByBoxedValue() {
        var a = Price.fromEuroCents(999);
        var b = Price.fromEuros(9.99f);

        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void shouldBeRecord() {
        var a = Price.fromEuroCents(999);

        assertInstanceOf(Record.class, a);
    }
}