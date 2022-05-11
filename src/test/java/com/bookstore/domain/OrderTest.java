package com.bookstore.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class OrderTest {

    @Test
    void testConstructor() {
        ArrayList list = mock(ArrayList.class);
        ShippingAddress shippingAddress = mock(ShippingAddress.class);
        BillingAddress billingAddress = mock(BillingAddress.class);
        Payment payment = mock(Payment.class);
        User user = mock(User.class);
        Date date = mock(Date.class);
        Order order = new Order(1L, date, date, "method", "status", new BigDecimal("111"), list,
                shippingAddress, billingAddress, payment, user);

        assertAll("order test",
                () -> assertEquals(order.getId(), 1L),
                () -> assertEquals(order.getShippingDate(), date),
                () -> assertEquals(order.getOrderDate(), date),
                () -> assertEquals(order.getShippingMethod(), "method"),
                () -> assertEquals(order.getOrderStatus(), "status"),
                () -> assertEquals(order.getOrderTotal(), new BigDecimal("111")),
                () -> assertEquals(order.getCartItemList(), list),
                () -> assertEquals(order.getShippingAddress(), shippingAddress),
                () -> assertEquals(order.getBillingAddress(), billingAddress),
                () -> assertEquals(order.getPayment(), payment),
                () -> assertEquals(order.getUser(), user));
    }

}