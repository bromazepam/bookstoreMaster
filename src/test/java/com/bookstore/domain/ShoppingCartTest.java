package com.bookstore.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class ShoppingCartTest {

    @Test
    void testContructor() {
        User user = mock(User.class);
        ArrayList list = mock(ArrayList.class);
        ShoppingCart shoppingCart = new ShoppingCart(1L, new BigDecimal("152207"), list, user);

        assertAll("ShoppingCart test",
                () -> assertEquals(shoppingCart.getId(), 1L),
                () -> assertEquals(shoppingCart.getGrandTotal(), new BigDecimal("152207")),
                () -> assertEquals(shoppingCart.getCartItemList(), list),
                () -> assertEquals(shoppingCart.getUser(), user));
    }
}