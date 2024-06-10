package com.example.demo2;

import org.springframework.data.repository.CrudRepository;


public interface TaskRepository extends CrudRepository<Tasks, Integer> {

}