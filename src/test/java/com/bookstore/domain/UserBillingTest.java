package com.bookstore.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class UserBillingTest {

    @Test
    void testContructor() {
        UserPayment userPayment = mock(UserPayment.class);
        UserBilling userBilling = new UserBilling(1L, "addressName", "street1",
                "street2", "city", "state", "country", "zipcode", userPayment);

        assertAll("UserBilling test",
                () -> assertEquals(userBilling.getId(), 1L),
                () -> assertEquals(userBilling.getUserBillingName(), "addressName"),
                () -> assertEquals(userBilling.getUserBillingStreet1(), "street1"),
                () -> assertEquals(userBilling.getUserBillingStreet2(), "street2"),
                () -> assertEquals(userBilling.getUserBillingCity(), "city"),
                () -> assertEquals(userBilling.getUserBillingState(), "state"),
                () -> assertEquals(userBilling.getUserBillingCountry(), "country"),
                () -> assertEquals(userBilling.getUserBillingZipcode(), "zipcode"),
                () -> assertEquals(userBilling.getUserPayment(), userPayment));
    }

}