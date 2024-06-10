package com.example.demo2;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<Users, Integer> {
    Optional<Users> findByUsername(String username);

}