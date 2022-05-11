package com.bookstore.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class ShippingAddressTest {

    @Test
    void testContructor() {
        Order order = mock(Order.class);
        ShippingAddress shippingAddress = new ShippingAddress(1L, "addressName", "street1",
                "street2", "city", "state", "country", "zipcode", order);

        assertAll("ShippingAddress test",
                () -> assertEquals(shippingAddress.getId(), 1L),
                () -> assertEquals(shippingAddress.getShippingAddressName(), "addressName"),
                () -> assertEquals(shippingAddress.getShippingAddressStreet1(), "street1"),
                () -> assertEquals(shippingAddress.getShippingAddressStreet2(), "street2"),
                () -> assertEquals(shippingAddress.getShippingAddressCity(), "city"),
                () -> assertEquals(shippingAddress.getShippingAddressState(), "state"),
                () -> assertEquals(shippingAddress.getShippingAddressCountry(), "country"),
                () -> assertEquals(shippingAddress.getShippingAddressZipcode(), "zipcode"),
                () -> assertEquals(shippingAddress.getOrder(), order));
    }

}