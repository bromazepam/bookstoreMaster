package com.bookstore.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class BookToCartItemTest {
    @Test
    void testConstructor() {
        Book book = mock(Book.class);
        CartItem cartItem = mock(CartItem.class);
        BookToCartItem item = new BookToCartItem(1L, book, cartItem);

        assertAll("BookToCartItem test",
                () -> assertEquals(item.getId(), 1L, "BookToCartItem id failed"),
                () -> assertEquals(item.getBook(), book, "BookToCartItem book failed"),
                () -> assertEquals(item.getCartItem(), cartItem, "BookToCartItem cartItem failed"));
    }
}

