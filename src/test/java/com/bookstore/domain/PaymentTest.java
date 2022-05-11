package com.bookstore.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class PaymentTest {

    @Test
    void testContructor() {
        Order order = mock(Order.class);
        UserBilling userBilling = mock(UserBilling.class);
        Payment payment = new Payment(1L, "type", "cardName", "cardNumber", 11,
                22, 123, "testName", order, userBilling);

        assertAll("payment test",
                () -> assertEquals(payment.getId(), 1L),
                () -> assertEquals(payment.getType(), "type"),
                () -> assertEquals(payment.getCardName(), "cardName"),
                () -> assertEquals(payment.getCardNumber(), "cardNumber"),
                () -> assertEquals(payment.getExpiryMonth(), 11),
                () -> assertEquals(payment.getExpiryYear(), 22),
                () -> assertEquals(payment.getCvc(), 123),
                () -> assertEquals(payment.getHolderName(), "testName"),
                () -> assertEquals(payment.getOrder(), order),
                () -> assertEquals(payment.getUserBilling(), userBilling));
    }
}