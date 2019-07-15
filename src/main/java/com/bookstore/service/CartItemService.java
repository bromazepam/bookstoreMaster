package com.bookstore.service;

import com.bookstore.domain.*;

import java.util.List;

public interface CartItemService {
   List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

    CartItem updateCartItem(CartItem cartItem);

    CartItem addBookToCartItem(Book book, User user, int qty);

    void removeCartItem(CartItem cartItem);

    CartItem findById(Long id);

    CartItem save(CartItem cartItem);

    List<CartItem> findByOrder(Order order);
}
