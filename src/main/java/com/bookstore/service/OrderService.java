package com.bookstore.service;

import com.bookstore.domain.*;

import java.util.Optional;

public interface OrderService {
    Order createOrder(ShoppingCart shoppingCart,
                      ShippingAddress shippingAddress,
                      BillingAddress billingAddress,
                      Payment payment,
                      String shippingMethod,
                      User user);

    Order findById(Long id);
}
