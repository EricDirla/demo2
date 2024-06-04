package com.example.demo2;

import org.springframework.data.repository.CrudRepository;

import com.example.demo2.Archiv;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ArchivRepository extends CrudRepository<Archiv, Integer> {

}