package com.example.tp5integrado.domain.shared;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void naoPermiteValorNegativo() {
        assertThrows(IllegalArgumentException.class, () -> Money.of(new BigDecimal("-1.00")));
    }

    @Test
    void somaComPrecisao() {
        Money a = Money.of(10.00);
        Money b = Money.of(2.50);
        Money r = a.plus(b);
        assertEquals(new BigDecimal("12.50"), r.toBigDecimal());
    }
}
