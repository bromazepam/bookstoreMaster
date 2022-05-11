package com.bookstore.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class UserTest {

    @Test
    void testConstructor() {
        ArrayList list = mock(ArrayList.class);
        ShoppingCart shoppingCart = mock(ShoppingCart.class);
        HashSet set = mock(HashSet.class);
        User user = new User(1L, "username", "password", "firstName", "lastName", "mail", "phone",
                true, shoppingCart, list, list, list, set);

        assertAll("user test",
                () -> assertEquals(user.getId(), 1L),
                () -> assertEquals(user.getUsername(), "username"),
                () -> assertEquals(user.getPassword(), "password"),
                () -> assertEquals(user.getFirstName(), "firstName"),
                () -> assertEquals(user.getLastName(), "lastName"),
                () -> assertEquals(user.getEmail(), "mail"),
                () -> assertEquals(user.getPhone(), "phone"),
                () -> assertTrue(user.isEnabled(), "is enabled failed"),
                () -> assertEquals(user.getShoppingCart(), shoppingCart),
                () -> assertEquals(user.getUserShippingList(), list),
                () -> assertEquals(user.getUserPaymentList(), list),
                () -> assertEquals(user.getOrderList(), list),
                () -> assertEquals(user.getUserRoles(), set));
    }
}