package com.test.proj.services;

import com.test.proj.entities.User;

import java.util.Optional;

public interface UserService{

    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);

    void deleteUser(Long id);
}
