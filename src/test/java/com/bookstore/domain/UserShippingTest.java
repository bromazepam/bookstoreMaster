package com.bookstore.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class UserShippingTest {

    @Test
    void testContructor() {
        User user = mock(User.class);
        UserShipping userShipping = new UserShipping(1L, "addressName", "street1",
                "street2", "city", "state", "country", "zipcode", true, user);

        assertAll("user shipping test",
                () -> assertEquals(userShipping.getId(), 1L),
                () -> assertEquals(userShipping.getUserShippingName(), "addressName"),
                () -> assertEquals(userShipping.getUserShippingStreet1(), "street1"),
                () -> assertEquals(userShipping.getUserShippingStreet2(), "street2"),
                () -> assertEquals(userShipping.getUserShippingCity(), "city"),
                () -> assertEquals(userShipping.getUserShippingState(), "state"),
                () -> assertEquals(userShipping.getUserShippingCountry(), "country"),
                () -> assertEquals(userShipping.getUserShippingZipcode(), "zipcode"),
                () -> assertTrue(userShipping.isUserShippingDefault(), "user shipping default failed"),
                () -> assertEquals(userShipping.getUser(), user));
    }

}