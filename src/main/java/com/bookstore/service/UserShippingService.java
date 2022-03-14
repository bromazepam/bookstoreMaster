package com.bookstore.service;

import com.bookstore.domain.UserShipping;

import java.util.Optional;

public interface UserShippingService {
    UserShipping findById(Long id);

    void removeById(Long id);
}
