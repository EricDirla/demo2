package com.example.demo2;

import org.springframework.data.repository.CrudRepository;

import com.example.demo2.Tasks;

public interface TaskRepository extends CrudRepository<Tasks, Integer> {

}