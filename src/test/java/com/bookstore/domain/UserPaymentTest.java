package com.bookstore.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class UserPaymentTest {

    @Test
    void testContructor() {
        User user = mock(User.class);
        UserBilling userBilling = mock(UserBilling.class);
        UserPayment userPayment = new UserPayment(1L, "type", "cardName", "cardNumber", 11,
                22, 123, "testName", true, user, userBilling);

        assertAll("UserPayment test",
                () -> assertEquals(userPayment.getId(), 1L),
                () -> assertEquals(userPayment.getType(), "type"),
                () -> assertEquals(userPayment.getCardName(), "cardName"),
                () -> assertEquals(userPayment.getCardNumber(), "cardNumber"),
                () -> assertEquals(userPayment.getExpiryMonth(), 11),
                () -> assertEquals(userPayment.getExpiryYear(), 22),
                () -> assertEquals(userPayment.getCvc(), 123),
                () -> assertEquals(userPayment.getHolderName(), "testName"),
                () -> assertTrue(userPayment.isDefaultPayment(), "default paymentt failed"),
                () -> assertEquals(userPayment.getUser(), user),
                () -> assertEquals(userPayment.getUserBilling(), userBilling));
    }
}