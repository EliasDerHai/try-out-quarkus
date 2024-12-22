package com.elija.domain.pizza;

import com.elija.domain.atomic.Price;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceTest {

    @Test
    void shouldPrint() {
        var actual = Price.fromEuroCents(999).toString();
        assertEquals("9,99 €", actual);
    }
}