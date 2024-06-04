package com.example.demo2;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo2.Users;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<Users, Integer> {
    Optional<Users> findByUsername(String username);

}