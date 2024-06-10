package com.example.demo2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo2.domain.Tasks;

@Repository
public interface TaskRepository extends CrudRepository<Tasks, Integer> {

}