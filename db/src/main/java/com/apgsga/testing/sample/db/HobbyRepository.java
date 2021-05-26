package com.apgsga.testing.sample.db;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HobbyRepository extends CrudRepository<HobbyTable, Integer> {

     List<HobbyTable> findByShortName(String shortName);

}
