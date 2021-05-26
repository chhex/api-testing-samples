package com.apgsga.testing.sample.db;

import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<PersonTable, Integer> {

}
