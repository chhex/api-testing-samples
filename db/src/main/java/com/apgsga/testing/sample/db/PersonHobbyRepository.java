package com.apgsga.testing.sample.db;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonHobbyRepository extends CrudRepository<PersonHobbyTable, Integer> {
    List<PersonHobbyTable> findByPersonId(Integer personId);
    List<PersonHobbyTable> findByHobbyId(Integer hobbyId);
}
