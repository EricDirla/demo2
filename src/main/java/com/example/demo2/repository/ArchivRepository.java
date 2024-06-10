package com.example.demo2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo2.domain.Archiv;

@Repository
public interface ArchivRepository extends CrudRepository<Archiv, Integer> {

}