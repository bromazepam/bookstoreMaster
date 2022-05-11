package com.bookstore.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class CartItemTest {

    @Test
    void testConstructor() {
        Book book = mock(Book.class);
        ShoppingCart shoppingCart = mock(ShoppingCart.class);
        ArrayList arrayList = mock(ArrayList.class);
        Order order = mock(Order.class);
        CartItem cartItem = new CartItem(1L, 10, new BigDecimal("152207"),
                book, arrayList, shoppingCart, order);

        assertAll("CartITem test",
                () -> assertEquals(cartItem.getId(), 1L, "CartItem id failed"),
                () -> assertEquals(cartItem.getQty(), 10, "CartItem quantity failed"),
                () -> assertEquals(cartItem.getSubtotal(), new BigDecimal("152207"), "CartItem subtotal failed"),
                () -> assertEquals(cartItem.getBook(), book, "CartItem book failed"),
                () -> assertEquals(cartItem.getBookToCartItemList(), arrayList, "CartItem book list failed"),
                () -> assertEquals(cartItem.getShoppingCart(), shoppingCart, "CartItem shoppingCart failed"),
                () -> assertEquals(cartItem.getOrder(), order, "CartItem order failed"));

    }

}