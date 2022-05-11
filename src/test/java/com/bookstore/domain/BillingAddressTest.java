package com.bookstore.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class BillingAddressTest {

    @Test
    void groupedAssertions() {
        Order order = mock(Order.class);
        BillingAddress address = new BillingAddress(1L, "addressName", "street1",
                "street2", "city", "state", "country",
                "zip", order);

        assertAll("BillingAddress test",
                () -> assertEquals(address.getId(), 1L, "address id failed"),
                () -> assertEquals(address.getBillingAddressName(), "addressName", "BillingAddress addressName failed"),
                () -> assertEquals(address.getBillingAddressStreet1(), "street1", "BillingAddress street1 failed"),
                () -> assertEquals(address.getBillingAddressStreet2(), "street2", "BillingAddress street2 failed"),
                () -> assertEquals(address.getBillingAddressCity(), "city", "BillingAddress city failed"),
                () -> assertEquals(address.getBillingAddressState(), "state", "BillingAddress state failed"),
                () -> assertEquals(address.getBillingAddressCountry(), "country", "BillingAddress country failed"),
                () -> assertEquals(address.getBillingAddressZipcode(), "zip", "BillingAddress zip failed"),
                () -> assertEquals(address.getOrder(), order, "BillingAddress Order failed"));

    }

}