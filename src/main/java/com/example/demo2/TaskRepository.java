package com.example.demo2;

import org.springframework.data.repository.CrudRepository;

import com.example.demo2.Tasks;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface TaskRepository extends CrudRepository<Tasks, Integer> {

}