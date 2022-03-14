package com.bookstore.service;

import com.bookstore.domain.UserPayment;

import java.util.Optional;

public interface UserPaymentService {
    UserPayment findById(Long id);

    void removeById(Long id);
}
